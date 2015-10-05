package com.example.vnvbnv.myapplication;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by vnvbnv on 05.10.2015.
 */
public class MyDetailFragment extends Fragment {
    TextView  tvTitle;
    TextView  tvDescription;
   ImageView tvImage;

public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){
    View v = inflater.inflate(R.layout.detail_fragment,null);
tvTitle = (TextView) v.findViewById(R.id.tvsection);
tvDescription = (TextView) v.findViewById(R.id.tvdescription);
tvImage = (ImageView) v.findViewById(R.id.tvimage);
    Bundle bundle = getArguments();
    String title = bundle.getString("title");
    String description = bundle.getString("description");
    String image = bundle.getString("image");
tvTitle.setText(title);
tvDescription.setText(description);
    Picasso.with(getActivity()).load(image).into(tvImage);
return v;
}
}
