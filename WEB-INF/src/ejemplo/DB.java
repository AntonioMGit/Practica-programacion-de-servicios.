package ejemplo;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.time.LocalDate;
import java.util.*;

// Bibliotecas para conectar con SQLite
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

//m5
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DB extends HttpServlet {

    public void iniciar(){ //para iniciar toda la base de datos creandola y creando las tablas necesarias
        Connection conn = null;
        StringBuffer respuesta = new StringBuffer();
        try {
            // Ruta a la base de datos. El archivo "base_datos.db".
            // Se puede indicar una ruta completa del tipo /home/usuario/... 
            String url = "jdbc:sqlite:base_datos.db";
            // Se crea la conexión a la base de datos:
		    Class.forName("org.sqlite.JDBC").getDeclaredConstructor().newInstance();
            conn = DriverManager.getConnection(url);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    // Se crea la tabla USUARIOS si no existe
                    String sql = """
                        CREATE TABLE IF NOT EXISTS usuarios (
                            usuario TEXT PRIMARY KEY,
                            password TEXT
                        );""";
                    Statement stmt = conn.createStatement();
                    stmt.execute(sql);

                    // Se crea la tabla ENTRADAS si no existe
                    //la fecha como int??
                    sql = """
                        CREATE TABLE IF NOT EXISTS entradas (
                            id INTEGER PRIMARY KEY AUTOINCREMENT,
                            titulo TEXT,
                            texto TEXT,
                            fecha TEXT
                        );""";
                    stmt = conn.createStatement();
                    stmt.execute(sql);

                    // Se cierra la conexión con la base de datos
                    conn.close();
                }
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
            }
        }
    }

    public void insertarUsuario(String usr, String contra){ //para insertar un usuario en la base de datos
        Connection conn = null;
        StringBuffer respuesta = new StringBuffer();
        try {
            // Ruta a la base de datos. El archivo "base_datos.db".
            // Se puede indicar una ruta completa del tipo /home/usuario/... 
            String url = "jdbc:sqlite:base_datos.db";
            // Se crea la conexión a la base de datos:
		    Class.forName("org.sqlite.JDBC").getDeclaredConstructor().newInstance();
            conn = DriverManager.getConnection(url);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {

                    contra = m5Contra(contra);

                    // Se insertan los datos de los usuarios
                    String sqlInsert = "INSERT INTO usuarios(usuario, password) VALUES(?,?)";
                    PreparedStatement pstmt = conn.prepareStatement(sqlInsert);
                    pstmt.setString(1, usr);
                    pstmt.setString(2, contra);
                    pstmt.executeUpdate();

                    // Se cierra la conexión con la base de datos
                    conn.close();
                }
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
            }
        }
    }

    public boolean loguear(String usr, String contra){ 
        Connection conn = null;
        StringBuffer respuesta = new StringBuffer();
        boolean iguales = false;
        try {
            // Ruta a la base de datos. El archivo "base_datos.db".
            // Se puede indicar una ruta completa del tipo /home/usuario/... 
            String url = "jdbc:sqlite:base_datos.db";
            // Se crea la conexión a la base de datos:
		    Class.forName("org.sqlite.JDBC").getDeclaredConstructor().newInstance();
            conn = DriverManager.getConnection(url);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    contra = m5Contra(contra);
                    // Se hace una consulta
                    String sqlSelect = "SELECT usuario, password FROM usuarios " +
                        "where usuario = ? and password = ?";

                    PreparedStatement pstmt = conn.prepareStatement(sqlSelect);
                    pstmt.setString(1, usr);
                    pstmt.setString(2, contra);

                    ResultSet cursor = pstmt.executeQuery(); //hay que dejarlo en blanco porque sino da "not implemented by SQLite JDBC driver"
                    while(cursor.next()) {
                        respuesta.append(cursor.getString("usuario"));
                        respuesta.append(" ");
                        respuesta.append(cursor.getString("password"));
                    }

                    // Se cierra la conexión con la base de datos
                    conn.close();
                }
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
            }
        }
        if(respuesta.toString().equals("")){
            iguales = false;
        }else{
            iguales = true;
        }

        return iguales;
    }

    public ArrayList<String> buscarUsuarios(){ //md5?
        Connection conn = null;
        ArrayList<String> respuesta = new ArrayList<String>();
        try {
            // Ruta a la base de datos. El archivo "base_datos.db".
            // Se puede indicar una ruta completa del tipo /home/usuario/... 
            String url = "jdbc:sqlite:base_datos.db";
            // Se crea la conexión a la base de datos:
		    Class.forName("org.sqlite.JDBC").getDeclaredConstructor().newInstance();
            conn = DriverManager.getConnection(url);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    
                    // Se hace una consulta
                    String sqlSelect = "SELECT usuario, password FROM usuarios " +
                        "WHERE not usuario = 'admin'";

                    PreparedStatement pstmt = conn.prepareStatement(sqlSelect);
                    ResultSet cursor = pstmt.executeQuery(); //hay que dejarlo en blanco porque sino da "not implemented by SQLite JDBC driver"
                    while(cursor.next()) {
                        respuesta.add(cursor.getString("usuario"));
                    }

                    // Se cierra la conexión con la base de datos
                    conn.close();
                }
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
            }
        }
        return respuesta;
    }

    public void actualizarUsuario(String usr, String contra){ 
        Connection conn = null;
        StringBuffer respuesta = new StringBuffer();
        try {
            // Ruta a la base de datos. El archivo "base_datos.db".
            // Se puede indicar una ruta completa del tipo /home/usuario/... 
            String url = "jdbc:sqlite:base_datos.db";
            // Se crea la conexión a la base de datos:
		    Class.forName("org.sqlite.JDBC").getDeclaredConstructor().newInstance();
            conn = DriverManager.getConnection(url);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    contra = m5Contra(contra);

                    String sqlInsert = "UPDATE usuarios SET password = ? WHERE usuario = ?";
                    PreparedStatement pstmt = conn.prepareStatement(sqlInsert);
                    pstmt.setString(2, usr);
                    pstmt.setString(1, contra);
                    pstmt.executeUpdate();

                    // Se cierra la conexión con la base de datos
                    conn.close();
                }
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
            }
        }
    }

    public void borrarUsuario(String id){
        Connection conn = null;
        try {
            // Ruta a la base de datos. El archivo "base_datos.db".
            // Se puede indicar una ruta completa del tipo /home/usuario/... 
            String url = "jdbc:sqlite:base_datos.db";
            // Se crea la conexión a la base de datos:
		    Class.forName("org.sqlite.JDBC").getDeclaredConstructor().newInstance();
            conn = DriverManager.getConnection(url);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    
                    // Se hace una consulta                   
                    String sqlSelect = "DELETE FROM usuarios " +
                        "WHERE usuario = ? ";
                    
                    PreparedStatement pstmt = conn.prepareStatement(sqlSelect);
                    pstmt.setString(1, id);
                    pstmt.executeUpdate(); //tiene que ser execute update sino no borra
                            
                    // Se cierra la conexión con la base de datos
                    conn.close();
                }
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
            }
        }
    }

    public void insertarEntrada(String titulo, String texto, String fecha){ 
        Connection conn = null;
        StringBuffer respuesta = new StringBuffer();
        try {
            // Ruta a la base de datos. El archivo "base_datos.db".
            // Se puede indicar una ruta completa del tipo /home/usuario/... 
            String url = "jdbc:sqlite:base_datos.db";
            // Se crea la conexión a la base de datos:
		    Class.forName("org.sqlite.JDBC").getDeclaredConstructor().newInstance();
            conn = DriverManager.getConnection(url);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    //reemplazar ciertos caracteres
                    titulo = titulo.replaceAll("&", "&amp;");
                    titulo = titulo.replaceAll(">", "&gt;");
                    titulo = titulo.replaceAll("<", "&lt;");
                    titulo = titulo.replaceAll("'", "&#039;");
                    titulo = titulo.replaceAll("\"", "&#034;");
                    titulo = titulo.replaceAll(",", "&#44;");
                    titulo = titulo.replaceAll("\n", "&lt;br&gt;");

                    texto = texto.replaceAll("&", "&amp;");
                    texto = texto.replaceAll(">", "&gt;");
                    texto = texto.replaceAll("<", "&lt;");
                    texto = texto.replaceAll("'", "&#039;");
                    texto = texto.replaceAll("\"", "&#034;");
                    texto = texto.replaceAll(",", "&#44;");
                    texto = texto.replaceAll("\n", "&lt;br&gt;");
                    
                    // Se insertan los datos de los usuarios
                    String sqlInsert = "INSERT INTO entradas(titulo, texto, fecha) VALUES(?,?,?)";
                    PreparedStatement pstmt = conn.prepareStatement(sqlInsert);
                    pstmt.setString(1, titulo);
                    pstmt.setString(2, texto);
                    pstmt.setString(3, fecha);
                    pstmt.executeUpdate();//tiene que ser execute update

                    // Se cierra la conexión con la base de datos
                    conn.close();
                }
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
            }
        }
    }

    public void actualizarEntrada(String id, String titulo, String texto, String fecha){ 
        Connection conn = null;
        StringBuffer respuesta = new StringBuffer();
        try {
            // Ruta a la base de datos. El archivo "base_datos.db".
            // Se puede indicar una ruta completa del tipo /home/usuario/... 
            String url = "jdbc:sqlite:base_datos.db";
            // Se crea la conexión a la base de datos:
		    Class.forName("org.sqlite.JDBC").getDeclaredConstructor().newInstance();
            conn = DriverManager.getConnection(url);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    //reemplazar ciertos caracteres
                    titulo = titulo.replaceAll("&", "&amp;");
                    titulo = titulo.replaceAll(">", "&gt;");
                    titulo = titulo.replaceAll("<", "&lt;");
                    titulo = titulo.replaceAll("'", "&#039;");
                    titulo = titulo.replaceAll("\"", "&#034;");
                    titulo = titulo.replaceAll(",", "&#44;");
                    titulo = titulo.replaceAll("\n", "&lt;br&gt;");

                    texto = texto.replaceAll("&", "&amp;");
                    texto = texto.replaceAll(">", "&gt;");
                    texto = texto.replaceAll("<", "&lt;");
                    texto = texto.replaceAll("'", "&#039;");
                    texto = texto.replaceAll("\"", "&#034;");
                    texto = texto.replaceAll(",", "&#44;");
                    texto = texto.replaceAll("\n", "&lt;br&gt;");

                    // Se insertan los datos de los usuarios
                    String sqlInsert = "UPDATE entradas SET titulo = ?, texto = ?, fecha = ? " +
                                            "WHERE id LIKE ?";
                    PreparedStatement pstmt = conn.prepareStatement(sqlInsert);
                    pstmt.setString(1, titulo);
                    pstmt.setString(2, texto);
                    pstmt.setString(3, fecha);
                    pstmt.setString(4, id);
                    pstmt.executeUpdate();//tiene que ser execute update

                    // Se cierra la conexión con la base de datos
                    conn.close();
                }
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
            }
        }
    }

    public ArrayList<String> buscarEntradas(){ 
        Connection conn = null;
        ArrayList<String> entradas = new ArrayList<String>();
        //HashMap<Integer, String> entradas = new HashMap<Integer, String>();
        try {
            // Ruta a la base de datos. El archivo "base_datos.db".
            // Se puede indicar una ruta completa del tipo /home/usuario/... 
            String url = "jdbc:sqlite:base_datos.db";
            // Se crea la conexión a la base de datos:
		    Class.forName("org.sqlite.JDBC").getDeclaredConstructor().newInstance();
            conn = DriverManager.getConnection(url);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    
                    // Se hace una consulta                   
                    String sqlSelect = "SELECT id, titulo, texto, fecha FROM entradas " +
                        "order by fecha desc";
                    
                    PreparedStatement pstmt = conn.prepareStatement(sqlSelect);
                    ResultSet cursor = pstmt.executeQuery(); //hay que dejarlo en blanco porque sino da "not implemented by SQLite JDBC driver"
                    while(cursor.next()) {
                        String unaEntrada = cursor.getInt("id") + ",";
                        unaEntrada += cursor.getString("titulo") + ",";
                        unaEntrada += cursor.getString("texto") + ",";
                        unaEntrada += cursor.getString("fecha");

                        entradas.add(unaEntrada);
                    }

                    // Se cierra la conexión con la base de datos
                    conn.close();
                }
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
            }
        }
        return entradas;
    }

    public ArrayList<String> buscarEntradaPorId(String id){
        Connection conn = null;
        ArrayList<String> entrada = new ArrayList<String>();
        //HashMap<Integer, String> entradas = new HashMap<Integer, String>();
        try {
            // Ruta a la base de datos. El archivo "base_datos.db".
            // Se puede indicar una ruta completa del tipo /home/usuario/... 
            String url = "jdbc:sqlite:base_datos.db";
            // Se crea la conexión a la base de datos:
		    Class.forName("org.sqlite.JDBC").getDeclaredConstructor().newInstance();
            conn = DriverManager.getConnection(url);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    
                    // Se hace una consulta                   
                    String sqlSelect = "SELECT id, titulo, texto, fecha FROM entradas " +
                        "where id = " + id;
                    
                    PreparedStatement pstmt = conn.prepareStatement(sqlSelect);
                    ResultSet cursor = pstmt.executeQuery(); //hay que dejarlo en blanco porque sino da "not implemented by SQLite JDBC driver"
                    while(cursor.next()) {
                        entrada.add(cursor.getInt("id") + "");
                        entrada.add(cursor.getString("titulo"));
                        entrada.add(cursor.getString("texto"));
                        entrada.add(cursor.getString("fecha"));
                    }

                    // Se cierra la conexión con la base de datos
                    conn.close();
                }
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
            }
        }
        return entrada;
    }

    public void borrarEntrada(String id){
        Connection conn = null;
        try {
            // Ruta a la base de datos. El archivo "base_datos.db".
            // Se puede indicar una ruta completa del tipo /home/usuario/... 
            String url = "jdbc:sqlite:base_datos.db";
            // Se crea la conexión a la base de datos:
		    Class.forName("org.sqlite.JDBC").getDeclaredConstructor().newInstance();
            conn = DriverManager.getConnection(url);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    
                    // Se hace una consulta                   
                    String sqlSelect = "DELETE FROM entradas " +
                        "where id like " + id;
                    
                    PreparedStatement pstmt = conn.prepareStatement(sqlSelect);
                    pstmt.executeUpdate(); //tiene que ser execute update sino no borra
                            
                    // Se cierra la conexión con la base de datos
                    conn.close();
                }
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
            }
        }
    }

    //geeksforgeeks.org/md5-hash-in-java/
    public String m5Contra(String contra){
        String c = "";

        try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(contra.getBytes());
            BigInteger no = new BigInteger(1,messageDigest);
            c = no.toString(16);
            while(c.length() < 32){
                c = "0" + c;
            }
        }catch(NoSuchAlgorithmException e){
            c="aaa";
        }

        return c;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Se escribe la página con la respuesta al usuario
        PrintWriter out = resp.getWriter();
        //out.println(PantillasHTML.prueba);
    
    }
}