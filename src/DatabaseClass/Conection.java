/*
*Team n7
*Database Hospital
*/
package DatabaseClass;
import java.awt.HeadlessException;
import java.sql.*; 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;
/*
 *Clase necesaria para instaurar la conexión a la BD realizada
 *Este paquete solamente contiene la conexión de la Base de datos
*/
public class Conection {
    Connection conn;//Maneja la conexión de la DB
    private  String driver = "com.mysql.cj.jdbc.Driver";//Carga del driver
    private  String user = "root";
    private  String password = "STR27z";
    private  String url = "jdbc:mysql://localhost:3306/hospital?useTimezone=true&serverTimezone=UTC";
    //Importante el url genera la conexion a la base de datos a usar, se define además una zona horaria
    Statement st;

    public Conection() {//genera la conexion a la BD
        try{
            conn = null;
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, password);
            if(conn != null)
            {
                JOptionPane.showMessageDialog(null, "Conection succesfull");
            }   
            
        }
        catch(HeadlessException | ClassNotFoundException | SQLException e)
        {
            JOptionPane.showMessageDialog(null, "Error: "+e);
        }

    }
    public Connection getConnection()
    {
        return conn;
    }
    public void disconection()
    {
        conn = null;
        if(conn == null)
            JOptionPane.showMessageDialog(null, "Conection finished");
    }
    Statement createStatement()
    {
        throw new UnsupportedOperationException("Not Suported");
    }
    
}
