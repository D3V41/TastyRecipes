package com.deval.tastyrecipes.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.deval.tastyrecipes.R;
import com.deval.tastyrecipes.activities.MainActivity;
import com.deval.tastyrecipes.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class RegisterFragment extends Fragment {

    //declaring widgets
    View v;
    FrameLayout registerFrame, homeFrame;
    String email, name, password, cPassword, address;
    EditText edtName, edtEmail, edtPassword, edtConfirmPassword, edtAddress;
    Button btnRegister;
    User user;

    //firebase reference
    private FirebaseAuth mAuth;

    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_register, container, false);

        //initializing widgets
        registerFrame = v.findViewById(R.id.registerFrame);
        homeFrame = (FrameLayout) inflater.inflate(R.layout.activity_main, container, false).findViewById(R.id.frame);
        edtName = v.findViewById(R.id.edtRegisterName);
        edtEmail = v.findViewById(R.id.edtRegisterEmail);
        edtPassword = v.findViewById(R.id.edtRegisterPassword);
        edtConfirmPassword = v.findViewById(R.id.edtRegisterConfirmPassword);
        edtAddress = v.findViewById(R.id.edtRegisterAddress);
        btnRegister = v.findViewById(R.id.btnRegister);

        mAuth = FirebaseAuth.getInstance();

        //Register Button Onclick event
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //userDefine method
                registerUser();
            }
        });

        return v;
    }

    private void registerUser() {
        //getting value from EditText (Register Form)
        email = edtEmail.getText().toString().trim();
        name = edtName.getText().toString().trim();
        password = edtPassword.getText().toString().trim();
        cPassword = edtConfirmPassword.getText().toString().trim();
        address = edtAddress.getText().toString().trim();

        //validating Register Form
        if(name.isEmpty()){
            edtName.setError("Name required!");
            edtName.requestFocus();
            return;
        }
        if(email.isEmpty()){
            edtEmail.setError("Email required!");
            edtEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            edtEmail.setError("Invalid Email!");
            edtEmail.requestFocus();
            return;
        }
        if(password.length() < 6){
            edtPassword.setError("6 Characters Password required!");
            edtPassword.requestFocus();
            return;
        }
        if(!password.equals(cPassword)){
            edtConfirmPassword.setError("Password doesn't match!");
            edtConfirmPassword.requestFocus();
            return;
        }
        if(address.isEmpty()){
            edtAddress.setError("Address required!");
            edtAddress.requestFocus();
            return;
        }

        //Creating Firebase User with method of firebase
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //create User Object
                            user = new User(name, email, password, address);

                            //get firebase database reference and Store user object in Realtime Database
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        //on successful register Redirect User to Main Activity
                                        Intent intent = new Intent(getActivity(), MainActivity.class);

                                        //welcome message
                                        Toast.makeText(getActivity(),"Welcome "+user.getName(),Toast.LENGTH_LONG).show();
                                        startActivity(intent);
                                    }else {
                                        //Fail error
                                        Snackbar.make(registerFrame, "Failed to Create User", Snackbar.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }else {
                            //Fail error
                            Snackbar.make(registerFrame, "Error: "+task.getException().getMessage(), Snackbar.LENGTH_INDEFINITE).show();
                        }
                    }
                });
    }
}