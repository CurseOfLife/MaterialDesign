package com.example.materialdesign.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class SimpleTabPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> list_of_fragments = new ArrayList<>();
    private final List<String> list_of_fragment_titles = new ArrayList<>();

    public SimpleTabPagerAdapter(FragmentManager supportFragmentManager) {
        super(supportFragmentManager);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return list_of_fragments.get(position);
    }

    @Override
    public int getCount() {
        return list_of_fragments.size();
    }

    public void addFragment(Fragment fragment, String title) {
        list_of_fragments.add(fragment);
        list_of_fragment_titles.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return list_of_fragment_titles.get(position);
    }
}
