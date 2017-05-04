package abcd.com.waya.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import abcd.com.waya.R;

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
        loadColors();
        loadActions();
        return  view;
    }

    private void loadColors() {
        favourites = (ImageView) view.findViewById(R.id.tofavourites);
        favourites.setColorFilter(ContextCompat.getColor(view.getContext(),R.color.colorNeonBlueLigth));
        coupons = (ImageView) view.findViewById(R.id.tocoupons);
        events = (ImageView) view.findViewById(R.id.toevents);

        favourites.setColorFilter(ContextCompat.getColor(view.getContext(),R.color.colorDarkOrange));
        favourites.setBackground(getResources().getDrawable(R.drawable.ripple_effect_primary_color));
        coupons.setColorFilter(ContextCompat.getColor(view.getContext(),R.color.greyLigth));
        coupons.setBackgroundColor(Color.parseColor("#424242"));
        events.setColorFilter(ContextCompat.getColor(view.getContext(),R.color.greyLigth));
        events.setBackgroundColor(Color.parseColor("#424242"));
    }

    private void loadActions() {
        favourites.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                favourites.setColorFilter(ContextCompat.getColor(view.getContext(),R.color.colorDarkOrange));
                favourites.setBackground(getResources().getDrawable(R.drawable.ripple_effect_primary_color));
                coupons.setColorFilter(ContextCompat.getColor(view.getContext(),R.color.greyLigth));
                coupons.setBackgroundColor(Color.parseColor("#424242"));
                events.setColorFilter(ContextCompat.getColor(view.getContext(),R.color.greyLigth));
                events.setBackgroundColor(Color.parseColor("#424242"));
                transaction = fragmentManager.beginTransaction();
                FindBarListFrag fbl = FindBarListFrag.getInstance();
                transaction.replace(R.id.tab_fragment_container, fbl);
                transaction.commit();
            }
        });

        coupons.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                favourites.setColorFilter(ContextCompat.getColor(view.getContext(),R.color.greyLigth));
                favourites.setBackgroundColor(Color.parseColor("#424242"));
                coupons.setColorFilter(ContextCompat.getColor(view.getContext(),R.color.colorDarkOrange));
                coupons.setBackground(getResources().getDrawable(R.drawable.ripple_effect_primary_color));
                events.setColorFilter(ContextCompat.getColor(view.getContext(),R.color.greyLigth));
                events.setBackgroundColor(Color.parseColor("#424242"));
                transaction = fragmentManager.beginTransaction();
                CouponsFrag cf = new CouponsFrag();
                transaction.replace(R.id.tab_fragment_container, cf);
                transaction.commit();
            }
        });

        events.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                favourites.setColorFilter(ContextCompat.getColor(view.getContext(),R.color.greyLigth));
                favourites.setBackgroundColor(Color.parseColor("#424242"));
                coupons.setColorFilter(ContextCompat.getColor(view.getContext(),R.color.greyLigth));
                coupons.setBackgroundColor(Color.parseColor("#424242"));
                events.setColorFilter(ContextCompat.getColor(view.getContext(),R.color.colorDarkOrange));
                events.setBackground(getResources().getDrawable(R.drawable.ripple_effect_primary_color));
                transaction = fragmentManager.beginTransaction();
                EventsFrag ef = new EventsFrag();
                transaction.replace(R.id.tab_fragment_container, ef);
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
