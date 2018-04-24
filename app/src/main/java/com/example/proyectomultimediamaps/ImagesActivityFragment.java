package com.example.proyectomultimediamaps;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;

/**
 * A placeholder fragment containing a simple view.
 */
public class ImagesActivityFragment extends Fragment {
    Button chooseImg, uploadImg;
    ImageView imgView;
    int PICK_IMAGE_REQUEST = 111;
    Uri filePath;
    ProgressDialog pd;
    double lat;
    double longi;
    Places place;
    //referencia para guardar las imagenes en Firebase
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReferenceFromUrl("gs://proyectomultimediamaps.appspot.com/");


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_images, container, false);

        chooseImg = view.findViewById(R.id.btnImages);
        uploadImg = view.findViewById(R.id.uploadImg);
        imgView = view.findViewById(R.id.ivImage);

        Intent i = getActivity().getIntent();
        if (i != null) {
            Places place = (Places) i.getSerializableExtra("place");
            if (place != null) {
                getActivity().setTitle(place.getNombre());
                String name = (String) getActivity().getTitle();
                lat = place.getLat();
                longi = place.getLongi();
                storageRef.child(name+".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.w("Storage", "uri: " + uri.toString());
                        Glide.with(getContext()).load(uri).into(imgView);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {

                    }
                });
            }
        }

        place = (Places) i.getSerializableExtra("place");
        place = new Places(place.getNombre(),place.getLat(),place.getLongi());

        pd = new ProgressDialog(getContext());
        pd.setMessage("Guardando nueva imagen....");

        imgView.setOnClickListener(listener);
        chooseImg.setOnClickListener(listener);
        uploadImg.setOnClickListener(listener);
        return view;
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch(view.getId())
            {
                case R.id.btnImages:
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_PICK);
                    startActivityForResult(Intent.createChooser(intent, "Selecciona una imagen"), PICK_IMAGE_REQUEST);
                    break;
                case R.id.uploadImg:
                    if(filePath != null) {
                        pd.show();

                        String name = getActivity().getTitle()+".jpg";
                        StorageReference childRef = storageRef.child(name);
                        UploadTask uploadTask = childRef.putFile(filePath);
                        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                pd.dismiss();
                                Toast.makeText(getContext(), "Carga exitosa", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                pd.dismiss();
                                Toast.makeText(getContext(), "Carga fallida -> " + e, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else {
                        Toast.makeText(getContext(), "Selecciona una imagen", Toast.LENGTH_SHORT).show();
                    }
                break;
                case R.id.ivImage:
                    Intent map = new Intent(view.getContext(), MapsActivity.class);
                    map.putExtra("place", place);
                    startActivityForResult(map, 0);
                    break;
            }
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
                File destination = new File(Environment.getExternalStorageDirectory(),System.currentTimeMillis() + ".jpg");
                FileOutputStream fo;
                try {
                    destination.createNewFile();
                    fo = new FileOutputStream(destination);
                    fo.write(bytes.toByteArray());
                    fo.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                imgView.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}