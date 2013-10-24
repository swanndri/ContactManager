package com.se206a3.Contacts;
/**
 * 
 * @author Ben Brown
 *
 */
import java.util.ArrayList;
import java.util.List;

/**
 * A Contact object - represents one contact.
 * 		
 */
public class Contact {

	/**
	 * The contact to be displayed in detail/edited
	 */
	public static Contact static_contactToDisplay;

	/**
	 * The id of the contact, a @Long
	 */
	private Long _id;
	
	/**
	 * The name of the contact, a @Name object.
	 */
	private Name _name;
	
	/**
	 * The company of the contact, a @String object.
	 */
	private String _company;
	
	/**
	 * The file path for the contacts profile picture, a @String object.
	 */
	private String _imagePath;
	
	/**
	 * The date of birth of the contact, a @String object.
	 */
	private String _dateOfBirth;
	
	/**
	 * The list of phone numbers for the contact.
	 * Consists of @PhoneNumber objects
	 */
	public List<PhoneNumber> phoneNumber_list = new ArrayList<PhoneNumber>();
	
	/**
	 * The list of emails for the contact.
	 * Consists of @Email objects.
	 */
	public List<Email> email_list = new ArrayList<Email>();
	
	/**
	 * The list of addresses for the contact.
	 * Consists of @Address objects.
	 */
	public List<Address> address_list = new ArrayList<Address>();

	/** 
	 * The default constructor.
	 */
	public Contact() {
	}

	/**
	 * The constructor for a Contact object, excludes id as this is determined by @ContactDataBaseHelper.
	 * @param name Name of the contact
	 * @param company Company of the contact
	 * @param imagePath File path for profile picture of contact
	 * @param dateOfBirth Date of birth of the contact
	 * @param numbers List of phone numbers for the contact
	 * @param emails List of emails for the contact
	 * @param address List of addresses for the contact
	 */
	public Contact(Name name, String company, String imagePath, String dateOfBirth,
			List<PhoneNumber> numbers, List<Email> emails, List<Address> address) {

		this.setName(name);
		this.setCompany(company);
		this.setImagePath(imagePath);
		this.setDateOfBirth(dateOfBirth);

		this.phoneNumber_list = numbers;
		this.email_list = emails;
		this.address_list = address;
	}

	public Long getId() {
		return _id;
	}

	public void setId(Long id) {
		_id = id;
	}

	public Name getName() {
		return _name;
	}

	public void setName(Name name) {
		this._name = name;
	}

	public String getCompany() {
		return _company;
	}

	public void setCompany(String company) {
		this._company = company;
	}


	public static class Name {
		private String _firstName;
		private String _lastName;

		public Name() {
		}

		public Name(String fName, String lName) {
			this.setFirstName(fName);
			this.setLastName(lName);
		}

		public String getFirstName() {
			return _firstName;
		}

		public void setFirstName(String firstName) {
			_firstName = firstName;
		}

		public String getLastName() {
			return _lastName;
		}

		public void setLastName(String lastName) {
			_lastName = lastName;
		}

		@Override
		public String toString() {
			return _firstName + " " + _lastName;
		}
	}

	public static class PhoneNumber {
		private String _type;
		private String _number;

		public PhoneNumber() {
		}

		public PhoneNumber(String type, String number) {
			this.setType(type);
			this.setNumber(number);
		}

		public String getType() {
			return _type;
		}

		public void setType(String type) {
			this._type = type;
		}

		public String getNumber() {
			return _number;
		}

		public void setNumber(String number) {
			this._number = number;
		}
	}

	public static class Email {
		private String _type;
		private String _email;

		public Email() {
		}

		public Email(String type, String email) {
			this.setType(type);
			this.setEmail(email);
		}

		public String getType() {
			return _type;
		}

		public void setType(String type) {
			this._type = type;
		}

		public String getEmail() {
			return _email;
		}

		public void setEmail(String email) {
			this._email = email;
		}

	}

	public static class Address {
		private String _type;
		private String _street1;
		private String _street2;
		private String _suburb;
		private String _city;
		private String _postCode;
		private String _country;

		public Address() {
		}

		public Address(String type, String street1, String street2,
				String suburb, String city, String postCode, String country) {
			this.setType(type);
			this.setStreet1(street1);
			this.setStreet2(street2);
			this.setSuburb(suburb);
			this.setCity(city);
			this.setPostCode(postCode);
			this.setCountry(country);
		}

		public String getStreet1() {
			return _street1;
		}

		public void setStreet1(String street1) {
			this._street1 = street1;
		}

		public String getStreet2() {
			return _street2;
		}

		public void setStreet2(String street2) {
			this._street2 = street2;
		}

		public String getSuburb() {
			return _suburb;
		}

		public void setSuburb(String suburb) {
			this._suburb = suburb;
		}

		public String getCity() {
			return _city;
		}

		public void setCity(String city) {
			this._city = city;
		}

		public String getPostCode() {
			return _postCode;
		}

		public void setPostCode(String postCode) {
			this._postCode = postCode;
		}

		public String getCountry() {
			return _country;
		}

		public void setCountry(String country) {
			this._country = country;
		}

		public String getType() {
			return _type;
		}

		public void setType(String type) {
			this._type = type;
		}

	}

	public String getImagePath() {
		return _imagePath;
	}

	public void setImagePath(String selectedImagePath) {
		this._imagePath = selectedImagePath;
	}

	public String getDateOfBirth() {
		return _dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		_dateOfBirth = dateOfBirth;
	}
}
