package com.se206a3.sorting;

import java.util.Comparator;

import com.se206a3.Contacts.Contact;

/**  
 * Class for sorting by first name then last name.
 */
public class firstLastNameSort implements Comparator<Contact> {

	/**
	 * Method to compare two @Contact objects, for the sorting method.
	 * @param contact1 - Contact 1
	 * @param contact2 - Contact 2
	 * @return result2 - 0 if equal, - if contact1<contact2, + if contact1>contact2
	 */
	public int compare(Contact contact1, Contact contact2) {
		// TODO Auto-generated method stub
		int result = String.CASE_INSENSITIVE_ORDER.compare(contact1.getName().getFirstName(), contact2.getName().getFirstName()); // Compare the first names
		
		if(result==0){ // If names are the same
			int result2 = String.CASE_INSENSITIVE_ORDER.compare(contact1.getName().getLastName(), contact2.getName().getLastName()); // Compare surnames
			return result2;
		}else{
			return result;
		}
	}
}
