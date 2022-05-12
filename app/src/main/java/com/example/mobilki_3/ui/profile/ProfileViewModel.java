package com.example.mobilki_3.ui.profile;

import android.graphics.Bitmap;
import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mobilki_3.R;
import com.example.mobilki_3.data.ProfileRepository;
import com.example.mobilki_3.data.Result;
import com.example.mobilki_3.data.SerializeBitmap;
import com.example.mobilki_3.data.model.LoggedInUser;
import com.example.mobilki_3.data.model.Profile;
import com.example.mobilki_3.ui.login.LoggedInUserView;
import com.example.mobilki_3.ui.login.LoginResult;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProfileViewModel extends ViewModel {
    private MutableLiveData<ProfileFormState> profileFormState = new MutableLiveData<>();
    private MutableLiveData<Profile> currentProfile = new MutableLiveData<>();
    private MutableLiveData<String> currentMood = new MutableLiveData<>();
    private ProfileRepository profileRepository;

    ProfileViewModel(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    LiveData<ProfileFormState> getProfileFormState() {
        return profileFormState;
    }

    public LiveData<Profile> getCurrentProfile() {
        return currentProfile;
    }

    public void setProfileRepository(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public MutableLiveData<String> getCurrentMood() {
        return currentMood;
    }

    public void setCurrentMood(String currentMood) {
        this.currentMood.setValue(currentMood);
    }

    public void find(String id) {
        Profile profile = profileRepository.findProfile(id);
        currentProfile.setValue(profile);
    }

    public boolean saveProfileInfo(String name, Date birthday, int weight, int height, String phoneNumber, String email) {
            Profile profile= currentProfile.getValue();

        assert profile != null;
            profile.setName(name);
            profile.setBirthday(birthday);
            profile.setWeight(weight);
            profile.setHeight(height);
            profile.setPhoneNumber(phoneNumber);
            profile.setEmail(email);
            if (profileRepository.saveProfileInfo(profile)) {
                currentProfile.setValue(profile);
                return true;
            }
            return false;
    }

    public boolean newProfile(String id, String name, String email){
        return profileRepository.newProfile(id, name, email);
    }

    public boolean saveProfilePhoto(Bitmap bitmap) {
        Profile profile = currentProfile.getValue();
        assert profile != null;

        profile.setPhoto(new SerializeBitmap(bitmap));
        if (profileRepository.saveProfilePhoto(new SerializeBitmap(bitmap))) {
            currentProfile.setValue(profile);
            return true;
        }
        return false;
    }

    public void profileDataChanged(String email, String phone, String birthday) {
        if (!isEmailValid(email)) {
            profileFormState.setValue(new ProfileFormState(R.string.invalid_email, null, null));
        } else if (!isPhoneValid(phone)) {
            profileFormState.setValue(new ProfileFormState(null, R.string.invalid_phone, null));
        } else if (!isBirthdayValid(birthday)){
            profileFormState.setValue(new ProfileFormState(null, null, R.string.invalid_birthday));
        } else {
            profileFormState.setValue(new ProfileFormState(true));
        }
    }

    private boolean isBirthdayValid(String birthday) {
        if (birthday.trim().equals(""))
        {
            return false;
        }  else  {
            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
            format.setLenient(false);
            try
            {
                Date javaDate = format.parse(birthday);
                return true;
            } catch (ParseException e) {
                return false;
            }
        }
    }

    // A placeholder username validation check
    private boolean isEmailValid(String email) {
        if (email == null) {
            return false;
        }
        if (email.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(email).matches();
        } else {
            return !email.trim().isEmpty();
        }
    }

    // A placeholder password validation check
    private boolean isPhoneValid(String phone) {
        if (phone == null) {
            return false;
        }
        if (phone.contains("@")) {
            return Patterns.PHONE.matcher(phone).matches();
        } else {
            return !phone.trim().isEmpty();
        }
        //return phone != null && phone.trim().length() > 5;
    }



}