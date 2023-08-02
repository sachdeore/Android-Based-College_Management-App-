package com.rohya.collegemanagement;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefrenceManager
{
    private final SharedPreferences sharedPreferences;

    public PrefrenceManager(Context context)
    {
        sharedPreferences = context.getSharedPreferences("keyPreference", Context.MODE_PRIVATE);
    }

    public void putBooleans(String key, Boolean value)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public Boolean getBoolean(String key)
    {
        return sharedPreferences.getBoolean(key, false);
    }

    public void putString(String key, String value)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getString(String key)
    {
        return sharedPreferences.getString(key, null);
    }

    public void clear()
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
