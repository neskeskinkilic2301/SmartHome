package com.cenah.smarthome.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.widget.Toolbar;

import com.cenah.smarthome.R;

import java.util.Objects;

public class IpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ip);

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://192.186.43.230/"));
                startActivity(browserIntent);
            }
        });
        setToolBar();
    }

    private void setToolBar() {
       Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("CAM");
        ((AppCompatActivity) Objects.requireNonNull(IpActivity.this)).setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        // toolbar.setSubtitle(activity.getString(R.string.today));
    }
}
