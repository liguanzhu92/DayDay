package com.guanzhuli.dayday.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import com.guanzhuli.dayday.NewDayActivity;
import com.guanzhuli.dayday.R;
import com.guanzhuli.dayday.SettingsActivity;
import com.guanzhuli.dayday.controller.ORMHelper;
import com.guanzhuli.dayday.customized.MyAdapter;
import com.guanzhuli.dayday.model.DaysList;
import com.guanzhuli.dayday.model.Item;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private BottomSheetBehavior mBottomSheetBehavior;
    private View rootView;
    private ImageView mImageSetting, mImageAdd, mImageBackgrount;
    private RecyclerView mRecyclerView;
    private ORMHelper mHelper;
    private DaysList mDaysList = DaysList.getInstance();


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_home, container, false);
        DaysList.getInstance().clear();
        mHelper = ORMHelper.getInstance(getContext());
        try {
            ArrayList<Item> temp = (ArrayList<Item>) mHelper.getUserDao().queryForAll();
            mDaysList.addAll(temp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        initialView();
        setListener();
        setRecyclerView();
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
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.home_recyclerview);
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
                Intent add = new Intent(getContext(), NewDayActivity.class);
                add.putExtra("add", true);
                startActivity(add);
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

    private void setRecyclerView() {
        MyAdapter adapter = new MyAdapter(getContext());
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                LinearLayoutManager.VERTICAL);
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        mRecyclerView.setLayoutManager(layoutManager);
    }

}
