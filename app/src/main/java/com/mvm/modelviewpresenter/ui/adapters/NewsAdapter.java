package com.mvm.modelviewpresenter.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mvm.modelviewpresenter.R;
import com.mvm.modelviewpresenter.model.News;

import java.util.ArrayList;

public class NewsAdapter extends ArrayAdapter<News> {

    private int mLayoutId;

    public NewsAdapter(Context context, int resId) {
        super(context, resId, new ArrayList<>());
        this.mLayoutId = resId;
    }
    
    @Override
    public View getView(int position, View view,  ViewGroup parent) {
        Holder holder;
        if (view != null) {
            holder = (Holder) view.getTag();
        }else {
            view = LayoutInflater.from(getContext())
                    .inflate(mLayoutId, parent, false);
            holder = new Holder();
            holder.title = view.findViewById(R.id.title);
            holder.thumbnails = view.findViewById(R.id.thumbnails);
            view.setTag(holder);
        }

        News item = getItem(position);
        holder.title.setText(item.title);

        Glide.with(getContext())
                .load(item.link)
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.placeholder_image)
                .into(holder.thumbnails);

        return view;
    }

    public static class Holder {
        TextView title;
        ImageView thumbnails;
    }
}
