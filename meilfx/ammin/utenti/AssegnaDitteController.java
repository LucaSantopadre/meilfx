/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meilfx.ammin.utenti;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import meilfx.ammin.AmministratoreController;
import static meilfx.ammin.utenti.sql.UtentiSql.*;

/**
 * FXML Controller class
 *
 * @author luca
 */
public class AssegnaDitteController implements Initializable {

    @FXML
    private Label lblTopCentro;
    @FXML
    private Label lblTopDestra;
    @FXML
    private TreeView<String> albero;
    
    TreeItem<String> root = new TreeItem<>("Utenti");
    TreeItem<String> selectedItem;
    
    

    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        caricaAlbero();
        
        // menu item!
        
        
        
    }    
    
    
    
    public void caricaAlbero(){
        
        albero.setRoot(root);
        root.setExpanded(true);
        
        List<String> menu;
        menu= getUtenti();
        addToAlbero(root, menu);
        
           
        //*** LISTENER dell'albero
        albero.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                selectedItem = (TreeItem<String>) newValue;     
                
            }
        });
        
        
        
        //*** DOPPIO CLICK dell'elemento dell'albero
        albero.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount() == 2) {             
                    
                    System.out.println("Selected Text : " + selectedItem.getValue());
                    
                    
                }
            }
        });
        
        
    }
        
    public void addToAlbero(TreeItem<String> nodo, List<String> listaMasCorrenti) {
        int i;
        for (i = 0; i < listaMasCorrenti.size(); i++) {
            nodo.getChildren().add(eseguiFiglo(listaMasCorrenti.get(i), nodo));
        }
    }

    public TreeItem<String> eseguiFiglo(String mastro, TreeItem<String> parent) {
        TreeItem<String> foglia = new TreeItem<>();
        foglia.setValue(mastro);

        return foglia;
    }
    
}
