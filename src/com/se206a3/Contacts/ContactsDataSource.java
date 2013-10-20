package com.se206a3.Contacts;

import java.util.ArrayList;
import java.util.List;

import com.se206a3.Contacts.Contact.Address;
import com.se206a3.Contacts.Contact.Email;
import com.se206a3.Contacts.Contact.Name;
import com.se206a3.Contacts.Contact.PhNumber;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class ContactsDataSource {

	// Database fields
	private SQLiteDatabase database;
	private ContactDataBaseHelper dbHelper;
	private String[] allColumns = { ContactDataBaseHelper.COLUMN_ID,
			ContactDataBaseHelper.COLUMN_FIRSTNAME,
			ContactDataBaseHelper.COLUMN_LASTNAME,
			ContactDataBaseHelper.COLUMN_COMPANY,
			ContactDataBaseHelper.COLUMN_PHONENUMBERS,
			ContactDataBaseHelper.COLUMN_EMAILS,
			ContactDataBaseHelper.COLUMN_ADDRESSES};

	public ContactsDataSource(Context context) {
		dbHelper = new ContactDataBaseHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	public Contact createContact(Contact contact) {
		ContentValues values = new ContentValues();
		values.put(ContactDataBaseHelper.COLUMN_FIRSTNAME, contact.getName().getFirstName());
		values.put(ContactDataBaseHelper.COLUMN_LASTNAME, contact.getName().getLastName());
		values.put(ContactDataBaseHelper.COLUMN_COMPANY, contact.getCompany());
		StringBuilder PhoneNumbers_String = new StringBuilder();
		StringBuilder Emails_String = new StringBuilder();
		StringBuilder Address_String = new StringBuilder();

		for (PhNumber number: contact.numbers){
			PhoneNumbers_String.append(number.getType()+","+number.getNumber()+"|");
		}
		values.put(ContactDataBaseHelper.COLUMN_PHONENUMBERS,PhoneNumbers_String.toString());


		for (Email email: contact.emails){
			Emails_String.append(email.getType()+","+email.getEmail()+"|");
		}
		values.put(ContactDataBaseHelper.COLUMN_EMAILS, Emails_String.toString());


		for (Address address: contact.address){

			Address_String.append(address.getType()
					+ "," + address.getStreet1()
					+ "," + address.getStreet2()
					+ "," + address.getSuburb()
					+ "," + address.getCity()
					+ "," + address.getCountry()
					+ "," + address.getPostCode()
					+ "|");
		}
		values.put(ContactDataBaseHelper.COLUMN_ADDRESSES, Address_String.toString());


		long insertId = database.insert(ContactDataBaseHelper.TABLE_CONTACTS, null,values);
		Cursor cursor = database.query(ContactDataBaseHelper.TABLE_CONTACTS,
				allColumns, ContactDataBaseHelper.COLUMN_ID + " = " + insertId, null,
				null, null, null);
		cursor.moveToFirst();
		Contact newComment = cursorToContact(cursor);
		cursor.close();
		return newComment;
	}

	public void deleteContact(Contact contact) {
		long id = contact.getId();
		String name = contact.getName().getFirstName();
		System.out.println("Contact deleted with name: " + name);
		database.delete(ContactDataBaseHelper.TABLE_CONTACTS, ContactDataBaseHelper.COLUMN_ID
				+ " = " + id, null);
	}

	public List<Contact> getAllContacts() {
		List<Contact> contacts = new ArrayList<Contact>();

		Cursor cursor = database.query(ContactDataBaseHelper.TABLE_CONTACTS,
				allColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Contact contact = cursorToContact(cursor);
			contacts.add(contact);
			cursor.moveToNext();
		}
		// make sure to close the cursor
		cursor.close();
		return contacts;
	}

	private Contact cursorToContact(Cursor cursor) {
		Contact contact = new Contact(); //New instance of contact to be filled and returned
		contact.setId(cursor.getLong(0));  //Set Id of contact from first column
		contact.setName(new Name(cursor.getString(1),cursor.getString(2))); //Set name with Name object
		contact.setCompany(cursor.getString(3)); //Set company		

		String phnNumbers = cursor.getString(4); //Get string of phnNumbers
		if (phnNumbers.length()!=0){
			String[] indvPhnNumbers = phnNumbers.split("\\|"); 
			for(String ph: indvPhnNumbers){
				String[] phs = ph.split(",");
				contact.numbers.add(new PhNumber(phs[0],phs[1]));
			}
		}

		String emails = cursor.getString(5);
		if(emails.length()!=0){
			String[] indvEmails = emails.split("\\|");
			for (String em: indvEmails){
				String[] ems = em.split(",");
				contact.emails.add(new Email(ems[0],ems[1]));
			}
		}

		String address = cursor.getString(6);
		if(address.length()!=0){
			String[] indvAddress = address.split("\\|");
			for (String ad: indvAddress){
				String[] ads = ad.split(",");
				contact.address.add(new Address(ads[0],
						ads[1],
						ads[2],
						ads[3],
						ads[4],
						ads[5],
						ads[6]));
			}
		}
		return contact;
	}

	public void deleteAll(){

		database.delete(ContactDataBaseHelper.TABLE_CONTACTS, ContactDataBaseHelper.COLUMN_ID, null);
	}
} 