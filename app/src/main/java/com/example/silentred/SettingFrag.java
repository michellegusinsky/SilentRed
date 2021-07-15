package com.example.silentred;


import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import androidx.fragment.app.Fragment;


public class SettingFrag extends Fragment implements OnClickListener{
    // Declare
    static final int PICK_CONTACT=123;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.setting_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        view.findViewById(R.id.EmergencyContact_btn).setOnClickListener(this);
    }


//this callback for the  button emergency contact btn
    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.EmergencyContact_btn) {
            Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
            startActivityForResult(intent, PICK_CONTACT);
        }
    }
    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        if (reqCode==PICK_CONTACT)
        {
                ContentResolver contentResolver=getActivity().getBaseContext().getContentResolver();
                String contactName="";
                String hasNumber="";
                if (resultCode == Activity.RESULT_OK) {
                    Uri contactData = data.getData();
                    Cursor c = contentResolver.query(contactData, null, null, null, null);
                    if (c.moveToFirst()) {
                        hasNumber = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.HAS_PHONE_NUMBER));
                        int nameIndex = c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                        contactName = c.getString(nameIndex);
                    }
                    if (hasNumber.equalsIgnoreCase("1")) {
                        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null,
                                "DISPLAY_NAME = '" + contactName + "'", null, null);
                        if (cursor.moveToFirst()) {

                            String contactId =
                                    cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                            //
                            //  Get all phone numbers.
                            //
                            Cursor phones = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
                            while (phones.moveToNext()) {
                                String number = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                int type = phones.getInt(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
                                switch (type) {
                                    case ContactsContract.CommonDataKinds.Phone.TYPE_HOME:
                                        // do something with the Home number here...
                                        break;
                                    case ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE:
                                        System.out.println("contact's name is:  " + contactName + "contact's number is: " + number + " ");
                                        break;
                                    case ContactsContract.CommonDataKinds.Phone.TYPE_WORK:
                                        // do something with the Work number here...
                                        break;
                                }
                            }
                            phones.close();
                        }
                        cursor.close();
                        c.close();
                    } else {
                        System.out.println("there is no number");
                    }
                }
        }
    }
}
