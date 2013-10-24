package com.se206a3.sorting;

import java.util.Comparator;

import com.se206a3.Contacts.Contact;
/**  
 * Class for sorting by last name.
 */
public class lastNameSort implements Comparator<Contact>{

	@Override
	/**
	 * Method to compare two @Contact objects, for the sorting method.
	 * @param contact1 - Contact 1
	 * @param contact2 - Contact 2
	 * @return result - 0 if equal, - if contact1<contact2, + if contact1>contact2
	 */
	public int compare(Contact contact1, Contact contact2) {
		// TODO Auto-generated method stub

		int result = String.CASE_INSENSITIVE_ORDER.compare(contact1.getName().getLastName(), contact2.getName().getLastName()); // Compare surnames
		return result;
	}
}
