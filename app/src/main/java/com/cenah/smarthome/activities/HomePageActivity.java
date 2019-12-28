package com.cenah.smarthome.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.cenah.smarthome.R;
import com.cenah.smarthome.fragments.MainPageFragment;
import com.cenah.smarthome.fragments.NotificationFragment;
import com.cenah.smarthome.fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomePageActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);

        bottomNavigationView.inflateMenu(R.menu.nav_items);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        fragment = new MainPageFragment();
                        setFragment(fragment,false);
                        return true;

                    case R.id.profile:
                        fragment = new ProfileFragment();
                        setFragment(fragment,false);

                        return true;

                    case R.id.notificatons:
                        androidx.fragment.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_layout, new NotificationFragment());
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                        return true;

                    default:
                        return false;

                }


            }
        });

        fragment = new MainPageFragment();
        setFragment(fragment,true);
    }

    public void setFragment(Fragment fragment,boolean isSatrt) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_layout, fragment);
        // if(!isSatrt) fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getFragmentManager();
        int backStackEntryCount = fm.getBackStackEntryCount();
        if (backStackEntryCount == 0) {
            new AlertDialog.Builder(this)
                    .setTitle(getString(R.string.are_you_s))
                    .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).show();
        } else if (backStackEntryCount == 1) {
            setNavigationVisibility(true);
            super.onBackPressed();
        } else {
            super.onBackPressed();
        }
    }

    public void setNavigationVisibility(boolean visible) {
        if (bottomNavigationView.isShown() && !visible) {
            bottomNavigationView.setVisibility(View.GONE);
        } else if (!bottomNavigationView.isShown() && visible) {
            bottomNavigationView.setVisibility(View.VISIBLE);
        }
    }

}
