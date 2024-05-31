
package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import models.Employees;
import models.EmployeesDao;
import static models.EmployeesDao.id_user;
import static models.EmployeesDao.rol_user;
import views.SystemView;

public class EmployeesController implements ActionListener, MouseListener, KeyListener{
    private Employees employee;
    private EmployeesDao employeeDao;
    private SystemView views;
    //Rol
    String rol= rol_user;
    //Sirve para manipular la tabla
    DefaultTableModel model = new DefaultTableModel();
    
    //Constructor
    public EmployeesController(Employees employee, EmployeesDao employeeDao, SystemView views) {
        this.employee = employee;
        this.employeeDao = employeeDao;
        this.views = views;
        //Boton registar empleado
        this.views.btn_register_employee.addActionListener(this);
        //boton de modificar empleado
        this.views.btn_update_employee.addActionListener(this);
        //boton de eliminar empleado.
        this.views.btn_delete_employee.addActionListener(this);
        //Boton de cancelar
        this.views.btn_cancel_employee.addActionListener(this);
        
        this.views.employees_table.addMouseListener(this);
        this.views.txt_search_employee.addKeyListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()== views.btn_register_employee){
            //Verificar si los campos no estan vacios
            if(views.txt_employee_id.getText().equals("")
                    || views.txt_employee_fullname.getText().equals("")
                    || views.txt_employee_username.getText().equals("")
                    || views.txt_employee_address.getText().equals("")
                    || views.txt_employee_telephone.getText().equals("")
                    || views.txt_employee_email.getText().equals("")
                    || views.cmb_rol.getSelectedItem().toString().equals("")
                    || String.valueOf(views.txt_employee_password.getPassword()).equals("")){
                JOptionPane.showMessageDialog(null,"Todos los campos son obligatorios");
            }else{
                //Realizar la insercion, trim es para no traer espacios en blanco
                employee.setId(Integer.parseInt(views.txt_employee_id.getText().trim()));
                employee.setFull_name(views.txt_employee_fullname.getText().trim());
                employee.setUsername(views.txt_employee_username.getText().trim());
                employee.setAddress(views.txt_employee_address.getText().trim());
                employee.setTelephone(views.txt_employee_telephone.getText().trim());
                employee.setEmail(views.txt_employee_email.getText().trim());
                employee.setPassword(String.valueOf(views.txt_employee_password.getPassword()));
                employee.setRol(views.cmb_rol.getSelectedItem().toString());
                
                if(employeeDao.registerEmployeeQuery(employee)){
                    cleanTable();
                    cleanFields();
                    listAllEmployees();
                    JOptionPane.showMessageDialog(null,"Empleado registrado con exito");
                }else{
                    JOptionPane.showMessageDialog(null,"Ha ocurrido un error al registrar un empleado");
                }
            }
        }else if(e.getSource()== views.btn_update_employee){
            //verificar que una persona a seleccionado una fila.
            //si esta vacio entonces
            if(views.txt_employee_id.equals("")){
                JOptionPane.showMessageDialog(null,"Selecciona una fila para continuar");
            }else{
                //verificar si los campos estan vacios
                if(views.txt_employee_id.getText().equals("")
                   || views.txt_employee_fullname.getText().equals("")
                   || views.cmb_rol.getSelectedItem().toString().equals("")){
                    
                   JOptionPane.showMessageDialog(null,"Todos los campos son obligatorios"); 
                
                }else{
                   employee.setId(Integer.parseInt(views.txt_employee_id.getText().trim()));
                   employee.setFull_name(views.txt_employee_fullname.getText().trim());
                   employee.setUsername(views.txt_employee_username.getText().trim());
                   employee.setAddress(views.txt_employee_address.getText().trim());
                   employee.setTelephone(views.txt_employee_telephone.getText().trim());
                   employee.setEmail(views.txt_employee_email.getText().trim());
                   employee.setPassword(String.valueOf(views.txt_employee_password.getPassword()));
                   employee.setRol(views.cmb_rol.getSelectedItem().toString());
                   
                   if(employeeDao.updateEmployeeQuery(employee)){
                       cleanTable();
                       cleanFields();
                       listAllEmployees();
                       views.btn_register_employee.setEnabled(true);
                       JOptionPane.showMessageDialog(null,"Empleado se ha actualizado con exito");
                   }else{
                       JOptionPane.showMessageDialog(null,"Ha ocurrido un error al modificar un empleado");
                   }
                }
            }
        }else if(e.getSource()== views.btn_delete_employee){
            //la fila seleccionada por el usuario
            int row= views.employees_table.getSelectedRow();
            //Si el usuario no selecciona ninguna fila, los valores seran -1
            if(row == -1){
                JOptionPane.showMessageDialog(null,"Debes seleccionar un empleado para eliminar");
                //Asegurarnos de que no se elimino asi mismo
            }else if(views.employees_table.getValueAt(row, 0).equals(id_user)){
                JOptionPane.showMessageDialog(null,"No puedes elimnar al usuario en uso");
            }else{
                int id= Integer.parseInt(views.employees_table.getValueAt(row, 0).toString());
                int question= JOptionPane.showConfirmDialog(null, "Estas seguro de eliminar el empleado?");
                
                if(question == 0 && employeeDao.deleteEmployeeQuery(id)!= false){
                    cleanFields();
                    cleanTable();
                    views.btn_register_employee.setEnabled(true);
                    views.txt_employee_password.setEnabled(true);
                    listAllEmployees();
                    JOptionPane.showMessageDialog(null, "Empleado eliminado con exito");
                }
            }
        }else if(e.getSource()== views.btn_cancel_employee){
            cleanFields();
            views.btn_register_employee.setEnabled(true);
            views.txt_employee_password.setEnabled(true);
            views.txt_employee_id.setEnabled(true);
        }
    }
    
    //Listar todos los empleados
    public void listAllEmployees(){
        if(rol.equals("Administrador")){
            List<Employees> list = employeeDao.listEmployeesQuery(views.txt_search_employee.getText());
            model= (DefaultTableModel) views.employees_table.getModel();
            //son 7 columnas
            Object[] row = new Object[7];
            // el .size, nos dice la cantidad de filas
            for(int i=0; i < list.size(); i++){
                //el 0 es la primera columna
                row[0]= list.get(i).getId();
                row[1]= list.get(i).getFull_name();
                row[2]= list.get(i).getUsername();
                row[3]= list.get(i).getAddress();
                row[4]= list.get(i).getTelephone();
                row[5]= list.get(i).getEmail();
                row[6]= list.get(i).getRol();
                model.addRow(row);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource()== views.employees_table){
            //esto sirve para saber en fila esta parado
            int row = views.employees_table.rowAtPoint(e.getPoint());
            //enviar texto a los textbox
            views.txt_employee_id.setText(views.employees_table.getValueAt(row, 0).toString());
            views.txt_employee_fullname.setText(views.employees_table.getValueAt(row, 1).toString());
            views.txt_employee_username.setText(views.employees_table.getValueAt(row, 2).toString());
            views.txt_employee_address.setText(views.employees_table.getValueAt(row, 3).toString());
            views.txt_employee_telephone.setText(views.employees_table.getValueAt(row, 4).toString());
            views.txt_employee_email.setText(views.employees_table.getValueAt(row, 5).toString());
            views.cmb_rol.setSelectedItem(views.employees_table.getValueAt(row, 6).toString());
            
            //deshabilitar
            views.txt_employee_id.setEditable(false);
            views.txt_employee_password.setEnabled(false);
            views.btn_register_employee.setEnabled(false);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
       
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
       if(e.getSource()== views.txt_search_employee){
           cleanTable();
           listAllEmployees();
       }
    }
    //limpiar campos
    public void cleanFields(){
        views.txt_employee_id.setText("");
        views.txt_employee_id.setEditable(true);
        views.txt_employee_fullname.setText("");
        views.txt_employee_username.setText("");
        views.txt_employee_address.setText("");
        views.txt_employee_telephone.setText("");
        views.txt_employee_email.setText("");
        views.txt_employee_password.setText("");
        views.cmb_rol.setSelectedIndex(0);
    }
    
    public void cleanTable(){
        for(int i=0; i < model.getRowCount(); i++){
            model.removeRow(i);
            i=i-1;
        }
    }
}
