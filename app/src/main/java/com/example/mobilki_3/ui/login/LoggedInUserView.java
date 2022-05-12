package com.example.mobilki_3.ui.login;

/**
 * Class exposing authenticated user details to the UI.
 */
public class LoggedInUserView {
    private String displayName;
    private String id;
    private boolean isProfile;
    //... other data fields that may be accessible to the UI

    LoggedInUserView(String displayName, String id, boolean isProfile) {
        this.displayName = displayName;
        this.id = id;
        this.isProfile = isProfile;
    }

    public String getDisplayName() {
        return displayName;
    }

    public boolean isProfile() {
        return isProfile;
    }

    public String getId() {
        return id;
    }
}