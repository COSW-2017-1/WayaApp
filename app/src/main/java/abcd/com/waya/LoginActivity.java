package abcd.com.waya;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Hide Action Bar
        getSupportActionBar().hide();
    }

    public void verifyAndSend(View view) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
