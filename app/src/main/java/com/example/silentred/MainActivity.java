package com.example.silentred;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private final int readContactCode = 111;
    private SettingFrag settingFrag;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();
        // remove setting fragment to avoid more than 1 object of that fragment
        settingFrag =  (SettingFrag) fragmentManager.findFragmentByTag("settingTag");
        if(settingFrag != null) {
            fragmentManager.beginTransaction().remove(settingFrag).commit();
        }

        requestPermissions();
    }

    private void requestPermissions(){

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED )
        {
            ActivityCompat.requestPermissions(this,new String[]{ Manifest.permission.READ_CONTACTS},readContactCode);
        }

        // TODO: add isNotificationListenerAccessGranted(ComponentName listener) check before asking permission to listen to notifications
      //  isNotificationListenerAccessGranted(new ComponentName())
        if(!RedColorNotificationListenerService.isNotificationAccessEnabled) {
            // open security settings - the user needs to allow listening to notifications
            Intent intent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
            startActivity(intent);
        }
        // TODO: check if user didn't grantee this permission and show message that this app won't work properly (not alert when 'red color' app produce notification)
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,@NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == readContactCode) {// If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted. Continue the action or workflow
                // in your app.
                Log.i(RedColorNotificationListenerService.TAG, "onRequestPermissionsResult: Has permissions for Read Contact");
            } else {
                // TODO: maybe to change this to dialog
                Toast.makeText(this, "You won't be able to select person from your contacts as an emergency number due the lack of Read Contact permission ", Toast.LENGTH_LONG).show();

                // Explain to the user that the feature is unavailable because
                // the features requires a permission that the user has denied.
                // At the same time, respect the user's decision. Don't link to
                // system settings in an effort to convince the user to change
                // their decision.

            }
        }
        // Other 'case' lines to check for other
        // permissions this app might request.
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu ) {
        getMenuInflater().inflate(R.menu.exit_menu, menu);
        getMenuInflater().inflate(R.menu.setting_menu, menu);
        super.onCreateOptionsMenu(menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.exit_b:
                showExitDialog();
                return super.onOptionsItemSelected(item);

            case R.id.settings:
                inflateSettingsFragment();
                return super.onOptionsItemSelected(item);
            default:
                //TODO: add catch statement somewhere
                throw new IllegalStateException("Unexpected value: " + item.getItemId());
        }
    }

    private void inflateSettingsFragment() {
        settingFrag = new SettingFrag();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.add(R.id.fragContainer, settingFrag, "detailsTag");
        ft.addToBackStack("BBB");
        // hide the count down fragment
        Fragment bottomFragment = fragmentManager.findFragmentById(R.id.count_down_fragment);
        if (bottomFragment != null) {
            ft.hide(bottomFragment).commit();
        }
        fragmentManager.executePendingTransactions();
    }

    private void showExitDialog() {
        DialogFragment newFragment = MyAlertDialogFragment.newInstance("Are you sure you want to exit");
        newFragment.show(getSupportFragmentManager(),"exit message");
    }

    // TODO: maybe need to ba changed to singleton instead of static
    public static class MyAlertDialogFragment extends DialogFragment {

        public static MyAlertDialogFragment newInstance(String title) {
            MyAlertDialogFragment frag = new MyAlertDialogFragment();
            Bundle args = new Bundle();
            args.putString("title", title);
            frag.setArguments(args);
            return frag;
        }
        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            String title = "";
            if(getArguments() != null) {
                title = getArguments().getString("title");
            }
            return new AlertDialog.Builder(requireActivity())
                    .setTitle(title)
                    .setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    ((MainActivity) requireActivity()).doPositiveClick();
                                }
                            }
                    )
                    .setNegativeButton("No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    ((MainActivity)requireActivity()).doNegativeClick();
                                }
                            }
                    )
                    .create();
        }
    }
    // exit dialog handle button ok
    public void doPositiveClick() {
        // Do stuff here.
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
        Log.i("FragmentAlertDialog", "Positive click!");
    }
    // exit dialog handle button cancel
    public void doNegativeClick() {
        // Do stuff here.
        Log.i("FragmentAlertDialog", "Negative click!");
    }

}
