package es.eduardsanz.ejercicio03_citastattoo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.List;

import es.eduardsanz.ejercicio03_citastattoo.R;
import es.eduardsanz.ejercicio03_citastattoo.configuraciones.Configuracion;
import es.eduardsanz.ejercicio03_citastattoo.modelos.CitaTattoo;

public class CitasAdapter extends ArrayAdapter<CitaTattoo> {

    private Context context;
    private int resource;
    private List<CitaTattoo> objects;

    public CitasAdapter(@NonNull Context context, int resource, @NonNull List<CitaTattoo> objects) {
        super(context, resource, objects);
        this.objects = objects;
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View filaCita = LayoutInflater.from(context).inflate(resource, null);
        CitaTattoo citaTattoo = objects.get(position);

        TextView txtNombre = filaCita.findViewById(R.id.txtNombreFilaCita);
        TextView txtFechaCita = filaCita.findViewById(R.id.txtFechaCitaFilaCita);

        txtNombre.setText(citaTattoo.getNombre());

        txtFechaCita.setText(Configuracion.SDF.format(citaTattoo.getFechaCita()));

        return filaCita;
    }
}
