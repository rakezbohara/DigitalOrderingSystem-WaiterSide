package com.OnCafe.Waiter;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.OnCafe.R;


public class SummaryFragment extends Fragment implements orderTabs.YourFragmentInterface  {
OrderedDBAdapter odbAdapter;
    TextView time;
    String t;

    String tableNum;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        final View view = inflater.inflate(R.layout.summary_fragment, container, false);
        SharedPreferences sTableNo = this.getActivity().getSharedPreferences("Table Number", Context.MODE_PRIVATE);
        tableNum= sTableNo.getString("hello","table");
        SharedPreferences sTime = this.getActivity().getSharedPreferences("Time"+tableNum, Context.MODE_PRIVATE);

        t= sTime.getString("Time","time");
        time=(TextView)view.findViewById(R.id.time);
        time.setText(t);
      //  printDatabase(view);
        return view;

    }






    @Override
    public void fragmentBecameVisible() {
        // You can do your animation here because we are visible! (make sure onViewCreated has been called too and the Layout has been laid. Source for another question but you get the idea.



    }





}






