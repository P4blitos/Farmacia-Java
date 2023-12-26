
package models;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JOptionPane;

//metodos java con mysql
public class EmployeesDao {
    //instancia la conexion
    ConnectionMySQL cn= new ConnectionMySQL();
    Connection conn;
    //sirve para las consultas
    PreparedStatement pst;
    //obtener datos de la consulta
    ResultSet rs;
    
    //variables para enviar datos entre interfaces
    public static int id_user=0;
    public static String full_name_user="";
    public static String username_user="";
    public static String address_user="";
    public static String rol_user="";
    public static String email_user="";
    public static String telephone_user="";
    
    //metodo del login
    public Employees loginQuery(String user,String password){
        String Query="SELECT * FROM employees WHERE username = ? AND password = ?";
        Employees employee = new Employees();
        try{
            conn= cn.getConnection();
            pst= conn.prepareStatement(Query);
            //enviar parametros
            pst.setString(1,user);
            pst.setString(2,password);
            rs= pst.executeQuery();
            
            if(rs.next()){
                employee.setId(rs.getInt("id"));
                id_user= employee.getId();
                employee.setFull_name(rs.getString("full_name"));
                full_name_user= employee.getFull_name();
                employee.setUsername(rs.getString("username"));
                username_user= employee.getUsername();
                employee.setAddress(rs.getString("address"));
                address_user= employee.getAddress();
                employee.setTelephone(rs.getString("telephone"));
                telephone_user= employee.getTelephone();
                employee.setEmail(rs.getString("email"));
                email_user= employee.getEmail();
                employee.setRol(rs.getString("rol"));
                rol_user= employee.getRol();
            }
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,"Error al obtener al empleado "+ e);
        }
        return employee;
    }
}
