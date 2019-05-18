package com.example.travelingagent.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.travelingagent.R;
import com.example.travelingagent.entity.ModeEntity;

import java.util.List;

public class ModeAdapter extends ArrayAdapter<ModeEntity> {
    private int resourceID;

    public ModeAdapter(Context context, int textViewResourceID, List<ModeEntity> objects) {
        super(context, textViewResourceID, objects);
        resourceID = textViewResourceID;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ModeEntity modeEntity = getItem(position); // 获取当前项的Fruit实例
        View view = LayoutInflater.from(getContext()).inflate(resourceID, null);
        ImageView modeImage = (ImageView) view.findViewById(R.id.mode_image);
        TextView modeName = (TextView) view.findViewById(R.id.mode_name);
        modeImage.setImageResource(modeEntity.getImageID());
        modeName.setText(modeEntity.getName());
        return view;
    }
}
