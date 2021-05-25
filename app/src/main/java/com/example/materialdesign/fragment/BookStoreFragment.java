package com.example.materialdesign.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.materialdesign.R;


public class BookStoreFragment extends Fragment {

    public BookStoreFragment() {
        // Required empty public constructor
    }

    public static BookStoreFragment newInstance() {
        BookStoreFragment fragment = new BookStoreFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_book_store, container, false);
    }
}