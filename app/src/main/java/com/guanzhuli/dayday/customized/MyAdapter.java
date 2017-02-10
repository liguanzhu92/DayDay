package com.guanzhuli.dayday.customized;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.guanzhuli.dayday.R;
import com.guanzhuli.dayday.model.DaysList;
import com.guanzhuli.dayday.model.Item;

/**
 * Created by Guanzhu Li on 2/6/2017.
 */
public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    private Context mContext;



    public static class ExampleViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout foreground;
        private TextView mTextTitle, mTextDate, mTextInterval;
        private ImageView mImageIcon, mImageBefore;

        public ExampleViewHolder(View v) {
            super(v);
            foreground = (LinearLayout) v.findViewById(R.id.swipe_foreground);
            mTextTitle = (TextView) v.findViewById(R.id.swipe_title_fore);
            mTextDate = (TextView) v.findViewById(R.id.swipe_date_fore);
            mTextInterval = (TextView) v.findViewById(R.id.swipe_interval_fore);
            mImageIcon = (ImageView) v.findViewById(R.id.swipe_icon_fore);
            mImageBefore = (ImageView) v.findViewById(R.id.swipe_before_fore);
        }
    }

    public MyAdapter(Context context) {
        mContext = context;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ExampleViewHolder) {
            final Item item = DaysList.getInstance().get(position);
            item.setInterval();
            item.setTheme();
            ((ExampleViewHolder) holder).mTextTitle.setText(item.getTitle());
            ((ExampleViewHolder) holder).mTextDate.setText(item.getDate());
            ((ExampleViewHolder) holder).mTextInterval.setText(String.valueOf(item.getInterval()));
            ((ExampleViewHolder) holder).mImageIcon.setImageResource(
                    item.getTheme().IconResources());
            ((ExampleViewHolder) holder).mImageBefore.setImageResource(
                    item.isBefore()? R.drawable.ic_arrow_upward : R.drawable.ic_arrow_downward);
            ((ExampleViewHolder) holder).itemView.setTag(String.valueOf(position));
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
        v.setOnClickListener(this);
        return new ExampleViewHolder(v);
    }

    // set on click listener
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    @Override
    public void onClick(View view) {
        mOnItemClickListener.onItemClick(view,(String)view.getTag());
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view , String data);
    }
}