package com.example.materialdesign.fragment;

import android.os.Bundle;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.materialdesign.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.navigation.NavigationView;


public class BottomSheetFragment extends BottomSheetDialogFragment {

    private NavigationView navigationView;

    public BottomSheetFragment() {
        // Required empty public constructor
    }

    public static BottomSheetFragment newInstance() {
        return new BottomSheetFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bottom_sheet, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navigationView = view.findViewById(R.id.bottom_sheet);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.navigation_home:
                        //Toast.makeText(getActivity(), "Fingerprint menu item selected", Toast.LENGTH_SHORT).show();
                        showToast("Home");
                        break;

                    case R.id.navigation_books:
                       // Toast.makeText(getActivity(), "Books menu item selected", Toast.LENGTH_SHORT).show();
                        showToast("Books");
                        break;

                    case R.id.navigation_favorites:
                       // Toast.makeText(getActivity(), "Favorites menu item selected", Toast.LENGTH_SHORT).show();
                        showToast("Favorites");
                        break;
                }
                return true;
            }
        });
    }

    public void showToast(String string) {
        String s1 = String.format("%s menu item selected", string);
        Toast toast = Toast.makeText(getActivity(), s1, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.BOTTOM, 0, 450);
        toast.show();
    }
}