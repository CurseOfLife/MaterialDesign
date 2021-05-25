package com.example.materialdesign.adapter;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.materialdesign.fragment.BookStoreFragment;
import com.example.materialdesign.fragment.BooksFragment;
import com.example.materialdesign.fragment.FavouritesFragment;

import java.util.ArrayList;
import java.util.List;

/*
    FragmentStateAdapter vs FragmentPagerAdapter vs PagerAdapter
    https://stackoverflow.com/questions/18747975/what-is-the-difference-between-fragmentpageradapter-and-fragmentstatepageradapte/18748107
    https://stackoverflow.com/questions/8425961/difference-fragmentpageradapter-and-pageradapter
*/
// BookFragment, BookStoreFragment, FvouritesFragment
public class BooksPagerAdapter extends FragmentStateAdapter {

    public BooksPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position) {
            case 0:
                return new BooksFragment();
            case 1:
                return new FavouritesFragment();
            default:
                return new BookStoreFragment();

        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
