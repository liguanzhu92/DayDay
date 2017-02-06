package com.guanzhuli.dayday.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.Toast;
import com.guanzhuli.dayday.NewDayActivity;
import com.guanzhuli.dayday.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {
    private BottomSheetBehavior mBottomSheetBehavior;
    private View rootView;
    private ImageView mImageEdit, mImageShare;
    private ImageView mImageFBShare, mImageGGShare, mImageTwitterShare, mImageEmailShare, mImageMSGShare;

    public DetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_detail, container, false);
        initialView();
        setListener();
        return rootView;
    }

    private void initialView() {
        View view = rootView.findViewById(R.id.details_bottom_sheet);
        mBottomSheetBehavior = BottomSheetBehavior.from(view);
        mBottomSheetBehavior.setPeekHeight(0);
        mImageEdit = (ImageView) rootView.findViewById(R.id.details_edit);
        mImageShare = (ImageView) rootView.findViewById(R.id.details_share);
        mImageFBShare = (ImageView) rootView.findViewById(R.id.details_share_fb);
        mImageGGShare = (ImageView) rootView.findViewById(R.id.details_share_google);
        mImageTwitterShare = (ImageView) rootView.findViewById(R.id.details_share_twitter);
        mImageEmailShare = (ImageView) rootView.findViewById(R.id.details_share_email);
        mImageMSGShare = (ImageView) rootView.findViewById(R.id.details_share_msg);
    }

    private void setListener() {
        mImageEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent edit = new Intent(getContext(), NewDayActivity.class);
                edit.putExtra("add", false);
                edit.putExtra("position", 0);
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
