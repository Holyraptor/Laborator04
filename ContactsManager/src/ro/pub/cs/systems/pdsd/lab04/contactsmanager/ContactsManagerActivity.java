package ro.pub.cs.systems.pdsd.lab04.contactsmanager;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.SyncStateContract.Constants;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;


public class ContactsManagerActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_manager);
        
        Button showFieldsBtn = (Button)findViewById(R.id.button1);
        Button saveBtn = (Button)findViewById(R.id.button2);
        Button cancelBtn = (Button)findViewById(R.id.button3);
        
        showFieldsBtn.setOnClickListener(new ButtonListener(showFieldsBtn));
        saveBtn.setOnClickListener(new ButtonListener(saveBtn));
        cancelBtn.setOnClickListener(new ButtonListener(cancelBtn));
        
        EditText phoneText = (EditText)findViewById(R.id.editText2);
        
        Intent intent = getIntent();
        if (intent != null) {
          String phone = intent.getStringExtra("ro.pub.cs.systems.pdsd.lab04.contactsmanager.PHONE_NUMBER_KEY");
          if (phone != null) {
        	  phoneText.setText(phone);
          } else {
            Toast.makeText(this, "Error on phone saving", Toast.LENGTH_LONG).show();
          }
        } 
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.contacts_manager, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    

	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		switch(requestCode) {
		  case 21:
		    setResult(resultCode, new Intent());
		    finish();
		    break;
		  }
		}
	
    public class ButtonListener implements View.OnClickListener{
    	private Button myButton;
    	
    	public ButtonListener(Button _button){
    		myButton = _button;
    	}
    	
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			int buttonId = myButton.getId();
			if(buttonId == R.id.button1){
				String buttonText = myButton.getText().toString();
				LinearLayout extraData = (LinearLayout)findViewById(R.id.linearLayout2);
				
				if(buttonText == "Show Additional Fields"){
					myButton.setText("Hide Additional Fields");
					extraData.setVisibility(View.VISIBLE);
				}
				else{
					myButton.setText("Show Additional Fields");
					extraData.setVisibility(View.GONE);
				}
			}
			
			/* Save */
			if(buttonId == R.id.button2){
				Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
				intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
				String name = ((EditText)findViewById(R.id.editText1)).getText().toString();
				String phone = ((EditText)findViewById(R.id.editText2)).getText().toString();
				String email = ((EditText)findViewById(R.id.editText3)).getText().toString();
				String address = ((EditText)findViewById(R.id.editText4)).getText().toString();
				String jobTitle = ((EditText)findViewById(R.id.editText5)).getText().toString();
				String company = ((EditText)findViewById(R.id.editText6)).getText().toString();
				String website = ((EditText)findViewById(R.id.editText7)).getText().toString();
				String im = ((EditText)findViewById(R.id.editText8)).getText().toString();
				
				if (name != null) {
				  intent.putExtra(ContactsContract.Intents.Insert.NAME, name);
				}
				if (phone != null) {
				  intent.putExtra(ContactsContract.Intents.Insert.PHONE, phone);
				}
				if (email != null) {
				  intent.putExtra(ContactsContract.Intents.Insert.EMAIL, email);
				}
				if (address != null) {
				  intent.putExtra(ContactsContract.Intents.Insert.POSTAL, address);
				}
				if (jobTitle != null) {
				  intent.putExtra(ContactsContract.Intents.Insert.JOB_TITLE, jobTitle);
				}
				if (company != null) {
				  intent.putExtra(ContactsContract.Intents.Insert.COMPANY, company);
				}
				ArrayList<ContentValues> contactData = new ArrayList<ContentValues>();
				if (website != null) {
				  ContentValues websiteRow = new ContentValues();
				  websiteRow.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Website.CONTENT_ITEM_TYPE);
				  websiteRow.put(ContactsContract.CommonDataKinds.Website.URL, website);
				  contactData.add(websiteRow);
				}
				if (im != null) {
				  ContentValues imRow = new ContentValues();
				  imRow.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE);
				  imRow.put(ContactsContract.CommonDataKinds.Im.DATA, im);
				  contactData.add(imRow);
				}
				intent.putParcelableArrayListExtra(ContactsContract.Intents.Insert.DATA, contactData);
				
				startActivityForResult(intent, 21);
			}
			
			/* Cancel */
			if(buttonId == R.id.button3){
				setResult(Activity.RESULT_CANCELED, new Intent());
			}
		}
    	
    }
}
