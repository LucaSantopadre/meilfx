/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meilfx.elencoditte;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;
import static meilfx.elencoditte.sql.ElencoDitteSql.*;
import meilfx.login.LoginController;
import meilfx.pub.*;

/**
 * FXML Controller class
 *
 * @author luca
 */
public class ElencoDitteController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    Label lblTopCentro = new Label();
    @FXML
    Label lblTopDestra = new Label();
    
    @FXML
    Button btnAmmin = new Button();
    @FXML
    Bottone btnEsci = new Bottone(); // con metodo esteso per uscire
    
    @FXML
    TreeView albero = new TreeView();
    TreeItem<String> root = new TreeItem<>("Ditte");
    
    String tipoAccesso = LoginController.Login.getTipoAccesso();
    int idUtente = LoginController.Login.getIdUtente();
    String emailUtente = LoginController.Login.getEmail();
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        lblTopCentro.setText(tipoAccesso);
        lblTopDestra.setText(idUtente + "_" + emailUtente);
        
        caricaAlbero();
        
        //bottone amministratore , solo se si entra come amministratore
        if(tipoAccesso.equals("amministratore")){
            btnAmmin.setVisible(true);
        }
        
    }    
    
    
    public void caricaAlbero(){       
        List<String> listaDitte = null;
        
        if(tipoAccesso.equals("utente")){
            listaDitte = getDitteUtente(emailUtente);
            System.out.println(listaDitte.toString());
        }else if(tipoAccesso.equals("amministratore")){
            listaDitte = getDitteAmmin();
            System.out.println(listaDitte.toString());
        }
        
        albero.setRoot(root);
        root.setExpanded(true);
        addToAlbero(root , listaDitte);
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
    
    
    public void btnAmminAction() throws IOException{
        Stage stage = (Stage) btnAmmin.getScene().getWindow();
        Parent node = FXMLLoader.load(getClass().getResource("/meilfx/ammin/Amministratore.fxml"));
        Scene scene = new Scene(node);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }
    
    
    public void btnEsciAction() throws IOException{
        btnEsci.btnEsciAction(btnAmmin);
    }
    
    
}
