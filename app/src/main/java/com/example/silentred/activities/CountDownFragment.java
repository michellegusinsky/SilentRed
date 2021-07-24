package com.example.silentred.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import androidx.fragment.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.silentred.service.NotificationReceiver;
import com.example.silentred.R;

public class CountDownFragment extends Fragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
                return inflater.inflate(R.layout.count_down_layout, container,false);
        }

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState){
           Button btn = view.findViewById(R.id.stop_btn);

                btn.setOnClickListener(btn_stop -> { // Called each time the button stop clickes
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