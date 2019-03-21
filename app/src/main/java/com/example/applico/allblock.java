package com.example.applico;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import org.json.JSONArray;

import java.util.ArrayList;

public class allblock extends AppCompatActivity implements SearchView.OnQueryTextListener  {
    SearchView search;
    ListView listview;
    ListViewAdapter adapter;
    ArrayList<Food> food=new ArrayList<Food>();
    ArrayList<Food> foodorder=new ArrayList<Food>();
    ArrayList<String> revieworder=new ArrayList<>();

Button order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.allblock);
        if(savedInstanceState!=null)
        {
            String x=savedInstanceState.getString("APPLE");
        }


Bundle bundle=getIntent().getExtras();
int x=bundle.getInt("id");
if(x==R.id.imageButton1){
    getProduct();
}

else if(x==R.id.imagebutton2){
    getproduct2();
    }
     else if(x==R.id.imageButton3)
    getproduct3();

        search=findViewById(R.id.search);
        search.setOnQueryTextListener(this);
        listview=(ListView)findViewById(R.id.listview);
        adapter=new com.example.applico.ListViewAdapter(this,food);
        listview.setAdapter(adapter);


        order=(Button)findViewById(R.id.order);
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlphaAnimation animation=new AlphaAnimation(1.0f,0.7f);
                v.startAnimation(animation);
               placeorder();

            }
        });




    }



    public void placeorder() {
        foodorder.clear();
        revieworder.clear();
        for (int i = 0; i < adapter.listfood.size(); i++) {
            if (adapter.listfood.get(i).quantity > 0) {
                Food food = new Food(adapter.listfood.get(i).name, adapter.listfood.get(i).cost);
                food.quantity = adapter.listfood.get(i).quantity;
                foodorder.add(food);//we will convert into json
                revieworder.add(food.getJsonObject());
            }
        }
        if(foodorder.size()>0)
        { showmessage("Number Of Items Ordered :"+foodorder.size());

        }
        JSONArray jsonArray=new JSONArray(revieworder);
        callintent(jsonArray.toString());

    }
    public void callintent(String revieworder)
    {
        Intent intent=new Intent(this,ReviewOrder.class);
        intent.putExtra("revieworder",revieworder);
        startActivity(intent);
    }
    public void showmessage(String s )
    {
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
    }





    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String text=newText;
        adapter.filter(text);
        return false;
    }






    private void getproduct2() {
        food.add(new Food("Dosa Plain",20.0d));
        food.add(new Food("Set Dosa",20.0d));
        food.add(new Food("Onion/Tomato Uthappa",30.0d));
        food.add(new Food("Tomato Uthappa",25.0d));
        food.add(new Food("Masala Dosa",25.0d));
        food.add(new Food("Bread Butter",15.0d));
        food.add(new Food("Veg Sandwich",20.0d));
        food.add(new Food("Alu/Tomato Sandwich",160.0d));
        food.add(new Food("Mix Veg Korma",35.0d));
        food.add(new Food("Alu Gobi Masala",35.0d));


    }

    private void getProduct() {
        food.add(new Food("Paneer Manchurian",40.0d));
        food.add(new Food("Paneer Tikka",50.0d));
        food.add(new Food("Parota",15.0d));
        food.add(new Food("Chapati",10.0d));
        food.add(new Food("Paneer Fried Rice",50.0d));
        food.add(new Food("Chicken Kabab(1pc)",20.0d));
        food.add(new Food("Grilled Chicken",270.0d));
        food.add(new Food("Half Chicken",160.0d));
    }

    private void getproduct3() {
        food.add(new Food("Paneer Manchurian",40.0d));
        food.add(new Food("Paneer Tikka",50.0d));
        food.add(new Food("Parota",15.0d));
        food.add(new Food("Chapati",10.0d));
        food.add(new Food("Paneer Fried Rice",50.0d));
        food.add(new Food("Chicken Kabab(1pc)",20.0d));
        food.add(new Food("Grilled Chicken",270.0d));
        food.add(new Food("Half Chicken",160.0d));
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString("APPLE","apple");

        super.onSaveInstanceState(savedInstanceState);
    }
}













