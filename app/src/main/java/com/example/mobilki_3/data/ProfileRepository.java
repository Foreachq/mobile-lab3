package com.example.mobilki_3.data;

import android.content.Context;

import com.example.mobilki_3.data.model.Profile;
import com.example.mobilki_3.data.storage.ProfileData;

import java.util.Date;

public class ProfileRepository {

    private static volatile ProfileRepository instance;
    private ProfileDataSource dataSource;
    private Profile user = null;

    private ProfileRepository() {}

    public static ProfileRepository getInstance() {
        if (instance == null) {
            instance = new ProfileRepository();
        }
        return instance;
    }

    public void setDataSource(ProfileDataSource dataSource){
        this.dataSource = dataSource;
    }

    public boolean isProfileFind() {
        return user != null;
    }

    public void logout() {
        user = null;
        dataSource.logout();
    }

    private void setProfile(Profile user) {
        this.user = user;
    }

    public Profile findProfile(String id) {
        Profile result = dataSource.findProfile(id);
        if (result != null) {
            setProfile(result);
        }
        return result;
    }

    public boolean saveProfileInfo(Profile profile){
        if (user != null){
            return dataSource.saveProfileInfo(user.getId(), profile);
        }
        return false;
    }

    public boolean newProfile(String id, String name, String email){
        return dataSource.newProfile(id, name, email);
    }

    public boolean saveProfilePhoto(SerializeBitmap bitmap){
        if (user != null){
            return dataSource.saveProfilePhoto(user.getId(), bitmap);
        }
        return false;
    }

    public void close(Context context){
        dataSource.close(context);
    }
}
