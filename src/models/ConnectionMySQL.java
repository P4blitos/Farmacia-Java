package models;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionMySQL {
    private String database_name="pharmacy_database";
    private String user ="root";
    private String password= "Pablolorena2002";
    private String url="jdbc:mysql://localhost:3306/"+ database_name;
    
    Connection conn = null;
    
    public Connection getConnection() throws SQLException{
        try{
            //obtener valor del driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            //obtener la conexion
            conn= DriverManager.getConnection(url,user,password);
        }catch(ClassNotFoundException e){
        
            System.err.println("Ha ocurrido ClassNotFoundException "+ e.getMessage());
        }catch(SQLException e){
            System.err.println("Ha ocurrido SQLException: "+ e.getMessage());
        }
        return conn;
    }
}
