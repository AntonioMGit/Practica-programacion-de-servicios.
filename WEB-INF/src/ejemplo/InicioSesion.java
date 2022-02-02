package ejemplo;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class InicioSesion extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PrintWriter out = resp.getWriter();
        String pagina = """
            <html>
                <head>
                    <h2>Iniciar sesion</h2>
                </head>
                <body>

                    {menuLateral}

                    <br>
                    
                    <form action='iniciosesion' method='post'>
                    Usuario: <input type='text' name='usuario'><br/> 
                    Contraseña: <input type='text' name='contra'><br/>
                    <input type='submit'> 
                    </form>
                </body>
            </html>
        """;
        PlantillasHTML plantilla = new PlantillasHTML();
        pagina = pagina.replace("{menuLateral}",plantilla.menuLateral);

        out.println(pagina);

        String usr = req.getParameter("usuario");
        String pss = req.getParameter("contra"); 
        
        DB db = new DB();

        out.println(usr + " " + pss);
        out.println("<br>");
        out.println(db.loguear(usr, pss).toString());
        
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}