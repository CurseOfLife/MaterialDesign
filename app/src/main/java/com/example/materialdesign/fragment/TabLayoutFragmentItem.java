package com.example.materialdesign.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.materialdesign.R;


public class TabLayoutFragmentItem extends Fragment {

    private static final String TAB_SECTION_ID = "tab_section_id";

    public TabLayoutFragmentItem() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_tab_layout_item, container, false);
        TextView textView = (TextView) view.findViewById(R.id.fragment_text);

        //https://stackoverflow.com/questions/35562503/how-do-i-place-text-in-tab-activity-in-java-in-android-studio
        //instead of making differnt layouts
        //in this example i will show how to change just the text of a resource on different pagers
        //might be useful if we are making a banner or a wizard
        textView.setText(getString(R.string.section_format, getArguments().getInt(TAB_SECTION_ID)));
        return view;
    }

    public static TabLayoutFragmentItem newInstance() {
        TabLayoutFragmentItem fragment = new TabLayoutFragmentItem();
        return fragment;
    }

    public static TabLayoutFragmentItem newInstance(int tab_section_id) {
        TabLayoutFragmentItem fragment = new TabLayoutFragmentItem();
        Bundle args = new Bundle();
        args.putInt(TAB_SECTION_ID, tab_section_id);
        fragment.setArguments(args);
        return fragment;
    }

}
