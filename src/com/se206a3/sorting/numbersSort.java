package com.se206a3.sorting;

import java.util.Comparator;

import com.se206a3.Contacts.Contact;
import com.se206a3.Contacts.Contact.PhoneNumber;

public class numbersSort implements Comparator<Contact>{

	@Override
	public int compare(Contact c1, Contact c2) {
		// TODO Auto-generated method stub
		PhoneNumber c1P=null;
		PhoneNumber c2P=null;
		int result = 0;


		for(PhoneNumber x : c1.phoneNumber_list){
			if(x.getType()=="Mobile"){
				c1P = x;
			}
		}

		for(PhoneNumber x : c2.phoneNumber_list){
			if(x.getType()=="Mobile"){
				c2P = x;
			}
		}

		if(c1P!=null && c2P!=null){
			result = String.CASE_INSENSITIVE_ORDER.compare(c1.getName().getLastName(), c2.getName().getLastName());
			return result;
		}
		
		return result;
	}
}
