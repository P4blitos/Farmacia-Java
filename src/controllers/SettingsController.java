package controllers;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import views.SystemView;

public class SettingsController implements MouseListener{
    
    private SystemView views;
    //constructor
    public SettingsController(SystemView views){
        this.views= views;
        this.views.jLabelProducts.addMouseListener(this);
        this.views.jLabelPurchases.addMouseListener(this);
        this.views.jLabelCustomers.addMouseListener(this);
        this.views.jLabelEmployees.addMouseListener(this);
        this.views.jLabelSuppliers.addMouseListener(this);
        this.views.jLabelCategories.addMouseListener(this);
        this.views.jLabelReports.addMouseListener(this);
        this.views.jLabelSettings.addMouseListener(this);
        this.views.jLabelSales.addMouseListener(this);
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }
    //Metodo de estar encima del label
    @Override
    public void mouseEntered(MouseEvent e) {
        if(e.getSource()== views.jLabelProducts){
            views.JpanelProducts.setBackground(new Color(200,67,63));
        }else if(e.getSource()== views.jLabelPurchases){
            views.jPanelPurchases.setBackground(new Color(200,67,63));
        }else if(e.getSource()==views.jLabelCustomers){
            views.jPanelCustomers.setBackground(new Color(200,67,63));
        }else if(e.getSource()== views.jLabelEmployees){
            views.jPanelEmployees.setBackground(new Color(200,67,63));
        }else if(e.getSource()== views.jLabelSuppliers){
            views.jPanelSuppliers.setBackground(new Color(200,67,63));
        }else if(e.getSource()== views.jLabelCategories){
            views.jPanelCategories.setBackground(new Color(200,67,63));
        }else if(e.getSource()== views.jLabelReports){
            views.jPanelReports.setBackground(new Color(200,67,63));
        }else if(e.getSource()== views.jLabelSettings){
            views.jPanelSettings.setBackground(new Color(200,67,63));
        }else if(e.getSource()== views.jLabelSales){
            views.jPanelSales.setBackground(new Color(200,67,63));
        }    
    }
    
    //metodo de salida del Label
    @Override
    public void mouseExited(MouseEvent e) {
         if(e.getSource()== views.jLabelProducts){
            views.JpanelProducts.setBackground(new Color(34,121,173));
        }else if(e.getSource()== views.jLabelPurchases){
            views.jPanelPurchases.setBackground(new Color(34,121,173));
        }else if(e.getSource()==views.jLabelCustomers){
            views.jPanelCustomers.setBackground(new Color(34,121,173));
        }else if(e.getSource()== views.jLabelEmployees){
            views.jPanelEmployees.setBackground(new Color(34,121,173));
        }else if(e.getSource()== views.jLabelSuppliers){
            views.jPanelSuppliers.setBackground(new Color(34,121,173));
        }else if(e.getSource()== views.jLabelCategories){
            views.jPanelCategories.setBackground(new Color(34,121,173));
        }else if(e.getSource()== views.jLabelReports){
            views.jPanelReports.setBackground(new Color(34,121,173));
        }else if(e.getSource()== views.jLabelSettings){
            views.jPanelSettings.setBackground(new Color(34,121,173));
        }else if(e.getSource()== views.jLabelSales){
            views.jPanelSales.setBackground(new Color(34,121,173));
        }

    }
    
}
