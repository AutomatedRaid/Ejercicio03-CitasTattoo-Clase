package es.eduardsanz.ejercicio03_citastattoo.modelos;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class CitaTattoo implements Parcelable {

    private String nombre;
    private String apellidos;
    private Date fechaNacimiento;
    private Date fechaCita;
    private float fianza;
    private boolean color;
    private boolean autorizado;

    public CitaTattoo(String nombre, String apellidos, Date fechaNacimiento,
                      Date fechaCita, float fianza, boolean color, boolean autorizado) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.fechaNacimiento = fechaNacimiento;
        this.fechaCita = fechaCita;
        this.fianza = fianza;
        this.color = color;
        this.autorizado = autorizado;
    }

    public CitaTattoo() {
        autorizado = false;
        fechaCita = new Date();
        fechaNacimiento = new Date();
    }

    protected CitaTattoo(Parcel in) {
        nombre = in.readString();
        apellidos = in.readString();
        fianza = in.readFloat();
        color = in.readByte() != 0;
        autorizado = in.readByte() != 0;
        fechaCita = new Date(in.readLong());
        fechaNacimiento = new Date(in.readLong());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nombre);
        dest.writeString(apellidos);
        dest.writeFloat(fianza);
        dest.writeByte((byte) (color ? 1 : 0));
        dest.writeByte((byte) (autorizado ? 1 : 0));
        dest.writeLong(fechaCita.getTime());
        dest.writeLong(fechaNacimiento.getTime());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CitaTattoo> CREATOR = new Creator<CitaTattoo>() {
        @Override
        public CitaTattoo createFromParcel(Parcel in) {
            return new CitaTattoo(in);
        }

        @Override
        public CitaTattoo[] newArray(int size) {
            return new CitaTattoo[size];
        }
    };

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Date getFechaCita() {
        return fechaCita;
    }

    public void setFechaCita(Date fechaCita) {
        this.fechaCita = fechaCita;
    }

    public float getFianza() {
        return fianza;
    }

    public void setFianza(float fianza) {
        this.fianza = fianza;
    }

    public boolean isColor() {
        return color;
    }

    public void setColor(boolean color) {
        this.color = color;
    }

    public boolean isAutorizado() {
        return autorizado;
    }

    public void setAutorizado(boolean autorizado) {
        this.autorizado = autorizado;
    }
}
