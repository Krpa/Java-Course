package hr.infinum.fer.ik47861;

import hr.infinum.fer.ik47861.models.Contact;
import hr.infinum.fer.ik47861.models.ContactAdapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

/**
 * Main activity for Address Book.
 * Lists all stored contacts and offers add button to add new contact.
 * @author Ivan Krpelnik
 *
 */
public class HomeActivity extends Activity {
	
	private static final String contacts = "people.json";
	private static final List<Contact> contactList = new ArrayList<Contact>();
	private static ContactAdapter contactAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		if(contactList.isEmpty()) {
			Log.d("Fill", "Puni se lista");
			fillContactList();
		}
		init();
	}

	
	/**
	 * Initialize add button and list view.
	 */
	private void init() {
		
		Button addButton = (Button) findViewById(R.id.button_add);
		addButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(HomeActivity.this, AddContactActivity.class);
				startActivity(intent);
			}
		});
		contactAdapter = new ContactAdapter(this, contactList);
		ListView list = (ListView) findViewById(R.id.list_contacts);
		list.setAdapter(contactAdapter);
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
				Log.d("INDEX", "Index je: " + position);
				intent.putExtra(ProfileActivity.CONTACT, position);
				startActivity(intent);
			}
			
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
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
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Log.d("LIST", "promjena dataseta");
		contactAdapter.notifyDataSetChanged();
	}
	
	/**
	 * Fills contact list from JSON file.
	 */
	private void fillContactList() {
		try {
			JSONArray jsonArray = new JSONObject(readFile(contacts)).getJSONArray("people");
			int size = jsonArray.length();
			for(int i = 0; i < size; ++i) {
				Contact contact = new Contact();
				contact.fillFromJSONObject(jsonArray.getJSONObject(i).getJSONObject("person"));
				contactList.add(contact);
			}
		} catch (JSONException e) {
			Log.e("JSON", e.toString());
		}
		
	}
	
	/**
	 * Constructs a string from a file.
	 * @param fileName - name of a file to be read.
	 * @return String with filled with contents of a file. <br>
	 * 			Empty string is returned if reading failed.
	 */
	private String readFile(String fileName) {
		StringBuilder sb = new StringBuilder();
		
		AssetManager assetManager = getAssets();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(assetManager.open(fileName)));
			String line;
			while((line = reader.readLine()) != null) {
				sb.append(line);
			}
			reader.close();
		} catch (IOException e) {
			return "";
		}

		return sb.toString();
	}
	
	/**
	 * Adds contact to list.
	 * @param contact to be added
	 */
	public static void addContact(Contact contact) {
		contactAdapter.add(contact);
	}
	
	/**
	 * Removes contact from list.
	 * @param contact to be removed
	 */
	public static void removeContact(Contact contact) {
		contactAdapter.remove(contact);
	}
	
	/**
	 * Returns unmodifiable list with all contacts.
	 * @return list of all contacts
	 */
	public static List<Contact> getContactList() {
		return Collections.unmodifiableList(contactList);
	}
}
