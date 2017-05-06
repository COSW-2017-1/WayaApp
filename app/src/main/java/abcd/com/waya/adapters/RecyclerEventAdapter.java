package abcd.com.waya.adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import abcd.com.waya.BarInformationActivity;
import abcd.com.waya.R;
import abcd.com.waya.entities.DataBar;
import abcd.com.waya.entities.DataEvent;

/**
 * Created by PERSONAL on 30/03/2017.
 */

public class RecyclerEventAdapter extends RecyclerView.Adapter<RecyclerEventAdapter.ViewHolder>{

    List<DataEvent> data= Collections.emptyList();
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    public RecyclerEventAdapter(List<DataEvent> data) {
        this.data = data;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public TextView title;
        public TextView eventDate;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.event_title);
            eventDate = (TextView) itemView.findViewById(R.id.event_date);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    Intent intent = new Intent(view.getContext() ,BarInformationActivity.class);
                    intent.putExtra("position",position);
                    view.getContext().startActivity(intent);
                }
            });
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.event_card_layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DataEvent current = data.get(position);
        holder.title.setText(current.title);
        Date date = new Date(current.eventDate);
        String expDateC = dateFormat.format(date);
        holder.eventDate.setText(expDateC);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}
