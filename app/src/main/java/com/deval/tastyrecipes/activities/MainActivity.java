package com.deval.tastyrecipes.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.deval.tastyrecipes.R;
import com.deval.tastyrecipes.fragments.FavouriteFragment;
import com.deval.tastyrecipes.fragments.HomeFragment;
import com.deval.tastyrecipes.fragments.ProfileFragment;
import com.deval.tastyrecipes.fragments.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    //declaring widgets
    BottomNavigationView bottomNavigationView;
    FrameLayout frameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initializing widgets
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        frameView = findViewById(R.id.frame);

        //when app open by default home is selected in bottom navigation bar and home fragment is
        //in frame layout
        bottomNavigationView.setBottom(R.id.homeFrag);
        Fragment fragment = new HomeFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame,fragment);
        fragmentTransaction.commit();

        //on bottom navigation view by clicking(search,home,favourite,profile) icon changing fragments
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment frag = null;
                int itemId = item.getItemId();
                if(itemId == R.id.homeFrag){
                    frag = new HomeFragment();
                }else if(itemId == R.id.favouriteFrag){
                    frag = new FavouriteFragment();
                }else if(itemId == R.id.searchFrag){
                    frag = new SearchFragment();
                }else if(itemId == R.id.profileFrag){
                    frag = new ProfileFragment();
                }

                if(frag != null){
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frame,frag);
                    fragmentTransaction.commit();
                    return true;
                }

                return false;
            }
        });
    }
}