package abcd.com.waya.adapters;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import abcd.com.waya.BarInformationActivity;
import abcd.com.waya.R;
import abcd.com.waya.entities.DataBar;
import abcd.com.waya.entities.DataCoupon;

/**
 * Created by PERSONAL on 30/03/2017.
 */

public class RecyclerCouponAdapter extends RecyclerView.Adapter<RecyclerCouponAdapter.ViewHolder>{

    List<DataCoupon> data= Collections.emptyList();
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    public RecyclerCouponAdapter(List<DataCoupon> data) {
        this.data = data;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public int currentItem;
        public TextView couponTitle;
        public TextView couponDiscount;
        public TextView expDate;

        public ViewHolder(View itemView) {
            super(itemView);
            couponTitle = (TextView) itemView.findViewById(R.id.coupon_title);
            couponDiscount = (TextView) itemView.findViewById(R.id.coupon_discount);
            expDate = (TextView) itemView.findViewById(R.id.exp_date);

            //TODO agregar evento al clic
            /**
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    Intent intent = new Intent(view.getContext() ,BarInformationActivity.class);
                    intent.putExtra("position",position);
                    view.getContext().startActivity(intent);
                    //Snackbar.make(view, "Clic detectado en el item " + position,
                    //       Snackbar.LENGTH_SHORT).setAction("Action", null).show();
                }
            });*/

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.coupon_card_layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DataCoupon current = data.get(position);
        holder.couponTitle.setText(current.title);
        holder.couponDiscount.setText(Integer.toString(current.discount) + "%");
        Date date = new Date(current.expDate);
        String expDateC = dateFormat.format(date);
        holder.expDate.setText(" " + expDateC);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}
