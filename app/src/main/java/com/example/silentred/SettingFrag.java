package com.example.silentred;

//import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

//import android.ragment.app.Fragment;
//import androidx.fragment.app.FragmentActivity;

import java.math.BigDecimal;

public class SettingFrag extends Fragment {


    private Context context;

    @Override
    public void onAttach(@NonNull Context context) {
        try {
            this.context = context;

            FragmentActivity myContext = (FragmentActivity) context;
        } catch (ClassCastException e) {
            throw new ClassCastException("the class " +
                    getActivity().getClass().getName() +
                    " must implements the interface 'ClickHandler'");
        }
        super.onAttach(context);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.setting_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {


       /* result = view.findViewById(R.id.result);
        opTXT = view.findViewById(R.id.opTXT);
        if (savedInstanceState != null)
            result.setText(savedInstanceState.getString("result"));

        BigDecimal ToShow = new BigDecimal(ResultSeekBar);
        result.setText(ToShow.setScale(precision, BigDecimal.ROUND_HALF_EVEN).toPlainString());

        super.onViewCreated(view, savedInstanceState);
*/
        //   oprandsTXT=view.findViewById(R.id.);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        //this.tvValue.setText(Integer.toString(listener.getClickCount())+ FragB.internalData);
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        /*savedInstanceState.putString("op1", op1TXT);
        savedInstanceState.putString("op2", op2TXT);
        savedInstanceState.putString("action", action);
        savedInstanceState.putString("result", result.getText().toString());*/


    }



}
