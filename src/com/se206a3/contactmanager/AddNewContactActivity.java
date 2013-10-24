package com.se206a3.contactmanager;

import java.util.ArrayList;
import java.util.List;

import com.se206a3.Contacts.Contact;
import com.se206a3.Contacts.Contact.Address;
import com.se206a3.Contacts.Contact.Email;
import com.se206a3.Contacts.Contact.Name;
import com.se206a3.Contacts.Contact.PhoneNumber;
import com.se206a3.contactmanager.R;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.MediaColumns;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
/**
 * 
 * @author Ben Brown
 *
 * The activity responsible for the addition of contacts to the database.
 */
public class AddNewContactActivity extends Activity {
	/**
	 * A value that acts as a check in getting the profile picture.
	 */
	private int RESULT_LOAD_IMAGE = 0;
	
	/**
	 * List that contains all the data entry views that are used to add phone numbers.
	 */
	private List<android.view.View> _phoneDataEntryViewsList = new ArrayList<android.view.View>();
	
	/**
	 * List that contains all the data entry views that are used to add emails.
	 */
	private List<android.view.View> _emailDataEntryViewList= new ArrayList<android.view.View>();
	
	/**
	 * List that contains all the data entry views that are used to add addresses.
	 */
	private List<android.view.View> _addressDataEntryViewList = new ArrayList<android.view.View>();
	
	/**
	 * ImageView for the contacts profile picture.
	 */
	private ImageView _profilePicture;
	
	/**
	 * String of the filepath for the profile picture.
	 */
	private String _selectedImagePath;


	@Override
	/**
	 * Method that is run when the activity is started.
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contact_add); // Inflate layout
		_profilePicture = ((ImageView)findViewById(R.id.add_profilePic)); // Get the profile picture view
	}

	@Override
	/**
	 * Inflates the menu at the top of the screen.
	 */
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.contact_add, menu);
		return true;
	}

	/**
	 * Run on click of the profile picture view.
	 * Allows the user to select a picture from the gallery.
	 * @param V
	 */
	public void addPhoto(View V){
		// Start gallery activity for result of picture
		Intent i = new Intent(
				Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				startActivityForResult(i, RESULT_LOAD_IMAGE);
	}

	@Override
	/**
	 * Runs when a activity for result returns.
	 * Ie. When a profile picture is selected.
	 * 
	 * Saves the file path of the selected image.
	 */
	 protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	     super.onActivityResult(requestCode, resultCode, data);
	      
	     // If valid return
	     if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
	         
	    	 // Get file path and save to database
	    	 Uri selectedImage = data.getData();
	         String[] filePathColumn = { MediaColumns.DATA };
	 
	         Cursor cursor = getContentResolver().query(selectedImage,
	                 filePathColumn, null, null, null);
	         cursor.moveToFirst();
	 
	         int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
	         _selectedImagePath = cursor.getString(columnIndex);
	         cursor.close();
	         
	         // Set the profile picture view to display the selected picture
	         _profilePicture.setImageBitmap(BitmapFactory.decodeFile(_selectedImagePath));
             _profilePicture.setLayoutParams(new LinearLayout.LayoutParams(-1,-1,3.0f));
	     }
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

		// Add a delete button to each data entry layout
		ImageView delete = new ImageView(this);
		delete.setLayoutParams(new LinearLayout.LayoutParams(-1,-1,2.5f));
		delete.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_remove));
		delete.setOnClickListener(new deletePhoneNumberClick());

		//Add views to super layout
		phoneBoxDataEntryLayout.addView(phoneBoxSpinner);
		phoneBoxDataEntryLayout.addView(phoneBoxText);
		phoneBoxDataEntryLayout.addView(delete);


		//Add Views to list for easy data retrieve
		_phoneDataEntryViewsList.add(phoneBoxSpinner);
		_phoneDataEntryViewsList.add(phoneBoxText);

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

		// Add a delete button to each data entry layout
		ImageView delete = new ImageView(this);
		delete.setLayoutParams(new LinearLayout.LayoutParams(-1,-1,2.5f));
		delete.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_remove));
		delete.setOnClickListener(new deleteEmailClick());

		//Add views to super layout
		emailBoxDataEntryLayout.addView(emailBoxSpinner);
		emailBoxDataEntryLayout.addView(emailBoxText);
		emailBoxDataEntryLayout.addView(delete);

		//Add views to list for easy data retrieve
		_emailDataEntryViewList.add(emailBoxSpinner);
		_emailDataEntryViewList.add(emailBoxText);

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

		// Add a delete button to each data entry layout
		ImageView delete = new ImageView(this);
		delete.setLayoutParams(new LinearLayout.LayoutParams(-1,-1,2.5f));
		delete.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_remove));
		delete.setOnClickListener(new deleteAddClick());

		//Add spinner and nested layout to superlayout
		addressBoxDataEntryLayout.addView(addressBoxSpinner);
		addressBoxDataEntryLayout.addView(addressBoxTextFields);
		addressBoxDataEntryLayout.addView(delete);

		//Add views to list for easy data retrieve
		_addressDataEntryViewList.add(addressBoxSpinner);
		_addressDataEntryViewList.add(addressBoxStreet1);
		_addressDataEntryViewList.add(addressBoxStreet2);
		_addressDataEntryViewList.add(addressBoxSuburb);
		_addressDataEntryViewList.add(addressBoxCity);
		_addressDataEntryViewList.add(addressBoxPostCode);
		_addressDataEntryViewList.add(addressBoxCountry);

		//Return superlayout
		return addressBoxDataEntryLayout;
	}

	/** 
	 * Defines the actions to be taken for a click of an action bar item.
	 * Determines which item was pressed by id of parsed item.
	 * @param MenuItem item - the item that was clicked.
	 * */
	@Override
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
	@Override
	public void onBackPressed(){

		//If !content
		//Do something
		//else
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				// User cancelled the dialog
			}
		});
		builder.setPositiveButton("Save and quit", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int id) {
				ContactListActivity.datasource.createContact(makeNewContact());
				finish();

			}
		});
		builder.setNeutralButton("Dont save", new DialogInterface.OnClickListener() {
			@Override
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
 * Creates a contact from the information entered in the screen.
 * @return
 */
	public Contact makeNewContact(){
		
		// Create new contact object
		Contact contact = new Contact();
		
		// Set basic data
		contact.setImagePath(_selectedImagePath);

		Name nm = new Name();
		String firstName =((EditText)findViewById(R.id.First_Name_enter)).getText().toString();
		String lastName =((EditText)findViewById(R.id.Surname_enter)).getText().toString();

		// If name is empty, set as no name
		if(firstName.trim().equals("")&&lastName.trim().equals("")){
			nm.setFirstName("No");
			nm.setLastName("Name");
		}else{
			nm.setFirstName(firstName);
			nm.setLastName(lastName);
		}

		contact.setName(nm);

		contact.setCompany(((EditText)findViewById(R.id.Company_enter)).getText().toString());

		contact.setDateOfBirth(((EditText)findViewById(R.id.Dob_enter)).getText().toString());
		

		// For all phone number data entry views, create a new phone number object and add to contact
		// If phone number = empty, set as no number give.
		for(int i=0;i<_phoneDataEntryViewsList.size();i++){
			PhoneNumber phn = new PhoneNumber();
			phn.setType(((Spinner) _phoneDataEntryViewsList.get(i)).getSelectedItem().toString());
			i++;
			if(((EditText) _phoneDataEntryViewsList.get(i)).getText().toString().equals("")){
				phn.setNumber("No number given");
			}else{
				phn.setNumber(((EditText) _phoneDataEntryViewsList.get(i)).getText().toString());
			}
			contact.phoneNumber_list.add(phn);
		}

		// For all email data entry views, create a new email object and add to contact
		// If email = empty, set as no email given.
		for(int i=0;i<_emailDataEntryViewList.size();i++){
			Email em = new Email();
			em.setType(((Spinner) _emailDataEntryViewList.get(i)).getSelectedItem().toString());
			i++;
			if(((EditText) _emailDataEntryViewList.get(i)).getText().toString().equals("")){
				em.setEmail("No email given");
			}else{
				em.setEmail(((EditText) _emailDataEntryViewList.get(i)).getText().toString());
			}
			contact.email_list.add(em);
		}

		// For all address data entry views, create a new address object and add to contact
		// If field = empty, set as no FIELD give.
		for(int i=0;i<_addressDataEntryViewList.size();i++){
			Address ad = new Address();
			ad.setType(((Spinner) _addressDataEntryViewList.get(i)).getSelectedItem().toString());
			i++;

			if(((EditText) _addressDataEntryViewList.get(i)).getText().toString().equals("")){
				ad.setStreet1("No street given");
			}else{
				ad.setStreet1(((EditText) _addressDataEntryViewList.get(i)).getText().toString());
			}

			i++;

			if(((EditText) _addressDataEntryViewList.get(i)).getText().toString().equals("")){
				ad.setStreet2("No street given");
			}else{
				ad.setStreet2(((EditText) _addressDataEntryViewList.get(i)).getText().toString());
			}

			i++;

			if(((EditText) _addressDataEntryViewList.get(i)).getText().toString().equals("")){
				ad.setSuburb("No suburb given");
			}else{
				ad.setSuburb(((EditText) _addressDataEntryViewList.get(i)).getText().toString());
			}

			i++;

			if(((EditText) _addressDataEntryViewList.get(i)).getText().toString().equals("")){
				ad.setCity("No city given");
			}else{
				ad.setCity(((EditText) _addressDataEntryViewList.get(i)).getText().toString());
			}

			i++;

			if(((EditText) _addressDataEntryViewList.get(i)).getText().toString().equals("")){
				ad.setPostCode("No postcode given ");
			}else{
				ad.setPostCode(((EditText) _addressDataEntryViewList.get(i)).getText().toString());

			}

			i++;

			if(((EditText) _addressDataEntryViewList.get(i)).getText().toString().equals("")){
				ad.setCountry("No country given");
			}else{
				ad.setCountry(((EditText) _addressDataEntryViewList.get(i)).getText().toString());
			}

			contact.address_list.add(ad);
		}
		return contact;
	}

	/**
	 * 
	 * @author Ben Brown
	 * Click listener for delete button for phone number input views.
	 * Deletes views.
	 */
	class deletePhoneNumberClick implements OnClickListener{
		@Override
		public void onClick(View v) {
			ImageView view = (ImageView) v; // Clicked view
			LinearLayout l = (LinearLayout) view.getParent();

			// Delete input views
			Spinner x = (Spinner)l.getChildAt(0);
			_phoneDataEntryViewsList.remove(x);
			EditText y = (EditText)l.getChildAt(1);
			_phoneDataEntryViewsList.remove(y);
			l.removeAllViews();

		}
		
	}
	
	/**
	 * 
	 * @author Ben Brown
	 * Click listener for delete button for email input views.
	 * Deletes views.
	 */
	class deleteEmailClick implements OnClickListener{
		@Override
		public void onClick(View v) {
			ImageView view = (ImageView) v; // Clicked view
			LinearLayout l = (LinearLayout) view.getParent();

			// Delete views
			Spinner x = (Spinner)l.getChildAt(0);
			_emailDataEntryViewList.remove(x);
			EditText y = (EditText)l.getChildAt(1);
			_emailDataEntryViewList.remove(y);
			l.removeAllViews();

		}

	}
	
	/**
	 * 
	 * @author Ben Brown
	 * Click listener for delete button for address input views.
	 * Deletes views.
	 */
	class deleteAddClick implements OnClickListener{
		@Override
		public void onClick(View v) {
			ImageView view = (ImageView) v; // Clicked view
			LinearLayout l = (LinearLayout) view.getParent();

			// Delete input views
			Spinner x = (Spinner)l.getChildAt(0);
			_phoneDataEntryViewsList.remove(x);
			LinearLayout y = (LinearLayout)l.getChildAt(1);
			
			for(int i=0;i<y.getChildCount(); i++){
				_phoneDataEntryViewsList.remove(y.getChildAt(i));
			}
			l.removeAllViews();
		}

	}
}




