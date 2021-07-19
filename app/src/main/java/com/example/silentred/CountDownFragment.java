package com.example.silentred;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import androidx.fragment.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class CountDownFragment extends Fragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
                return inflater.inflate(R.layout.count_down_layout, container,false);
        }

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState){
           Button btn = view.findViewById(R.id.stop_btn);
                btn.setOnClickListener(btn_stop -> {
                        //TODO: implement this function: the count down text need to be updated

                        Intent intent = new Intent(getActivity(), NotificationReceiver.class);
                        intent.setAction(NotificationReceiver.intentStopButtonClickedAction);
                        // send to notification receiver for stop the flash light
                        if (getActivity() != null) {
                                getActivity().sendBroadcast(intent);
                        }
                }
                );
                super.onViewCreated(view, savedInstanceState);
        }

}