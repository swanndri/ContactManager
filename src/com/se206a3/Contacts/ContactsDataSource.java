package com.se206a3.Contacts;

import java.util.ArrayList;
import java.util.List;

import com.se206a3.Contacts.Contact.Address;
import com.se206a3.Contacts.Contact.Email;
import com.se206a3.Contacts.Contact.Name;
import com.se206a3.Contacts.Contact.PhoneNumber;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Represents the database for the application
 * Contains the database helper.
 */
public class ContactsDataSource {


	/** 
	 * The readable/writable instance of the database
	 */
	private SQLiteDatabase _database;
	
	/** 
	 * The database helper, that defines the structure of the database 
	 */
	private ContactDataBaseHelper _dbHelper;
	
	/** 
	 * String array that contains all  the column names from the database:
	 * 		
  	 *		COLUMN_ID = "_id"
  	 *		COLUMN_FIRSTNAME = "first_name"
  	 *		COLUMN_LASTNAME = "last_name"
  	 *		COLUMN_COMPANY = "company"
 	 *		COLUMN_IMAGE = "image_path"
	 *		COLUMN_DATEOFBIRTH = "date_of_birth"
 	 *		COLUMN_PHONENUMBERS = "phone_numbers"
 	 *		COLUMN_EMAILS = "emails"
	 *		COLUMN_ADDRESSES = "addesses"
	 * 		
	 */
	private String[] _allColumns = { ContactDataBaseHelper.COLUMN_ID,
			ContactDataBaseHelper.COLUMN_FIRSTNAME,
			ContactDataBaseHelper.COLUMN_LASTNAME,
			ContactDataBaseHelper.COLUMN_COMPANY,
			ContactDataBaseHelper.COLUMN_IMAGE,
			ContactDataBaseHelper.COLUMN_DATEOFBIRTH,
			ContactDataBaseHelper.COLUMN_PHONENUMBERS,
			ContactDataBaseHelper.COLUMN_EMAILS,
			ContactDataBaseHelper.COLUMN_ADDRESSES };

	/**
	 * @Contact object that represents the most recently accessed contact
	 */
	public Contact mostRecentContact;

	/**
	 * Creates a new database helper of type @ContactDataBaseHelper
	 * @param context
	 */
	public ContactsDataSource(Context context) {
		_dbHelper = new ContactDataBaseHelper(context);
	}
	
	/**
	 * Opens the database for read/write
	 * @throws SQLException
	 */
	public void open() throws SQLException {
		_database = _dbHelper.getWritableDatabase();
	}

	/**
	 * Closes the database
	 */
	public void close() {
		_dbHelper.close();
	}

	/**
	 * Extracts data from the parsed contact.
	 * Puts this data into the database as a new record.
	 * Then creates a new contact object with the unique id and returns it.
	 * @param contact The contact for which to make the new record.
	 * @return contact The newly created contact with a unique Id.
	 */
	public Contact createContact(Contact contact) {

		ContentValues values = new ContentValues(); // Create new values object for parseing into database
		
		// Add contacts first name to values
		values.put(ContactDataBaseHelper.COLUMN_FIRSTNAME, contact.getName()
				.getFirstName());
		
		// Add contacts last name to values
		values.put(ContactDataBaseHelper.COLUMN_LASTNAME, contact.getName()
				.getLastName());
		
		values.put(ContactDataBaseHelper.COLUMN_COMPANY, contact.getCompany()); // Add contacts company to values
		values.put(ContactDataBaseHelper.COLUMN_IMAGE, contact.getImagePath()); // Add image path for profile picture to values
		values.put(ContactDataBaseHelper.COLUMN_DATEOFBIRTH, contact.getDateOfBirth()); // Add date of birth to values

		// Create string builders for: Phone numbers, Emails, Addresses
		StringBuilder PhoneNumbers_String = new StringBuilder();
		StringBuilder Emails_String = new StringBuilder();
		StringBuilder Address_String = new StringBuilder();
		
		/* For all phone numbers, create string to store:
		 * 		"type,number|type,number|...etc
		 */ 
		for (PhoneNumber number : contact.phoneNumber_list) {
			PhoneNumbers_String.append(number.getType() + ","
					+ number.getNumber() + "|");
		}
		
		// Add phone numbers string to values
		values.put(ContactDataBaseHelper.COLUMN_PHONENUMBERS,
				PhoneNumbers_String.toString());

		 /* For all emails create string to store:
		 * 		"type,email|type,email|...etc
		 */ 
		for (Email email : contact.email_list) {
			Emails_String
			.append(email.getType() + "," + email.getEmail() + "|");
		}
		
		// Add emails string to values
		values.put(ContactDataBaseHelper.COLUMN_EMAILS,
				Emails_String.toString());

		 /* For all addresses create string to store:
		 * 		"type,street1,street2,suburb,city,postcode,country|...etc"
		 */
		for (Address address : contact.address_list) {

			Address_String.append(address.getType() + ","
					+ address.getStreet1() + "," + address.getStreet2() + ","
					+ address.getSuburb() + "," + address.getCity() + ","
					+ address.getPostCode() + "," + address.getCountry() + "|");
		}
		
		// Add addresses string to values
		values.put(ContactDataBaseHelper.COLUMN_ADDRESSES,
				Address_String.toString());

		// Create unique id for each contact
		long insertId = _database.insert(ContactDataBaseHelper.TABLE_CONTACTS,
				null, values);
		
		// Get data for contact
		Cursor cursor = _database.query(ContactDataBaseHelper.TABLE_CONTACTS,
				_allColumns,
				ContactDataBaseHelper.COLUMN_ID + " = " + insertId, null, null,
				null, null);

		// Create new instance of contact with the data
		cursor.moveToFirst();
		Contact newComment = cursorToContact(cursor);
		mostRecentContact = newComment; // Save as most recent contact to/from DB
		cursor.close(); // Dont forget to close database
		
		//Return contact
		return newComment;
	}

	/**
	 * Deletes the contact parsed in from the data base.
	 * @param contact The contact to be deleted.
	 */
	public void deleteContact(Contact contact) {
		long id = contact.getId(); // Get unique id of parsed contact
		String name = contact.getName().toString(); // Get name for deletion message
		System.out.println("Contact deleted with name: " + name); // Show deletion message, as prompt/log
		_database.delete(ContactDataBaseHelper.TABLE_CONTACTS,
				ContactDataBaseHelper.COLUMN_ID + " = " + id, null); // Delete contact from database
	}

	/**
	 * Creates and returns a list of all contacts in the database.
	 * @return The list of contacts
	 */
	public List<Contact> getAllContacts() {
		List<Contact> contacts = new ArrayList<Contact>(); // List to store produced contacts

		Cursor cursor = _database.query(ContactDataBaseHelper.TABLE_CONTACTS,
				_allColumns, null, null, null, null, null); // Database query that gets ALL the information from the DB

		// Move through the data, one instance of contact at a time - producing a new contact object at each step.
		cursor.moveToFirst(); // Start at first contact
		while (!cursor.isAfterLast()) {
			Contact contact = cursorToContact(cursor); // Make contact object
			contacts.add(contact); // Add to contacts list
			cursor.moveToNext(); // Move to next contact
		}
		// make sure to close the cursor
		cursor.close();
		
		//Return list of contacts
		return contacts;
	}

	/**
	 * Creates a contact object from the database.
	 * Used in getAllContacts
	 * @param cursor
	 * @return
	 */
	private Contact cursorToContact(Cursor cursor) {

		Contact contact = new Contact(); // New instance of contact to be filled and returned
		contact.setId(cursor.getLong(0)); // Set Id of contact from first column


		contact.setName(new Name(cursor.getString(1), cursor.getString(2))); // Set name with name object
		contact.setCompany(cursor.getString(3)); // Set company
		contact.setImagePath(cursor.getString(4)); // Set file path for image
		contact.setDateOfBirth(cursor.getString(5)); //Set date of birth

		String phnNumbers = cursor.getString(6); // Get string of phone numbers
		if (phnNumbers.length() != 0) {	// Check for no phone numbers entered

			/* Separate out each phone number [String type + String number]
			 * Have to be very careful here, as slack implementation - if user enters | or , then most likely throws null pointer
			 */
			String[] indvPhnNumbers = phnNumbers.split("\\|"); 

			for (String ph : indvPhnNumbers) {
				String[] phs = ph.split(","); // Split each number into component parts [String type, String number]
				contact.phoneNumber_list.add(new PhoneNumber(phs[0], phs[1])); // Add new PhoneNumber object to the contacts phoneNumber_list
			}
		}

		String emails = cursor.getString(7); // Get string of Emails
		if (emails.length() != 0) {

			/* Separate out each email [String type + String email]
			 * Have to be very careful here, as slack implementation - if user enters | or , then most likely throws null pointer
			 */
			String[] indvEmails = emails.split("\\|");

			for (String em : indvEmails) {
				String[] ems = em.split(","); // Split each email into component parts [String type, String email]
				contact.email_list.add(new Email(ems[0], ems[1])); // Add new Email object to the contacts email_list
			}
		}

		String address = cursor.getString(8); // Get string of Emails
		if (address.length() != 0) {

			/* Separate out each email [String type + String street1 + String street2 + String suburb + String city + String postCode + String Country]
			 * Have to be very careful here, as slack implementation - if user enters | or , then most likely throws null pointer
			 */
			String[] indvAddress = address.split("\\|");

			for (String ad : indvAddress) {
				String[] ads = ad.split(","); // Split each email into component parts [String type, String street1, String street2, String suburb, String city, String postCode, String Country]
				contact.address_list.add(new Address(ads[0], ads[1], ads[2],
						ads[3], ads[4], ads[5], ads[6])); // Add new Email object to the contacts email_list
			}
		}
		
		return contact; // Return contact with all data
	}

	/**
	 * Deletes all contacts from the database.
	 */
	public void deleteAll() {
		_database.delete(ContactDataBaseHelper.TABLE_CONTACTS,
				ContactDataBaseHelper.COLUMN_ID, null);
	}
}