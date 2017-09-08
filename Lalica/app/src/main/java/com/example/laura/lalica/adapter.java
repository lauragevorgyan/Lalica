package com.example.laura.lalica;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class adapter extends FragmentPagerAdapter {
    public adapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment F=null;
        if(position==0){
            F= new BlankFragment();
        }else if(position==1){
            F=new BlankFragment1();
        }
        return F;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
