package com.se206a3.contactmanager;

import java.util.ArrayList;
import java.util.List;

import com.se206a3.Contacts.Contacts;
import com.se206a3.Contacts.Contacts.Address;
import com.se206a3.Contacts.Contacts.Contact;
import com.se206a3.Contacts.Contacts.Email;
import com.se206a3.Contacts.Contacts.Name;
import com.se206a3.Contacts.Contacts.PhNumber;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ContactListActivity extends Activity {
	private ListView contactListV;
	private ListAdapter la;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contact_list);
		
		createContactList();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.contact_list, menu);
		return true;
	}

	public void createContactList(){
		contactListV = (ListView)findViewById(R.id.Contact_list);
		//<Sample contact>
				Name Ben = new Name("Ben","Brown");

				List<PhNumber> a = new ArrayList<PhNumber>();
				a.add(new PhNumber("Mobile","123456789"));
				a.add(new PhNumber("Home","987654321"));
				a.add(new PhNumber("Fax","135790"));
				a.add(new PhNumber("LOL","08642"));


				List<Email> b = new ArrayList<Email>();
				b.add(new Email("Mobile","benbrown93@gmail.com"));
				b.add(new Email("Lol","benbrown93@gmail.com"));
				b.add(new Email("cat","benbrown93@gmail.com"));

				List<Address> c = new ArrayList<Address>();
				c.add(new Address("Home","Mobile","123456789", "def", "ghi", "JKL", "MNO"));
				c.add(new Address("Work","lol","123456789", "def", "ghi", "JKL", "MNO"));
				c.add(new Address("Vacation","Cat","123456789", "def", "ghi", "JKL", "MNO"));

				Contact contact = new Contact(Ben,"SRG + MRI",a,b,c);
				Contacts.ITEMS.add(contact);
				
				//</Sample contact>
		//if(!Contacts.ITEMS.isEmpty()){ //Got mates?
		//I DO HAVE MATES!!
		//			contactListD.add("ABC");
		//			contactListD.add("DEF");
		//			contactListD.add("123");
		//			contactListD.add("456");
		
		contactListV.setOnItemClickListener(new ListItemClickList());

		//}else{
		//NAH WHAT ARE THEY 
		//Toast.makeText(ContactListActivity.this, "ABC", Toast.LENGTH_LONG).show();
		//}

	}

	class ListItemClickList implements AdapterView.OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> parentView, View clickedView, int clickedViewPosition,
				long id) {
			// TODO Auto-generated method stub
			Intent Details = new Intent();
			Details.setClass(ContactListActivity.this,ContactDetailActivity.class);
			startActivity(Details);
		}

	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_addNew:
			Intent addNewContact = new Intent();
			addNewContact.setClass(ContactListActivity.this, AddNewContactActivity.class);
			startActivity(addNewContact);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void onResume(){
		super.onResume();
		la = new ArrayAdapter<String>(ContactListActivity.this,android.R.layout.simple_list_item_1,Contacts.getNames());
		contactListV.setAdapter(la);
	}
}

