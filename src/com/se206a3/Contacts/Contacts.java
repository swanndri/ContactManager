package com.se206a3.Contacts;

import java.util.ArrayList;
import java.util.List;


public class Contacts {

	/**
	 * An array of sample contact objects.
	 */
	public static List<Contact> ITEMS = new ArrayList<Contact>();

	/**
	 * @return The names (FName+LName) of each contact in the list
	 */
	public static List<String> getNames(){
		List<String> names = new ArrayList<String>();
		
		for(Contact i:Contacts.ITEMS){
			names.add(i.name.getFirstName()+ " " + i.name.getLastName());
		}
		return names;
		
	}

	/**
	 * A Contact object - represents one contact.
	 */
	public static class Contact {

		public Name name;
		public String company;

		public List<PhNumber> numbers;
		public List<Email> emails;
		public List<Address> address;

		public Contact(Name name, String company,List<PhNumber> numbers, List<Email> emails, List<Address> address) {
			this.name = name;
			this.company=company;
			this.numbers = numbers;
			this.emails = emails;
			this.address = address;
		}

	}
	
	public static class Name{
		private String FirstName;
		private String LastName;

		
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
	public static class Address{
		private String type;
		private String street1;
		private String street2;
		private String suburb;
		private String city;
		private String postCode;
		private String country;

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
	

}

