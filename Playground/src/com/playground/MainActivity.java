package com.playground;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

public class MainActivity extends FragmentActivity {
	private MyPageAdapter mPageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<Fragment> fragments = getFragments();
        mPageAdapter = new MyPageAdapter(getSupportFragmentManager(), fragments);
        ViewPager pager = (ViewPager)findViewById(R.id.viewpager);
        pager.setAdapter(mPageAdapter);
    }
    
    private List<Fragment> getFragments() {
    	List<Fragment> fList = new ArrayList<Fragment>();
    	
    	fList.add(MyFragment.newInstance("Fragment 1"));
    	fList.add(MyFragment.newInstance("Fragment 2"));
    	fList.add(MyFragment.newInstance("Fragment 3"));
    	
    	return fList;
    }
    
    private class MyPageAdapter extends FragmentPagerAdapter {
    	private List<Fragment> fragments;
    	
    	public MyPageAdapter(FragmentManager fm, List<Fragment> fragments) {
    		super(fm);
    		this.fragments = fragments;
    	}
    	
		@Override
		public Fragment getItem(int position) {
			return this.fragments.get(position);
		}

		@Override
		public int getCount() {
			return this.fragments.size();
		}
    	
    }

}
