package com.guanzhuli.dayday.fragment;


import android.content.Intent;
import android.graphics.*;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import com.guanzhuli.dayday.NewDayActivity;
import com.guanzhuli.dayday.R;
import com.guanzhuli.dayday.SettingsActivity;
import com.guanzhuli.dayday.controller.DBManipulation;
import com.guanzhuli.dayday.customized.MyAdapter;
import com.guanzhuli.dayday.customized.RecycleViewDivider;
import com.guanzhuli.dayday.model.DaysList;
import com.guanzhuli.dayday.model.Item;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private BottomSheetBehavior mBottomSheetBehavior;
    private View rootView;
    private ImageView mImageSetting, mImageAdd, mImageBackgrount;
    private RecyclerView mRecyclerView;
    private Paint p = new Paint();
    private DBManipulation mDBManipulation;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_home, container, false);
        DaysList.getInstance().clear();
        mDBManipulation = DBManipulation.getInstance(getContext());
        List<Item> items = mDBManipulation.selectAll();
        DaysList.getInstance().addAll(items);
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
/*        mRecyclerView.addItemDecoration(new RecycleViewDivider(getContext(), LinearLayoutManager.VERTICAL));*/
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                LinearLayoutManager.VERTICAL);
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        mRecyclerView.setLayoutManager(layoutManager);
/*        mRecyclerView.addItemDecoration(new RecycleViewDivider(
                getContext(), LinearLayoutManager.VERTICAL, 1, ContextCompat.getColor(getContext(), R.color.divider)));*/




        final ItemTouchHelper.SimpleCallback swipeCallback = new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT){


            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition();
                if (direction == ItemTouchHelper.LEFT) {
                    ((MyAdapter.ExampleViewHolder) viewHolder).background.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);
                viewHolder.itemView.setAlpha(1.0f);
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE){
                    if(dX < 0){
                        View itemView = viewHolder.itemView;
                        final float alpha = 1.0f - Math.abs(dX/2) / (float) viewHolder.itemView.getWidth();
                        viewHolder.itemView.setAlpha(alpha);
                    }
                }
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

}
