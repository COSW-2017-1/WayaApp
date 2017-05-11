package abcd.com.waya;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import abcd.com.waya.entities.singleton.UserProfileSingleton;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "Something";

    TextView text1;
    TextView text2;
    TextView text3;
    Button button;
    EditText mail;
    EditText pwd;
    //Instancia de Firebase
    private FirebaseAuth mAuth;
    //Resolver de cambios en estado de inicio de sesión
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Hide Action Bar
        getSupportActionBar().hide();
        loadCustomFonts();
        loadItems();
        mAuth = FirebaseAuth.getInstance();
        //Account creator handler
        firebaseSessionHandler();
    }

    private void loadItems() {
        mail = (EditText) findViewById(R.id.mail);
        pwd = (EditText) findViewById(R.id.password);
    }

    private void firebaseSessionHandler() {
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void login(final String email, final String password){
        System.out.println("ENTRA AL LOGIN con datos -> " + email + " pwd: " + password);
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail:failed", task.getException());
                            Toast.makeText(LoginActivity.this, "No se puede iniciar sesión.", Toast.LENGTH_SHORT).show();
                            Toast.makeText(LoginActivity.this, "Tratando de crear nueva cuenta...", Toast.LENGTH_SHORT).show();
                            createAccount(email, password);
                        }else{
                            Intent i = new Intent(LoginActivity.this, MainActivity.class);
                            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            prefs.edit().putBoolean("isLogged", true).commit();
                            startActivity(i);
                        }
                    }
                });
    }

    private void createAccount(String email, String password){

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "No se pudo registrar su cuenta.", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(LoginActivity.this, "Cuenta creada correctamente.", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void loadCustomFonts() {
        Typeface monserratAlternatesExtraLigth = Typeface.createFromAsset(getAssets(), "mael.otf");
        Typeface monserratExtraLigth = Typeface.createFromAsset(getAssets(), "mel.otf");
        Typeface monserrat = Typeface.createFromAsset(getAssets(), "m.otf");
        Typeface monserratAlternates = Typeface.createFromAsset(getAssets(), "ma.otf");
        button = (Button) findViewById(R.id.login_button);
        mail = (EditText) findViewById(R.id.mail);
        pwd = (EditText) findViewById(R.id.password);
        text1 = (TextView) findViewById(R.id.logint1);
        text2 = (TextView) findViewById(R.id.logint2);
        text3 = (TextView) findViewById(R.id.logint3);
        text1.setTypeface(monserratAlternatesExtraLigth);
        text2.setTypeface(monserrat);
        text3.setTypeface(monserratAlternatesExtraLigth);
        button.setTypeface(monserratExtraLigth);
        mail.setTypeface(monserratAlternates);
        pwd.setTypeface(monserratAlternates);
    }

    public void verifyAndSend(View view) {
        try{
            login(mail.getText().toString(), pwd.getText().toString());
        }catch (Exception e){
            if(e.getMessage().contains("empty") || e.getMessage().contains("null")){
                Toast.makeText(this, "Por favor, ingrese datos en los campos de Correo y Contraseña.", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "Not handled Error -> " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

}
