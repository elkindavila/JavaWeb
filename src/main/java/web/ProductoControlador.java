package web;

import datos.ProductoDaoJDBC;
import dominio.Producto;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

@MultipartConfig
@WebServlet("/ProductoControlador")
public class ProductoControlador extends HttpServlet {

    ProductoDaoJDBC dao = new ProductoDaoJDBC();
    Producto p = new Producto();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
        if (accion != null) {
            switch (accion) {
                case "Listar":
                    this.listarProducto(request, response);
                    break;
                case "editar":
                    this.editarProducto(request, response);
                    break;
                case "eliminar":
                    this.eliminarProducto(request, response);
                    break;

                default:
                    this.listarProducto(request, response);
            }
        } else {
            this.listarProducto(request, response);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        switch (accion) {
            case "Listar":
                this.listarProducto(request, response);
                break;
            case "modificar":
                this.modificarProducto(request, response);
                break;

            case "insertar":  
                this.insertarProducto(request, response);
                break;
            default:
                request.getRequestDispatcher("ProductoControlador?accion=Listar").forward(request, response);
                break;
        }
    }
    
     private void insertarProducto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //recuperamos los valores del formulario agregarCliente
        // captura la imagen seleccionada para guardar
                Part part = request.getPart("imagen");
                InputStream inputStram = part.getInputStream();

                String nombre = request.getParameter("nombre");
                String marca = request.getParameter("marca");

                double valor = 0;
                String valorString = request.getParameter("valor");
                if (valorString != null && !"".equals(valorString)) {
                    valor = Double.parseDouble(valorString);
                }
                int cantidad = 0;
                String cantidadString = request.getParameter("cantidad");
                if (cantidadString != null && !"".equals(cantidadString)) {
                    cantidad = (int) Double.parseDouble(cantidadString);
                }

                String descripcion = request.getParameter("descripcion");

                p.setImagen(inputStram);
                p.setNombre(nombre);
                p.setMarca(marca);
                p.setValor(valor);
                p.setCantidad(cantidad);
                p.setDescripcion(descripcion);

                dao.agregar(p);
                request.getRequestDispatcher("ProductoControlador?accion=Listar").forward(request, response);
    }

    private void listarProducto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<Producto> lista = dao.listar();
        HttpSession session = request.getSession();
        session.setAttribute("lista", lista);
        //request.getRequestDispatcher("listadoProducto.jsp").forward(request, response);
        response.sendRedirect("productos.jsp");

    }

    private void editarProducto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Recuperamos el idCliente
        int idProducto = Integer.parseInt(request.getParameter("idProducto"));
        Producto producto = dao.encontrar(new Producto(idProducto));
        request.setAttribute("producto", producto);
        String jspEditar = "/WEB-INF/paginas/producto/editarProducto.jsp";
        request.getRequestDispatcher(jspEditar).forward(request, response);
    }

    private void modificarProducto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //recuperamos los valores del formulario editar Producto
        int idProducto = Integer.parseInt(request.getParameter("idProducto"));
        // captura la imagen seleccionada para guardar
        Part part = request.getPart("imagen");
        InputStream inputStream = part.getInputStream();

        int val = inputStream.available();

        String nombre = request.getParameter("nombre");
        String marca = request.getParameter("marca");

        double valor = 0;
        String valorString = request.getParameter("valor");
        if (valorString != null && !"".equals(valorString)) {
            valor = Double.parseDouble(valorString);
        }
        int cantidad = 0;
        String cantidadString = request.getParameter("cantidad");
        if (cantidadString != null && !"".equals(cantidadString)) {
            cantidad = (int) Double.parseDouble(cantidadString);
        }

        String descripcion = request.getParameter("descripcion");

        if (val == 0) {
            Producto producto1 = new Producto(idProducto, nombre, marca, valor, cantidad, descripcion);
            int registrosModificados = dao.actualizarSinImagen(producto1);
            System.out.println("registrosModificados = " + registrosModificados);
        } else {

            //Creamos el objeto de cliente (modelo)
            Producto producto = new Producto(idProducto, inputStream, nombre, marca, valor, cantidad, descripcion);
            //Modificar el  objeto en la base de datos
            int registrosModificados = dao.actualizar(producto);
            System.out.println("registrosModificados = " + registrosModificados);
        }

        //Redirigimos hacia accion por default
        this.listarProducto(request, response);
    }
    
     private void eliminarProducto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //recuperamos id del producto
        int idProducto = Integer.parseInt(request.getParameter("idProducto"));
     

        //Creamos el objeto de cliente (modelo)
        Producto producto = new Producto(idProducto);

        //Eliminamos el  objeto en la base de datos
        int registrosModificados = new ProductoDaoJDBC().eliminarProducto(producto);
        System.out.println("registrosModificados = " + registrosModificados);

        //Redirigimos hacia accion por default
        this.listarProducto(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
