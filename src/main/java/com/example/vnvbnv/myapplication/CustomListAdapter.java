package com.example.vnvbnv.myapplication;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by vnvbnv on 03.10.2015.
 */
public class CustomListAdapter extends SimpleAdapter {
    private static final String IMAGE = null;
    private final Activity context;
    private ArrayList<HashMap<String, String>> result;
    public CustomListAdapter(Activity context, ArrayList<HashMap<String, String>> data, int resource, String[] from, int[] to) {
        super(context,data,resource,from,to);
        // TODO Auto-generated constructor stub

        this.result = data;
        this.context = context;
    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.list_item, null, true);


        ImageView imageView = (ImageView) rowView.findViewById(R.id.image);
        TextView title = (TextView) rowView.findViewById(R.id.title);
        title.setText(result.get(position).get("title"));
        TextView description = (TextView) rowView.findViewById(R.id.description);
        description.setText(result.get(position).get("description"));
        String url = result.get(position).get("image");
        Picasso.with(context)
                .load(url).into(imageView);


        return rowView;

    }


}
