
package dominio;

import java.io.InputStream;
import java.util.Date;

public class Producto {
    private int idProducto;
    private InputStream imagen;
    private String nombre;
    private String marca;
    private double valor;
    private int cantidad;
    private String descripcion;
    private Date fechaC;
    private Date fechaM;

    public Producto() {
    }

    public Producto(int idProducto) {
        this.idProducto = idProducto;
    }
    
    

    public Producto(int idProducto, InputStream imagen, String nombre, String marca, Double valor, int cantidad, String descripcion, Date fechaC, Date fechaM) {
        this.idProducto = idProducto;
        this.imagen = imagen;
        this.nombre = nombre;
        this.marca = marca;
        this.valor = valor;
        this.cantidad = cantidad;
        this.descripcion = descripcion;
        this.fechaC = fechaC;
        this.fechaM = fechaM;
    }

    public Producto(int idProducto, InputStream imagen, String nombre, String marca, Double valor, int cantidad, String descripcion) {
        this.idProducto = idProducto;
        this.imagen = imagen;
        this.nombre = nombre;
        this.marca = marca;
        this.valor = valor;
        this.cantidad = cantidad;
        this.descripcion = descripcion;
    }

    public Producto(int idProducto, String nombre, String marca, double valor, int cantidad, String descripcion) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.marca = marca;
        this.valor = valor;
        this.cantidad = cantidad;
        this.descripcion = descripcion;
    }
    
    

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public InputStream getImagen() {
        return imagen;
    }

    public void setImagen(InputStream imagen) {
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaC() {
        return fechaC;
    }

    public void setFechaC(Date fechaC) {
        this.fechaC = fechaC;
    }

    public Date getFechaM() {
        return fechaM;
    }

    public void setFechaM(Date fechaM) {
        this.fechaM = fechaM;
    }
    
    
    
    
}
