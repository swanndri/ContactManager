package com.se206a3.contactmanager;

import java.util.ArrayList;
import java.util.List;

import com.se206a3.Contacts.Contacts;
import com.se206a3.Contacts.Contacts.Address;
import com.se206a3.Contacts.Contacts.Contact;
import com.se206a3.Contacts.Contacts.Email;
import com.se206a3.Contacts.Contacts.Name;
import com.se206a3.Contacts.Contacts.PhNumber;
import com.se206a3.contactmanager.R;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.text.InputType;
import android.text.InputFilter.LengthFilter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AddNewContactActivity extends Activity {
	
	private List<android.view.View> phnCount = new ArrayList<android.view.View>();
	private List<LinearLayout> emailCount = new ArrayList<LinearLayout>();
	private List<LinearLayout> addCount = new ArrayList<LinearLayout>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contact_add);
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

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.contact_add, menu);
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
		phnCount.add(phoneBoxLayout);
	}

	/** 
	 * Dynamically adds a data entry box for a email to the contact_add layout.
	 * */
	public void addEmail(View V){
		LinearLayout emailBoxLayout = (LinearLayout) findViewById(R.id.Add_EmailBox);	//Get super (constraining) layout for emails
		LinearLayout emailBoxDataEntryLayout = createEmailDataBox();	//Dynamically create a new data entry box for an email
		emailBoxLayout.addView(emailBoxDataEntryLayout);	//Add data entry box to the super layout
		emailCount.add(emailBoxLayout);
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

		//Return complete layout
		phnCount.add(phoneBoxSpinner);
		phnCount.add(phoneBoxText);

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
		List<String> PhoneType = new ArrayList<String>();
		PhoneType.add("Home");
		PhoneType.add("Work");
		PhoneType.add("Other");

		//Create adapter for the spinner and assign it.
		//Simple_spinner_item = 1 line of text		
		addressBoxSpinner.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,PhoneType));

		//Add spinner and nested layout to superlayout
		addressBoxDataEntryLayout.addView(addressBoxSpinner);
		addressBoxDataEntryLayout.addView(addressBoxTextFields);

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
			makeNewContact();	//Create new contact object	
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


	/** 
	 * Creates new contact - haven't got implementation yet
	 * */	
	public void makeNewContact(){
		//For every edittext, get info and save
		//Make contact
		//Add to contacts
		System.out.println(((Spinner) phnCount.get(0)).getSelectedItem().toString());
		System.out.println(((EditText) phnCount.get(1)).getText().toString());
		
		
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

