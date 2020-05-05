package datos;

import dominio.Producto;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

public class ProductoDaoJDBC {

    private static final String SQL_SELECT = "SELECT idproducto,imagen,nombre,marca,valor,cantidad,descripcion FROM producto";

    private static final String SQL_SELECT_BY_ID = "SELECT idproducto,imagen,nombre,marca,valor,cantidad,descripcion,fecha_creacion,fecha_modificacion"
            + " FROM producto WHERE idproducto = ?";

    private static final String SQL_INSERT = "INSERT INTO producto(imagen,nombre,marca,valor,cantidad,descripcion)"
            + " VALUES(?, ?, ?, ?, ?, ?)";

    private static final String SQL_UPDATE = "UPDATE producto "
            + " SET imagen=?, nombre=?, marca=?, valor=?, cantidad=?, descripcion=?, fecha_creacion=?, fecha_modificacion=? WHERE idproducto = ? ";
    
    private static final String SQL_UPDATE_NOIMG = "UPDATE producto "
            + " SET nombre=?, marca=?, valor=?, cantidad=?, descripcion=?, fecha_creacion=?, fecha_modificacion=? WHERE idproducto = ? ";
    
    private static final String SQL_DELETE = "DELETE FROM producto WHERE idproducto = ? ";

    public List listar() {
        List<Producto> productos = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Producto producto = null;

        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT);
            rs = stmt.executeQuery();

            while (rs.next()) {

                Producto p = new Producto();

                p.setIdProducto(rs.getInt("idproducto"));
                p.setImagen(rs.getBinaryStream("imagen"));
                p.setNombre(rs.getString("nombre"));
                p.setMarca(rs.getString("marca"));
                p.setValor(rs.getDouble("valor"));
                p.setCantidad(rs.getInt("cantidad"));
                p.setDescripcion(rs.getString("descripcion"));
                //Date fechaC = rs.getDate("fecha_creacion");
                //Date fechaM = rs.getDate("fecha_modificacion");

                productos.add(p);
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            Conexion.close(conn);

        }

        return productos;
    }

    public void listarImg(int id, HttpServletResponse response) throws IOException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        InputStream inputStream = null;
        OutputStream outputStream = null;
        BufferedInputStream bufferedInputStream = null;
        BufferedOutputStream bufferedOutputStream = null;
        response.setContentType("image/gif");
        
        String sql = "select idproducto,imagen,nombre,marca,valor,cantidad,descripcion from producto where idproducto=" + id;

        try {
            
            outputStream = response.getOutputStream();
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            if (rs.next()) {
                inputStream = rs.getBinaryStream("imagen");
            }
            bufferedInputStream = new BufferedInputStream(inputStream);
            bufferedOutputStream = new BufferedOutputStream(outputStream);
            int i = 0;
            while ((i = bufferedInputStream.read())!= -1) {
                bufferedOutputStream.write(i);
            }

        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            Conexion.close(conn);

        }

    }

    public void agregar(Producto producto) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_INSERT);

            stmt.setBlob(1, producto.getImagen());
            stmt.setString(2, producto.getNombre());
            stmt.setString(3, producto.getMarca());
            stmt.setDouble(4, producto.getValor());
            stmt.setInt(5, producto.getCantidad());
            stmt.setString(6, producto.getDescripcion());
            //stmt.setDate(7, (java.sql.Date) producto.getFechaC());
            //stmt.setDate(8, (java.sql.Date) producto.getFechaM());

            stmt.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(stmt);
            Conexion.close(conn);
        }

    }

    public int actualizar(Producto producto) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;

        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_UPDATE);

            stmt.setBinaryStream(1, producto.getImagen());
            stmt.setString(2, producto.getNombre());
            stmt.setString(3, producto.getMarca());
            stmt.setDouble(4, producto.getValor());
            stmt.setInt(5, producto.getCantidad());
            stmt.setString(6, producto.getDescripcion());
            stmt.setDate(7, (java.sql.Date) producto.getFechaC());
            stmt.setDate(8, (java.sql.Date) producto.getFechaM());
            stmt.setInt(9, producto.getIdProducto());

            rows = stmt.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(stmt);
            Conexion.close(conn);
        }
        return rows;
    }
    
    public int actualizarSinImagen(Producto producto) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;

        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_UPDATE_NOIMG);
            
            stmt.setString(1, producto.getNombre());
            stmt.setString(2, producto.getMarca());
            stmt.setDouble(3, producto.getValor());
            stmt.setInt(4, producto.getCantidad());
            stmt.setString(5, producto.getDescripcion());
            stmt.setDate(6, (java.sql.Date) producto.getFechaC());
            stmt.setDate(7, (java.sql.Date) producto.getFechaM());
            stmt.setInt(8, producto.getIdProducto());

            rows = stmt.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(stmt);
            Conexion.close(conn);
        }
        return rows;
    }
    
        public Producto encontrar(Producto producto) {

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT_BY_ID);
            stmt.setInt(1, producto.getIdProducto());
            rs = stmt.executeQuery();
            rs.absolute(1); //nos posicionamos en el registro encontrado

            InputStream imagen = rs.getBinaryStream("imagen");
            String nombre = rs.getString("nombre");
            String marca = rs.getString("marca");
            double valor = rs.getDouble("valor");
            int cantidad = rs.getInt("cantidad");
            String descripcion = rs.getString("descripcion");
            Date fechaC = rs.getDate("fecha_creacion");
            Date fechaM = rs.getDate("fecha_modificacion");

            producto.setImagen(imagen);
            producto.setNombre(nombre);
            producto.setMarca(marca);
            producto.setValor(valor);
            producto.setCantidad(cantidad);
            producto.setDescripcion(descripcion);
            producto.setFechaC(fechaC);
            producto.setFechaM(fechaM);

        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            Conexion.close(conn);

        }
        return producto;
    }

    public int eliminarProducto(Producto producto) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;

        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_DELETE);

            stmt.setInt(1, producto.getIdProducto());

            rows = stmt.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(stmt);
            Conexion.close(conn);
        }
        return rows;
    }
}
