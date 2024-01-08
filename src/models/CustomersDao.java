
package models;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;

public class CustomersDao {
    //instancia la conexion
    ConnectionMySQL cn= new ConnectionMySQL();
    Connection conn;
    //sirve para las consultas
    PreparedStatement pst;
    //obtener datos de la consulta
    ResultSet rs;
    
    //Registrar cliente
    public boolean registerCustomerQuery(Customers customer){
        String Query="INSERT INTO customers (id, full_name, address, telephone, email, created, updated)"
                +"VALUES(?,?,?,?,?,?,?)";
        Timestamp datetime= new Timestamp(new Date().getTime());
        
        try{
            conn=cn.getConnection();
            pst= conn.prepareStatement(Query);
            pst.setInt(1, customer.getId());
            pst.setString(2,customer.getFull_name() );
            pst.setString(3,customer.getAddress());
            pst.setString(4,customer.getTelephone());
            pst.setString(5,customer.getEmail());
            pst.setTimestamp(6, datetime);
            pst.setTimestamp(7, datetime);
            pst.execute();
            return true;
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,"Error al registrar al cliente "+ e);
            return false;
        }
    }
    //Listar clientes.
    public List listCustomerQuery(String value){
        List<Customers> list_customers= new ArrayList();
        String Query="SELECT * FROM customers";
        String Query_search_customer="SELECT * FROM customers WHERE id LIKE '%"+ value +"%'";
        
        try{
            conn= cn.getConnection();
            if(value.equalsIgnoreCase("")){
                pst= conn.prepareStatement(Query);
                rs= pst.executeQuery();
            }else{
                pst= conn.prepareStatement(Query_search_customer);
                rs= pst.executeQuery();
            }
            
            while(rs.next()){
                Customers customer = new Customers();
                customer.setId(rs.getInt("id"));
                customer.setFull_name(rs.getString("full_name"));
                customer.setAddress(rs.getString("address"));
                customer.setTelephone(rs.getString("telephone"));
                customer.setEmail(rs.getString("email"));
                list_customers.add(customer);
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,e.toString());
           
        }
        return list_customers;
    }
    
    //modificar cliente
     public boolean updateCustomerQuery(Customers customer){
        String Query="UPDATE customers  SET full_name= ?, address= ?, telephone= ?, email= ?, updated= ? WHERE id= ?";
        Timestamp datetime= new Timestamp(new Date().getTime());
        
        try{
            conn=cn.getConnection();
            pst= conn.prepareStatement(Query);  
            pst.setString(1,customer.getFull_name() );
            pst.setString(2,customer.getAddress());
            pst.setString(3,customer.getTelephone());
            pst.setString(4,customer.getEmail());
            pst.setTimestamp(5, datetime);
            pst.setInt(6, customer.getId());
            pst.execute();
            return true;
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,"Error al modificar los datos al cliente "+ e);
            return false;
        }
    }
     
    //eliminar cliente
     public boolean deleteCustomerQuery(int id){
        String Query="DELETE FROM customers WHERE id= "+ id;
        try{
            conn=cn.getConnection();
            pst= conn.prepareStatement(Query);
            pst.execute();
            return true;
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,"No puede eliminar un cliente que tenga relacion con otra tabla: ");
            return false;
        }
    }
    
}
