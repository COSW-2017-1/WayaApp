package abcd.com.waya;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    TextView text1;
    TextView text2;
    TextView text3;
    Button button;
    EditText mail;
    EditText pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Hide Action Bar
        getSupportActionBar().hide();
        loadCustomFonts();
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
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
