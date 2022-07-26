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
import com.deval.tastyrecipes.activities.LoginActivity;
import com.deval.tastyrecipes.activities.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginFragment extends Fragment {

    //declaring widgets
    View v;
    FrameLayout loginFrame;
    String email, password;
    EditText edtEmail, edtPassword;
    Button btnLogin;

    //firebase reference
    private FirebaseAuth mAuth;
    DatabaseReference reference;
    FirebaseUser user;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_login, container, false);

        //initializing widgets
        loginFrame = v.findViewById(R.id.loginFrame);
        edtEmail = v.findViewById(R.id.edtLoginEmail);
        edtPassword = v.findViewById(R.id.edtLoginPassword);
        btnLogin = v.findViewById(R.id.btnLogin);

        //Login Button Onclick event
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //userDefine method
                loginUser();
            }
        });

        return v;
    }

    private void loginUser() {
        mAuth = FirebaseAuth.getInstance();
        //getting value from EditText (Login Form)
        email = edtEmail.getText().toString().trim();
        password = edtPassword.getText().toString().trim();

        //validating Login Form
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

        //SignIn method of Firebase to authentic user
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //if login successful Redirect User to Main Activity
                    Intent intent = new Intent(getActivity(), MainActivity.class);

                    //userDefine method to get Username from login-Email
                    getUserName();
                    startActivity(intent);
                }else {
                    //Fail error
                    Snackbar.make(loginFrame, "Failed to Login! Check Credentials", Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    private String getUserName() {
        String userName;

        //get userId from firebase current user
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        //set database reference
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        DatabaseReference userReference = databaseReference.child(uid);
        userReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //get username and Toast it
                String userName = dataSnapshot.child("name").getValue(String.class);
                Toast.makeText(getActivity(),"Welcome " + userName,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });
        return "Name";
    }
}