package abcd.com.waya;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import abcd.com.waya.entities.singleton.UserProfileSingleton;
import abcd.com.waya.fragments.FindFrag;
import abcd.com.waya.fragments.GalleryFrag;
import abcd.com.waya.fragments.ProfileFrag;

public class MainActivity extends AppCompatActivity {

    UserProfileSingleton ups = UserProfileSingleton.getInstance();

    //Obtener instancia de admin de fragmentos
    FragmentManager fragmentManager;
    //Crear transacción
    FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Hide Action Bar
        getSupportActionBar().hide();
        loadFragment();
    }

    private void loadFragment() {
        //Obtener instancia de admin de fragmentos
        fragmentManager = getFragmentManager();
        //Crear transacción
        transaction = fragmentManager.beginTransaction();
        FindFrag ff = new FindFrag();
        transaction.add(R.id.fragment_container, ff);
        transaction.commit();
    }

    public void changeView(View view) {
        transaction = fragmentManager.beginTransaction();
        //Crear Fragmento y añadirlo

        switch (view.getId()){
            case R.id.drink:
                FindFrag ff = new FindFrag();
                transaction.replace(R.id.fragment_container, ff);
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            case R.id.profile:
                ProfileFrag pf = new ProfileFrag();
                transaction.replace(R.id.fragment_container, pf);
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            case R.id.gallery:
                GalleryFrag gf = new GalleryFrag();
                transaction.replace(R.id.fragment_container, gf);
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            case R.id.comment:
                MapFrag mf = new MapFrag();
                transaction.replace(R.id.fragment_container, mf);
                transaction.addToBackStack(null);
                transaction.commit();
                break;
        }
    }
}
