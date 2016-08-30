package com.OnCafe.Waiter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.OnCafe.Management.ManagerActivity;
import com.OnCafe.R;
import com.OnCafe.SaveIp;

import java.text.DateFormat;
import java.util.Date;

public class Tables extends AppCompatActivity {
    public OrderedProducts oproduct;
    public OrderedDBAdapter odbAdapter;

    public String tablesNo;
    public boolean tablesCheck = true;

    OrderedDBAdapter odb;
    ServerFileTransfer.ClientRxThread serv;

    Button dbBtn;
    String currentDateTimeString;

    private  String name;
    TextView check;
    SharedPreferences sTableNo;
    SharedPreferences sTime;

    public Tables() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tables_main);


        RelativeLayout myLayout=(RelativeLayout)findViewById(R.id.myLayout);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#72231F")));
        //odbAdapter = new OrderedDBAdapter(this,null,null,1);
      /*  dbBtn= (Button) findViewById(R.id.databaseBtn);
        dbBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent m =  new Intent(Tables.this,ServerFileTransfer.class);
                startActivity(m);
            }
        });*/
        FloatingActionButton home=(FloatingActionButton)findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(Tables.this, new_trial.class));

            }
        });


        String[] tableNum = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15"};
        ListAdapter bishalsAdapter = new CustomTableAdapter(this,tableNum);
        GridView bishalsListView = (GridView) findViewById(R.id.gridView);
        bishalsListView.setAdapter(bishalsAdapter);
       // AnimationDrawable transition = (AnimationDrawable) myLayout.getBackground();
       // transition.start();


/*

        odb = new OrderedDBAdapter(this);
        OrderTable tableNo=new OrderTable("",tablesN);
        check= (TextView) findViewById(R.id.check);

        if( odb.TableExist(tableNo))
        {
check.setText("tbl exist");
            //bishalsImage.setImageResource(R.drawable.table_contained);
            }
        else {
           // bishalsImage.setImageResource(R.drawable.table_default);
           check.setText("table does not exist");
            }*/





        bishalsListView.setOnItemClickListener(
                new OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        tablesNo =String.valueOf(parent.getItemAtPosition(position));
                        sTableNo= getSharedPreferences("Table Number",0);

                        SharedPreferences.Editor editor=sTableNo.edit();
                        editor.putString("hello",tablesNo);
                        editor.apply();


                        //passing tableN to create database with the respective table name

                        OrderTable tableNo=new OrderTable(tablesNo);
                        odb = new OrderedDBAdapter(Tables.this);
                        odb.setName(tableNo);


                        if (!odb.frontTableExist(tablesNo)){

                           currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                            Toast.makeText(Tables.this,currentDateTimeString,Toast.LENGTH_LONG).show();
                            sTime= getSharedPreferences("Time"+tablesNo,0);

                            SharedPreferences.Editor timeEditor=sTime.edit();
                            timeEditor.putString("Time",currentDateTimeString);
                            timeEditor.apply();
                        }

                        Intent i = new Intent(Tables.this,WaiterCategoryMenu.class);


                        finish();
                        startActivity(i);


                    }
                }
        );



    }





    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(Tables.this,new_trial.class);
        finish();
        startActivity(i);
    }

}
