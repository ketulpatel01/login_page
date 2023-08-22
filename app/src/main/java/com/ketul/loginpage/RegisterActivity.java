package com.ketul.loginpage;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText inputUsername, inputEmail, inputPassword, confirmpassword;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        TextView allreadyhaveanaccount = findViewById(R.id.allreadyhaveanaccount);
        allreadyhaveanaccount.setOnClickListener(this);

        TextView btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(this);

        inputUsername= findViewById(R.id.inputUsername);
        inputEmail= findViewById(R.id.inputEmail);
        inputPassword=findViewById(R.id.inputPassword);
        confirmpassword=findViewById(R.id.confirmpassword);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.allreadyhaveanaccount:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.btnRegister:
                register();
                break;
        }

    }

    private void register() {
        String username=inputUsername.getText().toString().trim();
        String email=inputEmail.getText().toString().trim();
        String password=inputPassword.getText().toString();
        String conform=confirmpassword.getText().toString();

        if (username.isEmpty()){
            inputUsername.setError("Username is Empty");
            inputUsername.requestFocus();
            return;
        }
        else if (email.isEmpty()){
            inputEmail.setError("Email Required");
            inputEmail.requestFocus();
            return;
        }
        else if (password.isEmpty()){
            inputPassword.setError("Enter Password");
            inputPassword.requestFocus();
            return;
        }
        else if (password.length()<7){
            inputPassword.setError("Password must be 7 character");
            inputPassword.requestFocus();
            return;
        }
        else if (!conform.equals(password)){
            confirmpassword.setError("Password is not same");
            confirmpassword.requestFocus();
            return;
        }

        startActivity(new Intent(this, HomeActivity.class));

        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        user user = new user(username, email);

                        FirebaseDatabase.getInstance().getReference("Users")
                                .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                                .setValue(user).addOnCompleteListener(task1 -> {

                                    if (task1.isSuccessful()){
                                        Toast.makeText(RegisterActivity.this, "User has Register Successfully"
                                                , Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        Toast.makeText(RegisterActivity.this, "Fail to Register! Try Again"
                                                , Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                    else {
                        Toast.makeText(RegisterActivity.this, "Fail to Register! Try Again"
                                , Toast.LENGTH_SHORT).show();
                    }
                });

    }
}