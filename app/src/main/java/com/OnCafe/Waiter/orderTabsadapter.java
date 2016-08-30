package com.OnCafe.Waiter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class orderTabsadapter extends FragmentStatePagerAdapter{

    private int TOTAL_TABS = 3;
    public String tableNo;


    public orderTabsadapter(FragmentManager fm) {
        super(fm);



    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:

                return new OrderItemFragment();

            case 1:
                return new BillFragment();

            case 2:
                return new SummaryFragment();
        }

        return null;
    }

    @Override
    public int getCount() {

        return TOTAL_TABS;
    }

}