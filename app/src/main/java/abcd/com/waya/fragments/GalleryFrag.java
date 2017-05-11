package abcd.com.waya.fragments;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;

import abcd.com.waya.R;

/**
 * Created by PERSONAL on 20/03/2017.
 */

public class GalleryFrag extends Fragment {

    FirebaseAuth firebaseAuth;
    FirebaseStorage firebaseStorage;
    ArrayList<Bitmap> images = new ArrayList<>();
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_gallery, container, false);
        loadGallery();
        return  view;
    }

    private void loadGallery() {
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null){
            FirebaseUser user = firebaseAuth.getCurrentUser();
            user = firebaseAuth.getCurrentUser();
            try{
                firebaseStorage = FirebaseStorage.getInstance();
                StorageReference storageRef = firebaseStorage.getReferenceFromUrl("gs://wayaapp-9ee01.appspot.com/");
                StorageReference profileRef = storageRef.child(user.getUid() + "/shared/party.jpg");
                final long ONE_MEGABYTE = 1024 * 1024;
                profileRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        // Data for "images/island.jpg" is returns, use this as needed
                        Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        loadImage(bm);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(view.getContext(), "Problema cargando foto de perfil --> " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private void loadImage(Bitmap bm) {
        LinearLayout ll = (LinearLayout) view.findViewById(R.id.gallery_view);
        ImageView imageView = new ImageView(view.getContext());
        LinearLayout.LayoutParams vp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 240);
        imageView.setLayoutParams(vp);
        imageView.setImageBitmap(bm);
        ll.addView(imageView);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (view != null) {
            ViewGroup parentViewGroup = (ViewGroup) view.getParent();
            if (parentViewGroup != null) {
                parentViewGroup.removeAllViews();
            }
        }
    }

}
