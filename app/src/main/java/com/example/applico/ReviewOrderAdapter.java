package com.example.applico;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public class ReviewOrderAdapter extends BaseAdapter {
    Context context;
    ArrayList<Food> whatever;
    ReviewOrderAdapter(Context c, ArrayList<Food> m)
    {
        this.whatever=new ArrayList<Food>();
        this.whatever=m;
        this.context=c;
    }
    @Override
    public int getCount() {
        return whatever.size();
    }

    @Override
    public Object getItem(int position) {
        return whatever.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row;
        final ReviewClassHolder reviewholder;
        if(convertView==null)
        {
            LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row= inflater.inflate(R.layout.nc_review_row,parent,false);
            reviewholder=new ReviewClassHolder();
            reviewholder.nam=row.findViewById(R.id.nam);
            reviewholder.qty=row.findViewById(R.id.qty);
            reviewholder.ycost=row.findViewById(R.id.ycost);
            row.setTag(reviewholder);
        }
        else
        {
            row=convertView;
            reviewholder= (ReviewClassHolder) row.getTag();
        }

        reviewholder.nam.setText(whatever.get(position).name);
        reviewholder.qty.setText(whatever.get(position).quantity+"");
        reviewholder.ycost.setText(String.valueOf(whatever.get(position).cost*whatever.get(position).quantity) + " RS");

        return row;
    }
}
