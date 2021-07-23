package com.pylontradingintl.android_inventory_system_app;

import android.os.Build;
import android.text.TextUtils;
import android.util.Patterns;
import androidx.annotation.RequiresApi;
import java.util.function.Function;
import java.util.regex.Pattern;

import static com.pylontradingintl.android_inventory_system_app.RegistrationValidator.*;
import static com.pylontradingintl.android_inventory_system_app.RegistrationValidator.ValidationResult.*;

@RequiresApi(api = Build.VERSION_CODES.N)
public interface RegistrationValidator extends Function<User, ValidationResult> {
    enum ValidationResult{
        SUCCESS,
        USERNAME_EMPTY,
        EMAIL_EMPTY,
        EMAIL_NOT_VALID,
        PASSWORD_EMPTY,
        PASSWORD_LENGTH_LESS_SIX,
        PASSWORDS_NOT_MATCH
    }

    static RegistrationValidator isUsernameNotEmpty(){
        return user -> !user.getUsername().isEmpty() ? SUCCESS : USERNAME_EMPTY;
    }
    static RegistrationValidator isEmailNotEmpty(){
        return user -> !user.getEmail().isEmpty() ? SUCCESS : EMAIL_EMPTY;
    }
/*
    static RegistrationValidator isEmailValid(){
        return user ->  Patterns.EMAIL_ADDRESS.matcher(user.getEmail()).matches() ? SUCCESS : EMAIL_NOT_VALID;
    }

 */
    static RegistrationValidator isEmailValid(){
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        return user ->  pattern.matcher(user.getEmail()).matches() ? SUCCESS : EMAIL_NOT_VALID;
    }

    static RegistrationValidator isPasswordNotEmpty(){
        return user -> !user.getPassword().isEmpty() ? SUCCESS : PASSWORD_EMPTY;
    }
    static RegistrationValidator isPasswordNotLessThanSix(){
        return user -> user.getPassword().length() > 5 ? SUCCESS : PASSWORD_LENGTH_LESS_SIX;
    }
    static RegistrationValidator arePasswordsMatch(){
        return user -> user.getPassword().equals(user.getConfirmedPassword()) ? SUCCESS : PASSWORDS_NOT_MATCH;
    }

    default RegistrationValidator and (RegistrationValidator other){
        return user -> {
            ValidationResult result = this.apply(user);
            return result.equals(SUCCESS) ? other.apply(user) : result;
        };
    }

}
