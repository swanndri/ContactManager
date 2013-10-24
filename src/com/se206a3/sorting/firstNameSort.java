package com.se206a3.sorting;

import java.util.Comparator;

import com.se206a3.Contacts.Contact;

public class firstNameSort implements Comparator<Contact>{

	@Override
	public int compare(Contact c1, Contact c2) {
		// TODO Auto-generated method stub
		int result = String.CASE_INSENSITIVE_ORDER.compare(c1.getName().getFirstName(), c2.getName().getFirstName());
		return result;
	}
}

