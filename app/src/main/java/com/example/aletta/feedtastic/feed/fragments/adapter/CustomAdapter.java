package com.example.aletta.feedtastic.feed.fragments.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aletta.feedtastic.R;
import com.example.aletta.feedtastic.util.GlideApp;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    Context context;
    int flags[];
    ArrayList<String> countryNames;
    LayoutInflater inflter;

    public CustomAdapter(Context applicationContext, int[] flags, ArrayList<String> countryNames) {
        this.context = applicationContext;
        this.flags = flags;
        this.countryNames = countryNames;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return flags.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.custom_spinner_items, null);
        ImageView icon = view.findViewById(R.id.categoryBadge);
        TextView names = view.findViewById(R.id.categoryName);
        GlideApp.with(view.getContext())
                .load(flags[i])
                .into(icon);
        names.setText(countryNames.get(i));

        return view;
    }
}