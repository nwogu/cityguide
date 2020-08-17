package com.gabrielnwogu.citihelp;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    // shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "city-guide";

    private static final String CITY_SELECTED = "city-selected";

    private static final String CATEGORY_SELECTED = "category-selected";

    private  static final String KEYWORD_INPUT = "keyword-input";

    private static final String MAPS_RESPONSE = "maps-response";

    public PreferenceManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setCitySelected(String citySelected) {
        editor.putString(CITY_SELECTED, citySelected);
        editor.commit();
    }

    public void setCategorySelected(String categorySelected) {
        editor.putString(CATEGORY_SELECTED, categorySelected);
        editor.commit();
    }

    public void setKeywordInput(String keywordInput) {
        editor.putString(KEYWORD_INPUT, keywordInput);
        editor.commit();
    }

    public void setMapsResponse(String response) {
        editor.putString(MAPS_RESPONSE, response);
        editor.commit();
    }

    public String getCategorySelected() {
        return pref.getString(CATEGORY_SELECTED, "");
    }

    public String getCitySelected() {
        return pref.getString(CITY_SELECTED, "");
    }

    public String getKeywordInput() {
        return pref.getString(KEYWORD_INPUT, "");
    }

    public String getMapsResponse() {
        return pref.getString(MAPS_RESPONSE, "");
    }

}
