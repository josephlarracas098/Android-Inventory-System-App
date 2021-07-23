package com.pylontradingintl.android_inventory_system_app;

import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;
import static com.pylontradingintl.android_inventory_system_app.InputValidator.*;
import static com.pylontradingintl.android_inventory_system_app.InputValidator.ValidationResult.EMAIL_EMPTY;
import static com.pylontradingintl.android_inventory_system_app.InputValidator.ValidationResult.EMAIL_NOT_VALID;
import static com.pylontradingintl.android_inventory_system_app.InputValidator.ValidationResult.PASSWORD_EMPTY;
import static com.pylontradingintl.android_inventory_system_app.InputValidator.ValidationResult.SUCCESS;
import static com.pylontradingintl.android_inventory_system_app.InputValidator.isEmailNotEmpty;
import static com.pylontradingintl.android_inventory_system_app.InputValidator.isEmailValid;
import static com.pylontradingintl.android_inventory_system_app.InputValidator.isPasswordNotEmpty;


public class MainActivityValidatorTest {
    @Test
    public void isEmailNotEmptyReturnsSuccess(){
        User user = new User("example_user", "example_user@gmail.com", "123456", "123456");
        ValidationResult result = isEmailNotEmpty().apply(user);
        assertThat(result).isEqualTo(SUCCESS);
    }
    @Test
    public void isEmailEmptyReturnsEmailEmpty(){
        User user = new User("example_user", "", "123456", "123456");
        ValidationResult result = isEmailNotEmpty().apply(user);
        assertThat(result).isEqualTo(EMAIL_EMPTY);
    }
    @Test
    public void isEmailValidReturnsSuccess(){
        User user = new User("example_user", "josephlarracas@098gmail.com", "123456", "123456");
        ValidationResult result = isEmailValid().apply(user);
        assertThat(result).isEqualTo(SUCCESS);
    }

    @Test
    public void isEmailNotValidReturnsEmailNotValid(){
        User user = new User("example_user", "josephlarracas098gmail.com", "123456", "123456");
        ValidationResult result = isEmailValid().apply(user);
        assertThat(result).isEqualTo(EMAIL_NOT_VALID);
    }

    @Test
    public void isPasswordNotEmptyReturnsSuccess(){
        User user = new User("example_user", "example_user@gmail.com", "123456", "123456");
        ValidationResult result = isPasswordNotEmpty().apply(user);
        assertThat(result).isEqualTo(SUCCESS);
    }

    @Test
    public void isPasswordEmptyReturnsPasswordEmpty(){
        User user = new User("example_user", "example_user@gmail.com", "", "123456");
        ValidationResult result = isPasswordNotEmpty().apply(user);
        assertThat(result).isEqualTo(PASSWORD_EMPTY);
    }
}