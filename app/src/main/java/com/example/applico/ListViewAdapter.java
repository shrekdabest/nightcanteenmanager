package com.example.applico;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

public class ListViewAdapter extends BaseAdapter {

    ArrayList<Food> listfood;
    ArrayList<Food> searchfood;
    Context context;
    ListViewAdapter(Context c,ArrayList<Food> food)
    {
        this.context=c;
        this.listfood=new ArrayList<>();
        listfood.addAll(food);
        this.searchfood=new ArrayList<>();
        searchfood.addAll(food);

    }

    @Override
    public int getCount() {
        return listfood.size();
    }

    @Override
    public Object getItem(int position) {
        return listfood.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {View row;
        final ViewClassHolder viewClassHolder;
        if(convertView==null)
        {
            LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row= inflater.inflate(R.layout.nc_row,parent,false);
            viewClassHolder= new ViewClassHolder();
            viewClassHolder.name=row.findViewById(R.id.name);
            viewClassHolder.cost=row.findViewById(R.id.cost);
            viewClassHolder.plus=row.findViewById(R.id.plus);
            viewClassHolder.minus=row.findViewById(R.id.minus);
            viewClassHolder.quantity=row.findViewById(R.id.quantity);
            viewClassHolder.yourcost=row.findViewById(R.id.yourcost);
            row.setTag(viewClassHolder);
        }
        else
        {
            row=convertView;
            viewClassHolder=(ViewClassHolder)row.getTag();
        }
        final  Food food= (Food) getItem(position);
        viewClassHolder.name.setText(food.name);
        viewClassHolder.cost.setText(food.cost+" RS");
        viewClassHolder.quantity.setText(food.quantity+"");
        viewClassHolder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatequantity(position,viewClassHolder.quantity,viewClassHolder.yourcost,viewClassHolder.cost,1);
            }
        });
        viewClassHolder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatequantity(position,viewClassHolder.quantity,viewClassHolder.yourcost,viewClassHolder.cost,-1);
            }
        });
        return row;


    }

    public void updatequantity(int position, TextView q,TextView yc,TextView c,int value)
    {
        Food food= (Food) getItem(position);
        if(value>0)
        {
            food.quantity=food.quantity+1;
        }
        else{
            if(food.quantity>0)
            {
                food.quantity=food.quantity-1;
            }
        }
        q.setText(food.quantity+"");

        yc.setText(String.valueOf(food.quantity*food.cost+" RS"));

    }


    public void filter(String text)
    {
        text=text.toLowerCase(Locale.getDefault());
        listfood.clear();
        if(text.length()==0)
        {
            listfood.addAll(searchfood);
        }
        else
        {
            for(Food wp:searchfood)
            {
                if(wp.getname().toLowerCase(Locale.getDefault()).contains(text))
                {
                    listfood.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}
