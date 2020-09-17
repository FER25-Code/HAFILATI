package com.map.fer.t_bus.Station;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.map.fer.t_bus.Bus.MyBus;
import com.map.fer.t_bus.R;

import java.util.ArrayList;

public class BusByStationAdapter extends ArrayAdapter<MyBus> {

    private ArrayList<MyBus> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView bus, time;
    }

    public BusByStationAdapter(ArrayList<MyBus> data, Context context) {
        super(context, R.layout.bus_item, data);
        this.dataSet = data;
        this.mContext = context;
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        MyBus dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.bus_item, parent, false);
            viewHolder.bus = (TextView) convertView.findViewById(R.id.nbus);
            viewHolder.time = (TextView) convertView.findViewById(R.id.textView15);
            result = convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        lastPosition = position;
        viewHolder.bus.setText(dataModel.getBus());
        viewHolder.time.setText(dataModel.getArrivalTime());
        return convertView;
    }


}
