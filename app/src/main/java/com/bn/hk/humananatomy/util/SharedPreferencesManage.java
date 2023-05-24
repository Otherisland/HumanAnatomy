package com.bn.hk.humananatomy.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPreferencesManage {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    public SharedPreferencesManage(Context context)
    {
        pref= PreferenceManager.getDefaultSharedPreferences(context);
    }
    public SharedPreferences.Editor edit()
    {
        return pref.edit();
    }
    public  void setEditor(SharedPreferences.Editor editor)
    {
        this.editor=editor;
    }
    public void clear()
    {
        if(editor==null)
        {
            System.out.println("hhhhhhhhhhhhhhhhhhhh");
        }
        editor.clear();
    }
    public void commit()
    {
        editor.commit();
    }
    public void setBoolean(String str,boolean bool)
    {
        editor.putBoolean(str,bool);
    }
    public void setString(String strK,String strV)
    {
        editor.putString(strK,strV);
    }
    public boolean getBoolean(String str,boolean bool)
    {
       return pref.getBoolean(str,bool);
    }
    public String getString(String strK,String strV)
    {
        return pref.getString(strK,strV);
    }

}
