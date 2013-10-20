package com.se206a3.contactmanager;

import java.util.ArrayList;
import java.util.List;

import com.se206a3.Contacts.Contact;
import com.se206a3.Contacts.Contact.Address;
import com.se206a3.Contacts.Contact.Email;
import com.se206a3.Contacts.Contact.Name;
import com.se206a3.Contacts.Contact.PhNumber;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

public class EditContact extends Activity {
	
	private List<android.view.View> phnCount = new ArrayList<android.view.View>();
	private List<android.view.View> emailCount = new ArrayList<android.view.View>();
	private List<android.view.View> addCount = new ArrayList<android.view.View>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contact_add);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_contact, menu);
		return true;
	}
	

	/** 
	 * Dynamically adds a data entry box for a phone number to the contact_add layout.
	 * */
	public void addPhoneNumber(View V){
		LinearLayout phoneBoxLayout = (LinearLayout) findViewById(R.id.Add_PhoneBox); //Get super (constraining) layout for phone numbers
		LinearLayout phoneBoxDataEntryLayout = createPhoneDataBox();	//Dynamically create a new data entry box for a phone number
		phoneBoxDataEntryLayout.setId(0);
		phoneBoxLayout.addView(phoneBoxDataEntryLayout);	//Add data entry box to the super layout
	}

	/** 
	 * Dynamically adds a data entry box for a email to the contact_add layout.
	 * */
	public void addEmail(View V){
		LinearLayout emailBoxLayout = (LinearLayout) findViewById(R.id.Add_EmailBox);	//Get super (constraining) layout for emails
		LinearLayout emailBoxDataEntryLayout = createEmailDataBox();	//Dynamically create a new data entry box for an email
		emailBoxLayout.addView(emailBoxDataEntryLayout);	//Add data entry box to the super layout
	}

	/** 
	 * Dynamically adds a data entry box for a address to the contact_add layout.
	 * */
	public void addAdd(View V){
		LinearLayout addressBoxLayout = (LinearLayout) findViewById(R.id.Add_AddBox);	//Get super (constraining) layout for address'
		LinearLayout addressBoxDataEntryLayout = createAddDataBox();	//Dynamically create a new data entry box for an address
		addressBoxLayout.addView(addressBoxDataEntryLayout);	//Add data entry box to super layout
	}

	/** 
	 * Creates a data entry box for a phone number. 
	 * The data entry box consists of a spinner and textedit - to set the type and number respectively.
	 * Each text edit is assigned an appropriate hint.
	 * The spinner is prepopulated with appropriate choices.
	 * @return The data entry box.
	 * */
	public LinearLayout createPhoneDataBox(){


		LinearLayout phoneBoxDataEntryLayout = new LinearLayout(this);	//Create new layout to add views to, this will constrain all views needed.
		phoneBoxDataEntryLayout.setOrientation(LinearLayout.HORIZONTAL);
		phoneBoxDataEntryLayout.setId(1);

		Spinner phoneBoxSpinner = new Spinner(this);
		phoneBoxSpinner.setLayoutParams(new LinearLayout.LayoutParams(-1,-1,2.0f)); //Set params as Match_parent,Match_parent, weight = 0.5
		phoneBoxSpinner.setId(2);
		
		EditText phoneBoxText = new EditText(this);
		phoneBoxText.setInputType(InputType.TYPE_CLASS_PHONE);	//Set edit text type to only accept number (specifically phone numbers)
		phoneBoxText.setHint("Phone");	//Ghost text phone
		phoneBoxText.setLayoutParams(new LinearLayout.LayoutParams(-1,-2,1.0f)); // Set params to Match_parent,Wrap_content,weight = 1
		phoneBoxText.setId(3);


		//Define the option required in the spinner
		List<String> PhoneType = new ArrayList<String>();
		PhoneType.add("Home");
		PhoneType.add("Work");
		PhoneType.add("Mobile");
		PhoneType.add("Main");
		PhoneType.add("Home Fax");
		PhoneType.add("Work Fax");
		PhoneType.add("Pager");
		PhoneType.add("Other");

		//Create adapter for the spinner and assign it.
		//Simple_spinner_item = 1 line of text
		phoneBoxSpinner.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,PhoneType));

		//Add views to super layout
		phoneBoxDataEntryLayout.addView(phoneBoxSpinner);
		phoneBoxDataEntryLayout.addView(phoneBoxText);
		
		//Add Views to list for easy data retrieve
		phnCount.add(phoneBoxSpinner);
		phnCount.add(phoneBoxText);

		//Return complete layout
		return phoneBoxDataEntryLayout;	
	}

	/** 
	 * Creates a data entry box for a email. 
	 * The data entry box consists of a spinner and textedit - to set the type and email respectively.
	 * Each text edit is assigned an appropriate hint.
	 * The spinner is prepopulated with appropriate choices.
	 * @return The data entry box.
	 * */
	public LinearLayout createEmailDataBox(){

		LinearLayout emailBoxDataEntryLayout = new LinearLayout(this);	//Create new layout to add views to, this will constrain all views needed.
		emailBoxDataEntryLayout.setOrientation(LinearLayout.HORIZONTAL);

		Spinner emailBoxSpinner = new Spinner(this);
		emailBoxSpinner.setLayoutParams(new LinearLayout.LayoutParams(-1,-1,2.0f));	//Set params as Match_parent,Match_parent, weight = 0.5

		EditText emailBoxText = new EditText(this);
		emailBoxText.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS); //Set input type to accept email address
		emailBoxText.setHint("Email");	//Ghost text email
		emailBoxText.setLayoutParams(new LinearLayout.LayoutParams(-1,-2,1.0f)); //Set params to Match_parent,Wrap_content,weight = 1

		//Define options for spinner
		List<String> PhoneType = new ArrayList<String>();
		PhoneType.add("Home");
		PhoneType.add("Work");
		PhoneType.add("Other");

		//Create adapter for the spinner and assign it.
		//Simple_spinner_item = 1 line of text		
		emailBoxSpinner.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,PhoneType));

		//Add views to super layout
		emailBoxDataEntryLayout.addView(emailBoxSpinner);
		emailBoxDataEntryLayout.addView(emailBoxText);
		
		//Add views to list for easy data retrieve
		emailCount.add(emailBoxSpinner);
		emailCount.add(emailBoxText);
		
		//Return complete layout
		return emailBoxDataEntryLayout;

	}

	/** 
	 * Creates a data entry box for a address. 
	 * The data entry box consists of a spinner and multiple textedit to take the various data:
	 * 		-Steet1
	 * 		-Street2
	 * 		-Suburb
	 * 		..etc
	 * 
	 * Each text edit is assigned an appropriate hint.
	 * The spinner is prepopulated with appropriate choices.
	 * 
	 * @return The data entry box.
	 * */
	public LinearLayout createAddDataBox(){

		LinearLayout addressBoxDataEntryLayout = new LinearLayout(this);	//Create super layout that will constrain all needed views.
		addressBoxDataEntryLayout.setOrientation(LinearLayout.HORIZONTAL);

		Spinner addressBoxSpinner = new Spinner(this);
		addressBoxSpinner.setLayoutParams(new LinearLayout.LayoutParams(-1,-1,2.0f)); //Set params as Match_parent,Match_parent,Weight = 0.5

		LinearLayout addressBoxTextFields = new LinearLayout(this);	//Nested layout for edittexts - keeps tidy - allows for vertical stacking
		addressBoxTextFields.setOrientation(LinearLayout.VERTICAL);
		addressBoxTextFields.setLayoutParams(new LinearLayout.LayoutParams(-1,-2,1.0f));	//Set params as Match_parent, Wrap_content, weight =1

		//Street1 edittext box
		EditText addressBoxStreet1 = new EditText(this);
		addressBoxStreet1.setHint("Street");

		//Street2 edittext box
		EditText addressBoxStreet2 = new EditText(this);
		addressBoxStreet2.setHint("Street");

		//Suburb edittext box
		EditText addressBoxSuburb = new EditText(this);
		addressBoxSuburb.setHint("Suburb");

		//City edittext box
		EditText addressBoxCity = new EditText(this);
		addressBoxCity.setHint("City");

		//PostCode edittext box
		EditText addressBoxPostCode = new EditText(this);
		addressBoxPostCode.setHint("Post Code");

		//Country edittext box
		EditText addressBoxCountry = new EditText(this);
		addressBoxCountry.setHint("Country");

		//Add textedit views to nested layout
		addressBoxTextFields.addView(addressBoxStreet1);
		addressBoxTextFields.addView(addressBoxStreet2);
		addressBoxTextFields.addView(addressBoxSuburb);
		addressBoxTextFields.addView(addressBoxCity);
		addressBoxTextFields.addView(addressBoxPostCode);
		addressBoxTextFields.addView(addressBoxCountry);

		//Define options for spinner
		List<String> AddressType = new ArrayList<String>();
		AddressType.add("Home");
		AddressType.add("Work");
		AddressType.add("Other");

		//Create adapter for the spinner and assign it.
		//Simple_spinner_item = 1 line of text		
		addressBoxSpinner.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,AddressType));

		//Add spinner and nested layout to superlayout
		addressBoxDataEntryLayout.addView(addressBoxSpinner);
		addressBoxDataEntryLayout.addView(addressBoxTextFields);
		
		//Add views to list for easy data retrieve
		addCount.add(addressBoxSpinner);
		addCount.add(addressBoxStreet1);
		addCount.add(addressBoxStreet2);
		addCount.add(addressBoxSuburb);
		addCount.add(addressBoxCity);
		addCount.add(addressBoxPostCode);
		addCount.add(addressBoxCountry);

		//Return superlayout
		return addressBoxDataEntryLayout;
	}

	/** 
	 * Defines the actions to be taken for a click of an action bar item.
	 * Determines which item was pressed by id of parsed item.
	 * @param MenuItem item - the item that was clicked.
	 * */
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_Done:
			//If no exisitng contact matches
			Contact toBeAdded = makeNewContact();	//Create new contact object	
			ContactListActivity.datasource.createContact(toBeAdded);
			this.finish();

//			//else
//			AlertDialog.Builder builder = new AlertDialog.Builder(this);
//			builder.setTitle("Existing contact found");
//			builder.setItems(R.array.OptionsArray, new DialogInterface.OnClickListener() {
//				public void onClick(DialogInterface dialog, int which) {
//					// The 'which' argument contains the index position
//					// of the selected item
//					switch(which){
//					case 1:which=1;
//					finish();
//					break;
//					case 2:which=2;
//					mergeExistingContacts(contact);
//					break;
//					case 3:which=3;
//					replaceExistingContacts(contact);
//					break;
//					case 4:which=3;
//					break;
//					default:
//						break;
//
//					}
//
//				}
//			});
//			AlertDialog dialog = builder.create();
//			dialog.show();

		}
		return super.onOptionsItemSelected(item);
	}

	/** 
	 * Detects a click of the physical back button.
	 * Determines which action to take depending on the content on the screen.
	 * No content:
	 * 			Finish activity
	 * Content:
	 * 			Create dialog with options to cancel, quit, save and quit.
	 * */
	public void onBackPressed(){

		//If !content
		//Do something
		//else
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				// User cancelled the dialog
			}
		});
		builder.setPositiveButton("Save and quit", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				// User clicked save and quit button
				finish();

			}
		});
		builder.setNeutralButton("Dont save", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				finish();
			}
		});
		builder.setTitle("Would you like to save?");
		// Set other dialog properties

		// Create the AlertDialog
		AlertDialog dialog = builder.create();
		dialog.show();

	}


	public Contact makeNewContact(){
		//For every edittext, get info and save
		//Make contact
		//Add to contacts
		Contact contact = new Contact();
		
		Name nm = new Name();
		nm.setFirstName(((EditText)findViewById(R.id.First_Name_enter)).getText().toString());
		nm.setLastName(((EditText)findViewById(R.id.Surname_enter)).getText().toString());
		contact.setName(nm);
		
		contact.setCompany(((EditText)findViewById(R.id.Company_enter)).getText().toString());
		
		
		for(int i=0;i<phnCount.size();i++){
			PhNumber phn = new PhNumber();
			phn.setType(((Spinner) phnCount.get(i)).getSelectedItem().toString());
			i++;
			phn.setNumber(((EditText) phnCount.get(i)).getText().toString());
			contact.numbers.add(phn);
		}
		
		for(int i=0;i<emailCount.size();i++){
			Email em = new Email();
			em.setType(((Spinner) emailCount.get(i)).getSelectedItem().toString());
			i++;
			em.setEmail(((EditText) emailCount.get(i)).getText().toString());
			contact.emails.add(em);
		}
		
		for(int i=0;i<addCount.size();i++){
			Address ad = new Address();
			ad.setType(((Spinner) addCount.get(i)).getSelectedItem().toString());
			i++;
			ad.setStreet1(((EditText) addCount.get(i)).getText().toString());
			i++;
			ad.setStreet2(((EditText) addCount.get(i)).getText().toString());
			i++;
			ad.setSuburb(((EditText) addCount.get(i)).getText().toString());
			i++;
			ad.setCity(((EditText) addCount.get(i)).getText().toString());
			i++;
			ad.setPostCode(((EditText) addCount.get(i)).getText().toString());
			i++;
			ad.setCountry(((EditText) addCount.get(i)).getText().toString());
			
			contact.address.add(ad);
		}
		return contact;
		
		
		
	}

	public static void mergeExistingContacts(Contact contact){
		//Merge exisiting contact with new one
		//No idea how to do this yet
	}
	public static void replaceExistingContacts(Contact contact){
		//Replace exisiting contact with new one
		//No idea how to do this yet
	}

}
