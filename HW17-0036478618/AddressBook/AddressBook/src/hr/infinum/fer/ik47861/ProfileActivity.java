package hr.infinum.fer.ik47861;

import hr.infinum.fer.ik47861.models.Contact;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Profile activity for Address Book.
 * Lists information about the contact and offer two buttons.
 * Call button - call contact.
 * Facebook button - goes on contacts facebook.
 * @author Ivan Krpelnik
 *
 */
public class ProfileActivity extends Activity {

	public static final String CONTACT = "Contact_position";
	
	private TextView nameView;
	private TextView phoneView;
	private TextView emailView;
	private TextView noteView;
	private Button callButton;
	private Button facebookButton;
	
	/**
	 * Get components of user interface.
	 */
	private void getUI() {
		nameView = (TextView) findViewById(R.id.name);
		phoneView = (TextView) findViewById(R.id.phone);
		emailView = (TextView) findViewById(R.id.email);
		noteView = (TextView) findViewById(R.id.note);
		callButton = (Button) findViewById(R.id.call);
		facebookButton = (Button) findViewById(R.id.go_facebook);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		getUI();
		Bundle bundle = getIntent().getExtras();
		
		final Contact contact = HomeActivity.getContactList().get(bundle.getInt(CONTACT));
		
		nameView.setText(contact.getName());
		phoneView.setText(contact.getPhone());
		emailView.setText(contact.getEmail());
		noteView.setText(contact.getNote());
		
		callButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+contact.getPhone()));
				startActivity(intent);
			}
		});
		
		facebookButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(contact.getFacebookProfile()));
				startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.profile, menu);
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
}
