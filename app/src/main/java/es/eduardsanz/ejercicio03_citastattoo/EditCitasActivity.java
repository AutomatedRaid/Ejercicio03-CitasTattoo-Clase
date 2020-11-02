package es.eduardsanz.ejercicio03_citastattoo;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import es.eduardsanz.ejercicio03_citastattoo.configuraciones.Configuracion;
import es.eduardsanz.ejercicio03_citastattoo.modelos.CitaTattoo;

public class EditCitasActivity extends AppCompatActivity {

    // Elementos de la Vista
    private EditText txtNombre, txtApellidos, txtFechaCita, txtFechaNacimiento, txtFianza;
    private Switch swAutorizado, swColor;
    private Button btnCrear, btnEliminar;

    private CitaTattoo citaTattoo;
    private int posicion;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_citas);

        inicializaDatos();

        if (getIntent().getExtras() != null){
            citaTattoo = getIntent().getExtras().getParcelable("CITA");
            posicion = getIntent().getExtras().getInt("POS");

            if (citaTattoo != null){
                rellenaDatos();
            }
            else{
                citaTattoo = new CitaTattoo();
            }
        }

        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!txtNombre.getText().toString().isEmpty() &&
                        !txtApellidos.getText().toString().isEmpty() &&
                        !txtFechaNacimiento.getText().toString().isEmpty() &&
                        !txtFechaCita.getText().toString().isEmpty() &&
                        !txtFianza.getText().toString().isEmpty()) {

                    try {
                        citaTattoo.setNombre(txtNombre.getText().toString());
                        citaTattoo.setApellidos(txtApellidos.getText().toString());
                        citaTattoo.setAutorizado(swAutorizado.isChecked());
                        citaTattoo.setColor(swColor.isChecked());
                        citaTattoo.setFechaCita(Configuracion.SDF.parse(txtFechaCita.getText().toString()));
                        citaTattoo.setFechaNacimiento(Configuracion.SDF.parse(txtFechaNacimiento.getText().toString()));
                        citaTattoo.setFianza(Float.parseFloat(txtFianza.getText().toString()));
                        Intent intent = new Intent();
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("CITA", citaTattoo);
                        bundle.putInt("POS", posicion);
                        intent.putExtras(bundle);
                        setResult(RESULT_OK, intent);
                        finish();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    Toast.makeText(EditCitasActivity.this, "Todo es Obligatorio", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putInt("POS", posicion);
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }

    private void rellenaDatos() {
        txtNombre.setText(citaTattoo.getNombre());
        txtApellidos.setText(citaTattoo.getApellidos());
        txtFechaCita.setText(Configuracion.SDF.format(citaTattoo.getFechaCita()));
        txtFechaNacimiento.setText(Configuracion.SDF.format(citaTattoo.getFechaNacimiento()));
        txtFianza.setText(String.valueOf(citaTattoo.getFianza()));
        if (citaTattoo.isAutorizado()){
            swAutorizado.setVisibility(View.VISIBLE);
            swAutorizado.setChecked(true);
        }
        swColor.setChecked(citaTattoo.isColor());

        btnEliminar.setVisibility(View.VISIBLE);
        btnCrear.setText("guardar");
    }

    private void inicializaDatos() {
        txtNombre = findViewById(R.id.txtNombreCita);
        txtApellidos = findViewById(R.id.txtApellidosCita);
        txtFechaCita = findViewById(R.id.txtFechaCita);
        txtFechaNacimiento = findViewById(R.id.txtFechaNacimiento);
        txtFianza = findViewById(R.id.txtFianzaCita);
        swAutorizado = findViewById(R.id.swAutorizado);
        swColor = findViewById(R.id.swColorCita);
        btnCrear = findViewById(R.id.btnGuardarCita);
        btnEliminar = findViewById(R.id.btnEliminarCita);


        final LocalDate hoy = LocalDate.now();

        txtFechaNacimiento.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                try {
                    Date fechaNacimiento = Configuracion.SDF.parse(s.toString());
                    LocalDate fNacimiento = fechaNacimiento.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    if (hoy.getYear() - fNacimiento.getYear() < 18){
                        swAutorizado.setVisibility(View.VISIBLE);
                        btnCrear.setEnabled(false);
                    }
                    else
                    {
                        swAutorizado.setChecked(false);
                        swAutorizado.setVisibility(View.GONE);
                        btnCrear.setEnabled(true);
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        swAutorizado.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                btnCrear.setEnabled(isChecked);
            }
        });
    }
}
