package es.eduardsanz.ejercicio03_citastattoo;

import androidx.appcompat.app.AppCompatActivity;

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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.TooManyListenersException;

import es.eduardsanz.ejercicio03_citastattoo.configuraciones.Configuracion;
import es.eduardsanz.ejercicio03_citastattoo.modelos.CitaTattoo;

public class AddCitasActivity extends AppCompatActivity {

    // Elementos de la Vista
    private EditText txtNombre, txtApellidos, txtFechaCita, txtFechaNacimiento, txtFianza;
    private Switch swAutorizado, swColor;
    private Button btnCrear;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_citas);

        txtNombre = findViewById(R.id.txtNombreCita);
        txtApellidos = findViewById(R.id.txtApellidosCita);
        txtFechaCita = findViewById(R.id.txtFechaCita);
        txtFechaNacimiento = findViewById(R.id.txtFechaNacimiento);
        txtFianza = findViewById(R.id.txtFianzaCita);
        swAutorizado = findViewById(R.id.swAutorizado);
        swColor = findViewById(R.id.swColorCita);
        btnCrear = findViewById(R.id.btnGuardarCita);

        final LocalDate hoy = LocalDate.now();

        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!txtNombre.getText().toString().isEmpty() &&
                    !txtApellidos.getText().toString().isEmpty() &&
                    !txtFechaNacimiento.getText().toString().isEmpty() &&
                    !txtFechaCita.getText().toString().isEmpty() &&
                    !txtFianza.getText().toString().isEmpty()) {

                    try {
                        CitaTattoo citaTattoo = new CitaTattoo(txtNombre.getText().toString(),
                                                                txtApellidos.getText().toString(),
                                                                Configuracion.SDF.parse(txtFechaNacimiento.getText().toString()),
                                                                Configuracion.SDF.parse(txtFechaCita.getText().toString()),
                                                                Float.parseFloat(txtFianza.getText().toString()),
                                                                swColor.isChecked(), swAutorizado.isChecked());
                        Intent intent = new Intent();
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("CITA", citaTattoo);
                        intent.putExtras(bundle);
                        setResult(RESULT_OK, intent);
                        finish();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    Toast.makeText(AddCitasActivity.this, "Todo es Obligatorio", Toast.LENGTH_SHORT).show();
                }

            }
        });



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