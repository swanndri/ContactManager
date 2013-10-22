package com.se206a3.Contacts;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * A Contact object - represents one contact.
 */
public class Contact implements Comparable {

	private Long Id;
	private Name name;
	private String company;
	private String imagePath;
	
	public List<PhNumber> numbers = new ArrayList<PhNumber>();
	public List<Email> emails = new ArrayList<Email>();
	public List<Address> address = new ArrayList<Address>();
	public static Contact toDisplay;

	public Contact(){}


	public Contact(Name name, String company, String imagePath, List<PhNumber> numbers, List<Email> emails, List<Address> address) {
		this.setName(name);
		this.setCompany(company);
		this.setImagePath(imagePath);
		
		this.numbers = numbers;
		this.emails = emails;
		this.address = address;
	}


	public Long getId() {
		return Id;
	}


	public void setId(Long id) {
		Id = id;
	}


	public Name getName() {
		return name;
	}


	public void setName(Name name) {
		this.name = name;
	}


	public String getCompany() {
		return company;
	}


	public void setCompany(String company) {
		this.company = company;
	}



	public static class Name {
		private String FirstName;
		private String LastName;

		public Name(){}
		public Name(String fName, String lName){
			this.setFirstName(fName);
			this.setLastName(lName);
		}


		public String getFirstName() {
			return FirstName;
		}


		public void setFirstName(String firstName) {
			FirstName = firstName;
		}


		public String getLastName() {
			return LastName;
		}


		public void setLastName(String lastName) {
			LastName = lastName;
		}
	}

	public static class PhNumber {
		private String type;
		private String number;

		public PhNumber(){}

		public PhNumber(String type, String number) {
			this.setType(type);
			this.setNumber(number);
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getNumber() {
			return number;
		}

		public void setNumber(String number) {
			this.number = number;
		}
	}


	public static class Email {
		private String type;
		private String email;

		public Email(){}

		public Email(String type, String email) {
			this.setType(type);
			this.setEmail(email);
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}




	}
	public static class Address {
		private String type;
		private String street1;
		private String street2;
		private String suburb;
		private String city;
		private String postCode;
		private String country;

		public Address(){}

		public Address(String type,String street1, String street2,String suburb,String city, String postCode, String country){
			this.setType(type);
			this.setStreet1(street1);
			this.setStreet2(street2);
			this.setSuburb(suburb);
			this.setCity(city);
			this.setPostCode(postCode);
			this.setCountry(country);
		}

		public String getStreet1() {
			return street1;
		}

		public void setStreet1(String street1) {
			this.street1 = street1;
		}

		public String getStreet2() {
			return street2;
		}

		public void setStreet2(String street2) {
			this.street2 = street2;
		}

		public String getSuburb() {
			return suburb;
		}

		public void setSuburb(String suburb) {
			this.suburb = suburb;
		}

		public String getCity() {
			return city;
		}

		public void setCity(String city) {
			this.city = city;
		}

		public String getPostCode() {
			return postCode;
		}

		public void setPostCode(String postCode) {
			this.postCode = postCode;
		}

		public String getCountry() {
			return country;
		}

		public void setCountry(String country) {
			this.country = country;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

	}




	@Override
	public int compareTo(Object arg0) {
		// TODO Auto-generated method stub
		Contact contact = (Contact)arg0;
		int i = 0;
		if(this.getName().getFirstName().compareTo(contact.getName().getFirstName())==0){
			if(this.getName().getLastName().compareTo(contact.getName().getLastName())==0){
				i = 0;
			}else if(this.getName().getLastName().compareTo(contact.getName().getLastName())>0){
				i = 1;
			}else if(this.getName().getLastName().compareTo(contact.getName().getLastName())<0){
				i = -1;
			}
		}else if(this.getName().getFirstName().compareTo(contact.getName().getFirstName())>0){
			i = 1;

		}else if(this.getName().getFirstName().compareTo(contact.getName().getFirstName())<0){
			i = -1;
		}
		return i;
	}


	public String getImagePath() {
		return imagePath;
	}


	public void setImagePath(String selectedImagePath) {
		this.imagePath = selectedImagePath;
	}
}


