package com.example.mobilki_3.ui.profile;

import androidx.annotation.Nullable;
/**
 * Data validation state of the profile form.
 */
public class ProfileFormState {
    @Nullable
    private Integer emailError;
    @Nullable
    private Integer phoneError;
    @Nullable
    private Integer birthdayError;
    private boolean isDataValid;

    ProfileFormState(@Nullable Integer emailError, @Nullable Integer phoneError, @Nullable Integer birthdayError) {
        this.emailError = emailError;
        this.phoneError = phoneError;
        this.birthdayError = birthdayError;
        this.isDataValid = false;
    }

    ProfileFormState(boolean isDataValid) {
        this.emailError = null;
        this.phoneError = null;
        this.birthdayError = null;
        this.isDataValid = isDataValid;
    }

    @Nullable
    Integer getEmailError() {
        return emailError;
    }

    @Nullable
    Integer getPhoneError() {
        return phoneError;
    }

    @Nullable
    public Integer getBirthdayError() { return birthdayError; }

    boolean isDataValid() {
        return isDataValid;
    }
}
