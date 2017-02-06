package com.guanzhuli.dayday.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import com.guanzhuli.dayday.NewDayActivity;
import com.guanzhuli.dayday.R;
import com.guanzhuli.dayday.SettingsActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private BottomSheetBehavior mBottomSheetBehavior;
    private View rootView;
    private ImageView mImageSetting, mImageAdd, mImageBackgrount;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_home, container, false);
        initialView();
        setListener();
        return rootView;
    }

    private void initialView() {
        View bottomSheet = rootView.findViewById( R.id.bottom_sheet );
        mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        mBottomSheetBehavior.setPeekHeight(154);
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        mImageSetting = (ImageView) rootView.findViewById(R.id.home_setting);
        mImageAdd = (ImageView) rootView.findViewById(R.id.home_add);
        mImageBackgrount = (ImageView) rootView.findViewById(R.id.home_background);
    }

    private void setListener() {
        mBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    mBottomSheetBehavior.setPeekHeight(154);
                }
            }

            @Override
            public void onSlide(View bottomSheet, float slideOffset) {
            }
        });
        mImageAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), NewDayActivity.class));
            }
        });
        mImageSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent setting = new Intent(getContext(), SettingsActivity.class);
                startActivity(setting);
            }
        });
        mImageBackgrount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetailFragment detailFragment = new DetailFragment();
                getFragmentManager().beginTransaction()
                        .addToBackStack(getTag())
                        .replace(R.id.container_main, detailFragment)
                        .commit();
            }
        });
    }


}
