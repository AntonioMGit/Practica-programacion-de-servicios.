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

    public StringBuffer loguear(String usr, String contra){ //encriptar contrasenia?
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
                    // Se insertan los datos de los usuarios
                    String sqlInsert = "INSERT INTO entradas(titulo, texto, fecha) VALUES(?,?,?)";
                    PreparedStatement pstmt = conn.prepareStatement(sqlInsert);
                    pstmt.setString(1, titulo);
                    pstmt.setString(2, texto);
                    pstmt.setString(3, fecha);
                    pstmt.executeUpdate();

                    // Se cierra la conexión con la base de datos
                    conn.close();
                }
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
            }
        }
    }

    public ArrayList<String> buscarEntradas(){ //encriptar contrasenia?
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
                    String sqlSelect = "SELECT titulo, texto, fecha FROM entradas " +
                        "order by fecha";
                    
                    PreparedStatement pstmt = conn.prepareStatement(sqlSelect);
                    ResultSet cursor = pstmt.executeQuery(); //hay que dejarlo en blanco porque sino da "not implemented by SQLite JDBC driver"
                    while(cursor.next()) {
                        //entradas.add(cursor.getInt("id"));
                        entradas.add(cursor.getString("titulo"));
                        entradas.add(cursor.getString("texto"));
                        entradas.add(cursor.getString("fecha"));
                        /*
                        respuesta.append(cursor.getString("titulo"));
                        respuesta.append(", ");
                        respuesta.append(cursor.getString("texto"));
                        respuesta.append(", ");
                        respuesta.append(cursor.getString("fecha"));
                        respuesta.append("<br>");

                        entradas.put(cursor.getInt("id"), respuesta.toString());
                        */
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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Se escribe la página con la respuesta al usuario
        PrintWriter out = resp.getWriter();
        //out.println(PantillasHTML.prueba);
    
    }
    /*
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
                    // Se crea la tabla si no existe
                    String sql = "CREATE TABLE IF NOT EXISTS usuarios (\n"
                        + "usuario TEXT PRIMARY KEY,\n"
                        + "email TEXT\n"
                        + ");";
                    Statement stmt = conn.createStatement();
                    stmt.execute(sql);

                    // Se insertan los datos
                    String sqlInsert = "INSERT INTO usuarios(usuario, email) VALUES(?,?)";
                    PreparedStatement pstmt = conn.prepareStatement(sqlInsert);
                    String usuario = req.getParameter("usuario");
                    String email = req.getParameter("email");
                    pstmt.setString(1, usuario);
                    pstmt.setString(2, email);
                    pstmt.executeUpdate();

                    // Se hace una consulta
                    String sqlSelect = "SELECT usuario, email FROM usuarios";
                    ResultSet cursor = stmt.executeQuery(sqlSelect);
                    while(cursor.next()) {
                        // Se construye la respuesta que se insertará en el HTML
                        respuesta.append(cursor.getString("usuario"));
                        respuesta.append(" ");
                        respuesta.append(cursor.getString("email"));
                        respuesta.append("<br/>");
                    }

                    // Se cierra la conexión con la base de datos
                    conn.close();
                }
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
            }
        }

        // Se escribe la página con la respuesta al usuario
        PrintWriter out = resp.getWriter();
        out.println("<html>");
        out.println("<body>");
        out.println("Usuarios:<br/>");
        out.println(respuesta.toString());
        out.println("</body>");
        out.println("</html>");
    }
    */
}