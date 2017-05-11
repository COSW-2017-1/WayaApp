package abcd.com.waya;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

import abcd.com.waya.entities.UserProfile;

public class EditInformation extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private ImageView profilePic;
    private DatabaseReference databaseReference;
    private EditText name, lname, cellphone, status;
    private static final int CAMERA_REQUEST = 1888;
    private FirebaseStorage firebaseStorage;

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
        firebaseStorage = FirebaseStorage.getInstance();
    }

    public void applyChanges(View view) {
        try{
            FirebaseUser user = firebaseAuth.getCurrentUser();
            //Crear referencia a imagen de perfil de usuario con uid+profile
            StorageReference storageRef = firebaseStorage.getReferenceFromUrl("gs://wayaapp-9ee01.appspot.com/");
            StorageReference profileRef = storageRef.child(user.getUid() + "/profile/profile.jpg");
            String downloadProfileUrl = uploadImage(profileRef);
            //Setter de datos de usuario
            UserProfile up = new UserProfile(name.getText().toString(), lname.getText().toString(),
                    status.getText().toString(), cellphone.getText().toString(), downloadProfileUrl);
            databaseReference.child(user.getUid()).setValue(up);
            Toast.makeText(this, "Datos guardados correctamente.", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(this, "Problema guardando los datos --> " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private String uploadImage(StorageReference profileRef) {
        final String[] res = {""};
        profilePic.setDrawingCacheEnabled(true);
        profilePic.buildDrawingCache();
        Bitmap bitmap = profilePic.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = profileRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                @SuppressWarnings("VisibleForTests") Uri downloadUrl = taskSnapshot.getDownloadUrl();
                res[0] = downloadUrl.toString();
            }
        });
        return res[0];
    }

    public void startCamera(View view) {
        if( ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{android.Manifest.permission.CAMERA}, 5);
            }
        }else {
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_REQUEST);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 5) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            } else {
                // Your app will not have this permission. Turn off all functions
                // that require this permission or it will force close like your
                // original question
            }
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            profilePic.setImageBitmap(photo);
        }
    }
}
