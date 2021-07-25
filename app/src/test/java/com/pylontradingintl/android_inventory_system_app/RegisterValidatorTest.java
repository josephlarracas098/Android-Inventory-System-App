package com.pylontradingintl.android_inventory_system_app;

import org.junit.Test;
import static com.google.common.truth.Truth.assertThat;
import static com.pylontradingintl.android_inventory_system_app.InputValidator.*;
import static com.pylontradingintl.android_inventory_system_app.InputValidator.ValidationResult.*;

public class RegisterValidatorTest {
    @Test
    public void isCompanyNotEmptyReturnsSuccess(){
        User user = new User("example_user", "example_user@gmail.com", "123456", "123456");
        ValidationResult result = isUsernameNotEmpty().apply(user);
        assertThat(result).isEqualTo(SUCCESS);
    }
    @Test
    public void isCompanyEmptyReturnsUsernameEmpty(){
        User user = new User("", "example_user@gmail.com", "123456", "123456");
        ValidationResult result = isUsernameNotEmpty().apply(user);
        assertThat(result).isEqualTo(USERNAME_EMPTY);
    }

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

    @Test
    public void isPasswordGreaterThan5ReturnsSuccess(){
        User user = new User("example_user", "example_user@gmail.com", "123456", "123456");
        ValidationResult result = isPasswordNotLessThanSix().apply(user);
        assertThat(result).isEqualTo(SUCCESS);
    }

    @Test
    public void isPasswordLessThan6ReturnsPasswordLessThanSix(){
        User user = new User("example_user", "example_user@gmail.com", "12345", "123456");
        ValidationResult result = isPasswordNotLessThanSix().apply(user);
        assertThat(result).isEqualTo(PASSWORD_LENGTH_LESS_SIX);
    }
    @Test
    public void arePasswordMatchReturnsSuccess(){
        User user = new User("example_user", "example_user@gmail.com", "123456", "123456");
        ValidationResult result = arePasswordsMatch().apply(user);
        assertThat(result).isEqualTo(SUCCESS);
    }

    @Test
    public void arePasswordNotMatchReturnsSuccess(){
        User user = new User("example_user", "example_user@gmail.com", "123456", "12456");
        ValidationResult result = arePasswordsMatch().apply(user);
        assertThat(result).isEqualTo(PASSWORDS_NOT_MATCH);
    }

}