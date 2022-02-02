package ejemplo;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class Hora extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        String page = """
            <html>
            <body>
            <h1>Hola mundo</h1>
            hoy es {hora}        
            </body>
            </html>""";
        out.print(page.replace("{hora}", (new Date()).toString()));
    }
}
