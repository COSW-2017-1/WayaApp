package abcd.com.waya.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import abcd.com.waya.R;
import abcd.com.waya.adapters.BarListAdapter;

/**
 * Created by PERSONAL on 20/03/2017.
 */

public class FindFrag extends Fragment {

    //Definicion de botones
    ImageView favourites;
    ImageView coupons;
    ImageView events;
    //Obtener instancia de admin de fragmentos
    FragmentManager fragmentManager;
    //Crear transacción
    FragmentTransaction transaction;
    //Vista del fragmento
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_find, container, false);
        loadFragments();
        loadActions();
        return  view;
    }

    private void loadActions() {
        favourites = (ImageView) view.findViewById(R.id.tofavourites);
        favourites.setColorFilter(ContextCompat.getColor(view.getContext(),R.color.colorNeonBlueLigth));
        coupons = (ImageView) view.findViewById(R.id.tocoupons);
        events = (ImageView) view.findViewById(R.id.toevents);

        favourites.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                favourites.setColorFilter(ContextCompat.getColor(view.getContext(),R.color.colorNeonBlueLigth));
                coupons.setColorFilter(ContextCompat.getColor(view.getContext(),R.color.colorDarkOrange));
                events.setColorFilter(ContextCompat.getColor(view.getContext(),R.color.colorDarkOrange));
                transaction = fragmentManager.beginTransaction();
                FindBarListFrag fbl = new FindBarListFrag();
                transaction.replace(R.id.tab_fragment_container, fbl);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        coupons.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                favourites.setColorFilter(ContextCompat.getColor(view.getContext(),R.color.colorDarkOrange));
                coupons.setColorFilter(ContextCompat.getColor(view.getContext(),R.color.colorNeonBlueLigth));
                events.setColorFilter(ContextCompat.getColor(view.getContext(),R.color.colorDarkOrange));
                transaction = fragmentManager.beginTransaction();
                CouponsFrag cf = new CouponsFrag();
                transaction.replace(R.id.tab_fragment_container, cf);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        events.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                favourites.setColorFilter(ContextCompat.getColor(view.getContext(),R.color.colorDarkOrange));
                coupons.setColorFilter(ContextCompat.getColor(view.getContext(),R.color.colorDarkOrange));
                events.setColorFilter(ContextCompat.getColor(view.getContext(),R.color.colorNeonBlueLigth));
                transaction = fragmentManager.beginTransaction();
                EventsFrag ef = new EventsFrag();
                transaction.replace(R.id.tab_fragment_container, ef);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

    }

    private void loadFragments() {
        //Obtener instancia de admin de fragmentos
        fragmentManager = getFragmentManager();
        //Crear transacción
        transaction = fragmentManager.beginTransaction();
        FindBarListFrag ff = new FindBarListFrag();
        transaction.add(R.id.tab_fragment_container, ff);
        transaction.commit();
    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Resources res = getResources();
        /*barList = (ListView) getView().findViewById(R.id.bar_list);
        BarListAdapter barAdapter = new BarListAdapter(getView().getContext(), title, imgs, description);
        barList.setAdapter(barAdapter);*/

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
