package abcd.com.waya.adapters;

import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import abcd.com.waya.R;

/**
 * Created by PERSONAL on 30/03/2017.
 */

public class RecyclerBarAdapter extends RecyclerView.Adapter<RecyclerBarAdapter.ViewHolder>{

    String[] title = {"Casa de la Cerveza",
            "Gnoveva Bar",
            "Once Once Bar",
            "La Villa Bar",
            "La zorra",
            "The Pub",
            "Opera Bar"};

    int[] imgs = {R.drawable.casadlcbar,
            R.drawable.gnovevabar,
            R.drawable.onceoncebar,
            R.drawable.lavillabar,
            R.drawable.lazorra,
            R.drawable.thepubbar,
            R.drawable.operabar};

    String description = "Lorem Ipsum es simplemente el texto de relleno de las imprentas y " +
            "archivos de texto. Lorem Ipsum haLorem Ipsum es simplemente el texto de relleno de las" +
            " imprentas y archivos de texto. Lorem Ipsum ha";

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
                    Snackbar.make(view, "Clic detectado en el item " + position,
                            Snackbar.LENGTH_SHORT).setAction("Action", null).show();
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
        holder.barImage.setImageResource(imgs[position]);
        holder.barTitle.setText(title[position]);
        holder.barDescription.setText(description);
    }

    @Override
    public int getItemCount() {
        return title.length;
    }
}
