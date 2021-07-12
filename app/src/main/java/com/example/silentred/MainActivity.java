package com.example.silentred;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.app.Dialog;
//import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.content.res.Configuration;


public class MainActivity extends AppCompatActivity {

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
                showDialog();

                return true;

            case R.id.settings:
                settingFrag = new SettingFrag();

                manager.beginTransaction().
                        add(R.id.r, settingFrag, "detailsTag").
                        addToBackStack("BBB").
                        commit();
                manager.executePendingTransactions();
/*manager.beginTransaction().add()
                manager.beginTransaction().
                        add(R.id.r,settingFrag, "settingTag").
                        addToBackStack("BBB").
                        commit();
                manager.executePendingTransactions();*/
                //LayoutInflater inflater = null;
                //ViewGroup container = null;
                //inflater.inflate(R.layout.setting_layout, , false);
                //seekBarFragment = SeekBarDialog.newInstance("Change Precision",seekBarPrecision);
               // seekBarFragment.show(getSupportFragmentManager(),"precision");

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    void showDialog() {
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
