package com.example.mobilki_3.data;

import android.content.Context;

import com.example.mobilki_3.data.model.LoggedInUser;
import com.example.mobilki_3.data.storage.UserData;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


public class LoginDataSource {
    private static final String FILE_NAME = "storage.out";
    private ArrayList<UserData> userList = new ArrayList<>();

    public Result<LoggedInUser> login(String username, String password) {
            LoggedInUser loggedInUser = null;
            for (UserData user : userList){
                if ((user.getEmail().equals(username)) && (user.getPassword().equals(password))){
                    loggedInUser = new LoggedInUser(user.getId(), user.getName());
                    return new Result.Success<>(loggedInUser);
                }
            }
            return new Result.Error(new IOException("Error logging in"));
    }

    public Result<LoggedInUser> register(String email, String password, String login) {
        LoggedInUser loggedInUser = null;
        boolean isUserExist = false;
        for (UserData user : userList){
            if ((user.getEmail().equals(email)) && (user.getPassword().equals(password))){
                isUserExist = true;
                break;
            }
        }
        if (isUserExist){
            return new Result.Error(new IOException("User already registered"));
        } else {
            String id = java.util.UUID.randomUUID().toString();
            loggedInUser = new LoggedInUser(id, email);
            userList.add(new UserData(id,login, password, email));
            return new Result.Success<>(loggedInUser);
        }

    }

    public void deserializeObjects(Context context){
        try {
            FileInputStream fileIn = context.openFileInput(FILE_NAME);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            userList = (ArrayList<UserData>)in.readObject();
            in.close();
            fileIn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (userList.size() == 0){
            //userList.add(new UserData(java.util.UUID.randomUUID().toString(),"No name", "123456","noname@gmail.com"));
            userList.add(new UserData("6c1e0ae4-b919-4e2f-8d76-061817f9ee4a","No name", "123456","noname@gmail.com"));
        }
    }

    public void serializeObjects(Context context){
        try {
            FileOutputStream fileOut = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(userList);
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