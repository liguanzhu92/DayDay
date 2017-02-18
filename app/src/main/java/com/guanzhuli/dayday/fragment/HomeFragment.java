package com.guanzhuli.dayday.fragment;



import android.content.Intent;
import android.graphics.*;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import android.widget.ImageView;
import android.widget.TextView;
import com.guanzhuli.dayday.NewDayActivity;
import com.guanzhuli.dayday.R;
import com.guanzhuli.dayday.SettingsActivity;
import com.guanzhuli.dayday.controller.ORMHelper;
import com.guanzhuli.dayday.customized.MyAdapter;
import com.guanzhuli.dayday.model.DaysList;
import com.guanzhuli.dayday.model.Item;
import com.guanzhuli.dayday.utils.CheckCover;
import com.guanzhuli.dayday.utils.Reminder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
    private MyAdapter mAdapter;


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
            if (temp.size() == 0) {
                Item item = new Item();
                item.setCover(true);
                item.setTitle("Big Day");
                item.setDate("2016/01/12");
                item.setThemeName("anniversary");
                mDaysList.add(item);
                mHelper.getUserDao().create(item);
            } else {
                mDaysList.addAll(temp);
            }

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
        mBottomSheetBehavior.setPeekHeight(175);
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        mImageSetting = (ImageView) rootView.findViewById(R.id.home_setting);
        mImageAdd = (ImageView) rootView.findViewById(R.id.home_add);
        mImageBackground = (ImageView) rootView.findViewById(R.id.home_background);
        mImageBackground.setScaleType(ImageView.ScaleType.CENTER_CROP);
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
                    mBottomSheetBehavior.setPeekHeight(175);
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
        mAdapter = new MyAdapter(getContext(), DaysList.getInstance());
        mAdapter.setOnItemClickListener(new MyAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, String data) {
                Reminder reminder = new Reminder(getContext());
                reminder.scheduleReminder(15000, data);
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
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                LinearLayoutManager.VERTICAL);
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        mRecyclerView.setLayoutManager(layoutManager);
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition();
                if (direction == ItemTouchHelper.LEFT) {
                    try {
                        mHelper.getUserDao().delete(mDaysList.get(position));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    boolean cover = mDaysList.get(position).isCover();
                    mDaysList.remove(position);
                    if (cover) {
                        setContent();
                    }
/*                    mAdapter.notifyItemRemoved(position);
                    mAdapter.notifyItemChanged(position, mDaysList);*/
                    mAdapter.notifyDataSetChanged();
                }
            }

/*            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);
                viewHolder.itemView.setAlpha(1);
            }*/

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                Bitmap icon;
                Paint p = new Paint();
                if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE){

                    View itemView = viewHolder.itemView;
                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
                    float width = height / 3;

                    if(dX < 0){
                        p.setColor(Color.parseColor("#D32F2F"));
                        RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(),(float) itemView.getRight(), (float) itemView.getBottom());
                        c.drawRect(background,p);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_delete);
                        RectF icon_dest = new RectF((float) itemView.getRight() - 2*width ,(float) itemView.getTop() + width,(float) itemView.getRight() - width,(float)itemView.getBottom() - width);
                        c.drawBitmap(icon,null,icon_dest,p);
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
/*                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    float width = (float) viewHolder.itemView.getWidth();
                    float alpha = 1.0f - Math.abs(dX) / width;
                    viewHolder.itemView.setAlpha(alpha);
                    viewHolder.itemView.setTranslationX(dX);
                } else {
                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY,
                            actionState, isCurrentlyActive);
                    return;
                }*/
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

}
