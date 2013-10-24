package com.se206a3.contactmanager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.se206a3.Contacts.Contact;
import com.se206a3.Contacts.ContactsDataSource;
import com.se206a3.contactmanager.SwipeDetector.Action;
import com.se206a3.sorting.firstLastNameSort;
import com.se206a3.sorting.firstNameSort;
import com.se206a3.sorting.lastNameSort;
import com.se206a3.sorting.numbersSort;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Filterable;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
/**
 * 
 * @author Ben Brown
 *
 */

/**
 * The main activity for the application.
 * Displays a searchable list of contacts.
 */
public class ContactListActivity extends Activity {

	/**
	 * The database from which the contacts are taken.
	 * Of type @ContactsDataSource
	 */
	public static ContactsDataSource datasource;

	/**
	 * The comparator for the currently selected sorting method.
	 */
	private static Comparator<Contact> sortMethod = new firstLastNameSort();

	/**
	 * The list of all contacts.
	 */
	private List<Contact> contactList;

	/**
	 * The filtered list of contacts, changes with the search query.
	 */
	private List<Contact> filteredContactList = new ArrayList<Contact>();

	/**
	 * The @ListView for the contacts to be displayed in.
	 */
	private ListView contactListView;

	/**
	 * The @ListAdapter for the contacts.
	 */
	private ListAdapter contactListAdapter;

	/**
	 * The @EditText that acts as the search box.
	 */
	private EditText searchBox;

	/**
	 * A @SwipeDetector for the items in the listview.
	 */
	private SwipeDetector listItemSwipeDetector;

	@Override
	/**
	 * The method that is run when the application first opens.
	 * Inflates the super layout.
	 * Creates and saves the @SwipeDetector.
	 * Creates/opens the database and populate the contactList.
	 * Runs @createContactList 
	 * 
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contact_list); // Inflate superview

		//Open or create database
		datasource = new ContactsDataSource(this);
		datasource.open();
		contactList = datasource.getAllContacts(); // Populate contactList

		createContactList(); // Create the listview

		//Create the search box
		searchBox = (EditText) findViewById(R.id.Contact_list_search);
		searchBox.addTextChangedListener(new searchDetector());
	}

	@Override
	/**
	 * Inflates the menu at the top of the screen.
	 */
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.contact_list, menu);
		return true;
	}

	/**
	 * Creates the @ListView and assigns the listeners.
	 */
	public void createContactList(){
		contactListView = (ListView)findViewById(R.id.Contact_list);
		contactListView.setOnTouchListener(listItemSwipeDetector);
		contactListView.setOnItemClickListener(new ListItemClickList());
	}

	/**
	 * On click listener for the ListView.
	 * Contains functionality for the swipe listener also.
	 */
	class ListItemClickList implements AdapterView.OnItemClickListener{

		@Override
		/**
		 * Method that is run when list item is clicked.
		 */
		public void onItemClick(AdapterView<?> parentView, View clickedView, int clickedViewPosition,
				long id) {

			// The contact that was clicked in the list
			Contact contact = filteredContactList.get(clickedViewPosition);

			// Set contact to display as selected contact
			Contact.static_contactToDisplay = contact;

			if(listItemSwipeDetector.swipeDetected()){

				if(listItemSwipeDetector.getSwipeType()==Action.RL){ // If swipe is right to left
					AlertDialog.Builder builder = new AlertDialog.Builder(ContactListActivity.this); // Create dialog asking user if they would like to delete the contact
					builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							// User cancelled the dialog
						}
					});
					builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() { 
						public void onClick(DialogInterface dialog, int id) {
							// User clicked save and quit button
							ContactListActivity.datasource.deleteContact(Contact.static_contactToDisplay); // Delete contact
							onResume();
						}
					});
					// Set other dialog properties
					builder.setTitle("Are you sure you want to delete " + Contact.static_contactToDisplay.getName().getFirstName() +" "+ Contact.static_contactToDisplay.getName().getLastName()+ "?");

					// Create the AlertDialog
					AlertDialog dialog = builder.create();
					dialog.show();

				}else if(listItemSwipeDetector.getSwipeType()==Action.LR){ // If swipe is left to right
					// Start new edit activity
					Intent editContact = new Intent();
					editContact.setClass(ContactListActivity.this, EditContactActivity.class);
					startActivity(editContact);
				}
			}else{
				// Start new detail activity
				Intent Details = new Intent();
				Details.setClass(ContactListActivity.this,ContactDetailActivity.class);
				startActivity(Details);
			}
		}

	}

	/**
	 * Method run when a item is clicked in the menu bar, or options list.
	 */
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_addNew: // New button clicked
			// Start add new contact activity
			Intent addNewContact = new Intent();
			addNewContact.setClass(ContactListActivity.this, AddNewContactActivity.class);
			startActivity(addNewContact);
			return true;

		case R.id.action_sorts: // Sort button clicked
			AlertDialog.Builder builder = new AlertDialog.Builder(this); // Build dialog asking user which kind of sort
			builder.setTitle("Choose how to sort");
			builder.setItems(R.array.Sorting_methods, new DialogInterface.OnClickListener() {
				@SuppressWarnings("unchecked")
				public void onClick(DialogInterface dialog, int which) {
					// The 'which' argument contains the index position
					// of the selected item

					switch(which){
					case 0: // Default
						sortMethod = new firstLastNameSort();
					case 1: // First name sort
						sortMethod = new firstNameSort();
						break;
					case 2: // Last name sort
						sortMethod = new lastNameSort();
						break;
					case 3: // Mobile number sort
						sortMethod = new numbersSort();
						break;
					}
					((ArrayAdapter<Contact>) contactListView.getAdapter()).sort(sortMethod); // Apply sort method
					Collections.sort(filteredContactList, sortMethod); // Sort fileredContactList so we dont get index mismatch
				}
			});
			// Create and show dialog
			AlertDialog dialog = builder.create();
			dialog.show();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onResume()
	 */
	@SuppressWarnings("unchecked")
	public void onResume(){
		super.onResume();
		searchBox.setText(""); // Reset search
		contactList = datasource.getAllContacts(); // Get all contacts incase of addition/edit
		filteredContactList = datasource.getAllContacts();
		contactListAdapter = new ContactListAdapter(this, contactList);
		contactListView.setAdapter(contactListAdapter);
		
		// Apply sorting
		((ArrayAdapter<Contact>) contactListView.getAdapter()).sort(sortMethod);
		Collections.sort(filteredContactList, sortMethod);
	}

	/**
	 * @author Ben Brown
	 * Custom list adapter for @Contact objects.
	 *
	 */
	private class ContactListAdapter extends ArrayAdapter<Contact> implements Filterable{

		private Context context;
		private List<Contact> contacts;

		ContactListAdapter(Context context, List<Contact> contacts){
			super(context, android.R.layout.simple_list_item_1,contacts);
			
			// Get data
			this.context = context;
			this.contacts = contacts;
		}

		public View getView(int position, View convertView, ViewGroup Parent){
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View listItemView = inflater.inflate(R.layout.contact_listitem, null);

			// Set Textview
			TextView name = (TextView) listItemView.findViewById(R.id.List_item_name);

			name.setText(contacts.get(position).getName().getFirstName() + " " + contacts.get(position).getName().getLastName());

			return listItemView;
		}


	}


	/**
	 * @author Ben Brown
	 * Search detector for the list search.
	 * Updates the list when the search query is changed.
	 */
	@SuppressLint("DefaultLocale")
	class searchDetector implements TextWatcher{


		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {

		}


		@SuppressWarnings("unchecked")
		@Override
		/**
		 * Updates the list
		 */
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			filteredContactList.clear(); // Reset filtered list
			
			// If contact name contains search query, add to display list
			for (Contact c: contactList){
				if(c.getName().toString().toLowerCase().contains(searchBox.getText().toString().toLowerCase())){
					filteredContactList.add(c);
				}
			}
			
			// Update list
			contactListAdapter = new ContactListAdapter(ContactListActivity.this, filteredContactList);
			
			// Apply sorting
			contactListView.setAdapter(contactListAdapter);
			((ArrayAdapter<Contact>) contactListView.getAdapter()).sort(sortMethod);

		}
	}
}


