package com.se206a3.sorting;

import java.util.Comparator;

import com.se206a3.Contacts.Contact;

public class firstLastNameSort implements Comparator<Contact> {

	// TODO Auto-generated method stub

	@Override
	public int compare(Contact c1, Contact c2) {
		// TODO Auto-generated method stub
		int result = String.CASE_INSENSITIVE_ORDER.compare(c1.getName().getFirstName(), c2.getName().getFirstName());

		if(result==0){
			int result2 = String.CASE_INSENSITIVE_ORDER.compare(c1.getName().getLastName(), c2.getName().getLastName());
			return result2;
		}else{
			return result;
		}
	}
}
