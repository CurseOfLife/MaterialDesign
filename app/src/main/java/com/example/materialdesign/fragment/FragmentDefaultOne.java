package com.example.materialdesign.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.materialdesign.R;
import com.example.materialdesign.utility.VariousTools;


public class FragmentDefaultOne extends Fragment {

    public FragmentDefaultOne() {
        // Required empty public constructor
    }

    public static FragmentDefaultOne newInstance() {
        FragmentDefaultOne fragment = new FragmentDefaultOne();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_fragment_default_one, container, false);

        VariousTools.displayImage(getActivity(), (ImageView) view.findViewById(R.id.image_1), R.drawable.image_1);
        VariousTools.displayImage(getActivity(), (ImageView) view.findViewById(R.id.image_2), R.drawable.image_2);
        VariousTools.displayImage(getActivity(), (ImageView) view.findViewById(R.id.image_3), R.drawable.image_3);
        VariousTools.displayImage(getActivity(), (ImageView) view.findViewById(R.id.image_4), R.drawable.image_4);
        VariousTools.displayImage(getActivity(), (ImageView) view.findViewById(R.id.image_5), R.drawable.image_5);
        VariousTools.displayImage(getActivity(), (ImageView) view.findViewById(R.id.image_6), R.drawable.image_6);
        VariousTools.displayImage(getActivity(), (ImageView) view.findViewById(R.id.image_7), R.drawable.image_7);
        VariousTools.displayImage(getActivity(), (ImageView) view.findViewById(R.id.image_8), R.drawable.image_8);
        VariousTools.displayImage(getActivity(), (ImageView) view.findViewById(R.id.image_9), R.drawable.image_9);
        VariousTools.displayImage(getActivity(), (ImageView) view.findViewById(R.id.image_10), R.drawable.image_10);

        return view;
    }
}
