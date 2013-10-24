package com.se206a3.sorting;

import java.util.Comparator;

import com.se206a3.Contacts.Contact;
import com.se206a3.Contacts.Contact.PhoneNumber;
/**  
 * Class for sorting by mobile number.
 */
public class numbersSort implements Comparator<Contact>{

	@Override
	/**
	 * Method to compare two @Contact objects, for the sorting method.
	 * @param contact1PhoneNumber - Contact 1
	 * @param contact2PhoneNumber - Contact 2
	 * @return result - 0 if equal, - if contact1<contact2, + if contact1>contact2
	 */
	public int compare(Contact contact1, Contact contact2) {

		// Create PhoneNumber objects for each contact
		PhoneNumber contact1PhoneNumber=null;
		PhoneNumber contact2PhoneNumber=null;
		int result = 0;

		// Get first mobile number for each contact
		for(PhoneNumber x : contact1.phoneNumber_list){
			if(x.getType()=="Mobile"){
				contact1PhoneNumber = x;
			}
		}

		for(PhoneNumber x : contact2.phoneNumber_list){
			if(x.getType()=="Mobile"){
				contact2PhoneNumber = x;
			}
		}

		// Check if both have a mobile number
		if(contact1PhoneNumber!=null && contact2PhoneNumber!=null){
			result = String.CASE_INSENSITIVE_ORDER.compare(contact1.getName().getLastName(), contact2.getName().getLastName()); // Compare mobile numbers
			return result;
		}
		
		return result;
	}
}
