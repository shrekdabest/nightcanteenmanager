package com.example.applico;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class LocationOfDriver extends FragmentActivity implements OnMapReadyCallback {
String x,y;
double lat,lng;
String latti,longi;
//Marker currentmarker=null;
    private GoogleMap mMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_of_driver);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        subscribetopewdiepie();
        mMap.setMaxZoomPreference(110);



        // Add a marker in Sydney and move the camera
        /*LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/
    }
    public void subscribetopewdiepie()
    {
        DatabaseReference mref=FirebaseDatabase.getInstance().getReferenceFromUrl("https://applico-9faa6.firebaseio.com/");
mref.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

mMap.clear();


    latti=String.valueOf(dataSnapshot.child("9601608920(Lattitude)").getValue());
        longi=String.valueOf(dataSnapshot.child("9601608920(Longitude)").getValue());
        //Toast.makeText(getApplicationContext(),latti+" "+longi,Toast.LENGTH_LONG).show();
        lat=Double.parseDouble(latti);
        lng=Double.parseDouble(longi);
        setmarker();


    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
});

    }
    public void setmarker()
    {/*Marker currentMarker = null;
        if (currentMarker!=null) {
            currentMarker.remove();
            currentMarker=null;
        }

        if (currentMarker==null) {
            currentMarker = mMap.addMarker(new MarkerOptions().position(arg0).
                    icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        }*/

       LatLng block=new LatLng(lat,lng);
        mMap.addMarker(new MarkerOptions().position(block).title("here i am"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(block));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(block, 21));



    }



}
