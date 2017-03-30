package abcd.com.waya.fragments;

import android.app.Fragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import abcd.com.waya.R;
import abcd.com.waya.adapters.BarListAdapter;

public class FindBarListFrag extends Fragment{

    //Vista del fragmento
    View view;
    //Elementos quemados
    ListView barList;
    String[] title = {"Casa de la Cerveza","Gnoveva Bar", "Once Once Bar", "La Villa Bar",
            "La zorra", "The Pub","Opera Bar"};
    int[] imgs = {R.drawable.casadlcbar, R.drawable.gnovevabar, R.drawable.onceoncebar,
            R.drawable.lavillabar, R.drawable.lazorra, R.drawable.thepubbar, R.drawable.operabar};
    String description = "lorem ipsum dolor sit amet";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_find_bar_list, container, false);
        return  view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Resources res = getResources();
        barList = (ListView) getView().findViewById(R.id.bar_list);
        BarListAdapter barAdapter = new BarListAdapter(getView().getContext(), title, imgs, description);
        barList.setAdapter(barAdapter);
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