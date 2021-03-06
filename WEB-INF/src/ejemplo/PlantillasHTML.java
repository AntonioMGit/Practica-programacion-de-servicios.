package ejemplo;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.stringtemplate.v4.*;
import java.util.*;


public class PlantillasHTML extends HttpServlet {

    String menuLateral = """
        <div class='menuLateral'>
            <a href='/practica/iniciosesion'>Iniciar/Cerrar sesion</a><br>
            <a href='/practica/panel'>Panel de control</a><br>
            <a href='/practica/blog'>Ir a blog</a><br>
        </div>
    """;

    public String baseHTML(String titulo, String extra){
        
        ST paginaST = new ST("""
            <html>
                <head>
                    <h2 style='text-align:center;color:white;background-color:SeaGreen;'>$titulo$</h2>
                </head>
                <body>
                    <br>
                    <table style='width:100%'>
                        <tr style='vertical-align:top'>
                            <th style='width:25%;'>
                            </th>
                            <th style='text-align:left;'>
                                $extra$
                            </th>
                            <th style='width:30%;text-align:left;'>
                                $menuLateral$
                            </th>  
                        </tr>                 
                    </table>
                </body>
            </html>
        """ , '$', '$');

        paginaST.add("menuLateral", menuLateral);
        paginaST.add("titulo", titulo);
        paginaST.add("extra", extra);

        return paginaST.render();
    }

    public String inicioSesionHTML(){
        
        ST paginaST = new ST("""
                <br>
                La sesión no está iniciada; inicia sesión.
                <br>
                <br>            
                <form action='iniciosesion' method='post'>
                Usuario: <input type='text' name='usuario'><br> 
                Contraseña: <input type='password' name='contra'><br>
                <input type='submit' value='Loguear'> 
                </form>
                """, '$', '$');

        return paginaST.render();
    }

    public String blogHTML(ArrayList<String> listaEntradas, boolean conectado){

        String entradas = "";
        String extra = "";

        ST entradasST = null;
        
        for(int i = 0; i < listaEntradas.size(); i++){
            entradas = "";
            entradas = entradas +   """
                                    <h3 style='text-align:center;color:DarkGreen;'>$titulo$</h3>
                                    <p style='color:MediumSeaGreen;font-size:60%'>$fecha$</p>
                                    $texto$
                                    <br>
                                    """;
            if(conectado){
                entradas = entradas +"""
                                    <p style='font-size:75%;'>
                                        <a href='$eEditar$'> Editar<a>
                                        <br>
                                        <a href='$eBorrar$'> Borrar<a>
                                    </p>
                                    """;    
            }
            entradas= entradas + "<hr style='width:80%;margin-left:10%;'>";

            entradasST = new ST(entradas, '$', '$');


            String[] datos = listaEntradas.get(i).split(",");//separa la liena de string, que tiene todos los datos juntos
            
            String eEditar = "/practica/editor?identrada=" + datos[0]; //datos[0] es la id de la entrada
            String eBorrar = "/practica/borrar?tipo=entrada&identrada="+ datos[0]; //es con ?

            entradasST.add("titulo", datos[1]);//el 1 es el titulo
            entradasST.add("fecha", datos[3]);//el 3 es la fecha
            entradasST.add("texto", datos[2]);//el 2 es el texto
            entradasST.add("eEditar", eEditar);
            entradasST.add("eBorrar", eBorrar);

            extra = extra + entradasST.render();

        }

        return extra;
    }

    public String panelHTML(String usr, ArrayList<String> listaEntradas){

        ST formularioST = new ST("""
            <form action='panel' method='post'>
            Contraseña: <input type='password' name='contra1'><br/> 
            Repetir contraseña: <input type='password' name='contra2'><br/> 
            <input type='submit' value='Cambiar contraseña'> 
            </form>
            <br>
            $gestionUsuarios$
            <a href='/practica/editor'>Crear nueva entrada<a>
            <br>
            <br>
        """,'$','$');
        
        if(usr.equals("admin")){
            String gUsuarios = """
            <a href='/practica/usuarios'>Gestionar usuarios<a>
            <br>
            """;
            formularioST.add("gestionUsuarios",gUsuarios);
        }else{
            formularioST.add("gestionUsuarios","");
        }

        ST entradas = null;
        String extra = formularioST.render();

        for(int i = 0; i < listaEntradas.size(); i++){
            entradas = new ST("""
                                <a href='$eEditar$'> Editar<a>
                                <a href='$eBorrar$'> Borrar<a>
                                $titulo$
                                <br>
                                """,'$','$');

            String[] datos = listaEntradas.get(i).split(",");//separa la liena de string, que tiene todos los datos juntos
            
            String eEditar = "/practica/editor?identrada=" + datos[0]; //datos[0] es la id de la entrada
            String eBorrar = "/practica/borrar?tipo=entrada&identrada="+ datos[0]; //es con ?

            entradas.add("eEditar", eEditar);
            entradas.add("eBorrar", eBorrar);
            entradas.add("titulo", datos[1]);//el 1 es el titulo

            extra = extra + entradas.render();
        }

        return extra;
    }

    public String editorHTML(String titulo, String texto, String fecha){
        ST formularioST = new ST("""
            <form action='editor' method='post'>
                Titulo: <br>
                <input type='text' name='titulo' value=$titulo$><br/> 
                Texto: <br>
                <textarea name='texto'>$texto$</textarea><br/> 
                <input type='date' name='fecha' value='$fecha$'><br/> 
                <input type='submit' value='Guardar'> 
            </form>
        """, '$', '$');

        formularioST.add("titulo", titulo);
        formularioST.add("texto", texto);
        formularioST.add("fecha", fecha);

        return formularioST.render();
    }

    public String usuariosHTML(ArrayList<String> listaUsuarios){
        String formulario = """
            <form action='usuarios' method='post'>
            Usuario: <input type='text' name='usr'><br/> 
            Contraseña: <input type='password' name='contra'><br/> 
            <input type='submit' value='Insertar usuario'> 
            </form>
            <br>
            <a href='/practica/usuarios'>Crear nuevo usuario<a>
            <br>
            <a href='/practica/editor'>Crear nueva entrada<a>
            <br>
        """;

        ST entradas = null;
        String extra = formulario;

        for(int i = 0; i < listaUsuarios.size(); i++){
            entradas = new ST ("""
                                <a href='$eBorrar$'> Borrar<a>
                                $usr$
                                <br>
                                """, '$', '$');

            String eBorrar = "/practica/borrar?tipo=usuario&idusr="+ listaUsuarios.get(i); 
            //entradas = entradas.replace("{eEditar}", eEditar);
            entradas.add("eBorrar", eBorrar);
            entradas.add("usr", listaUsuarios.get(i));

            extra = extra + entradas.render();
        }

        return extra;
    }

    public String borrarHTML(String tipo, String id){
        String texto = "";
        if(tipo.equals("entrada")){ 
            texto = "Entrada con id: " + id + " eliminada correctamente. " +
                "<br>" +
                "<a href='/practica/panel'>Volver al panel de control<a>";

        }
        if(tipo.equals("usuario")){ 

            texto = "Usuario con nombre: " + id + " eliminado correctamente. " +
                "<br>" +
                "<a href='/practica/panel'>Volver al panel de control<a>";
        }
        return texto;
    }


}
    