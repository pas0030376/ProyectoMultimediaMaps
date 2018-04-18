package com.example.proyectomultimediamaps;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.firebase.ui.auth.AuthUI;

import java.util.Arrays;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
    FirebaseAuth auth;
    Button map;
    Button places;
    private static final int RC_SIGN_IN = 123;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        getActivity().setTitle("");
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(Arrays.asList(
                                new AuthUI.IdpConfig.GoogleBuilder().build()))
                        .build(),
                RC_SIGN_IN);

        map = view.findViewById(R.id.btnMaps);
        places= view.findViewById(R.id.btnFavorite);

        map.setOnClickListener(listener);
        places.setOnClickListener(listener);

        return view;
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch(view.getId())
            {
                case R.id.btnMaps:
                    Intent Maps = new Intent(view.getContext(), MapsActivity.class);
                    startActivityForResult(Maps, 0);
                    break;
                case R.id.btnFavorite:
                    Intent places = new Intent(view.getContext(), LugaresFavoritosActivity.class);
                    startActivityForResult(places, 0);
                    break;
            }
        }
    };
}
