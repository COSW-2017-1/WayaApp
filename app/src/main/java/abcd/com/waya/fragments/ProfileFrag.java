package abcd.com.waya.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

import abcd.com.waya.EditInformation;
import abcd.com.waya.LoginActivity;
import abcd.com.waya.R;
import abcd.com.waya.entities.UserProfile;
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
            System.out.println("ENTRA A DATOS DE USUARIO -- nombre: " + user.getDisplayName());
            System.out.println("ENTRA A DATOS DE USUARIO -- mail: " + user.getEmail());
            databaseReference = FirebaseDatabase.getInstance().getReference();
            databaseReference.child(user.getUid()).addListenerForSingleValueEvent(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            try{
                                System.out.println(dataSnapshot.toString());
                                userProfile = (UserProfile) dataSnapshot.getValue();
                                name.setText(userProfile.name + " " + userProfile.lname);
                                status.setText(userProfile.about);
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    }
            );
        }
    }


    private void loadActions() {
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(view.getContext(), LoginActivity.class);
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
