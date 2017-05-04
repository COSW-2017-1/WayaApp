package abcd.com.waya;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import abcd.com.waya.entities.DataBarSingleton;

public class BarInformationActivity extends AppCompatActivity {

    ImageView logo;
    TextView title;
    TextView descriptionLabel;
    TextView description;
    TextView horarioLabel;
    TextView horario;
    TextView tipo;
    TextView direccionLabel;
    TextView direccion;
    TextView genero;
    //fonts
    Typeface monserratAlternatesExtraLigth;
    Typeface monserratExtraLigth;
    Typeface monserrat;
    Typeface monserratAlternates;

    DataBarSingleton dbs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_information);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbs = DataBarSingleton.getInstance();
        int i = (int) getIntent().getExtras().get("position");
        loadFonts();
        cargarInfoBar(i);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void loadFonts() {
        monserratAlternatesExtraLigth = Typeface.createFromAsset(getAssets(), "mael.otf");
        monserratExtraLigth = Typeface.createFromAsset(getAssets(), "mel.otf");
        monserrat = Typeface.createFromAsset(getAssets(), "m.otf");
        monserratAlternates = Typeface.createFromAsset(getAssets(), "ma.otf");
    }

    private void cargarInfoBar(int index) {
        loadElements();
        setFonts();
        title.setText(dbs.getData().get(index).title);
        description.setText(dbs.getData().get(index).description);
        genero.setText(dbs.getData().get(index).genero);
        setTipo(tipo, index);
        direccion.setText(dbs.getData().get(index).direccion);

        if(dbs.getData().get(index).horario.equals("") || dbs.getData().get(index).horario == null || dbs.getData().get(index).horario.equals("null")){
            horario.setText("11 pm a 3 am (auto)");
        }else {
            horario.setText(dbs.getData().get(index).horario);
        }
    }

    private void setFonts() {
        title.setTypeface(monserratAlternates);
        tipo.setTypeface(monserratExtraLigth);
        genero.setTypeface(monserratExtraLigth);
        descriptionLabel.setTypeface(monserratAlternates);
        description.setTypeface(monserratAlternates);
        horario.setTypeface(monserratExtraLigth);
        horarioLabel.setTypeface(monserratAlternates);
        direccionLabel.setTypeface(monserratAlternates);
        direccion.setTypeface(monserratExtraLigth);
    }

    private void loadElements() {
        title = (TextView) findViewById(R.id.titlebar);
        descriptionLabel = (TextView) findViewById(R.id.descripcionlabel);
        description = (TextView) findViewById(R.id.descripcionbar);
        horarioLabel = (TextView) findViewById(R.id.horariolabel);
        horario = (TextView) findViewById(R.id.horariobar);
        genero = (TextView) findViewById(R.id.generobar);
        tipo = (TextView) findViewById(R.id.tipobar);
        direccionLabel = (TextView) findViewById(R.id.direccionlabel);
        direccion = (TextView) findViewById(R.id.direccionbar);
    }

    public void setTipo(TextView tipo, int i) {
        if(dbs.getData().get(i).tipo.equals("normal") || dbs.getData().get(i).tipo.contains("ormal")) {
            tipo.setText("Normal");
            tipo.setBackground(getResources().getDrawable(R.drawable.rounded_corner_normal));
        }
        if(dbs.getData().get(i).tipo.equals("Cover") || dbs.getData().get(i).tipo.contains("over")) {
            tipo.setText("Pago Cover");
            tipo.setBackground(getResources().getDrawable(R.drawable.rounded_corner_cover));
        }
        if(dbs.getData().get(i).tipo.equals("free") || dbs.getData().get(i).tipo.contains("bar")) {
            tipo.setText("Barra Libre");
            tipo.setBackground(getResources().getDrawable(R.drawable.rounded_corner_free));
        }
    }
}
