package com.example.applico;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth mauth;
    AlphaAnimation alpha;
float dist;
    double latti,longi;
    private static final int REQUEST_LOCATION = 1;
    Location location,location1,location2;
    LocationManager locationManager;
    String lattitude,longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        alpha=new AlphaAnimation(1.0f,0.5f);
        mauth=FirebaseAuth.getInstance();
Toolbar toolbar=findViewById(R.id.toolbar);
setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
getSupportActionBar().setTitle("  Nocturnal Eats");
getSupportActionBar().setLogo(R.mipmap.logos_round);
   }


    public void imagebutton(View view) {
        SharedPreferences todecide=getSharedPreferences("orderzse",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=todecide.edit();
        editor.putInt("chooser",view.getId());
        editor.apply();
      /*  int x=view.getId();
      Intent intent=new Intent(this, allblock.class);
      intent.putExtra("id",x);
      startActivity(intent);*/
      view.startAnimation(alpha);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            getLocation();
        }
        if(view.getId()==R.id.imagebutton2)
        {
        if(latti!=0&&longi!=0)
        {
        Location loc1=new Location("");
        loc1.setLatitude(latti);
        loc1.setLongitude(longi);
        Location loc2=new Location("");
        loc2.setLatitude(13.006459);
        loc2.setLongitude(74.796531);
        dist=loc1.distanceTo(loc2);
            String distancebetween=String.format("%.2f",dist);
        if(dist<=3000){
for(int z=0;z<3;z++)
        Toast.makeText(MainActivity.this,"You are currently "+distancebetween+" meters(approximately) away from 3rd block which is less than the 3km range for delivery ,so you can place an order",Toast.LENGTH_LONG).show();
            int x=view.getId();
            Intent intent=new Intent(this, allblock.class);
            intent.putExtra("id",x);
            startActivity(intent);


        }
        else
        {float distancekm=dist/1000;
            for(int z=0;z<3;z++)
            Toast.makeText(MainActivity.this,"Since you are "+distancekm +" Kilometers(approximately) away from 3rd block night canteen which is beyond the 3km range ,you cant place an order",Toast.LENGTH_LONG).show();

        }
        }
        else
            takemetomaps();}
            if(view.getId()==R.id.imageButton1)
            {
                if(latti!=0&&longi!=0)
                {
                    Location loc1=new Location("");
                    loc1.setLatitude(latti);
                    loc1.setLongitude(longi);
                    Location loc2=new Location("");
                    loc2.setLatitude(13.007839);
                    loc2.setLongitude(74.796366);
                    dist=loc1.distanceTo(loc2);
                    String distancebetween=String.format("%.2f",dist);
                    if(dist<=3000){
                        for(int z=0;z<3;z++)
                            Toast.makeText(MainActivity.this,"You are currently "+distancebetween+" meters(approximately) away from 7th block which is less than the 3km range for delivery ,so you can place an order",Toast.LENGTH_LONG).show();
                        int x=view.getId();
                        Intent intent=new Intent(this, allblock.class);
                        intent.putExtra("id",x);
                        startActivity(intent);


                    }
                    else
                    {float distancekm=dist/1000;
                        for(int z=0;z<3;z++)
                            Toast.makeText(MainActivity.this,"Since you are "+distancekm +" Kilometers(approximately) away from 7th block night canteen which is beyond the 3km range ,you cant place an order",Toast.LENGTH_LONG).show();

                    }
                }
                else
                   takemetomaps();

            }
            if(view.getId()==R.id.imageButton3)
            {
                if(latti!=0.0&&longi!=0.0)
                {
                    Location loc1=new Location("");
                    loc1.setLatitude(latti);
                    loc1.setLongitude(longi);
                    Location loc2=new Location("");
                    loc2.setLatitude(13.013770);
                    loc2.setLongitude(74.795064);
                    dist=loc1.distanceTo(loc2);
                    String distancebetween=String.format("%.2f",dist);
                    if(dist<=3000){
                        for(int z=0;z<3;z++)
                            Toast.makeText(MainActivity.this,"You are currently "+distancebetween+" meters(approximately) away from girls block which is less than the 3km range for delivery ,so you can place an order",Toast.LENGTH_LONG).show();
                        int x=view.getId();
                        Intent intent=new Intent(this, allblock.class);
                        intent.putExtra("id",x);
                        startActivity(intent);


                    }
                    else
                    {float distancekm=dist/1000;
                        for(int z=0;z<3;z++)
                            Toast.makeText(MainActivity.this,"Since you are "+distancekm +" Kilometers(approximately) away from girls block night canteen which is beyond the 3km range ,you cant place an order",Toast.LENGTH_LONG).show();

                    }
                }
                else
                    takemetomaps();

            }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.logout:
                mauth.signOut();
                Intent intent=new Intent(MainActivity.this,Login.class);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
        return true;
    }
/* @Override
    protected void onStart() {
        FirebaseUser currentuser=mauth.getCurrentUser();

        if(currentuser==null)
        {
           Intent authintent=new Intent(MainActivity.this,Login.class);
           startActivity(authintent);

            finish();

        }


        super.onStart();
    }*/
    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        } else {
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            location1 = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            location2 = locationManager.getLastKnownLocation(LocationManager. PASSIVE_PROVIDER);

            if (location != null) {
                latti = location.getLatitude();
                longi = location.getLongitude();
                lattitude = String.valueOf(latti);
                longitude = String.valueOf(longi);



            } else  if (location1 != null) {
                latti = location1.getLatitude();
                longi = location1.getLongitude();
                lattitude = String.valueOf(latti);
                longitude = String.valueOf(longi);




            } else  if (location2 != null) {
                latti = location2.getLatitude();
                longi = location2.getLongitude();
                lattitude = String.valueOf(latti);
                longitude = String.valueOf(longi);



            }else{

                Toast.makeText(this,"Unable to Trace your location",Toast.LENGTH_SHORT).show();

            }
        }
    }

    protected void buildAlertMessageNoGps() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.MyDialogTheme);
        builder.setTitle("Please turn ON your GPS connection");
                builder.setMessage("We need to verify that you are in Nitk");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick( DialogInterface dialog,  int id) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick( DialogInterface dialog,  int id) {
                        dialog.cancel();
                    }
                });
         AlertDialog alert = builder.create();
        alert.show();
    }
    public void takemetomaps()
    {
       AlertDialog.Builder buil=new AlertDialog.Builder(this,R.style.MyDialogTheme);
        buil.setTitle("We can't access your location");
                buil.setMessage("It seems that we are unable to detect your location.we need you to press on the 'your location' button in google maps to effectively pinpoint your location");
                buil.setCancelable(true);
                buil.setPositiveButton("Take me there", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick( DialogInterface dialogInterface, int i) {
                      Intent intent=new Intent(Intent.ACTION_VIEW);
                      intent.setData(Uri.parse("geo:13.010642, 74.794243"));
                      startActivity(intent);
                    }
                });
    buil.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
         AlertDialog alert=buil.create();
        alert.show();
    }

}
