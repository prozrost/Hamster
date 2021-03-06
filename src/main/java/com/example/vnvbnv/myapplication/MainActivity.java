package com.example.vnvbnv.myapplication;

import android.app.Activity;

import android.app.FragmentTransaction;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {
    private String[] mCatTitles;
    private ListView mDrawerListView;
AboutFragment aboutfragment;
    MainList fragmenlist;
    DrawerLayout drawer;

    FragmentTransaction ftrans;
    FragmentTransaction ftrans1;
    MyDetailFragment detailfragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
fragmenlist = new MainList();
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        detailfragment = new MyDetailFragment();
        ftrans = getFragmentManager().beginTransaction();
        mCatTitles = getResources().getStringArray(R.array.screen_array);
        mDrawerListView = (ListView) findViewById(R.id.left_drawer);
View headerdrawer = View.inflate(this,R.layout.headernawdrawer,null);
        mDrawerListView.addHeaderView(headerdrawer);
        mDrawerListView.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mCatTitles));
            ftrans.add(R.id.FragmentCont, fragmenlist);
            ftrans.commit();
        mDrawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position==1){
                    aboutfragment=new AboutFragment();
                    ftrans1 = getFragmentManager().beginTransaction();
drawer.closeDrawer(mDrawerListView);
                    ftrans1.replace(R.id.FragmentCont,aboutfragment);
                    ftrans1.addToBackStack(null);
                    ftrans1.commit();

                }
            }
        });
        }




    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni == null) {
            // There are no active networks.
            return false;
        } else
            return true;
    }
}
