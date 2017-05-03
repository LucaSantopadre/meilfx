/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meilfx.ammin;

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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import meilfx.login.LoginController;
import meilfx.pub.Bottone;

/**
 * FXML Controller class
 *
 * @author luca
 */
public class AmministratoreController implements Initializable {

    @FXML
    private Label lblTopCentro;
    @FXML
    private Label lblTopDestra;
    
    @FXML
    private TreeView albero;
    private TreeItem<String> root = new TreeItem<>("amministratore");
    private TreeItem<String> selectedItem = new TreeItem<>();
    
    @FXML
    private Button btnStampa;
    @FXML
    private Button btnRegistra;
    @FXML// da inserire nel file fxml!!!
    Bottone btnEsci = new Bottone() ; // con metodo esteso per uscire

    String tipoAccesso = LoginController.Login.getTipoAccesso();
    int idUtente = LoginController.Login.getIdUtente();
    String emailUtente = LoginController.Login.getEmail();
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO  
        lblTopCentro.setText("amministratore");
        lblTopDestra.setText(idUtente+"_"+emailUtente);
        
        caricaAlbero();

    }    
    
    
    public void caricaAlbero(){
        albero.setRoot(root);
        root.setExpanded(true);
        
        List<String> menu = new ArrayList<>();
        menu.add("utenti");  //0 
        menu.add("backup"); //1
        addToAlbero(root , menu);
        
        menu.clear();
        menu.add("crea utente"); //0.0
        menu.add("assegna ditte"); //0.1
        addToAlbero(root.getChildren().get(0) , menu);
        
           
        //*** LISTENER dell'albero
        albero.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                selectedItem = (TreeItem<String>) newValue;

                if (oldValue.equals(newValue)) {

                    try {
                        System.out.println("Selected Text : " + selectedItem.getValue());

                        switch (selectedItem.getValue()) {
                            case "assegna ditte":
                                Stage stage = (Stage) albero.getScene().getWindow();
                                Parent node = FXMLLoader.load(getClass().getResource("/meilfx/ammin/utenti/AssegnaDitte.fxml"));
                                Scene scene = new Scene(node);
                                stage.setScene(scene);
                                stage.setMaximized(true);
                                stage.show();

                        }
                    } catch (IOException ex) {
                        Logger.getLogger(AmministratoreController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    // }

                }

            }
        });
        
        
        
        //*** DOPPIO CLICK dell'elemento dell'albero
        albero.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                //if (mouseEvent.getClickCount() == 2) {             
                    try {
                        System.out.println("Selected Text : " + selectedItem.getValue());
                        
                        switch(selectedItem.getValue()){
                            case "assegna ditte" :
                                Stage stage = (Stage) albero.getScene().getWindow();
                                Parent node = FXMLLoader.load(getClass().getResource("/meilfx/ammin/utenti/AssegnaDitte.fxml"));
                                Scene scene = new Scene(node);
                                stage.setScene(scene);
                                stage.setMaximized(true);
                                stage.show();
                                
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(AmministratoreController.class.getName()).log(Level.SEVERE, null, ex);
                    }
               // }
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
    
    
    public void btnEsciAction() throws IOException{
        btnEsci.btnEsciAction(btnStampa);
    }
    
}
