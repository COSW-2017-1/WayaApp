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

    private void loadColors(int id){
        switch (id){
            case R.id.tofavourites:
                favourites.setBackground(getResources().getDrawable(R.drawable.ripple_effect_bg_find));
                coupons.setBackground(getResources().getDrawable(R.color.barColor5));
                events.setBackground(getResources().getDrawable(R.color.barColor5));
                break;
            case R.id.tocoupons:
                favourites.setBackground(getResources().getDrawable(R.color.barColor5));
                coupons.setBackground(getResources().getDrawable(R.drawable.ripple_effect_bg_find));
                events.setBackground(getResources().getDrawable(R.color.barColor5));
                break;
            case R.id.toevents:
                favourites.setBackground(getResources().getDrawable(R.color.barColor5));
                coupons.setBackground(getResources().getDrawable(R.color.barColor5));
                events.setBackground(getResources().getDrawable(R.drawable.ripple_effect_bg_find));
                break;
        }
    }

    private void loadColors() {
        favourites = (ImageView) view.findViewById(R.id.tofavourites);
        favourites.setColorFilter(ContextCompat.getColor(view.getContext(),R.color.barColor6));
        coupons = (ImageView) view.findViewById(R.id.tocoupons);
        coupons.setColorFilter(ContextCompat.getColor(view.getContext(),R.color.barColor6));
        events = (ImageView) view.findViewById(R.id.toevents);
        events.setColorFilter(ContextCompat.getColor(view.getContext(),R.color.barColor6));
        loadColors(favourites.getId());

        /**favourites = (ImageView) view.findViewById(R.id.tofavourites);
        favourites.setColorFilter(ContextCompat.getColor(view.getContext(),R.color.colorNeonBlueLigth));
        coupons = (ImageView) view.findViewById(R.id.tocoupons);
        events = (ImageView) view.findViewById(R.id.toevents);

        favourites.setColorFilter(ContextCompat.getColor(view.getContext(),R.color.colorDarkOrange));
        favourites.setBackground(getResources().getDrawable(R.drawable.ripple_effect_primary_color));
        coupons.setColorFilter(ContextCompat.getColor(view.getContext(),R.color.greyLigth));
        coupons.setBackgroundColor(Color.parseColor("#424242"));
        events.setColorFilter(ContextCompat.getColor(view.getContext(),R.color.greyLigth));
        events.setBackgroundColor(Color.parseColor("#424242"));*/
    }

    private void loadActions() {
        favourites.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                /**favourites.setColorFilter(ContextCompat.getColor(view.getContext(),R.color.colorDarkOrange));
                favourites.setBackground(getResources().getDrawable(R.drawable.ripple_effect_primary_color));
                coupons.setColorFilter(ContextCompat.getColor(view.getContext(),R.color.greyLigth));
                coupons.setBackgroundColor(Color.parseColor("#424242"));
                events.setColorFilter(ContextCompat.getColor(view.getContext(),R.color.greyLigth));
                events.setBackgroundColor(Color.parseColor("#424242"));**/
                loadColors(favourites.getId());

                transaction = fragmentManager.beginTransaction();
                FindBarListFrag fbl = FindBarListFrag.getInstance();
                if(!fbl.isAdded()){
                    transaction.replace(R.id.tab_fragment_container, fbl);
                    transaction.commit();
                }else{
                    transaction.show(fbl);
                }
            }
        });

        coupons.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                /**favourites.setColorFilter(ContextCompat.getColor(view.getContext(),R.color.greyLigth));
                favourites.setBackgroundColor(Color.parseColor("#424242"));
                coupons.setColorFilter(ContextCompat.getColor(view.getContext(),R.color.colorDarkOrange));
                coupons.setBackground(getResources().getDrawable(R.drawable.ripple_effect_primary_color));
                events.setColorFilter(ContextCompat.getColor(view.getContext(),R.color.greyLigth));
                events.setBackgroundColor(Color.parseColor("#424242"));*/
                loadColors(coupons.getId());

                transaction = fragmentManager.beginTransaction();
                CouponsFrag cf = CouponsFrag.getInstance();
                if(!cf.isAdded()){
                    transaction.replace(R.id.tab_fragment_container, cf);
                    transaction.commit();
                }else {
                    transaction.show(cf);
                }
            }
        });

        events.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                /**favourites.setColorFilter(ContextCompat.getColor(view.getContext(),R.color.greyLigth));
                favourites.setBackgroundColor(Color.parseColor("#424242"));
                coupons.setColorFilter(ContextCompat.getColor(view.getContext(),R.color.greyLigth));
                coupons.setBackgroundColor(Color.parseColor("#424242"));
                events.setColorFilter(ContextCompat.getColor(view.getContext(),R.color.colorDarkOrange));
                events.setBackground(getResources().getDrawable(R.drawable.ripple_effect_primary_color));*/
                loadColors(events.getId());

                transaction = fragmentManager.beginTransaction();
                EventsFrag ef = EventsFrag.getInstance();
                if(!ef.isAdded()){
                    transaction.replace(R.id.tab_fragment_container, ef);
                    transaction.commit();
                }else{
                    transaction.show(ef);
                }

            }
        });

    }

    private void loadFragments() {
        //Obtener instancia de admin de fragmentos
        fragmentManager = getFragmentManager();
        //Crear transacción
        transaction = fragmentManager.beginTransaction();
        FindBarListFrag ff = FindBarListFrag.getInstance();
        if(!ff.isAdded()){
            transaction.replace(R.id.tab_fragment_container, ff);
            transaction.commit();
        }else{
            transaction.show(ff);
        }
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
