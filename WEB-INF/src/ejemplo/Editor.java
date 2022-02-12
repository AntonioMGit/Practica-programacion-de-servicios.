package ejemplo;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;

import java.time.LocalDate;
import java.util.*;


public class Editor extends HttpServlet {

    //static boolean pasa = false;
    static String sId;
    //quitar
    //static String sTitulo;
    //static String sTexto;
    //static String sFecha;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //
        DB db = new DB();
    
        PrintWriter out = resp.getWriter();

        String tipo = req.getParameter("tipo");

        String titulo = req.getParameter("titulo");
        String texto = req.getParameter("texto"); 
        String fecha = req.getParameter("fecha");

        String id = req.getParameter("identrada");

        //se guarda la id si le pasan alguna
        if(id!=null){
            sId=id;
        }

        PlantillasHTML plantilla = new PlantillasHTML();
        String extra = "";

        if(id!=null){
            List<String> entrada = db.buscarEntradaPorId(id);
            extra = plantilla.editorHTML(entrada.get(1), entrada.get(2), entrada.get(3));//1titulo 2texto
        }else{
            extra = plantilla.editorHTML("", "", "");
        }
        
        String pagina = plantilla.baseHTML("Editar entrada", extra);

        out.println(pagina);

        if(!titulo.equals("")&&!texto.equals("")&&!fecha.equals("")){
            //si le han pasado alguna id
            if(sId!=null){
                db.actualizarEntrada(sId, titulo, texto, fecha);
                pasa=false;
                out.println("assafdsf");
                sId=id; //vuelve a dejar la variable como si no le hubieran pasado ninguna
                //redireccionar
                resp.sendRedirect(req.getContextPath() + "/blog");
            }else{
                db.insertarEntrada(titulo, texto, fecha);
                //redireccionar
                resp.sendRedirect(req.getContextPath() + "/blog");
            }
        }else{
            out.println("Faltan datos");
        }

    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        
        HttpSession sesion = req.getSession(false);

        //lo pongo asi porque sino, con uno me da error cuando ya he iniciado una vez y con el otro 
        //cuando no he iniciado. Poniendo los dos no da error en ningun caso
        if(sesion != null && sesion.getAttribute("usuario") != null){
            doPost(req, resp);
        }else{
            resp.sendRedirect(req.getContextPath() + "/iniciosesion");
        }
    }
}