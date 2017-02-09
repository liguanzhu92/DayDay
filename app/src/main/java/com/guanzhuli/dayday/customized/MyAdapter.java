package com.guanzhuli.dayday.customized;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.guanzhuli.dayday.R;
import com.guanzhuli.dayday.controller.DBManipulation;
import com.guanzhuli.dayday.model.DaysList;
import com.guanzhuli.dayday.model.Item;

/**
 * Created by Guanzhu Li on 2/6/2017.
 */
public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;

    public static class ExampleViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout foreground,background;
        private TextView mTextTitle, mTextDate, mTextInterval;
        private ImageView mImageIcon, mImageBefore;
        public ImageButton mImageButton;

        public ExampleViewHolder(View v) {
            super(v);
            foreground = (LinearLayout) v.findViewById(R.id.swipe_foreground);
            background = (LinearLayout) v.findViewById(R.id.swipe_background);
            mTextTitle = (TextView) v.findViewById(R.id.swipe_title_fore);
            mTextDate = (TextView) v.findViewById(R.id.swipe_date_fore);
            mTextInterval = (TextView) v.findViewById(R.id.swipe_interval_fore);
            mImageIcon = (ImageView) v.findViewById(R.id.swipe_icon_fore);
            mImageBefore = (ImageView) v.findViewById(R.id.swipe_before_fore);
            mImageButton = (ImageButton) v.findViewById(R.id.swipe_btn_delete);
        }
    }

    public MyAdapter(Context context) {
        mContext = context;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ExampleViewHolder) {
            // ((ExampleViewHolder) holder).background.setBackgroundColor(); // do your manipulation of background and foreground here.
            final Item item = DaysList.getInstance().get(position);
            ((ExampleViewHolder) holder).mTextTitle.setText(item.getTitle());
            ((ExampleViewHolder) holder).mTextDate.setText(item.getDate());
            ((ExampleViewHolder) holder).mTextInterval.setText(String.valueOf(item.getInterval()));
            ((ExampleViewHolder) holder).mImageIcon.setImageResource(
                    item.getTheme().IconResources());
            ((ExampleViewHolder) holder).mImageBefore.setImageResource(
                    item.isBefore()? R.drawable.ic_arrow_upward : R.drawable.ic_arrow_downward);
            ((ExampleViewHolder) holder).mImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // delete item
                    Log.d("sqlite","try delete");
                    DBManipulation.getInstance(mContext).delete(String.valueOf(item.getID()));
                    DaysList.getInstance().remove(item);
                    notifyItemRemoved(position);
                    notifyItemChanged(position, DaysList.getInstance());
                    notifyDataSetChanged();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return DaysList.getInstance().size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.swipe_item, parent, false);
        return new ExampleViewHolder(v);
    }


}