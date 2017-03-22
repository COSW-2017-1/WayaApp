package abcd.com.wayaapp.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import abcd.com.wayaapp.R;
import abcd.com.wayaapp.fragments.FindFrag;

/**
 * Created by PERSONAL on 21/03/2017.
 */

public class BarListAdapter extends ArrayAdapter<String> {

    Context context;
    int[] imgs;
    String titles[];
    String description;
    View view;

    public BarListAdapter(Context c, String[] titles, int[] imgs, String description){
        super(c, R.layout.item_bar, R.id.title_text, titles);
        this.context = c;
        this.imgs = imgs;
        this.titles = titles;
        this.description = description;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View barItem = layoutInflater.inflate(R.layout.item_bar, parent, false);
        ImageView img = (ImageView) barItem.findViewById(R.id.bar_icon);
        TextView tv1 = (TextView) barItem.findViewById(R.id.title_text);
        TextView tv2 = (TextView) barItem.findViewById(R.id.descriptiontext);
        img.setImageResource(imgs[position]);
        tv1.setText(titles[position]);
        System.out.println("ENTRAAAAAAAAAAAAAAA" + description);
        tv2.setText(description);
        return barItem;
    }

}
