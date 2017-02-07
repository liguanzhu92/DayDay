package com.guanzhuli.dayday.customized;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.guanzhuli.dayday.R;

/**
 * Created by Guanzhu Li on 2/6/2017.
 */
public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;

    public static class ExampleViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout foreground;
        public LinearLayout background;

        public ExampleViewHolder(View v) {
            super(v);
            foreground = (LinearLayout) v.findViewById(R.id.swipe_foreground);
            background = (LinearLayout) v.findViewById(R.id.swipe_background);
        }
    }

    public MyAdapter(Context context) {
        mContext = context;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ExampleViewHolder) {
            // ((ExampleViewHolder) holder).background.setBackgroundColor(); // do your manipulation of background and foreground here.
            Toast.makeText(mContext, "do your manipulation of background and foreground here.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.swipe_item, parent, false);
        return new ExampleViewHolder(v);
    }


}