package com.lammyngoc.bookstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class MyContactActivity extends AppCompatActivity {
    EditText edtContactName;
    EditText edtPhoneNumber;
    ListView lvContact;
    ArrayAdapter<String> contactAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_contact);
        addViews();
        loadContacts();
        addEvents();
    }

    private void addEvents() {
        lvContact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String contact=contactAdapter.getItem(i);
                String []arr=contact.split("\n");
                String name=arr[0];
                String phone=arr[1];
                Intent intent=new Intent(MyContactActivity.this, MySMSActivity.class);
                intent.putExtra("ContactName",name);
                intent.putExtra("PhoneNumber",phone);
                startActivity(intent);
            }
        });
    }

    private void loadContacts() {
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        Cursor cursor = getContentResolver().query(uri,
                null, null, null, null);

        contactAdapter.clear();

        while (cursor.moveToNext())
        {
            int nameIndex =cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
            String name = cursor.getString(nameIndex); //Get Name
            int phoneIndex =cursor.getColumnIndex(ContactsContract.CommonDataKinds. Phone.NUMBER);
            String phone = cursor.getString(phoneIndex); //Get Phone Number
            contactAdapter.add(name+"\n"+phone);
        }
        cursor.close();
    }

    private void addViews() {
        edtContactName=findViewById(R.id.edtContactName);
        edtPhoneNumber=findViewById(R.id.edtPhoneNumber);
        lvContact=findViewById(R.id.lvContact);
        contactAdapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        lvContact.setAdapter(contactAdapter);
    }

    public void doInsertContact (View view)
    {
        Uri addContactsUri = ContactsContract.Data.CONTENT_URI;
        //Add an empty contact and get the generated Id
        long rowContactId = getRawContactId();
        //Add contact name data
        insertContactDisplayName(addContactsUri, rowContactId, edtContactName.getText().toString());
        //Add phone number
        insertContactPhoneNumber(addContactsUri, rowContactId, edtPhoneNumber.getText().toString());
    }

    private long getRawContactId() {//Insert an empty contact
        ContentValues values = new ContentValues();
        Uri rawContactUri = getContentResolver().insert(ContactsContract.RawContacts.CONTENT_URI, values);
        return ContentUris.parseId(rawContactUri);
    }

    private void insertContactDisplayName(Uri addContactsUri, long rawContactId, String displayName)
    {
        ContentValues values = new ContentValues();
        values.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);
        values.put(ContactsContract.Data.MIMETYPE,
                ContactsContract.CommonDataKinds. StructuredName.CONTENT_ITEM_TYPE);
        values.put(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, displayName);
        getContentResolver().insert(addContactsUri, values);
    }

    private void insertContactPhoneNumber(Uri addContactsUri, long rawContactId, String phoneNumber)
    {
        ContentValues values = new ContentValues();
        values.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);
        values.put(ContactsContract.Data.MIMETYPE,
                ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
        values.put(ContactsContract.CommonDataKinds.Phone.NUMBER, phoneNumber);
        getContentResolver().insert(addContactsUri, values);
    }

}