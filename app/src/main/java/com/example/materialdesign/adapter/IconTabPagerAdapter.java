package com.example.materialdesign.adapter;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class IconTabPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> list_of_fragments = new ArrayList<>();
    private final List<String> list_of_gragment_titles = new ArrayList<>();

    public IconTabPagerAdapter(FragmentManager manager) {
        super(manager);
    }

    @Override
    public Fragment getItem(int position)
    {
        return list_of_fragments.get(position);
    }

    @Override
    public int getCount() {
        return list_of_fragments.size();
    }

    public void addFragment(Fragment fragment, String title) {
        list_of_fragments.add(fragment);
        list_of_gragment_titles.add(title);
    }

    public String getTitle(int position) {

        return list_of_gragment_titles.get(position);
    }

    //we dont want to return the title in this case so we only have the icon displayed
    @Override
    public CharSequence getPageTitle(int position) {
        return null;
    }
}
