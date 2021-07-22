package com.pylontradingintl.android_inventory_system_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity implements View.OnClickListener{
    private ProgressDialog progressDialog;
    private EditText editTextUserName;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        findViewById(R.id.id_register).setOnClickListener(this);
        findViewById(R.id.id_register_login).setOnClickListener(this);

    }

    @Override
    public void onBackPressed() {
        progressDialog.dismiss();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_register:
                FirebaseAuth mAuth = FirebaseAuth.getInstance();

             editTextUserName = findViewById(R.id.id_register_username);
             editTextEmail = findViewById(R.id.id_register_email);
             editTextPassword = findViewById(R.id.id_register_password);
             editTextConfirmPassword = findViewById(R.id.id_register_confirm_password);

            String userName = editTextUserName.getText().toString().trim();
            String email = editTextEmail.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();
            String confirmPassword = editTextConfirmPassword.getText().toString().trim();


/*
            if (userName.isEmpty()) {
                editTextUserName.setError("Username is Required");
                editTextUserName.requestFocus();
                return;
            }

            if (email.isEmpty()) {
                editTextEmail.setError("Email is Required");
                editTextEmail.requestFocus();
                return;
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                editTextEmail.setError("Email Address is not valid");
                editTextEmail.requestFocus();
                return;
            }

            if (password.isEmpty()) {
                editTextPassword.setError("Please provide password");
                editTextPassword.requestFocus();
                return;
            }

            if (password.length() < 6) {
                editTextPassword.setError("Minimum password length should be 6 characters!");
                editTextPassword.requestFocus();
                return;
            }
            if (!password.equals(confirmPassword)) {
                editTextPassword.setError("Passwords did not match!");
                editTextPassword.requestFocus();
                editTextConfirmPassword.setError("Passwords did not match!");
                editTextConfirmPassword.requestFocus();
                return;
            }

 */
             if(validateRegistrationInput(userName,email,password,confirmPassword)) {
                 progressDialog = new ProgressDialog(Register.this);
                 progressDialog.show();
                 progressDialog.setContentView(R.layout.progress_dialog);
                 progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                 mAuth.createUserWithEmailAndPassword(email, password)
                         .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                             @Override
                             public void onComplete(@NonNull Task<AuthResult> task) {
                                 if (task.isSuccessful()) {
                                     User user = new User(userName, email, password);

                                     FirebaseDatabase.getInstance().getReference("Users")
                                             .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                             .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                         @Override
                                         public void onComplete(@NonNull Task<Void> task) {
                                             if (task.isSuccessful())
                                                 Toast.makeText(Register.this, "User has been registered successfully!", Toast.LENGTH_LONG).show();
                                             else
                                                 Toast.makeText(Register.this, "Failed to register! Try Again!", Toast.LENGTH_LONG).show();
                                             progressDialog.hide();
                                         }
                                     });
                                 } else {
                                     Toast.makeText(Register.this, "Failed to register! Try Again!", Toast.LENGTH_LONG).show();
                                     progressDialog.hide();
                                 }
                             }
                         });
             }
            break;
            case R.id.id_register_login:
                startActivity(new Intent(Register.this, MainActivity.class));
                break;

        }
    }

    Boolean validateRegistrationInput(String userName, String email , String password, String confirmedPassword){
        if(userName.isEmpty()){
            editTextUserName.setError("Username is Required");
            editTextUserName.requestFocus();
            return false;
        }
        if(email.isEmpty()){
            editTextEmail.setError("Email is Required");
            editTextEmail.requestFocus();
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Email Address is not valid");
            editTextEmail.requestFocus();
            return false;
        }
        if(password.isEmpty()){
            editTextPassword.setError("Please provide password");
            editTextPassword.requestFocus();
            return false;
        }

        if (password.length() < 6) {
            editTextPassword.setError("Minimum password length should be 6 characters!");
            editTextPassword.requestFocus();
            return false;
        }
        if (!password.equals(confirmedPassword)) {
            editTextPassword.setError("Passwords did not match!");
            editTextPassword.requestFocus();
            editTextConfirmPassword.setError("Passwords did not match!");
            editTextConfirmPassword.requestFocus();
            return false;
        }

        return true;
    }
}