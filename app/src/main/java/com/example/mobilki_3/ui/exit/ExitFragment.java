package com.example.mobilki_3.ui.exit;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

import com.example.mobilki_3.data.LoginRepository;
import com.example.mobilki_3.data.ProfileRepository;

public class ExitFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LoginRepository.getInstance().close(getActivity().getApplicationContext());
        ProfileRepository.getInstance().close(getActivity().getApplicationContext());
        getActivity().finish();
        System.exit(0);

    }
}
