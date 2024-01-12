
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

public class SuppliersDao {
    //instancia la conexion
    ConnectionMySQL cn= new ConnectionMySQL();
    Connection conn;
    //sirve para las consultas
    PreparedStatement pst;
    //obtener datos de la consulta
    ResultSet rs;
    
    //registrar proveedor
    public boolean registerSupplierQuery(Suppliers supplier){
        String Query= "INSERT INTO suppliers (name, description, address,telephone, email, city, created, updated)"
                +"VALUES(?,?,?,?,?,?,?,?)";
        Timestamp datetime= new Timestamp(new Date().getTime());
        try{
            conn=cn.getConnection();
            pst= conn.prepareStatement(Query);
            pst.setString(1, supplier.getName());
            pst.setString(2, supplier.getDescription());
            pst.setString(3, supplier.getAddress());
            pst.setString(4, supplier.getTelephone());
            pst.setString(5, supplier.getEmail());
            pst.setString(6, supplier.getCity());
            pst.setTimestamp(7, datetime);
            pst.setTimestamp(8, datetime);
            pst.execute();
            return true;
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,"Error al registrar al proveedor "+ e);
            return false;
        }
    }
    //listar proveedores
    public List listSuppliersQuery(String value){
        List<Suppliers> list_suppliers= new ArrayList();
        String Query="SELECT * FROM suppliers";
        String Query_search_supplier="SELECT * FROM suppliers WHERE name LIKE '%"+ value +"%'";
        
        try{
            conn= cn.getConnection();
            if(value.equalsIgnoreCase("")){
                pst= conn.prepareStatement(Query);
                rs= pst.executeQuery();
            }else{
                pst= conn.prepareStatement(Query_search_supplier);
                rs= pst.executeQuery();
            }
            
            while(rs.next()){
                Suppliers supplier = new Suppliers();
                supplier.setId(rs.getInt("id"));
                supplier.setName(rs.getString("name"));
                supplier.setDescription(rs.getString("description"));
                supplier.setAddress(rs.getString("address"));
                supplier.setTelephone(rs.getString("telephone"));
                supplier.setEmail(rs.getString("email"));
                supplier.setCity(rs.getString("city"));
                
                list_suppliers.add(supplier);
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,e.getMessage());
           
        }
        return list_suppliers;
    }
    
    //modificar proveedor
    
    public boolean updateSupplierQuery(Suppliers supplier){
        String Query= "UPDATE suppliers SET name= ?, description= ?, address= ?,telephone= ?, email= ?, city= ?,updated= ?"
                +"WHERE id= ?";
        
        Timestamp datetime= new Timestamp(new Date().getTime());
        try{
            conn=cn.getConnection();
            pst= conn.prepareStatement(Query);
            pst.setString(1, supplier.getName());
            pst.setString(2, supplier.getDescription());
            pst.setString(3, supplier.getAddress());
            pst.setString(4, supplier.getTelephone());
            pst.setString(5, supplier.getEmail());
            pst.setString(6, supplier.getCity());
            pst.setTimestamp(7, datetime);
            pst.setInt(8, supplier.getId());
            pst.execute();
            return true;
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,"Error al modificar los datos al proveedor "+ e);
            return false;
        }
    }
    
    //eliminar proveedor
    public boolean deleteSupplierQuery(int id){
        String Query="DELETE FROM suppliers WHERE id= "+ id;
        try{
            conn=cn.getConnection();
            pst= conn.prepareStatement(Query);
            pst.execute();
            return true;
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,"No puede eliminar un proveedor que tenga relacion con otra tabla: ");
            return false;
        }
    }
}
