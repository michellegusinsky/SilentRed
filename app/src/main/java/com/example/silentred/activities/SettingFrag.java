package com.example.silentred.activities;


import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.silentred.R;

import com.example.silentred.model.Area;
import com.example.silentred.xml.LoadAreasXML;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


import static android.content.Context.MODE_PRIVATE;


public class SettingFrag extends Fragment implements OnClickListener  {

    // region members
    private static final int PICK_CONTACT = 123;
    private final String fileName =("myPreferencesFile");
    public static SharedPreferences sp;
    private String userLocation;
    private String toSaveEmergencyName;
    private String toSaveEmergencyNumber;
    private Integer flashPerSecond_seekBarProgress;
    private Integer userLocationPosition;
    private boolean flashFrequencyChanged =false;
    private boolean areaChanged=false;
    private boolean emergencyChanged=false;
    // endregion

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (getActivity() != null) {
            sp = getActivity().getSharedPreferences(fileName, MODE_PRIVATE);
        }
        return inflater.inflate(R.layout.setting_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // set listener to save btn
        Button btnSave = view.findViewById(R.id.buttonSave);
        setListenerSaveBtn(btnSave, view);

        // set text to user name from SP file
        EditText userNameEditText=view.findViewById(R.id.editTextTextPersonName);
        userNameEditText.setText(sp.getString("userName",""));

        // set seek bar
        setSeekBar(view);

        // set spinner (combo box)
        setSpinnerOfAreas(view);

        // Emergency contact
        setEmergencyContactViews(view);

    }

    private void setEmergencyContactViews(View view){

        view.findViewById(R.id.EmergencyContact_btn).setOnClickListener(this);
        TextView eName = view.findViewById(R.id.contact_name_textView);
        TextView eNumber = view.findViewById(R.id.contact_number_textView);
        eName.setText(sp.getString("EmergencyName",""));
        eNumber.setText(sp.getString("EmergencyNumber",""));
    }

    private void setSpinnerOfAreas(View view){

        ArrayList<Area> arrayArea = LoadAreasXML.parseAreas(getContext());
        ArrayList<String> arrayAreasNames = new ArrayList<>();

        for(Area area:arrayArea){
            arrayAreasNames.add(area.getName());
        }
        String[] namesOfAreas = arrayAreasNames.toArray(new String[arrayAreasNames.size()]);

        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter(this.getContext(), android.R.layout.simple_spinner_dropdown_item, namesOfAreas);
        // create a spinner
        Spinner spinner = view.findViewById(R.id.areas_spinner);
        // add adapter to spinner
        spinner.setAdapter(stringArrayAdapter);
        // create listener and add to spinner
        spinner.setSelection(sp.getInt("AreaPos",0));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                userLocation = namesOfAreas[position];
                userLocationPosition = position;
                areaChanged = true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void setSeekBar(View view) {
        final SeekBar mySeekBar = view.findViewById(R.id.seekBar);
        if (getActivity() != null) {

            TextView seekBarText = view.findViewById(R.id.seekBar_text);
            String seekBarTextString = ("Flash " + sp.getInt("flashPerSecond", 1) + " times per second");
            seekBarText.setText(seekBarTextString);
            mySeekBar.setProgress(sp.getInt("flashPerSecond", 1));
            mySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    TextView seekBarTextView = view.findViewById(R.id.seekBar_text);
                    String seekBarTextString = "Flash " + progress + " times per second";
                    seekBarTextView.setText(seekBarTextString);
                    flashPerSecond_seekBarProgress = progress;
                    flashFrequencyChanged = true;
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {}

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {}
            });
        }
    }

    private void setListenerSaveBtn(Button btnSave, View view){
        btnSave.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if (getActivity() != null) {
                    sp = getActivity().getSharedPreferences(fileName, MODE_PRIVATE);
                    SharedPreferences.Editor myEdit = sp.edit();

                    EditText userText = view.findViewById(R.id.editTextTextPersonName);
                    myEdit.putString("userName", userText.getText().toString());
                    if (flashFrequencyChanged)
                        myEdit.putInt("flashPerSecond", flashPerSecond_seekBarProgress);
                    if (areaChanged) {
                        myEdit.putString("Area", userLocation);
                        myEdit.putInt("AreaPos", userLocationPosition);
                    }
                    if (emergencyChanged) {
                        myEdit.putString("EmergencyName", toSaveEmergencyName);
                        myEdit.putString("EmergencyNumber", toSaveEmergencyNumber);
                    }
                    myEdit.apply();
                    Toast toast = Toast.makeText(getContext(), "Preferences Saved Successfully", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }

    //this callback for the  button emergency contact btn
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.EmergencyContact_btn) {
            Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
            startActivityForResult(intent, PICK_CONTACT);
        }
    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        if (reqCode == PICK_CONTACT) {
            if (getActivity() == null) return;
            ContentResolver contentResolver = getActivity().getBaseContext().getContentResolver();
            String contactName = "";
            String hasNumber = "";
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
                            String contactNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            int type = phones.getInt(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
                            switch (type) {
                                case ContactsContract.CommonDataKinds.Phone.TYPE_HOME:
                                    // do something with the Home number here...
                                    break;
                                case ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE:
                                    toSaveEmergencyName=contactName;
                                    toSaveEmergencyNumber=contactNumber;
                                    emergencyChanged=true;
                                    if (getView() == null) return;
                                    TextView eName = getView().findViewById(R.id.contact_name_textView);
                                    TextView eNumber = getView().findViewById(R.id.contact_number_textView);
                                    eName.setText(contactName);
                                    eNumber.setText(contactNumber);
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
    

