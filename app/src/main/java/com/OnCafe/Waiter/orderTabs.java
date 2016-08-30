package com.OnCafe.Waiter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.OnCafe.R;

public class orderTabs extends ActionBarActivity implements android.support.v7.app.ActionBar.TabListener {

    private ViewPager tabsviewPager;

    private orderTabsadapter mTabsAdapter;

    String tableN;
    SharedPreferences sTableNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_menu_activity);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0000ff")));

        tabsviewPager = (ViewPager) findViewById(R.id.tabspager);

        mTabsAdapter = new orderTabsadapter(getSupportFragmentManager());

        tabsviewPager.setAdapter(mTabsAdapter);

        sTableNo= getSharedPreferences("Table Number",0);
        tableN= sTableNo.getString("hello","Table");

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);


        Tab menuTab = getSupportActionBar().newTab().setText("Orders").setTabListener(this);
        Tab ordersTab = getSupportActionBar().newTab().setText("Bill").setTabListener(this);
        Tab transcriptTab = getSupportActionBar().newTab().setText("Summary").setTabListener(this);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#72231F")));
        getSupportActionBar().setTitle("Table " + tableN + "  Details");
        getSupportActionBar().addTab(menuTab);
        getSupportActionBar().addTab(ordersTab);
        getSupportActionBar().addTab(transcriptTab);


        FloatingActionButton home=(FloatingActionButton)findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(orderTabs.this, new_trial.class));
            }
        });


        //This helps in providing swiping effect for v7 compat library
        tabsviewPager.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                YourFragmentInterface fragment = (YourFragmentInterface) mTabsAdapter.instantiateItem(tabsviewPager, position);
                if (fragment != null) {
                    fragment.fragmentBecameVisible();
                }

                getSupportActionBar().setSelectedNavigationItem(position);

            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {


            }

            @Override
            public void onPageScrollStateChanged(int arg0) {


            }
        });

    }

    @Override
    public void onTabReselected(Tab arg0, FragmentTransaction arg1) {


    }

    @Override
    public void onTabSelected(Tab selectedtab, FragmentTransaction arg1) {

        tabsviewPager.setCurrentItem(selectedtab.getPosition()); //update tab position on tap
    }

    @Override
    public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_tabs, menu);

        return super.onCreateOptionsMenu(menu);
    }
    public interface YourFragmentInterface {
        void fragmentBecameVisible();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        // Handle presses on the action bar items
        switch (item.getItemId()) {

            case R.id.send:

                Intent intent = new Intent(orderTabs.this, ServerFileTransfer.class);

                finish();
                startActivity(intent);
                return true;
            case R.id.action_list:

                Intent m = new Intent(orderTabs.this, WaiterCategoryMenu.class);

                finish();
                startActivity(m);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(orderTabs.this,WaiterCategoryMenu.class);
        finish();
        startActivity(i);
    }
}