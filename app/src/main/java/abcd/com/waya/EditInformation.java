package abcd.com.waya;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import abcd.com.waya.entities.UserProfile;

public class EditInformation extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private ImageView profilePic;
    private DatabaseReference databaseReference;
    private EditText name, lname, cellphone, status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_information);
        loadFireBase();
        loadItems();
    }

    private void loadItems() {
        name = (EditText) findViewById(R.id.usr_name);
        lname = (EditText) findViewById(R.id.usr_lname);
        status = (EditText) findViewById(R.id.usr_status);
        cellphone = (EditText) findViewById(R.id.usr_cellphone);
        profilePic = (ImageView) findViewById(R.id.usr_profile_pic);
    }

    private void loadFireBase() {
        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    public void applyChanges(View view) {
        try{
            //Bitmap image = ((BitmapDrawable)profilePic.getDrawable()).getBitmap();
            UserProfile up = new UserProfile(name.getText().toString(), lname.getText().toString(),
                    status.getText().toString());
            FirebaseUser user = firebaseAuth.getCurrentUser();
            databaseReference.child(user.getUid()).setValue(up);
            Toast.makeText(this, "Datos guardados correctamente.", Toast.LENGTH_SHORT).show();
        }catch (Exception e){

        }
    }
}
