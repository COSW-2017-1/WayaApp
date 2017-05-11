package abcd.com.waya.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import abcd.com.waya.EditInformation;
import abcd.com.waya.LoginActivity;
import abcd.com.waya.R;
import abcd.com.waya.entities.UserProfile;
import abcd.com.waya.entities.singleton.UserProfileSingleton;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by PERSONAL on 20/03/2017.
 */

public class ProfileFrag extends Fragment {

    View view;
    FirebaseAuth firebaseAuth;
    Button signOut;
    Button toEditMode;
    TextView name, status;
    CircleImageView userpic;
    private DatabaseReference databaseReference;
    FirebaseStorage firebaseStorage;
    UserProfile userProfile;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        signOut = (Button) view.findViewById(R.id.sign_out);
        toEditMode = (Button) view.findViewById(R.id.edit_mode);
        loadActions();
        loadUserInfo();
        return  view;
    }

    private void loadUserInfo() {
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null){
            FirebaseUser user = firebaseAuth.getCurrentUser();
            user = firebaseAuth.getCurrentUser();
            System.out.println("ENTRA A DATOS DE USUARIO -- nombre: " + user.getDisplayName());
            System.out.println("ENTRA A DATOS DE USUARIO -- mail: " + user.getEmail());
            databaseReference = FirebaseDatabase.getInstance().getReference();
            databaseReference.child(user.getUid()).addListenerForSingleValueEvent(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            try{
                                System.out.println("PROFILE DATA --> " + dataSnapshot.toString());
                                HashMap<String, String> values = (HashMap<String, String>) dataSnapshot.getValue();
                                name.setText(values.get("name") + " " + values.get("lname"));
                                status.setText(values.get("about"));
                                //load image

                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    }
            );
            try{
                firebaseStorage = FirebaseStorage.getInstance();
                StorageReference storageRef = firebaseStorage.getReferenceFromUrl("gs://wayaapp-9ee01.appspot.com/");
                StorageReference profileRef = storageRef.child(user.getUid() + "/profile/profile.jpg");
                final long ONE_MEGABYTE = 1024 * 1024;
                profileRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        // Data for "images/island.jpg" is returns, use this as needed
                        Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        userpic.setImageBitmap(bm);
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


    private void loadActions() {
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(view.getContext(), LoginActivity.class);
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(view.getContext());
                prefs.edit().putBoolean("isLogged", false).commit();
                startActivity(i);
            }
        });

        toEditMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(view.getContext(), EditInformation.class);
                startActivity(i);
            }
        });

        name = (TextView) view.findViewById(R.id.username);
        status = (TextView) view.findViewById(R.id.userstatus);
        userpic = (CircleImageView) view.findViewById(R.id.userpic);
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
