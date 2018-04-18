package com.example.proyectomultimediamaps;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A placeholder fragment containing a simple view.
 */
public class addPlaceActivityFragment extends Fragment{
        private View view;
        private DatabaseReference mRef;
        private Task<Void> mDatabase;
        Button addplace;
        Button cancel;


        public addPlaceActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_place, container, false);
        getActivity().setTitle("Añadiendo nuevo lugar");

        cancel = view.findViewById(R.id.btncancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent places = new Intent(view.getContext(), LugaresFavoritosActivity.class);
                startActivityForResult(places, 0);
            }
        });
        addplace = view.findViewById(R.id.btnok);
        addplace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewPlace();
            }
        });

        return view;
    }

        private void NewPlace() {
        Context context = getContext();
        EditText nom =  view.findViewById(R.id.etnom);
        String fname = nom.getText().toString();
        EditText lat = view.findViewById(R.id.etlat);
        String nlat = lat.getText().toString();
        EditText longi = view.findViewById(R.id.etlong);
        String nlong =longi.getText().toString();
        double dlat =Double.valueOf(nlat);
        double dlong =Double.valueOf(nlong);

        Places place = new Places(fname,dlat,dlong);
        mRef =  FirebaseDatabase.getInstance().getReferenceFromUrl("https://proyectomultimediamaps.firebaseio.com/");
        String mId = fname;
        mDatabase = mRef.child("Places").child(mId).setValue(place);
        CharSequence text = "Se ha añadido correctamente";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        Intent backPlaces = new Intent(view.getContext(), LugaresFavoritosActivity.class);
        startActivityForResult(backPlaces, 0);
    }
}
