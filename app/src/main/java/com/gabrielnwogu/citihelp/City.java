package com.gabrielnwogu.citihelp;

public class City {


    public final String state;

    public City(String city, String state){

        this.state = state;
    }

    public String toString()
    {
        return this.state;
    }
}
