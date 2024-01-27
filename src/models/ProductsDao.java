
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

public class ProductsDao {
    //instancia la conexion
    ConnectionMySQL cn= new ConnectionMySQL();
    Connection conn;
    //sirve para las consultas
    PreparedStatement pst;
    //obtener datos de la consulta
    ResultSet rs;
    
    //registrar producto
    public boolean registerProductQuery(Products product){
        String Query= "INSERT INTO products(code, name, description, unit_price, created, updated, category_id)"
                 + "VALUES(?,?,?,?,?,?,?)";
        Timestamp datetime= new Timestamp(new Date().getTime());
        try{
            conn=cn.getConnection();
            pst= conn.prepareStatement(Query);
            pst.setInt(1, product.getCode());
            pst.setString(2, product.getName());
            pst.setString(3, product.getDescription());
            pst.setDouble(4, product.getUnit_price());
            pst.setTimestamp(5, datetime);
            pst.setTimestamp(6, datetime);
            pst.setInt(7, product.getCategory_id());
            pst.execute();
            return true;
            
        }catch(SQLException e){
             JOptionPane.showMessageDialog(null,"Error al registrar el producto: "+ e);
            return false;
        }
    }
    
    //listar productos
    public List listProductsQuery(String value){
        List<Products> list_products= new ArrayList();
        String Query= "SELECT pro.*, ca.name AS category_name FROM products pro, categories ca WHERE pro.category_id = ca.id";
        String Query_search_product="SELECT pro.*, ca.name AS category_name FROM products pro INNER JOIN categories ca"
                + "ON pro.category_id = ca.id WHERE pro.name LIKE '%"+ value +"%'";
        
        try{
            conn= cn.getConnection();
            if(value.equalsIgnoreCase("")){
                pst= conn.prepareStatement(Query);
                rs= pst.executeQuery();
            }else{
                pst= conn.prepareStatement(Query_search_product);
                rs= pst.executeQuery();
            }
            while(rs.next()){
                Products product= new Products();
                product.setId(rs.getInt("id"));
                product.setCode(rs.getInt("code"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setUnit_price(rs.getDouble("unit_price"));
                product.setProduct_quantity(rs.getInt("product_quantity"));
                product.setCategory_name(rs.getString("category_name"));
                list_products.add(product);
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,e.toString());
        }
        return list_products;   
    }
    
    // Modificar producto
    public boolean updateProductQuery(Products product){
        String Query= "UPDATE products SET code= ?, name= ?, description= ?, unit_price= ?, updated= ?, "
                + "category_id= ? WHERE id = ?";
        Timestamp datetime= new Timestamp(new Date().getTime());
        try{
            conn=cn.getConnection();
            pst= conn.prepareStatement(Query);
            pst.setInt(1, product.getCode());
            pst.setString(2, product.getName());
            pst.setString(3, product.getDescription());
            pst.setDouble(4, product.getUnit_price());
            pst.setTimestamp(5, datetime);
            pst.setInt(6, product.getCategory_id());
            pst.setInt(7, product.getId());
            pst.execute();
            return true;
            
        }catch(SQLException e){
             JOptionPane.showMessageDialog(null,"Error al modificar los datos del producto: "+ e);
            return false;
        }
    }
    
    //eliminar producto
    public boolean deleteProductQuery(int id){
        String Query="DELETE FROM products WHERE id= "+ id;
        try{
            conn=cn.getConnection();
            pst= conn.prepareStatement(Query);
            pst.execute();
            return true;
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,"No puede eliminar un producto que tenga relacion con otra tabla: ");
            return false;
        }
    }
    
    //buscar producto
    //la idea es presionar el id y que se rellene en el SystemView
    public Products searchProduct(int id){
        String Query="SELECT pro.*, ca.name AS category_name FROM products pro"
                + "INNER JOIN categories ca ON pro.category_id = ca.id WHERE pro.id = ?";
        Products product = new Products();
        try{
            conn=cn.getConnection();
            pst= conn.prepareStatement(Query);
            pst.setInt(1, id);
            rs= pst.executeQuery();
            
            if(rs.next()){
                product.setId(rs.getInt("id"));
                product.setCode(rs.getInt("code"));
                product.setName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setUnit_price(rs.getDouble("unit_price"));
                product.setCategory_id(rs.getInt("category_id"));
                product.setCategory_name(rs.getString("category_name"));
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,e.getMessage());
        }
        return product;
    }
    
    //buscar producto por codigo
    public Products searchCode(int code){
        String Query="SELECT pro.id, pro.name FROM products pro WHERE code = ?";
        Products product = new Products();
        try{
            conn=cn.getConnection();
            pst= conn.prepareStatement(Query);
            pst.setInt(1, code);
            rs= pst.executeQuery();
            
            if(rs.next()){
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,e.getMessage());
        }
        return product;
    }
    
    //traer la cantidad de productos
    public Products searchId(int id){
        String Query= "SELECT pro.product_quantity FROM products pro WHERE pro.id= ?";
        Products product = new Products();
        try{
            conn=cn.getConnection();
            pst= conn.prepareStatement(Query);
            pst.setInt(1, id);
            rs= pst.executeQuery();
            
            if(rs.next()){
                product.setProduct_quantity(rs.getInt("product_quantity"));
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,e.getMessage());
        }
        return product;
    }
    
    //actualizar stock
    public boolean updateStockQuery(int amount, int product_id){
        String Query= "UPDATE products SET product_quantity = ? WHERE id= ?";
        
        try{
            conn=cn.getConnection();
            pst= conn.prepareStatement(Query);
            pst.setInt(1, amount);
            pst.setInt(2, product_id);
            pst.execute();
            return true;
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,e.getMessage());
            return false;
        }
    }
}
