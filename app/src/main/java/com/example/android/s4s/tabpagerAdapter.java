package com.example.android.s4s;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class tabpagerAdapter extends FragmentPagerAdapter {

    String[] tabarray = new String[]{"Computer Science","Mechanical","Electrical","Electronics","Information Technology","Civil","Mining","Metallurgy"};

    Integer tabnumber = 8;
    private Context context;

    public tabpagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabarray[position];
    }

    @Override
    public Fragment getItem(int i) {
        switch(i)
        {
            case 0:
                CS cs1 = new CS();
                return cs1;

            case 1:
                ME  me1 = new ME();
                return me1;

            case 2:
                EE ee1 = new EE();
                return ee1;

            case 3:
                EC ec1 = new EC();
                return ec1;

            case 4:
                IT it1 = new IT();
                return it1;

            case 5:
                CV cv1 = new CV();
                return cv1;

            case 6:
                MN mn1 = new MN();
                return mn1;

            case 7:
                MT mt1 = new MT();
                return mt1;
        }
        return null;
    }

    @Override
    public int getCount() {
        return tabnumber;
    }
}
