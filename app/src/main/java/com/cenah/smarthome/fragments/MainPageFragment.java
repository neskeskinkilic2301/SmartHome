package com.cenah.smarthome.fragments;


import android.content.Intent;
import android.os.Bundle;

import android.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cenah.smarthome.R;
import com.cenah.smarthome.activities.CctvActivity;
import com.cenah.smarthome.activities.RegisterActivity;
import com.cenah.smarthome.helpers.PrograssBarDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainPageFragment extends Fragment {


    private View rootView;
    private PrograssBarDialog prograssBarDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_main_page, container, false);

        prograssBarDialog = new PrograssBarDialog(getActivity());
        clicklistners();

        return rootView;
    }

    private void clicklistners() {
        rootView.findViewById(R.id.btn_cctv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), CctvActivity.class));
            }
        });
        rootView.findViewById(R.id.btn_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), RegisterActivity.class));
            }
        });
    }

}
