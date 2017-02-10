package com.guanzhuli.dayday.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.guanzhuli.dayday.NewDayActivity;
import com.guanzhuli.dayday.R;
import com.guanzhuli.dayday.SettingsActivity;
import com.guanzhuli.dayday.controller.ORMHelper;
import com.guanzhuli.dayday.customized.MyAdapter;
import com.guanzhuli.dayday.model.DaysList;
import com.guanzhuli.dayday.model.Item;
import com.guanzhuli.dayday.utils.CheckCover;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private BottomSheetBehavior mBottomSheetBehavior;
    private View rootView;
    private TextView mTextDays, mTextTitle;
    private ImageView mImageSetting, mImageAdd, mImageBackground, mImageBefore, mImageIcon;
    private RecyclerView mRecyclerView;
    private ORMHelper mHelper;
    private int mCoverPosition;
    private DaysList mDaysList = DaysList.getInstance();


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_home, container, false);
        mHelper = ORMHelper.getInstance(getContext());
        initialView();
        setListener();
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("home", "Start");
        try {
            DaysList.getInstance().clear();
            ArrayList<Item> temp = (ArrayList<Item>) mHelper.getUserDao().queryForAll();
            mDaysList.addAll(temp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        setContent();
        setRecyclerView();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("home", "Resume");
    }

    private void initialView() {
        View bottomSheet = rootView.findViewById( R.id.bottom_sheet );
        mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        mBottomSheetBehavior.setPeekHeight(154);
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        mImageSetting = (ImageView) rootView.findViewById(R.id.home_setting);
        mImageAdd = (ImageView) rootView.findViewById(R.id.home_add);
        mImageBackground = (ImageView) rootView.findViewById(R.id.home_background);
        mImageIcon = (ImageView)rootView.findViewById(R.id.home_icon);
        mImageBefore = (ImageView) rootView.findViewById(R.id.home_before);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.home_recyclerview);
        mTextDays = (TextView) rootView.findViewById(R.id.home_days);
        mTextTitle = (TextView) rootView.findViewById(R.id.home_title);
    }

    private void setContent() {
        mCoverPosition = new CheckCover(getContext()).getCoverPosition();
        Item item = DaysList.getInstance().get(mCoverPosition);
        item.setInterval();
        item.setTheme();
        mImageBackground.setImageResource(item.getTheme().BackgroundResources());
        mImageIcon.setImageResource(item.getTheme().IconResources());
        mImageBefore.setImageResource(
                item.isBefore()? R.drawable.ic_arrow_upward : R.drawable.ic_arrow_downward);
        mTextTitle.setText(item.getTitle());
        mTextDays.setText(String.valueOf(item.getInterval()));
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
        mImageBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetailFragment detailFragment = new DetailFragment();
                Bundle bundle = new Bundle();
                bundle.putString("list_position", String.valueOf(mCoverPosition));
                detailFragment.setArguments(bundle);
                getFragmentManager().beginTransaction()
                        .addToBackStack(getTag())
                        .replace(R.id.container_main, detailFragment)
                        .commit();
            }
        });
    }

    private void setRecyclerView() {
        MyAdapter adapter = new MyAdapter(getContext());
        adapter.setOnItemClickListener(new MyAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, String data) {
                DetailFragment detailFragment = new DetailFragment();
                Bundle bundle = new Bundle();
                bundle.putString("list_position", data);
                detailFragment.setArguments(bundle);
                getFragmentManager().beginTransaction()
                        .addToBackStack(getTag())
                        .replace(R.id.container_main, detailFragment)
                        .commit();
            }
        });
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                LinearLayoutManager.VERTICAL);
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        mRecyclerView.setLayoutManager(layoutManager);
    }

}
