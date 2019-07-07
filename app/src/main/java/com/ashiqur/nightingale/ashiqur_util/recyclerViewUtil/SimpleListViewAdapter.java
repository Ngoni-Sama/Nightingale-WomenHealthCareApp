package com.ashiqur.nightingale.ashiqur_util.recyclerViewUtil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ashiqur.nightingale.R;

import java.util.ArrayList;


public class SimpleListViewAdapter extends ArrayAdapter<String> {

    private ArrayList<String> dataSet;
    Context mContext;


    // View lookup cache
    private class ViewHolder {
        TextView txtName;

    }

    public SimpleListViewAdapter(Context context, ArrayList<String> data) {
        super(context, R.layout.simple_listview_item, data);
        this.dataSet = data;
        this.mContext=context;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        String string = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.simple_listview_item, parent, false);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.tv_simple_listview);


            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }


        int lastPosition = position;

        viewHolder.txtName.setText(string);

        // Return the completed view to render on screen
        return convertView;
    }
}