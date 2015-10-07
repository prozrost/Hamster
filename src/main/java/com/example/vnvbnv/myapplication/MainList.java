package com.example.vnvbnv.myapplication;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by vnvbnv on 03.10.2015.
 */
public class MainList extends ListFragment{
    SqlHelper dbHelper;
    ListView mainList;
    private static String url = "https://fierce-citadel-4259.herokuapp.com/hamsters";
    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";
    private static final String IMAGE = "image";
    ArrayList<HashMap<String,String>> jsonlist1 = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String,String>> bdList = new ArrayList<HashMap<String, String>>();
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.list_fragme, null);
        mainList = (ListView)v.findViewById(android.R.id.list);

        return v;
    }




    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        new ProgressTask().execute();

    }

    @Override
    public void onListItemClick(ListView l, View view, int position, long id) {
        super.onListItemClick(l,view,position,id);
        String title = jsonlist1.get(position).get("title");
        String description= jsonlist1.get(position).get("description");
        String image = jsonlist1.get(position).get("image");
        MyDetailFragment detailFragment = new MyDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title",title);
        bundle.putString("description", description);
        bundle.putString("image", image);
        detailFragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getActivity().getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.FragmentCont,detailFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private class ProgressTask extends AsyncTask<String,Void,Boolean> {
        private ProgressDialog dialog;
        private Activity activity;
        private MainActivity context;
        private String[] params;

        public ProgressTask(MainActivity activity) throws SQLException {
            this.activity = getActivity();
            context = activity;
            dialog = new ProgressDialog(getActivity().getApplicationContext());
dbHelper = new SqlHelper(getActivity());
        }

        public ProgressTask()  {
            dialog = new ProgressDialog(getActivity().getApplicationContext());
            try {
                dbHelper = new SqlHelper(getActivity().getApplicationContext());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected Boolean doInBackground(String... params) {
            this.params = params;
            JSONParser jParser = new JSONParser();
            JSONArray json = jParser.getJSONFromUrl(url);
            for(int i =0;i<json.length();i++) {
                try {
                    JSONObject c = json.getJSONObject(i);
                    String vtitle = c.getString(TITLE);
                    String vdescription = c.getString(DESCRIPTION);
                    String vimage = c.getString(IMAGE);
                    dbHelper.open();
                    dbHelper.createEntry(vtitle, vimage, vdescription);

                    dbHelper.close();

                    HashMap<String, String> map = new HashMap<>();
                    map.put(TITLE, vtitle);
                    map.put(DESCRIPTION, vdescription);
                    map.put(IMAGE, vimage);

                    jsonlist1.add(map);

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }


        protected void onPreExecute(){

        }
        protected void onPostExecute(final Boolean success){


            try{
                if((this.dialog != null)&& this.dialog.isShowing()){
                    this.dialog.dismiss();
                }


                CustomListAdapter adapter = new CustomListAdapter(getActivity(), jsonlist1, R.layout.list_item, new String[]{TITLE, DESCRIPTION}, new int[]{R.id.title, R.id.description});
                mainList.setAdapter(adapter);




            }catch
                    (final IllegalArgumentException e){e.printStackTrace();}
        }

    }

}
