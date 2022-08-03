package com.deval.tastyrecipes.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.deval.tastyrecipes.R;
import com.deval.tastyrecipes.activities.LoginActivity;
import com.deval.tastyrecipes.models.User;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends Fragment {

    View v;
    FrameLayout profileFrame;
    EditText edtName, edtEmail, edtPassword ,edtAddress;
    Button btnLogout, btnUpdate;
    DatabaseReference reference;
    FirebaseUser user;
    String userId;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_profile, container, false);
        edtName = v.findViewById(R.id.edtUserName);
        edtEmail = v.findViewById(R.id.edtUserEmail);
        edtPassword = v.findViewById(R.id.edtUserPassword);
        edtAddress = v.findViewById(R.id.edtUserAddress);
        btnLogout = v.findViewById(R.id.btnLogout);
        btnUpdate = v.findViewById(R.id.btnUpdate);
        profileFrame = v.findViewById(R.id.profile_fragment);
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userId = user.getUid();


        edtPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtPassword.getInputType() == InputType.TYPE_CLASS_TEXT)
                {
                    edtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }else {
                    edtPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                }
            }
        });

        reference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if(userProfile != null){
                    edtName.setText(userProfile.getName());
                    edtEmail.setText(userProfile.getEmail());
                    edtPassword.setText(userProfile.getPassword());
                    edtAddress.setText(userProfile.getAddress());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Snackbar.make(profileFrame, "Something Went Wrong!", Snackbar.LENGTH_LONG).show();
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference.child(userId).child("name").setValue(edtName.getText().toString().trim());
                reference.child(userId).child("address").setValue(edtAddress.getText().toString().trim());
                Snackbar.make(profileFrame, "Profile Updated!", Snackbar.LENGTH_LONG).show();
            }
        });
        return v;
    }
}