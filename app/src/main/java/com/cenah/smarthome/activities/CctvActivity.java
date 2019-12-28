package com.cenah.smarthome.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.cenah.smarthome.R;
import com.cenah.smarthome.helpers.PrograssBarDialog;

import java.util.Objects;

public class CctvActivity extends AppCompatActivity {
    private PrograssBarDialog prograssBarDialog;
    private Toolbar toolbar;
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cctv);
        activity = this;
        setToolBar();
        prograssBarDialog = new PrograssBarDialog(this);
    }
    private void setToolBar() {
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("CCTV");
        ((AppCompatActivity) Objects.requireNonNull(activity)).setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        // toolbar.setSubtitle(activity.getString(R.string.today));
    }
}
