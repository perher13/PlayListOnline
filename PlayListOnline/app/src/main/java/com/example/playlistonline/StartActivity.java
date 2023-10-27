package com.example.playlistonline;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class StartActivity extends AppCompatActivity {

    protected ListView list1;
    protected DataBaseSQL audiodb;
    protected ArrayList<String> songList = new ArrayList<String>();
    private ArrayAdapter<String> adaptador;

    private String contenidoItem = "";
    private String songContenido = "";

    private Integer positionInt;

    private String urlPasar ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        audiodb = new DataBaseSQL(this);

        list1 = (ListView) findViewById(R.id.list1_start);

        songList = audiodb.getAllSongs();
        adaptador = new ArrayAdapter<>(StartActivity.this, android.R.layout.simple_list_item_1, songList);
        list1.setAdapter(adaptador);

        adaptador = new ArrayAdapter<String>(StartActivity.this, android.R.layout.simple_list_item_1, songList);
        list1.setAdapter(adaptador);


        list1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                Toast.makeText(StartActivity.this, getString(R.string.label1_start), Toast.LENGTH_SHORT).show();
                contenidoItem= parent.getItemAtPosition(position).toString();

                System.out.println();

                adaptador.notifyDataSetChanged();
                String[] parts = contenidoItem.split(".- ");
                songContenido = parts[1];
                positionInt = Integer.parseInt(parts[0]);
                urlPasar = audiodb.selectURL(positionInt);

                Intent pasarPantalla= new Intent(StartActivity.this, ReproductorActivity.class);    //Poner la pantalla a la que lleva
                pasarPantalla.putExtra("NOMBRE", songContenido);
                pasarPantalla.putExtra("URL", urlPasar);
                finish();
                startActivity(pasarPantalla);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_lista_canciones, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.mas_button_menu:

                Intent pasarPantalla = new Intent(StartActivity.this, CrearActivity.class);
                finish();
                startActivity(pasarPantalla);

                return true;
            case R.id.boton1_menu:

                System.exit(0);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}