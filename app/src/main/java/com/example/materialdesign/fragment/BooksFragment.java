package com.example.materialdesign.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.materialdesign.R;
import com.example.materialdesign.interfaces.IBadge;

import static android.os.SystemClock.sleep;


public class BooksFragment extends Fragment {

    public BooksFragment() {
        // Required empty public constructor
    }

    public static BooksFragment newInstance() {
        BooksFragment fragment = new BooksFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_books, container, false);

        view.findViewById(R.id.increment1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try
                {
                    ((IBadge) getActivity()).setBadgeNumber(1,1);
                }catch (ClassCastException e){
                    e.printStackTrace();
                }
            }
        });

        view.findViewById(R.id.increment10).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try
                {
                    ((IBadge) getActivity()).setBadgeNumber(1,10);
                }catch (ClassCastException e){
                    e.printStackTrace();
                }
            }
        });
        return view;
    }
}
