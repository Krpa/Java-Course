package hr.infinum.fer.ik47861.models;

import hr.infinum.fer.ik47861.R;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Class that serves as adapter for {@link Contact}
 * @author Ivan Krpelnik
 *
 */
public class ContactAdapter extends ArrayAdapter<Contact> {

	private List<Contact> contacts;
	
	public ContactAdapter(Context context, List<Contact> contacts) {
		super(context, 0);
		this.contacts = contacts;
	}
	
	@Override
	public int getCount() {
		return contacts.size();
	}
	
	@Override
	public void add(Contact object) {
		contacts.add(object);
		notifyDataSetChanged();
	}
	
	@Override
	public Contact getItem(int position) {
		return contacts.get(position);
	}
	
	@Override
	public void remove(Contact object) {
		if(contacts.remove(object)) {
			notifyDataSetChanged();
		}
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Contact contact = getItem(position);
		if(convertView == null) {
			convertView = ((LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).
									inflate(R.layout.list_item, parent, false);
		}
		TextView nameText = (TextView) convertView.findViewById(R.id.name);
		nameText.setText(contact.getName());
		TextView infoText = (TextView) convertView.findViewById(R.id.info);
		infoText.setText(contact.getPhone() + ", " + contact.getEmail());
		return convertView;
	}
}
