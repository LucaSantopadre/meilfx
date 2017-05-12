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
import javafx.scene.input.KeyCode;
import static javafx.scene.input.KeyCode.F2;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import meilfx.elencoditte.ElencoDitteController;
import meilfx.login.LoginController;
import static meilfx.panels.Albero.addToAlbero;
import meilfx.panels.Schermo;
import static meilfx.panels.Schermo.SCENE;
import static meilfx.panels.Schermo.STAGE;
import meilfx.pub.Bottone;

/**
 * FXML Controller class
 *
 * @author luca
 */
public class AmministratoreController implements Initializable {

    @FXML
    private Label lblTopSinistra;
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
    @FXML
    private Button btnIndietro;
    @FXML// da inserire nel file fxml!!!
    Bottone btnEsci = new Bottone(); // con metodo esteso per uscire

    int idUtente = LoginController.Login.getIdUtente();
    String emailUtente = LoginController.Login.getEmail();
    String tipoAccesso = LoginController.Login.getTipoAccesso();
    
    
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //label
        lblTopSinistra.setText(idUtente + "_" + emailUtente);
        lblTopCentro.setText(tipoAccesso);
        lblTopDestra.setText("AmministratoreController");

        //carico a video l'albero
        caricaAlbero();
        
        /*
        
        //**-- event handler della scene **--
        //ESC
        SCENE.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
              if(key.getCode()==KeyCode.ESCAPE) {
                  try {
                      System.out.println("You pressed ESC");
                      btnEsciAction();
                  } catch (IOException ex) {
                      Logger.getLogger(ElencoDitteController.class.getName()).log(Level.SEVERE, null, ex);
                  }
              }
        }); 
        //F1
        SCENE.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
              if(key.getCode()==KeyCode.F1) {
                  try {
                      System.out.println("You pressed F1");
                      btnIndietroAction();
                  } catch (IOException ex) {
                      Logger.getLogger(ElencoDitteController.class.getName()).log(Level.SEVERE, null, ex);
                  }
              }
        });
        //**-- fine event handler scene **--
        
        */
        

    }

    
    //***** INIZIO METODO PER CREARE L'ALBERO
    public void caricaAlbero() {
        albero.setRoot(root);
        root.setExpanded(true);

        List<String> menu = new ArrayList<>();
        menu.add("utenti");  //0 
        menu.add("backup"); //1
        addToAlbero(root, menu);

        menu.clear();
        menu.add("crea utente"); //0.0
        menu.add("assegna ditte a utenti"); //0.1
        addToAlbero(root.getChildren().get(0), menu);

        //*** LISTENER dell'albero
        albero.getSelectionModel().selectedItemProperty().addListener((ObservableValue observable, Object oldValue, Object newValue) -> {
            selectedItem = (TreeItem<String>) newValue;
        });

        //*** DOPPIO CLICK dell'elemento dell'albero
        albero.setOnMouseClicked((MouseEvent mouseEvent) -> {
            if (mouseEvent.getClickCount() == 2) {
                try {
                    System.out.println("Selected Text : " + selectedItem.getValue());
                    
                    switch(selectedItem.getValue()){
                        case "assegna ditte a utenti" :
                            Parent newRoot = FXMLLoader.load(getClass().getResource("/meilfx/ammin/utenti/AssegnaDitte.fxml"));
                            STAGE.getScene().setRoot(newRoot);                                      
                    }
                    
                    
                } catch (IOException ex) {
                    Logger.getLogger(AmministratoreController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        //*** F2 su un elemento dell'albero
        albero.setOnKeyReleased((KeyEvent keyEvent) -> {
            if (keyEvent.getCode().equals(F2)) {
                try {
                    System.out.println("Selected Text : " + selectedItem.getValue());
                    
                    switch(selectedItem.getValue()){
                        case "assegna ditte a utenti" :
                            Parent newRoot = FXMLLoader.load(getClass().getResource("/meilfx/ammin/utenti/AssegnaDitte.fxml"));
                            STAGE.getScene().setRoot(newRoot);           
                            
                    }
                    
                    
                } catch (IOException ex) {
                    Logger.getLogger(AmministratoreController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
    }
    // ***** FINE METODO CREA ALBERO
    
    
    

    public void btnIndietroAction() throws IOException {
        Parent backRoot = FXMLLoader.load(getClass().getResource("/meilfx/elencoditte/ElencoDitte.fxml"));
        STAGE.getScene().setRoot(backRoot);
    }

    public void btnEsciAction() throws IOException {
        btnEsci.btnEsciActionGlobal();
    }

}
