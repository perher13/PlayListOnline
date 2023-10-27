package com.example.playlistonline;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CrearActivity extends AppCompatActivity {

    protected EditText caja1;
    protected EditText caja2;
    protected Button boton1;
    protected Button boton2;

    private String contenidoCaja1;
    private String contenidoCaja2;

    protected DataBaseSQL db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear);

        caja1 = (EditText) findViewById(R.id.caja1_crear);
        caja2 = (EditText) findViewById(R.id.caja2_crear);
        boton1 = (Button) findViewById(R.id.boton1_crear);
        boton2 = (Button) findViewById(R.id.boton2_crear);

        db = new DataBaseSQL(this);

        boton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contenidoCaja1 = caja1.getText().toString();
                contenidoCaja2 = caja2.getText().toString();

                if(contenidoCaja1.equals("") || contenidoCaja2.equals("")){
                    Toast.makeText(CrearActivity.this, getString(R.string.label1_crear), Toast.LENGTH_SHORT).show();
                }else{
                    db.insertCanciones(caja1.getText().toString(), caja2.getText().toString(), 1);
                    db.getAllSongs();

                    Intent pasarPantalla = new Intent(CrearActivity.this, StartActivity.class);
                    finish();
                    startActivity(pasarPantalla);
                    Toast.makeText(CrearActivity.this, getString(R.string.label2_crear), Toast.LENGTH_SHORT).show();
                }

            }
        });

        boton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pasarPantalla = new Intent(CrearActivity.this, StartActivity.class);
                finish();
                startActivity(pasarPantalla);
            }
        });
    }
}