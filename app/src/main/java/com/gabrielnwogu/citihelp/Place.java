package com.gabrielnwogu.citihelp;

public class Place {

    protected String name;

    protected String formatted_address;

    protected String photo_reference;

    protected String base_api = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=";

    protected String api_key = "AIzaSyB8btDO1Temu--H1hkk2kWX8u2N-l-PBkc";

    public Place(String name, String formatted_address, String photo_reference)
    {
        this.name = name;
        this.formatted_address = formatted_address;
        this.photo_reference = photo_reference;
    }

    public String getName()
    {
        return this.name;
    }

    public String getAddress()
    {
        return this.formatted_address;
    }

    public String getPhoto()
    {
        return base_api + photo_reference + "&key=" + api_key;
    }
}
