package ejemplo;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class Blog extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PrintWriter out = resp.getWriter();
        String pagina = """
            <html>
                <head>
                    <h2>Blog</h2>
                </head>
                <body>

                    {menuLateral}

                    <br>
                       
                </body>
            </html>
        """;
        PlantillasHTML plantilla = new PlantillasHTML();
        pagina = pagina.replace("{menuLateral}",plantilla.menuLateral);

        out.println(pagina);

        String pss1 = req.getParameter("contra1");
        String pss2 = req.getParameter("contra2"); 

        if(!pss1.equals("") && !pss2.equals("") && pss1.equals(pss2)){
            DB db = new DB();

            db.actualizarUsuario("admin", pss1);
            out.println("pasa");
        }
 
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}