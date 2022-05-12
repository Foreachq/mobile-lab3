package com.example.mobilki_3.ui.menu;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import com.example.mobilki_3.AboutActivity;

public class AboutFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(getActivity(), AboutActivity.class);
        startActivity(intent);
    }
}
