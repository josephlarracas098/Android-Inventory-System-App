package com.pylontradingintl.android_inventory_system_app;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.core.utilities.Validation;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

import static com.pylontradingintl.android_inventory_system_app.InputValidator.*;
import static com.pylontradingintl.android_inventory_system_app.InputValidator.isEmailNotEmpty;
import static com.pylontradingintl.android_inventory_system_app.InputValidator.isEmailValid;
import static com.pylontradingintl.android_inventory_system_app.InputValidator.isPasswordNotEmpty;
import static com.pylontradingintl.android_inventory_system_app.R.*;
import static com.pylontradingintl.android_inventory_system_app.R.id.id_login_register;

@RequiresApi(api = Build.VERSION_CODES.N)
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);

        findViewById(id_login_register).setOnClickListener(this);
        findViewById(id.id_login).setOnClickListener(this);

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case id_login_register:
                startActivity(new Intent(MainActivity.this, Register.class));
                break;
            case id.id_login:
                userLogin();
               break;

        }
    }

        void userLogin() {
            ProgressDialog progressDialog;
            EditText editTextEmail = findViewById(id.id_login_email);
            EditText editTextPassword = findViewById(id.id_login_password);
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
                progressDialog.setContentView(layout.progress_dialog);
                progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            if (user.isEmailVerified()) {
                                startActivity(new Intent(MainActivity.this, IntroActivity.class));
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