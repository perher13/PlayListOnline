package com.example.playlistonline;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DataBaseSQL extends SQLiteOpenHelper {

   protected SQLiteDatabase audio;

   public DataBaseSQL(Context context) {super(context, "media", null, 1);}

    @Override
    public void onCreate(SQLiteDatabase audio) {
        audio.execSQL("CREATE table media (id integer primary key autoincrement not null, title text, url text, priority int)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase audio, int i, int i1) {
        audio.execSQL("DROP TABLE IF EXISTS media");
    }

    public void insertCanciones(String title, String url, int priority ){
       audio =this.getReadableDatabase();
       audio.execSQL("INSERT INTO media(title, url, priority) VALUES ('" + title +"','"+ url +"','"+ priority +"')");
    }

    public void deleteAllSongs()
    {
        audio = this.getWritableDatabase();
        audio.execSQL("DELETE FROM media");
    }

    public void updateSongs(int id, String title, String url, int priority){
       audio = this.getWritableDatabase();
       audio.execSQL("UPDATE media SET title='"+title+"',url='"+url+"',priority='"+priority+"' media WHERE id = "+id);
    }

    public int numberOfSongs(){
       int num = 0;
       audio = this.getReadableDatabase();
       num = (int) DatabaseUtils.queryNumEntries(audio, "media");
       return num;
    }

    @SuppressLint("Range")
    public String selectURL(int id){
        Cursor res=null;
        String url = "";

        if(numberOfSongs() > 0){
            audio = this.getReadableDatabase();
            res = audio.rawQuery("SELECT * FROM media WHERE id=" + id, null);
            res.moveToFirst();

            while(res.isAfterLast() == false){
                url = res.getString(res.getColumnIndex("url"));
                res.moveToNext();
            }
        }

        return url;
    }

    @SuppressLint("Range")
    public ArrayList<String> getAllSongs(){
        ArrayList<String> filas = new ArrayList<>();

        Cursor res=null;
        String contenido="";
        String url = "";
        Integer id;
        if(numberOfSongs() > 0){
            audio = this.getReadableDatabase();
            res = audio.rawQuery("SELECT * FROM media ORDER BY priority ASC", null);
            res.moveToFirst();

            while(res.isAfterLast() == false){
                contenido = res.getInt(res.getColumnIndex("id")) + ".- " + res.getString(res.getColumnIndex("title"));
                url = res.getString(res.getColumnIndex("url"));
                id= res.getInt(res.getColumnIndex("id"));
                filas.add(contenido);
                res.moveToNext();
            }
        }

        return filas;
    }

    public void close(){
        audio.close();
   }

}
