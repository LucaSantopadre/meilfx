package meilfx.elencoditte;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import static javafx.scene.input.KeyCode.*;
import javafx.scene.input.KeyEvent;
import meilfx.ammin.AmministratoreController;
import static meilfx.elencoditte.sql.ElencoDitteSql.*;
import meilfx.login.LoginController;
import static meilfx.panels.Albero.addToAlbero;
import meilfx.panels.Schermo;
import static meilfx.panels.Schermo.SCENE;
import static meilfx.panels.Schermo.STAGE;
import meilfx.pub.*;

/**
 * FXML Controller class
 *
 * @author luca
 */
public class ElencoDitteController implements Initializable {
    @FXML
    Label lblTopSinistra = new Label();
    @FXML
    Label lblTopCentro = new Label();
    @FXML
    Label lblTopDestra = new Label();
    
    @FXML
    TreeView albero = new TreeView();
    TreeItem<String> root = new TreeItem<>("Ditte");
    
    @FXML
    Button btnAmmin = new Button();
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
        lblTopDestra.setText("ElencoDitte");
        
        //carico elementi dell'albero
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
        
        
        //bottone amministratore , solo se si entra come amministratore
        if(tipoAccesso.equals("amministratore")){
            btnAmmin.setVisible(true);
        }      
        
        albero.requestFocus();
    }    
    
    
    public void caricaAlbero(){       
        List<String> listaDitte = null;
        
        //se utente -> ditte visibili ad Utente *** se amministratore -> tutte le ditte
        if(tipoAccesso.equals("utente")){
            listaDitte = getDitteUtente(emailUtente);
            System.out.println(listaDitte.toString());
        }else if(tipoAccesso.equals("amministratore")){
            listaDitte = getDitteAmmin();
            System.out.println(listaDitte.toString());
        }
        
        albero.setRoot(root);
        root.setExpanded(true);
        //aggiungo al root la lista delle ditte precedentemente ricavate
        addToAlbero(root , listaDitte);
    }
    


    
    public void btnAmminAction() throws IOException{
        //cambio root tramite il set root e la nuova schermata
        Parent newRoot = FXMLLoader.load(getClass().getResource("/meilfx/ammin/Amministratore.fxml"));
        STAGE.getScene().setRoot(newRoot); 
    }
    
    
    public void btnIndietroAction() throws IOException{
        Parent backRoot = FXMLLoader.load(getClass().getResource("/meilfx/login/Login.fxml"));
        STAGE.getScene().setRoot(backRoot); 
    }
    
    public void btnEsciAction() throws IOException{
        btnEsci.btnEsciActionGlobal();
    } 
}
