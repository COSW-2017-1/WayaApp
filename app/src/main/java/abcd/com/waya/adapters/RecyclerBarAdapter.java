package abcd.com.waya.adapters;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.List;

import abcd.com.waya.BarInformationActivity;
import abcd.com.waya.R;
import abcd.com.waya.entities.DataBar;

/**
 * Created by PERSONAL on 30/03/2017.
 */

public class RecyclerBarAdapter extends RecyclerView.Adapter<RecyclerBarAdapter.ViewHolder>{

    List<DataBar> data= Collections.emptyList();

    public RecyclerBarAdapter(List<DataBar> data) {
        this.data = data;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public int currentItem;
        public ImageView barImage;
        public TextView barTitle;
        public TextView barDescription;

        public ViewHolder(View itemView) {
            super(itemView);
            barImage = (ImageView) itemView.findViewById(R.id.bar_image);
            barTitle = (TextView) itemView.findViewById(R.id.bar_title);
            barDescription = (TextView) itemView.findViewById(R.id.bar_description);

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
            });
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DataBar current = data.get(position);
        System.out.println("URL DE IMAGEN -> " + current.imgs);
        Picasso.with(holder.barImage.getContext()).load(current.imgs).into(holder.barImage);

        //new DownloadImageTask(current).execute(current.imgs);
        //holder.barImage.setImageBitmap(current.getImage());
        holder.barTitle.setText(current.title);
        holder.barDescription.setText(current.description);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        DataBar db;

        public DownloadImageTask(DataBar db) {
            this.db = db;
        }

        @Override
        protected Bitmap doInBackground(String... urls) {
            System.out.println("CARGANDO DESDE LA URL -> " + urls[0]);
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return mIcon11;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            db.setImage(result);
        }

    }

}
