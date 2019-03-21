package com.example.applico;

import org.json.JSONObject;

public class Food {
    String name;
    Double cost;
    int quantity=0;
    public Food(String name,Double cost)
    {
        this.name=name;
        this.cost=cost;

    }
    public String getname()
    {
        return this.name;
    }
    public String getJsonObject()
    {
        JSONObject cartitems=new JSONObject();
        try{
            cartitems.put("foodname",name);
            cartitems.put("foodcost",cost);
            cartitems.put("foodquantity",quantity);
        }
        catch (Exception e)
        {}
        return cartitems.toString();
    }


}
