package ejemplo;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;

import javax.servlet.http.Cookie;

public class InicioSesion extends HttpServlet {

    static Cookie usuarioC = new Cookie("usuario", "");

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        usuarioC.getValue();

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
                    Usuario: <input type='text' name='usuario' value={usr}><br/> 
                    Contraseña: <input type='password' name='contra'><br/>
                    <input type='submit' value='Loguear'> 
                    </form>
                </body>
            </html>
        """;
        PlantillasHTML plantilla = new PlantillasHTML();
        pagina = pagina.replace("{menuLateral}",plantilla.menuLateral);
        pagina = pagina.replace("{usr}",usuarioC.getValue());

        out.println(pagina);

        String usr = req.getParameter("usuario");
        String pss = req.getParameter("contra"); 
        
        DB db = new DB();

        out.println(usr + " " + pss);
        out.println("<br>");
        String comprobar = db.loguear(usr, pss).toString();

        if(comprobar!=""&&comprobar.equals(usr+" "+pss)){   
            HttpSession sesion = req.getSession(true);
            sesion.setAttribute("usuario", usr);
            sesion.setMaxInactiveInterval(10 * 60); //la sesion durara 10 minutos

            //?
            usuarioC = new Cookie("usuario", usr);
            usuarioC.setMaxAge(60 * 60 * 24);
            usuarioC.setPath("/practica");
            resp.addCookie(usuarioC);
            
            //redireccionar
            resp.sendRedirect(req.getContextPath() + "/panel");
        }
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession sesion = req.getSession();
        sesion.invalidate(); //se desconecta de la sesion que ya tenga
        doPost(req, resp);
    }
}