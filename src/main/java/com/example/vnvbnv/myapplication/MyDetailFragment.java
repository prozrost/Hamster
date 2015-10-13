package com.example.vnvbnv.myapplication;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    View v = inflater.inflate(R.layout.detail_fragment, null);
tvTitle = (TextView) v.findViewById(R.id.tvsection);
tvDescription = (TextView) v.findViewById(R.id.tvdescription);
tvImage = (ImageView) v.findViewById(R.id.tvimage);

return v;
}
    public void onCreate(Bundle savedInstanceState){
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = this.getArguments();
    String title = getArguments().getString("title");
    String description = getArguments().getString("description");
    String image = getArguments().getString("image");
    tvTitle.setText(title);
    tvDescription.setText(description);
    Picasso.with(getActivity()).load(image).into(tvImage);
}
public void onCreateOptionsMenu(Menu menu,MenuInflater inflater){
inflater.inflate(R.menu.detail_fragmement_menu, menu);
    super.onCreateOptionsMenu(menu, inflater);
    MenuItem shareaction = menu.findItem(R.id.action_share);
    if(isNetworkConnected()!=true){
        shareaction.setVisible(false);
    }

}
public boolean onOptionsItemSelected(MenuItem item){
    switch (item.getItemId()){
        case R.id.action_share:
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TITLE, intent.getStringExtra("title"));
            intent.putExtra(Intent.EXTRA_TEXT, intent.getStringExtra("url"));
            startActivity(Intent.createChooser(intent, "Share via:"));

    }
    return super.onOptionsItemSelected(item);
}
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni == null) {
            // There are no active networks.
            return false;
        } else
            return true;
    }

}
