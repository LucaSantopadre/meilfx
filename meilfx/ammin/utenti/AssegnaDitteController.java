package meilfx.ammin.utenti;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import static meilfx.ammin.utenti.sql.UtentiSql.*;
import meilfx.login.LoginController;
import static meilfx.panels.Albero.addToAlbero;
import static meilfx.panels.Schermo.STAGE;
import meilfx.pub.Bottone;


public class AssegnaDitteController implements Initializable {

    @FXML
    Label lblTopSinistra = new Label();
    @FXML
    Label lblTopCentro = new Label();
    @FXML
    Label lblTopDestra = new Label();
    
    @FXML
    private TreeView<String> albero;    
    TreeItem<String> root = new TreeItem<>("Utenti");
    TreeItem<String> selectedItem;
    
    @FXML
    Bottone btnEsci = new Bottone(); // con metodo esteso per uscire
    @FXML
    Button btnIndietro = new Button();
    
    
    int    idUtente    = LoginController.Login.getIdUtente();
    String emailUtente = LoginController.Login.getEmail();
    String tipoAccesso = LoginController.Login.getTipoAccesso();

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // inizializzo le label nel TOP panel
        lblTopSinistra.setText(idUtente + "_" + emailUtente);          
        lblTopCentro.setText(tipoAccesso);
        lblTopDestra.setText("AssegnaDitte");
        
        // carico a video l'albero
        caricaAlbero();   
        
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
    
    
    
    public void btnIndietroAction() throws IOException{
        Parent backRoot = FXMLLoader.load(getClass().getResource("/meilfx/ammin/Amministratore.fxml"));
        STAGE.getScene().setRoot(backRoot); 
    }
    
    public void btnEsciAction() throws IOException{
        btnEsci.btnEsciActionGlobal();
    } 
    
}
