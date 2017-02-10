package com.guanzhuli.dayday.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.guanzhuli.dayday.NewDayActivity;
import com.guanzhuli.dayday.R;
import com.guanzhuli.dayday.model.DaysList;
import com.guanzhuli.dayday.model.Item;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {
    private BottomSheetBehavior mBottomSheetBehavior;
    private View rootView;
    private ImageView mImageEdit, mImageShare, mImageIcon, mImageBefore, mImageBackground;
    private ImageView mImageFBShare, mImageGGShare, mImageTwitterShare, mImageEmailShare, mImageMSGShare;
    private TextView mTextDetails, mTextDays;
    private int mPosition;
    private Item mItem;

    public DetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_detail, container, false);
        mPosition = Integer.parseInt(getArguments().getString("list_position"));
        mItem = DaysList.getInstance().get(mPosition);
        initialView();
        setListener();
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("detail", "Start");
        setContent();
    }

    private void initialView() {
        View view = rootView.findViewById(R.id.details_bottom_sheet);
        mBottomSheetBehavior = BottomSheetBehavior.from(view);
        mBottomSheetBehavior.setPeekHeight(0);
        mImageIcon = (ImageView) rootView.findViewById(R.id.details_icon);
        mImageBefore = (ImageView) rootView.findViewById(R.id.details_before);
        mImageBackground = (ImageView) rootView.findViewById(R.id.details_background);
        mTextDays = (TextView) rootView.findViewById(R.id.details_days);
        mTextDetails = (TextView) rootView.findViewById(R.id.details_title);
        mImageEdit = (ImageView) rootView.findViewById(R.id.details_edit);
        mImageShare = (ImageView) rootView.findViewById(R.id.details_share);
        mImageFBShare = (ImageView) rootView.findViewById(R.id.details_share_fb);
        mImageGGShare = (ImageView) rootView.findViewById(R.id.details_share_google);
        mImageTwitterShare = (ImageView) rootView.findViewById(R.id.details_share_twitter);
        mImageEmailShare = (ImageView) rootView.findViewById(R.id.details_share_email);
        mImageMSGShare = (ImageView) rootView.findViewById(R.id.details_share_msg);
    }

    private void setContent() {
        mImageIcon.setImageResource(mItem.getTheme().IconResources());
        mImageBackground.setImageResource(mItem.getTheme().BackgroundResources());
        mImageBefore.setImageResource(
                mItem.isBefore()? R.drawable.ic_arrow_upward : R.drawable.ic_arrow_downward);
        mTextDetails.setText(mItem.getTitle());
        mTextDays.setText(String.valueOf(mItem.getInterval()));
    }

    private void setListener() {
        mImageEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent edit = new Intent(getContext(), NewDayActivity.class);
                edit.putExtra("add", false);
                edit.putExtra("position", mPosition);
                startActivity(edit);
            }
        });
        mImageShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });
        mImageFBShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"FB share", Toast.LENGTH_LONG).show();
            }
        });
        mImageGGShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"Google share", Toast.LENGTH_LONG).show();
            }
        });
        mImageTwitterShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"Twitter share", Toast.LENGTH_LONG).show();
            }
        });
        mImageEmailShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"Email share", Toast.LENGTH_LONG).show();
            }
        });
        mImageMSGShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"Message share", Toast.LENGTH_LONG).show();
            }
        });
        mBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    mBottomSheetBehavior.setPeekHeight(0);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
    }

}
