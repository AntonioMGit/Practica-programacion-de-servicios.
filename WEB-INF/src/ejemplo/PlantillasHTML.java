package ejemplo;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import org.stringtemplate.v4.*;


public class PlantillasHTML extends HttpServlet {

    String menuLateral = """
        <div class='menuLateral'>
            <a href='/practica/iniciosesion'>Cerrar sesion</a><br>
            <a href='/practica/panel'>Panel de control</a><br>
            <a href='/practica/blog'>Ir a blog</a><br>
        </div>
    """;

    public String baseHTML(String titulo, String extra){
        String pagina = """
            <html>
                <head>
                    <h2>{titulo}</h2>
                </head>
                <body>
                    {menuLateral}
                    <br>
                    {extra}
                </body>
            </html>
        """;
        //ST paginaST = new ST(pagina);

        //paginaST.add("menuLateral", menuLateral);
        //paginaST.add("titulo", titulo);
        //paginaST.add("extra", extra);

        pagina = pagina.replace("{menuLateral}", menuLateral);
        pagina = pagina.replace("{titulo}", titulo);
        pagina = pagina.replace("{extra}", extra);

        return pagina;
    }

    //public String 
}
    /*
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
    
        String pBase = """
        <html>
            <head>
                Blog
            </head>
            <body>
                
            </body>
        </html>
        """;
        //falta el tema de editar y borrar abajo
        String pEntradas = """
        <html>
            <head>
                {titulo}
            </head>
            <body>
                {fecha}
                {texto}
            </body>
        </html>
        """;
        
        String pIniciarSesion = """
        <form action='acceso' method='post'>
            Usuario: <input type='text' name='usuario'><br/>
            Contraseña: <input type='hidden' name='contra'><br/>
            <input type='submit'> 
        </form>
        """;
        //String usr = req.getParameter("usuario");
        //String pss = req.getParameter("contra");
    }
    */
