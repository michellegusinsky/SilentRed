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
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private final int readContactCode=111;
    private SettingFrag settingFrag;
    private FragmentManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manager = (FragmentManager)getSupportFragmentManager();
       settingFrag =  (SettingFrag)manager.findFragmentByTag("settingTag");
       if(settingFrag!=null) {
            manager.beginTransaction().remove(settingFrag).commit();
       }

        requestPermissions();
    }
    private void requestPermissions(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED )
        {
            ActivityCompat.requestPermissions(this,new String[]{ Manifest.permission.READ_CONTACTS},readContactCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,@NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == readContactCode) {// If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted. Continue the action or workflow
                // in your app.
                Log.i("onRequestPermissionsResult", "Has permissions for Read Contact");
            } else {
                Toast.makeText(this, "Contact content won't be read due the lack of Read Contact permission ", Toast.LENGTH_LONG).show();

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.exit_b:
                showExitDialog();

                return true;

            case R.id.settings:
                settingFrag = new SettingFrag();
                FragmentTransaction ft =manager.beginTransaction();
                ft.add(R.id.fragContainer, settingFrag, "detailsTag");
                ft.addToBackStack("BBB");
                Fragment bottomFragment = manager.findFragmentById(R.id.count_down_fragment);
                ft.hide(bottomFragment);
                ft.commit();
                manager.executePendingTransactions();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    void showExitDialog() {
        DialogFragment newFragment = MyAlertDialogFragment.newInstance("Are you sure you want to exit");
        newFragment.show(getSupportFragmentManager(),"exit message");

    }

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
            String title = getArguments().getString("title");

            return new AlertDialog.Builder(getActivity())

                    .setTitle(title)
                    .setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    ((MainActivity)getActivity()).doPositiveClick();
                                }
                            }
                    )
                    .setNegativeButton("No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    ((MainActivity)getActivity()).doNegativeClick();
                                }
                            }
                    )
                    .create();
        }
    }

    public void doPositiveClick() {
        // Do stuff here.
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
        Log.i("FragmentAlertDialog", "Positive click!");
    }

    public void doNegativeClick() {
        // Do stuff here.
        Log.i("FragmentAlertDialog", "Negative click!");
    }

}
