package com.example.mobilki_3.data;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.mobilki_3.data.model.Profile;
import com.example.mobilki_3.data.storage.ProfileData;
import com.example.mobilki_3.data.storage.UserData;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;

public class ProfileDataSource {
    private static final String FILE_NAME = "profilesStorage.out";
    private ArrayList<ProfileData> profileList = new ArrayList<>();

    public Profile findProfile(String id) {
        for (ProfileData profile : profileList){
            if (profile.getId().equals(id)){
                return new Profile(profile.getId(), profile.getName(), profile.getBirthday(), profile.getWeight(), profile.getHeight(), profile.getPhoto(), profile.getPhoneNumber(), profile.getEmail());
            }
        }
        return null;
    }

    public boolean newProfile(String id, String name, String email){
        boolean isProfileExist = false;
        for (ProfileData profileData : profileList) {
            if (profileData.getId().equals(id)) {
                isProfileExist = true;
                break;
            }
        }
        if (!isProfileExist){
            profileList.add(new ProfileData(id, name, new Date(100,1,1),70,180, null, "", email));
            return true;
        } else {
            return false;
        }
    }

    public boolean saveProfileInfo(String id, Profile profile){
        for (ProfileData profileData : profileList){
            if (profileData.getId().equals(id)){
                profileData.setName(profile.getName());
                profileData.setBirthday(profile.getBirthday());
                profileData.setPhoneNumber(profile.getPhoneNumber());
                profileData.setWeight(profile.getWeight());
                profileData.setHeight(profile.getHeight());
                profileData.setEmail(profile.getEmail());
                return true;
            }
        }
        return false;
    }

    public boolean saveProfilePhoto(String id, SerializeBitmap bitmap){
        for (ProfileData profileData : profileList){
            if (profileData.getId().equals(id)){
                profileData.setPhoto(bitmap);
                return true;
            }
        }
        return false;
    }

    public void deserializeObjects(Context context){
        try {
            FileInputStream fileIn = context.openFileInput(FILE_NAME);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            profileList = (ArrayList<ProfileData>)in.readObject();
            in.close();
            fileIn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (profileList.size() == 0){
            profileList.add(new ProfileData("6c1e0ae4-b919-4e2f-8d76-061817f9ee4a","No name", new Date(100,1,1),70,180, null, "222-22-22","mylogin@gmail.com"));
        }
    }

    public void serializeObjects(Context context){
        try {
            FileOutputStream fileOut = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(profileList);
            out.close();
            fileOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }

    public void close(Context context){
        serializeObjects(context);
    }
}
