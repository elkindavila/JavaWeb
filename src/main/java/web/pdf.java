package web;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.List;
import com.itextpdf.layout.element.ListItem;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import datos.Conexion;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "pdf", urlPatterns = {"/pdf"})
public class pdf extends HttpServlet {

    public static final String DOG = "E:\\elkin davila\\dog.png";

    private static final String SQL_SELECT = "SELECT id_cliente,nombre,apellido,email,telefono,saldo FROM cliente";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/pdf");
        OutputStream out = response.getOutputStream();

        try {
            Connection conn = null;
            PreparedStatement stmt = null;
            ResultSet rs = null;

            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT);
            rs = stmt.executeQuery();

            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdf = new PdfDocument(writer);
            //por defecto el tamaño de la hoja es A4, pero con pageZise le puede asignar el tamaño deseado
            Document document = new Document(pdf, PageSize.A4);
            document.setMargins(45, 45, 45, 45);

            // Preparo la imagen
            Image dog = new Image(ImageDataFactory.create(DOG));
            //tamaño de ,a imagen
            dog.scaleToFit(100, 60);
            //posicion de la imagen a la derecha
            dog.setMarginLeft(220);

            //Agrego la imagen
            Paragraph p = new Paragraph();
            p.add(dog);

            //Agregar imagen al documento PDF
            document.add(p);
            
                      //Salto de linea
            document.add(new Paragraph(" "));
                      //Salto de linea
            document.add(new Paragraph(" "));
                      //Salto de linea
            document.add(new Paragraph(" "));

            // Crear una nueva fuente para el titulo y el texto del cuerpo
            PdfFont fontTitulo = PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD);
            PdfFont fontCuerpo = PdfFontFactory.createFont(FontConstants.TIMES_ROMAN);

            // texto centrado y con la nueva fuente creada.  Por defecto la fuente es HELVETYCA
            Paragraph parTitulo = new Paragraph();
            parTitulo.setTextAlignment(TextAlignment.CENTER);
            //Agrego el texto al documento
            document.add(parTitulo.add("Ejemplo PDF con itext7").setFont(fontTitulo));
            
                      //Salto de linea
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));

            Paragraph parCuerpo = new Paragraph();
            document.add(parCuerpo.add("En este documnento dejo el codigo con sus respectivos comentarios"
                    + "para crear un documento PDF con reportes para la aplicacion web deseada").setFont(fontCuerpo));
            
              //Salto de linea
            document.add(new Paragraph(" "));
            
            Paragraph par1 = new Paragraph();
            //crear lista
            List list = new List().setSymbolIndent(12).setListSymbol("\u2022").setFont(fontCuerpo);

            list.add(new ListItem("Lechuga"))
                    .add(new ListItem("Tomate"))
                    .add(new ListItem("Albahaca"))
                    .add(new ListItem("Toronjil"));

            // Agrego lista al documento
            document.add(par1.add("Lista con viñetas").setFont(fontTitulo));
            document.add(list);
            
            //Salto de linea
            document.add(new Paragraph(" "));
            
            Paragraph TituloTabla = new Paragraph("Tabla desde base de datos");
            
            //crear una tabla, en new float esta el ancho que deseo para cada columna de la base de datos
            Table table = new Table(new float[]{2, 4, 4, 4, 4});
            //para que la tabla ocupe el 100% de la hoja
            table.setWidthPercent(100);

            table.addHeaderCell(new Cell().add(new Paragraph("Id").setFont(fontTitulo)));
            table.addHeaderCell(new Cell().add(new Paragraph("Nombre").setFont(fontTitulo)));
            table.addHeaderCell(new Cell().add(new Paragraph("Email").setFont(fontTitulo)));
            table.addHeaderCell(new Cell().add(new Paragraph("Telefono").setFont(fontTitulo)));
            table.addHeaderCell(new Cell().add(new Paragraph("Saldo").setFont(fontTitulo)));

            while (rs.next()) {
                table.addCell(rs.getString("id_cliente"));
                table.addCell(rs.getString("nombre") + " " + rs.getString("apellido"));
                table.addCell(rs.getString("email"));
                table.addCell(rs.getString("telefono"));
                table.addCell("$ " + rs.getString("saldo"));

            }
            
            document.add(TituloTabla);
            document.add(table);
            // Cerrar el documento pdf
            document.close();

        } catch (Exception ex) {
            ex.getMessage();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
