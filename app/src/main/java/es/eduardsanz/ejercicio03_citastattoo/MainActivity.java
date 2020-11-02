package es.eduardsanz.ejercicio03_citastattoo;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import es.eduardsanz.ejercicio03_citastattoo.adapters.CitasAdapter;
import es.eduardsanz.ejercicio03_citastattoo.modelos.CitaTattoo;

public class MainActivity extends AppCompatActivity {

    private final int EDIT_CITA = 99;
    private final int ADD_CITA = 77;

    // 1. Modelo de Datos
    private ArrayList<CitaTattoo> listadoCitas;

    // 2. Fila a mostrar
    private int resource;

    // 3. Contenedor (Contenedor + Adapter)
    private ListView contenedor;
    private CitasAdapter citasAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listadoCitas =  new ArrayList<>();
        resource = R.layout.fila_cita;
        contenedor = findViewById(R.id.contenedorCitas);
        citasAdapter = new CitasAdapter(this, resource, listadoCitas);
        contenedor.setAdapter(citasAdapter);

        contenedor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CitaTattoo citaTattoo = listadoCitas.get(position);
                Intent intent = new Intent(MainActivity.this, EditCitasActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("CITA", citaTattoo);
                bundle.putInt("POS", position);
                intent.putExtras(bundle);
                startActivityForResult(intent, EDIT_CITA);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(MainActivity.this, AddCitasActivity.class), ADD_CITA);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_CITA && resultCode == RESULT_OK){
            if (data != null && data.getExtras() != null){
                CitaTattoo citaTattoo = data.getExtras().getParcelable("CITA");
                if (citaTattoo != null){
                    listadoCitas.add(citaTattoo);
                    citasAdapter.notifyDataSetChanged();
                }
            }
        }
        if (requestCode == EDIT_CITA && resultCode == RESULT_OK){
            if (data != null && data.getExtras() != null){
                int posicion = data.getExtras().getInt("POS");
                CitaTattoo citaTattoo = data.getExtras().getParcelable("CITA");
                if (citaTattoo == null){ // Eliminar
                    listadoCitas.remove(posicion);
                }
                else {
                    listadoCitas.set(posicion, citaTattoo);
                }
                citasAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("CITAS",listadoCitas);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        ArrayList<CitaTattoo> temp= savedInstanceState.getParcelableArrayList("CITAS");
        listadoCitas.clear();
        listadoCitas.addAll(temp);
    }
}