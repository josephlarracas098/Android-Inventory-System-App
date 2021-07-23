package com.pylontradingintl.android_inventory_system_app;

import android.os.Build;
import androidx.annotation.RequiresApi;
import java.util.function.Function;
import java.util.regex.Pattern;

import static com.pylontradingintl.android_inventory_system_app.InputValidator.*;
import static com.pylontradingintl.android_inventory_system_app.InputValidator.ValidationResult.*;

@RequiresApi(api = Build.VERSION_CODES.N)
public interface InputValidator extends Function<User, ValidationResult> {
    enum ValidationResult{
        SUCCESS,
        USERNAME_EMPTY,
        EMAIL_EMPTY,
        EMAIL_NOT_VALID,
        PASSWORD_EMPTY,
        PASSWORD_LENGTH_LESS_SIX,
        PASSWORDS_NOT_MATCH
    }

    static InputValidator isUsernameNotEmpty(){
        return user -> !user.getUsername().isEmpty() ? SUCCESS : USERNAME_EMPTY;
    }
    static InputValidator isEmailNotEmpty(){
        return user -> !user.getEmail().isEmpty() ? SUCCESS : EMAIL_EMPTY;
    }
/*
    static RegistrationValidator isEmailValid(){
        return user ->  Patterns.EMAIL_ADDRESS.matcher(user.getEmail()).matches() ? SUCCESS : EMAIL_NOT_VALID;
    }

 */
    static InputValidator isEmailValid(){
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        return user ->  pattern.matcher(user.getEmail()).matches() ? SUCCESS : EMAIL_NOT_VALID;
    }

    static InputValidator isPasswordNotEmpty(){
        return user -> !user.getPassword().isEmpty() ? SUCCESS : PASSWORD_EMPTY;
    }
    static InputValidator isPasswordNotLessThanSix(){
        return user -> user.getPassword().length() > 5 ? SUCCESS : PASSWORD_LENGTH_LESS_SIX;
    }
    static InputValidator arePasswordsMatch(){
        return user -> user.getPassword().equals(user.getConfirmedPassword()) ? SUCCESS : PASSWORDS_NOT_MATCH;
    }

    default InputValidator and (InputValidator other){
        return user -> {
            ValidationResult result = this.apply(user);
            return result.equals(SUCCESS) ? other.apply(user) : result;
        };
    }

}
