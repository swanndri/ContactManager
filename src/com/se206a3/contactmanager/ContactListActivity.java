package com.se206a3.contactmanager;

import java.util.Collections;
import java.util.List;

import com.se206a3.Contacts.Contact;
import com.se206a3.Contacts.ContactsDataSource;
import com.se206a3.contactmanager.SwipeDetector.Action;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ContactListActivity extends Activity {
	private ListView contactListV;
	private ListAdapter la;
	public static ContactsDataSource datasource;
	private List<Contact> values;
	private SwipeDetector sd;

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contact_list);
		sd = new SwipeDetector();
		datasource = new ContactsDataSource(this);
		datasource.open();
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
		contactListV.setOnTouchListener(sd);
		contactListV.setOnItemClickListener(new ListItemClickList());

	}

	class ListItemClickList implements AdapterView.OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> parentView, View clickedView, int clickedViewPosition,
				long id) {
			Contact contact = values.get(clickedViewPosition);
			Contact.toDisplay = contact;

			if(sd.swipeDetected()){
				
				if(sd.getSwipeType()==Action.RL){
				AlertDialog.Builder builder = new AlertDialog.Builder(ContactListActivity.this);
				builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						// User cancelled the dialog
					}
				});
				builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						// User clicked save and quit button
						ContactListActivity.datasource.deleteContact(Contact.toDisplay);
						onResume();
					}
				});
				builder.setTitle("Are you sure you want to delete " + Contact.toDisplay.getName().getFirstName() +" "+ Contact.toDisplay.getName().getLastName()+ "?");
				// Set other dialog properties

				// Create the AlertDialog
				AlertDialog dialog = builder.create();
				dialog.show();
				}else if(sd.getSwipeType()==Action.LR){
					Intent Edit = new Intent();
					Edit.setClass(ContactListActivity.this,EditContactActivity.class);
					startActivity(Edit);
				}
			}else{
				// TODO Auto-generated method stub
				Intent Details = new Intent();
				Details.setClass(ContactListActivity.this,ContactDetailActivity.class);
				startActivity(Details);
			}
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

	@SuppressWarnings("unchecked")
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

			name.setText(contacts.get(position).getName().getFirstName() + " " + contacts.get(position).getName().getLastName());

			return listItemView;
		}
	}

}


