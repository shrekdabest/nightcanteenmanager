package com.example.applico;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ReviewOrder extends AppCompatActivity {
    ListView listreview;
    CountDownTimer ctimer;
    ArrayList<Food> foodrevieworder=new ArrayList<>();
    Button ordernow;
    TextView totally;
    ReviewOrderAdapter reviewadapter;
    TextView savename;
    TextView savemobile;
    TextView saveblock;
int j=0;
int k=0;
    EditText editname;
    TextView editmobile;
    EditText editblock;
    String mobilenumberr,namezz,blockzz;
    String realname,realblock;
    Button saveinfo,editinfo;
    AlphaAnimation animesss;
    DatabaseReference mref,mrefchildblock;
    DatabaseReference phonenumber,name,block,menu;
   int i=0;
   int id;
    String  currentDateTimeString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences todecide=getSharedPreferences("orderzse",Context.MODE_PRIVATE);
        id=todecide.getInt("chooser",0);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_order);
     //   currentDateTimeString = DateFormat.getDateTimeInstance()
            //    .format(new Date());
        animesss=new AlphaAnimation(1.0f,0.5f);
mref= FirebaseDatabase.getInstance().getReferenceFromUrl("https://applico-9faa6.firebaseio.com/");
        totally=findViewById(R.id.total);
        listreview=findViewById(R.id.listreview);
         savename=findViewById(R.id.savename);
        savemobile=findViewById(R.id.savemobile);
         saveblock=findViewById(R.id.saveblock);
         editname=findViewById(R.id.editname);
         editmobile=findViewById(R.id.editmobile);
         editblock=findViewById(R.id.editblock);
saveinfo=findViewById(R.id.saveinfo);
ordernow=findViewById(R.id.ordernow);
editinfo=findViewById(R.id.retrieve);
editinfo.setVisibility(View.GONE);

        getorderitemdata();
        total();
        SharedPreferences sp= getSharedPreferences("mobileno",Context.MODE_PRIVATE);
      //  editmobile.setText(sp.getString("phonenumber","lateraligator"));
        mobilenumberr=sp.getString("phonenumber","" +
                "");
        editmobile.setText(mobilenumberr);
        SharedPreferences nc=getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        editname.setText(nc.getString("username",""));

        editblock.setText(nc.getString("blockno",""));


        ordernow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentDateTimeString = DateFormat.getDateTimeInstance()
                        .format(new Date());
                i++;
                if(i % 2 == 0) {
                    ctimer.cancel();
                    ctimer=null;
                    Toast.makeText(getApplicationContext(),"Your order is cancelled ",Toast.LENGTH_SHORT).show();

                    ordernow.setBackgroundColor(Color.parseColor("#b6d161"));
                    ordernow.setTextColor(Color.parseColor("#000000"));
                    ordernow.setText("ORDER NOW!!");

                }
                view.setAnimation(animesss);
                if(editname.getText().toString().isEmpty())
                {
                    editname.setError("please enter your name");
                    editname.requestFocus();
                    return;
                }
                if(editblock.getText().toString().isEmpty())
                {
                    editblock.setError("please enter your block number");
                    editblock.requestFocus();
                    return;
                }
                realname=editname.getText().toString();
                realblock=editblock.getText().toString();
                boolean a =ordernow.getText().toString().toLowerCase().contains("now");
             //  Toast.makeText(getApplicationContext(),""+a,Toast.LENGTH_SHORT).show();
                if(a){
                    k=1;
                    }
if(i % 2 == 1){
  ctimer =  new CountDownTimer(30000, 1000) {

                    public void onTick(long millisUntilFinished) {
                       ordernow.setText("You can cancel order within "+Long.toString(millisUntilFinished/1000)+" seconds");

                       ordernow.setBackgroundColor(Color.parseColor("#FF0000"));
                       ordernow.setTextColor(Color.parseColor("#FFFFFF"));
                    }

                    public void onFinish() {
j=5;
Toast.makeText(getApplicationContext(),"Your order is successfully placed",Toast.LENGTH_SHORT).show();
                    }

                }.start();
}

                if(i % 2 == 1)
                {   Handler handler=new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        sendordertofirebase();
                        ordernow.setBackgroundColor(Color.parseColor("#b6d161"));
                        ordernow.setTextColor(Color.parseColor("#000000"));
                        ordernow.setText("ORDER NOW!!");  i = 0;

                    }
                },30000);


            }

           }

        });

    }
    public void saveinfo(View v)
    {
        SharedPreferences nc=getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=nc.edit();
        editor.putString("username",editname.getText().toString());

        editor.putString("blockno",editblock.getText().toString());
        editor.apply();
        saveinfo.setBackgroundColor(Color.parseColor("#87CEFA"));
        saveinfo.setText("Saved");
        Toast.makeText(this,"Your details are successfully stored",Toast.LENGTH_SHORT).show();
    }
    /*public void showinfo(View v)
    { SharedPreferences nc=getSharedPreferences("userinfo", Context.MODE_PRIVATE);
editname.setText(nc.getString("username",""));
editblock.setText(nc.getString("blockno",""));

    }*/


    public void getorderitemdata()
    {
        Bundle extra=getIntent().getExtras();
        if(extra!=null)
        {
            String review=extra.getString("revieworder",null);
            if (review!=null&& review.length()>0)
            {try {
                JSONArray jsonArray = new JSONArray(review);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = new JSONObject(jsonArray.getString(i));
                    Food food = new Food(jsonObject.getString("foodname"), jsonObject.getDouble("foodcost"));
                    food.quantity = jsonObject.getInt("foodquantity");
                    foodrevieworder.add(food);



                }
                if(foodrevieworder.size()>0)
                {
                    reviewadapter=new ReviewOrderAdapter(this,foodrevieworder);
                    listreview.setAdapter(reviewadapter);
                }
                else {
                    Toast.makeText(this, "Make sure to select items from the menu", Toast.LENGTH_LONG).show();

ordernow.setVisibility(View.INVISIBLE);
                }   }

            catch (Exception e){}

            }
        }

    }
    public void total()
    {
        Double total=0.0d;
        for (int i=0;i<foodrevieworder.size();i++)
        {
            total=total+foodrevieworder.get(i).quantity*foodrevieworder.get(i).cost;
        }
        totally.setText("You Pay : "+total+" RS");
    }
    public void sendordertofirebase()
    {
if(id==R.id.imageButton1)
    mrefchildblock=mref.child("7th block");
        if(id==R.id.imagebutton2)
        mrefchildblock=mref.child("3rd block");
        if(id==R.id.imageButton3)
        mrefchildblock=mref.child("Girls block");
phonenumber=mrefchildblock.child(mobilenumberr+"("+currentDateTimeString+")");
name=phonenumber.child("NAME");
name.setValue(realname);
block=phonenumber.child("BLOCK");
block.setValue(realblock);
menu=phonenumber.child("ORDER");
                for(int k=0;k<foodrevieworder.size();k++)
                {
                    DatabaseReference reffood=menu.child(foodrevieworder.get(k).name);
                    reffood.setValue(foodrevieworder.get(k).quantity);
                }


    }



}
