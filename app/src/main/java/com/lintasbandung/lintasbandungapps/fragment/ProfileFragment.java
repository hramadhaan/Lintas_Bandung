package com.lintasbandung.lintasbandungapps.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.lintasbandung.lintasbandungapps.R;
import com.lintasbandung.lintasbandungapps.awalan.LoginActivity;
import com.lintasbandung.lintasbandungapps.data.AppState;


public class ProfileFragment extends Fragment {

    private TextView textView;
    private AppState appState;
    private Button logout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View viewProfile = inflater.inflate(R.layout.fragment_profile, container, false);

        appState = AppState.getInstance();
        logout = viewProfile.findViewById(R.id.profile_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        textView = viewProfile.findViewById(R.id.profile_textView);
        String name = AppState.getInstance().getUser().getFirstName();
        textView.setText(name);

        return viewProfile;
    }

    private void logout() {
        if (AppState.getInstance().isLoggedIn()) {
            AppState.getInstance().logout();
            AppState.getInstance().removeCurrentUser();
            startActivity(new Intent(getActivity(), LoginActivity.class));
            getActivity().finish();
        }
    }
}
