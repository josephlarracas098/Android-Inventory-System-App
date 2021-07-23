package com.pylontradingintl.android_inventory_system_app;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import static com.pylontradingintl.android_inventory_system_app.InputValidator.*;

public class Register extends AppCompatActivity implements View.OnClickListener{
    private ProgressDialog progressDialog;
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_register:
                userRegister();
            break;
            case R.id.id_register_login:
                startActivity(new Intent(Register.this, MainActivity.class));
                break;

        }
    }
    
    @RequiresApi(api = Build.VERSION_CODES.N)
    void userRegister(){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        EditText editTextUserName = findViewById(R.id.id_register_username);
        EditText editTextEmail = findViewById(R.id.id_register_email);
        EditText editTextPassword = findViewById(R.id.id_register_password);
        EditText editTextConfirmPassword = findViewById(R.id.id_register_confirm_password);

        String username = editTextUserName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String confirmPassword = editTextConfirmPassword.getText().toString().trim();

        ValidationResult result = isUsernameNotEmpty()
                .and(isEmailNotEmpty())
                .and(isEmailValid())
                .and(isPasswordNotEmpty())
                .and(isPasswordNotLessThanSix())
                .and(arePasswordsMatch())
                .apply(new User(username,email,password,confirmPassword));


        switch (result){
            case SUCCESS:
                progressDialog = new ProgressDialog(Register.this);
                progressDialog.show();
                progressDialog.setContentView(R.layout.progress_dialog);
                progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    User user = new User(username, email, password);
                                    FirebaseDatabase.getInstance().getReference("Users")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(Register.this, "User has been registered successfully!", Toast.LENGTH_LONG).show();
                                                startActivity(new Intent(Register.this, MainActivity.class));
                                            }
                                            else {
                                                Toast.makeText(Register.this, "Failed to register! Try Again!", Toast.LENGTH_LONG).show();
                                                editTextUserName.setText("");
                                                editTextEmail.setText("");
                                                editTextPassword.setText("");
                                                editTextConfirmPassword.setText("");
                                            }
                                            progressDialog.hide();
                                        }
                                    });
                                } else {
                                    Toast.makeText(Register.this, "Failed to register! Try Again!", Toast.LENGTH_LONG).show();
                                    progressDialog.hide();
                                    editTextEmail.setText("");
                                    editTextPassword.setText("");
                                    editTextConfirmPassword.setText("");
                                    editTextEmail.setError("Email Address has already been used");
                                    editTextEmail.requestFocus();
                                }
                            }
                        });
                break;
            case USERNAME_EMPTY:
                editTextUserName.setError("Username is Required");
                editTextUserName.requestFocus();
                break;
            case EMAIL_EMPTY:
                editTextEmail.setError("Email is Required");
                editTextEmail.requestFocus();
                break;
            case EMAIL_NOT_VALID:
                editTextEmail.setError("Email Address is not valid");
                editTextEmail.requestFocus();
                break;
            case PASSWORD_EMPTY:
                editTextPassword.setError("Please provide password");
                editTextPassword.requestFocus();
                break;
            case PASSWORD_LENGTH_LESS_SIX:
                editTextPassword.setError("Minimum password length should be 6 characters!");
                editTextPassword.requestFocus();
                break;
            case PASSWORDS_NOT_MATCH:
                editTextPassword.setError("Passwords did not match!");
                editTextPassword.requestFocus();
                editTextConfirmPassword.setError("Passwords did not match!");
                editTextConfirmPassword.requestFocus();
                break;
        }
    }
}