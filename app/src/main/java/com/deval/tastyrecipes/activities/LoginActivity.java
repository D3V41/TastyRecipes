package com.deval.tastyrecipes.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.deval.tastyrecipes.R;
import com.deval.tastyrecipes.fragments.HomeFragment;
import com.deval.tastyrecipes.fragments.LoginFragment;
import com.deval.tastyrecipes.fragments.RegisterFragment;

public class LoginActivity extends AppCompatActivity {

    //declaring widgets
    FrameLayout frameView;
    Button loginSwitch,registerSwitch;
    TextView txtLoginSwitch,txtRegisterSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //initializing widgets
        frameView = findViewById(R.id.loginFragmentContainer);
        loginSwitch = findViewById(R.id.loginSwitch);
        registerSwitch = findViewById(R.id.registerSwitch);
        txtLoginSwitch = findViewById(R.id.txtLoginSwitch);
        txtRegisterSwitch = findViewById(R.id.txtRegisterSwitch);

        //Initially displaying LoginFragment to Login Activity
        Fragment fragment = new LoginFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.loginFragmentContainer,fragment);
        fragmentTransaction.commit();

        //Set Login Button Invisible which is used to navigate Login Fragment
        loginSwitch.setVisibility(View.GONE);
        txtLoginSwitch.setVisibility(View.GONE);

        //login Switch button Onclick event
        loginSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //displaying LoginFragment to Login Activity
                Fragment frag = new LoginFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.loginFragmentContainer,frag);
                fragmentTransaction.commit();

                //Set Login Button Invisible which is used to navigate Login Fragment
                loginSwitch.setVisibility(View.GONE);
                txtLoginSwitch.setVisibility(View.GONE);

                //Set Register Button Visible which is used to navigate Register Fragment
                registerSwitch.setVisibility(View.VISIBLE);
                txtRegisterSwitch.setVisibility(View.VISIBLE);

            }
        });

        //register Switch button Onclick event
        registerSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //displaying RegisterFragment to Login Activity
                Fragment frag = new RegisterFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.loginFragmentContainer,frag);
                fragmentTransaction.commit();

                //Set Login Button Visible which is used to navigate Login Fragment
                loginSwitch.setVisibility(View.VISIBLE);
                txtLoginSwitch.setVisibility(View.VISIBLE);

                //Set Register Button Invisible which is used to navigate Register Fragment
                registerSwitch.setVisibility(View.GONE);
                txtRegisterSwitch.setVisibility(View.GONE);
            }
        });
    }
}