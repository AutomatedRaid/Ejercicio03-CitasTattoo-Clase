package es.eduardsanz.ejercicio03_citastattoo.configuraciones;

import java.text.SimpleDateFormat;

public class Configuracion {
    public static final SimpleDateFormat SDF;

    static {
        SDF = new SimpleDateFormat("dd/MM/yyyy");
    }
}
