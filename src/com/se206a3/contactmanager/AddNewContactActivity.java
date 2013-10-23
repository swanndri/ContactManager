package com.se206a3.contactmanager;

import java.util.ArrayList;
import java.util.List;

import com.se206a3.Contacts.Contact;
import com.se206a3.Contacts.Contact.Address;
import com.se206a3.Contacts.Contact.Email;
import com.se206a3.Contacts.Contact.Name;
import com.se206a3.Contacts.Contact.PhNumber;
import com.se206a3.contactmanager.R;
import com.se206a3.contactmanager.ContactListActivity.ListItemClickList;
import com.se206a3.contactmanager.SwipeDetector.Action;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

public class AddNewContactActivity extends Activity {

	private static final int SELECT_PICTURE = 1;
	private List<android.view.View> phnCount = new ArrayList<android.view.View>();
	private List<android.view.View> emailCount = new ArrayList<android.view.View>();
	private List<android.view.View> addCount = new ArrayList<android.view.View>();
	private ImageView img;
	private String selectedImagePath;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contact_add);
		img = ((ImageView)findViewById(R.id.add_profilePic));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.contact_add, menu);
		return true;
	}

	public void addPhoto(View V){
		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		startActivityForResult(Intent.createChooser(intent,"Select Picture"), SELECT_PICTURE);
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			if (requestCode == SELECT_PICTURE) {
				Uri selectedImageUri = data.getData();
				selectedImagePath = getPath(selectedImageUri);
				System.out.println("Image Path : " + selectedImagePath);
				img.setImageURI(selectedImageUri);
				img.setLayoutParams(new LinearLayout.LayoutParams(-1,-1,3.0f));
			}
		}
	}
	public String getPath(Uri uri) {
		String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor = managedQuery(uri, projection, null, null, null);
		int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}

	/** 
	 * Dynamically adds a data entry box for a phone number to the contact_add layout.
	 * */
	public void addPhoneNumber(View V){
		LinearLayout phoneBoxLayout = (LinearLayout) findViewById(R.id.Add_PhoneBox); //Get super (constraining) layout for phone numbers
		LinearLayout phoneBoxDataEntryLayout = createPhoneDataBox();//Dynamically create a new data entry box for a phone number
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

		//Create adapter for the spinner and assign it.
		//Simple_spinner_item = 1 line of text
		phoneBoxSpinner.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.Phone_Spinner)));


		ImageView delete = new ImageView(this);
		delete.setLayoutParams(new LinearLayout.LayoutParams(-1,-1,2.5f));
		delete.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_remove));
		delete.setOnClickListener(new deleteClick());

		//Add views to super layout
		phoneBoxDataEntryLayout.addView(phoneBoxSpinner);
		phoneBoxDataEntryLayout.addView(phoneBoxText);
		phoneBoxDataEntryLayout.addView(delete);


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

		//Create adapter for the spinner and assign it.
		//Simple_spinner_item = 1 line of text		
		emailBoxSpinner.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.Email_Spinner)));

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

		//Create adapter for the spinner and assign it.
		//Simple_spinner_item = 1 line of text		
		addressBoxSpinner.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.Address_Spinner)));

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
			Contact toBeAdded = makeNewContact();	//Create new contact object	
			ContactListActivity.datasource.createContact(toBeAdded);
			this.finish();
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
				ContactListActivity.datasource.createContact(makeNewContact());
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
		String firstName =((EditText)findViewById(R.id.First_Name_enter)).getText().toString();
		String lastName =((EditText)findViewById(R.id.Surname_enter)).getText().toString();


		if(firstName.trim().equals("")&&lastName.trim().equals("")){
			nm.setFirstName("No");
			nm.setLastName("Name");
		}else{
			nm.setFirstName(firstName);
			nm.setLastName(lastName);
		}

		contact.setName(nm);

		contact.setCompany(((EditText)findViewById(R.id.Company_enter)).getText().toString());

		contact.setImagePath(selectedImagePath);


		for(int i=0;i<phnCount.size();i++){
			PhNumber phn = new PhNumber();
			phn.setType(((Spinner) phnCount.get(i)).getSelectedItem().toString());
			i++;
			if(((EditText) phnCount.get(i)).getText().toString().equals("")){
				phn.setNumber("No number given");
			}else{
				phn.setNumber(((EditText) phnCount.get(i)).getText().toString());
			}
			contact.numbers.add(phn);
		}

		for(int i=0;i<emailCount.size();i++){
			Email em = new Email();
			em.setType(((Spinner) emailCount.get(i)).getSelectedItem().toString());
			i++;
			if(((EditText) emailCount.get(i)).getText().toString().equals("")){
				em.setEmail("No email given");
			}else{
				em.setEmail(((EditText) emailCount.get(i)).getText().toString());
			}
			contact.emails.add(em);
		}

		for(int i=0;i<addCount.size();i++){
			Address ad = new Address();
			ad.setType(((Spinner) addCount.get(i)).getSelectedItem().toString());
			i++;

			if(((EditText) addCount.get(i)).getText().toString().equals("")){
				ad.setStreet1("No street given");
			}else{
				ad.setStreet1(((EditText) addCount.get(i)).getText().toString());
			}

			i++;

			if(((EditText) addCount.get(i)).getText().toString().equals("")){
				ad.setStreet2("No street given");
			}else{
				ad.setStreet2(((EditText) addCount.get(i)).getText().toString());
			}

			i++;

			if(((EditText) addCount.get(i)).getText().toString().equals("")){
				ad.setSuburb("No suburb given");
			}else{
				ad.setSuburb(((EditText) addCount.get(i)).getText().toString());
			}

			i++;

			if(((EditText) addCount.get(i)).getText().toString().equals("")){
				ad.setCity("No city given");
			}else{
				ad.setCity(((EditText) addCount.get(i)).getText().toString());
			}

			i++;

			if(((EditText) addCount.get(i)).getText().toString().equals("")){
				ad.setPostCode("No postcode given ");
			}else{
				ad.setPostCode(((EditText) addCount.get(i)).getText().toString());

			}

			i++;

			if(((EditText) addCount.get(i)).getText().toString().equals("")){
				ad.setCountry("No country given");
			}else{
				ad.setCountry(((EditText) addCount.get(i)).getText().toString());
			}

			contact.address.add(ad);
		}
		return contact;
	}

	class deleteClick implements OnClickListener{
		@Override
		public void onClick(View v) {
			ImageView view = (ImageView) v;
			LinearLayout l = (LinearLayout) view.getParent();

			Spinner x = (Spinner)l.getChildAt(0);
			phnCount.remove(x);
			EditText y = (EditText)l.getChildAt(1);
			phnCount.remove(y);
			l.removeAllViews();

		}

	}
}


