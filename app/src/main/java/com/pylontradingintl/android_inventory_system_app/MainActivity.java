package com.pylontradingintl.android_inventory_system_app;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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
import com.google.firebase.auth.FirebaseUser;

import static com.pylontradingintl.android_inventory_system_app.InputValidator.*;
import static com.pylontradingintl.android_inventory_system_app.InputValidator.isEmailNotEmpty;
import static com.pylontradingintl.android_inventory_system_app.InputValidator.isEmailValid;
import static com.pylontradingintl.android_inventory_system_app.InputValidator.isPasswordNotEmpty;
import static com.pylontradingintl.android_inventory_system_app.PreferenceUtils.*;
import static com.pylontradingintl.android_inventory_system_app.R.id.*;
import static com.pylontradingintl.android_inventory_system_app.R.id.id_login_register;

@RequiresApi(api = Build.VERSION_CODES.N)
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(!containsPreference(getApplicationContext(), "logged", IS_USER_LOGGED)){
            setContentView(R.layout.activity_main);
            findViewById(id_login_register).setOnClickListener(this);
            findViewById(id_login).setOnClickListener(this);
        }else{
            if(!containsPreference(getApplicationContext(),"intro-data",IS_INTRO_OPENED)) {
                Intent intent = new Intent(MainActivity.this, IntroActivity.class);
                startActivity(intent);
            }else {
                Intent intent = new Intent(MainActivity.this, Category.class);
                startActivity(intent);
            }
        }

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case id_login_register:
                startActivity(new Intent(MainActivity.this, Register.class));
                break;
            case id_login:

                userLogin();
               break;

        }
    }

        void userLogin() {
            ProgressDialog progressDialog;
            EditText editTextEmail = findViewById(id_login_email);
            EditText editTextPassword = findViewById(id_login_password);
            String email = editTextEmail.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();

            ValidationResult result = isEmailNotEmpty()
                    .and(isEmailValid())
                    .and(isPasswordNotEmpty())
                    .apply(new User(email, password));

            FirebaseAuth mAuth = FirebaseAuth.getInstance();



            switch (result) {
                case SUCCESS:

                progressDialog = new ProgressDialog(MainActivity.this);
                progressDialog.show();
                progressDialog.setContentView(R.layout.progress_dialog);
                progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);


                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            if (user.isEmailVerified()) {
                                if(containsPreference(getApplicationContext(), "intro-data", IS_INTRO_OPENED)){
                                    Intent intent = new Intent(getApplicationContext(), Category.class);
                                    startActivity(intent);
                                    finish();
                                }else{
                                    Intent intent = new Intent(getApplicationContext(), IntroActivity.class);
                                    startActivity(intent);
                                    finish();
                                }

                                savePreferenceData(getApplicationContext(),"logged", IS_USER_LOGGED);

                            } else {
                                user.sendEmailVerification();
                                Toast.makeText(MainActivity.this, "Check your email to verify your account!", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "Failed to login! Try Again!", Toast.LENGTH_LONG).show();
                        }
                        progressDialog.hide();
                    }
                });
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
            }
        }
}