package com.se206a3.contactmanager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.se206a3.Contacts.Contact;
import com.se206a3.Contacts.ContactsDataSource;
import com.se206a3.Contacts.Contact.*;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ContactListActivity extends Activity {
	private ListView contactListV;
	private ListAdapter la;
	public static ContactsDataSource datasource;
	private List<Contact> values;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contact_list);
		datasource = new ContactsDataSource(this);
		datasource.open();

//		//<Sample contact>
//		Name Ben = new Name("Ben","Brown");
//
//		List<PhNumber> a = new ArrayList<PhNumber>();
//		a.add(new PhNumber("Mobile","123456789"));
//		a.add(new PhNumber("Home","987654321"));
//		a.add(new PhNumber("Fax","135790"));
//		a.add(new PhNumber("LOL","08642"));
//
//		List<Email> b = new ArrayList<Email>();
//		b.add(new Email("Mobile","benbrown93@gmail.com"));
//		b.add(new Email("Lol","benbrown93@gmail.com"));
//		b.add(new Email("cat","benbrown93@gmail.com"));
//
//		List<Address> c = new ArrayList<Address>();
//		c.add(new Address("Home","Mobile","123456789", "def", "ghi", "JKL", "MNO"));
//		c.add(new Address("Work","lol","123456789", "def", "ghi", "JKL", "MNO"));
//		c.add(new Address("Vacation","Cat","123456789", "def", "ghi", "JKL", "MNO"));
//
//		Contact contact = new Contact(Ben,"SRG + MRI",a,b,c);
//		//</Sample contact>

		//datasource.createContact(contact);
		values = datasource.getAllContacts();
		Collections.sort(values);
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
		contactListV.setOnItemClickListener(new ListItemClickList());

	}

	class ListItemClickList implements AdapterView.OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> parentView, View clickedView, int clickedViewPosition,
				long id) {
			// TODO Auto-generated method stub
			Intent Details = new Intent();
			Contact.toDisplay = values.get(clickedViewPosition);
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
		values = datasource.getAllContacts();
		Collections.sort(values);
		la = new ContactListAdapter(this, values);
		contactListV.setAdapter(la);
	}

	private class ContactListAdapter extends ArrayAdapter<Contact>{

		private Context context;
		private List<Contact> contacts;

		ContactListAdapter(Context context, List<Contact> contacts){
			super(context, android.R.layout.simple_list_item_1,contacts);

			this.context = context;
			this.contacts = contacts;
		}

		public View getView(int position, View convertView, ViewGroup Parent){
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View listItemView = inflater.inflate(R.layout.contact_listitem, null);

			TextView name = (TextView) listItemView.findViewById(R.id.List_item_name);
			ImageView img = (ImageView) listItemView.findViewById(R.id.List_item_image);
			
			name.setText(contacts.get(position).getName().getFirstName() + " " + contacts.get(position).getName().getLastName());
			
			return listItemView;
		}
	}

}


