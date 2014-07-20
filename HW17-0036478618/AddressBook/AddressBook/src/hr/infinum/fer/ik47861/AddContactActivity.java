package hr.infinum.fer.ik47861;

import hr.infinum.fer.ik47861.models.Contact;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Activity that adds a contact.
 * Name, phone and email fields must not be empty.
 * Offers save and cancel buttons.
 * @author Ivan Krpelnik
 *
 */
public class AddContactActivity extends Activity {

	
	private EditText nameText;
	private EditText phoneText;
	private EditText emailText;
	private EditText noteText;
	private EditText facebookText;
	private Button saveButton;
	private Button cancelButton;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_contact);
		getUI();

		cancelButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		saveButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(!isValid()) {
					return;
				}
				
				
				Contact contact = fillContact();
				HomeActivity.addContact(contact);
				finish();
			}
			
		});
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.add_contact, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
		
	}
	
	private Contact fillContact() {
		Contact contact = new Contact();
		contact.setName(nameText.getText().toString().trim());
		contact.setPhone(phoneText.getText().toString().trim());
		contact.setEmail(emailText.getText().toString().trim());
		contact.setNote(noteText.getText().toString().trim());
		contact.setFacebookProfile(facebookText.getText().toString().trim());
		return contact;
	}

	private void getUI() {
		nameText = (EditText) findViewById(R.id.name);
		phoneText = (EditText) findViewById(R.id.phone);
		emailText = (EditText) findViewById(R.id.email);
		noteText = (EditText) findViewById(R.id.note);
		facebookText = (EditText) findViewById(R.id.facebook_profile);
		
		saveButton = (Button) findViewById(R.id.button_save);
		cancelButton = (Button) findViewById(R.id.button_cancel);
	}
	
	private boolean isValid() {
		if(nameText.getText().toString().trim().isEmpty()) {
			Toast.makeText(this, "Kontakt mora imati ime!", Toast.LENGTH_LONG).show();
			return false;
		}
		if(emailText.getText().toString().trim().isEmpty()) {
			Toast.makeText(this, "Kontakt mora imati email!", Toast.LENGTH_LONG).show();
			return false;
		}
		if(phoneText.getText().toString().trim().isEmpty()) {
			Toast.makeText(this, "Kontakt mora imati broj telefona!", Toast.LENGTH_LONG).show();
			return false;
		}
		return true;
	}
}
