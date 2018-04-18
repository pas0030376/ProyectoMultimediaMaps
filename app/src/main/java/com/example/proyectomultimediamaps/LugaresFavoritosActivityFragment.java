package com.example.proyectomultimediamaps;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.util.Date;

public class LugaresFavoritosActivityFragment extends Fragment {
    FirebaseListAdapter<Places> adapter;
    FirebaseListOptions<Places> options;
    Button btnnew;
    Button images;
    Button map;

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    public LugaresFavoritosActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lugares_favoritos, container, false);
        ListView lvplaces = view.findViewById(R.id.lvplaces);
        getActivity().setTitle("Favoritos");

        DatabaseReference query = FirebaseDatabase.getInstance()
                .getReference()
                .child("Places");


        options = new FirebaseListOptions.Builder<Places>()
                .setQuery(query,Places.class)
                .setLayout(R.layout.lv_places)
                .build();

        adapter = new FirebaseListAdapter<Places>(options){
            @Override
            protected void populateView(View view,Places model, int position) {
                TextView tvName = view.findViewById(R.id.tvnombre);
                tvName.setText(model.getNombre());
            }
        };
        lvplaces.setAdapter(adapter);
        btnnew = view.findViewById(R.id.btnnew);
        btnnew.setOnClickListener(listener);

        lvplaces.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Places place = (Places) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(getContext(), ImagesActivity.class);
                intent.putExtra("place",place);
                startActivity(intent);

            }
        });
        return view;
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch(view.getId())
            {
                case R.id.btnnew:
                    Intent addnew =new Intent(view.getContext(), addPlaceActivity.class);
                    startActivityForResult(addnew, 0);
                    break;
            }
        }
    };
}
