package com.se206a3.contactmanager;


import com.se206a3.Contacts.Contact;
import com.se206a3.Contacts.Contact.Address;
import com.se206a3.Contacts.Contact.Email;
import com.se206a3.Contacts.Contact.PhNumber;
import com.se206a3.contactmanager.R;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ContactDetailActivity extends Activity {
	public Contact contact;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contact_detail);
		contact = Contact.toDisplay;
		addContent(); //Add all content of contact to contact_detail layout dynamically.
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.contact_detail, menu);
		return true;

	}


	/**
	 * Creates and sets three basic items:
	 * 		Profile picture
	 * 		Name (FirstName+LastName)
	 * 		Company
	 * 
	 * Then creates three super layouts for the information to be stored in:
	 * 		PhoneBox
	 * 		EmailBox
	 * 		AddressBox
	 * 
	 * Passes these super layouts into the relevant function.
	 * 			 */
	public void addContent(){

		//Create three basic items and super layouts
		ImageView ProfilePic = (ImageView)findViewById(R.id.Profile_pic);
		TextView Name = (TextView)findViewById(R.id.Name);
		TextView Company = (TextView)findViewById(R.id.Company);
		TextView DateOfBirth = (TextView)findViewById(R.id.Dob);
		LinearLayout PhoneBox = (LinearLayout)findViewById(R.id.PhoneBox);
		LinearLayout EmailBox = (LinearLayout)findViewById(R.id.EmailBox);
		LinearLayout AddressBox = (LinearLayout)findViewById(R.id.AddBox);

		//Set three basic items
		if(contact.getImagePath()!=null){
			ProfilePic.setImageBitmap(BitmapFactory.decodeFile(contact.getImagePath()));
			ProfilePic.setLayoutParams(new LinearLayout.LayoutParams(-1,-1,3.0f));
		}

		Name.setText(contact.getName().getFirstName()+" "+contact.getName().getLastName());
		Company.setText(contact.getCompany());
		DateOfBirth.setText(contact.getDOB());

		//Fill super layouts
		addPhoneNumbers(PhoneBox);
		addEmails(EmailBox);
		addAddress(AddressBox);

	}

	/**
	 * Fill the super layout with a linear layout containing two text views for each phone number in the contact.
	 * 
	 * @param PhoneBox Layout for all phone number data boxes.
	 */
	public void addPhoneNumbers(LinearLayout PhoneBox){
		for(PhNumber i : contact.numbers){	//For each phone number
			int pos =  contact.numbers.indexOf(i);

			LinearLayout phoneNumberLayout = new LinearLayout(this); //Create layout box for phonenumber and type
			phoneNumberLayout.setOrientation(LinearLayout.VERTICAL);

			//Define type textview
			TextView phoneType = new TextView(this);
			phoneType.setText(contact.numbers.get(pos).getType());
			phoneType.setTextSize(16);
			phoneType.setTextColor(Color.parseColor("#00BFFF")); //Set light blue
			phoneType.setPadding(0, 6, 0, 0);	//Pad 6dp to bottom

			//Define number textview
			TextView phoneNumber = new TextView(this);
			phoneNumber.setText(contact.numbers.get(pos).getNumber());
			phoneNumber.setTextSize(24);

			//Add textviews to layout
			phoneNumberLayout.addView(phoneType);
			phoneNumberLayout.addView(phoneNumber);

			//Add layout to super layout
			PhoneBox.addView(phoneNumberLayout);
		}

		if(contact.numbers.size()==0){
			TextView phoneNumber = new TextView(this);
			phoneNumber.setText("No phone numbers to display");
			PhoneBox.addView(phoneNumber);
		}

		//Add graphical break bar
		addBreakbar(PhoneBox);

	}

	/**
	 * Fill the super layout with a linear layout containing two text views for each email in the contact.
	 * @param EmailBox Super layout for all emails
	 */
	public void addEmails(LinearLayout EmailBox){
		for(Email i : contact.emails){	//For each email in the contact
			int pos =  contact.emails.indexOf(i);

			//Create layout for text views
			LinearLayout emailLayout = new LinearLayout(this);
			emailLayout.setOrientation(LinearLayout.VERTICAL);

			//Define type textview
			TextView emailType = new TextView(this);
			emailType.setText(contact.emails.get(pos).getType());
			emailType.setTextSize(16);
			emailType.setTextColor(Color.parseColor("#00BFFF"));
			emailType.setPadding(0, 6, 0, 0);

			//Define number textview
			TextView email = new TextView(this);
			email.setText(contact.emails.get(pos).getEmail());
			email.setTextSize(24);

			//Add textviews to layout
			emailLayout.addView(emailType);
			emailLayout.addView(email);

			//Add layout to super layout
			EmailBox.addView(emailLayout);
		}

		if(contact.emails.size()==0){
			TextView email = new TextView(this);
			email.setText("No emails to display");
			EmailBox.addView(email);
		}

		//Add graphical break bar
		addBreakbar(EmailBox);

	}

	/**
	 * Fill the super layout with a linear layout containing seven texts view for each address in the contact
	 * @param AddressBox
	 */
	public void addAddress(LinearLayout AddressBox){
		for(Address i : contact.address){
			int pos =  contact.address.indexOf(i);

			LinearLayout addressLayout = new LinearLayout(this);
			addressLayout.setOrientation(LinearLayout.VERTICAL);

			//Create relevant text views
			TextView addressType = new TextView(this);
			addressType.setText(contact.address.get(pos).getType());
			addressType.setTextSize(16);
			addressType.setTextColor(Color.parseColor("#00BFFF"));
			addressType.setPadding(0, 6, 0, 0);

			TextView street1 = new TextView(this);
			street1.setText(contact.address.get(pos).getStreet1());
			street1.setTextSize(20);

			TextView street2 = new TextView(this);
			street2.setText(contact.address.get(pos).getStreet2());
			street2.setTextSize(20);

			TextView suburb = new TextView(this);
			suburb.setText(contact.address.get(pos).getSuburb());
			suburb.setTextSize(20);

			TextView city = new TextView(this);
			city.setText(contact.address.get(pos).getCity());
			city.setTextSize(20);

			TextView postCode = new TextView(this);
			postCode.setText(contact.address.get(pos).getPostCode());
			postCode.setTextSize(20);


			TextView country = new TextView(this);
			country.setText(contact.address.get(pos).getCountry());
			country.setTextSize(20);


			//Add to linear layout
			addressLayout.addView(addressType);
			addressLayout.addView(street1);
			addressLayout.addView(street2);
			addressLayout.addView(suburb);
			addressLayout.addView(city);
			addressLayout.addView(postCode);
			addressLayout.addView(country);

			//Add linear layout to super layout
			AddressBox.addView(addressLayout);
		}

		if(contact.address.size()==0){
			TextView address = new TextView(this);
			address.setText("No addresses to display");
			AddressBox.addView(address);
		}


		addBreakbar(AddressBox);

	}

	/**
	 * Graphical break bar
	 * @param l
	 */
	public void addBreakbar(LinearLayout l){
		TextView breakBar = new TextView(this);
		breakBar.setText("_______________________________________________________________________________________________________________________________________________");
		breakBar.setTextSize(6);
		breakBar.setTextColor(Color.parseColor("#00BFFF"));
		breakBar.setPadding(0, 0, 0, 0);

		l.addView(breakBar);
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_editContact:
			Intent editContact = new Intent();
			editContact.setClass(this, EditContactActivity.class);
			startActivity(editContact);
			this.finish();
			break;
		case R.id.action_deleteContact: 
			AlertDialog.Builder builder = new AlertDialog.Builder(this);

			builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					// User cancelled the dialog
				}
			});
			builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					// User clicked save and quit button
					ContactListActivity.datasource.deleteContact(Contact.toDisplay);
					finish();
				}
			});
			builder.setTitle("Are you sure you want to delete " + Contact.toDisplay.getName().getFirstName() +" "+ Contact.toDisplay.getName().getLastName()+ "?");
			// Set other dialog properties

			// Create the AlertDialog
			AlertDialog dialog = builder.create();
			dialog.show();
			break;
		default:

		}
		return super.onOptionsItemSelected(item);
	}
}
