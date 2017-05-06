package abcd.com.waya.fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import abcd.com.waya.R;
import abcd.com.waya.adapters.RecyclerBarAdapter;
import abcd.com.waya.adapters.RecyclerCouponAdapter;
import abcd.com.waya.entities.DataBar;
import abcd.com.waya.entities.DataCoupon;
import abcd.com.waya.entities.singleton.DataBarSingleton;
import abcd.com.waya.entities.singleton.DataCouponSingleton;

public class CouponsFrag extends Fragment{

    //Elementos del RecyclerView
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    DataCouponSingleton dcs = DataCouponSingleton.getInstance().getInstance();
    DataBarSingleton dbs = DataBarSingleton.getInstance();
    //Vista del fragmento
    View view;

    private static CouponsFrag instance = null;

    public CouponsFrag() {
    }

    public static CouponsFrag getInstance() {
        if(instance == null) {
            instance = new CouponsFrag();
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_coupon_list, container, false);
        return  view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //----------------------------------------------------------------------------------------------
        //Make call to AsyncTask
        try{
            dcs.getData().get(0);
            recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_coupon_list);
            layoutManager = new LinearLayoutManager(view.getContext());
            recyclerView.setLayoutManager(layoutManager);
            adapter = new RecyclerCouponAdapter(dcs.getData());
            recyclerView.setAdapter(adapter);
        }catch (Exception e){
            loadCoupons();
        }
    }

    private void loadCoupons() {

        Iterator<DataBar> it = dbs.getData().iterator();
        List<DataCoupon> data = new ArrayList<>();

        try {
            while(it.hasNext()){
                String coupons = it.next().coupons;
                JSONArray cArray = new JSONArray(coupons);
                for (int j=0; j<cArray.length(); j++){
                    JSONObject json_c_data = cArray.getJSONObject(j);
                    DataCoupon dataCoupon = new DataCoupon();
                    dataCoupon.type = json_c_data.getString("tipo");
                    dataCoupon.title = json_c_data.getString("titulo");
                    dataCoupon.discount = json_c_data.getInt("descuento");
                    dataCoupon.expDate = json_c_data.getLong("fechaExpiracion");
                    data.add(dataCoupon);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        dcs.setData(data);

        // Setup and Handover data to recyclerview
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_coupon_list);
        layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerCouponAdapter(data);
        recyclerView.setAdapter(adapter);
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
