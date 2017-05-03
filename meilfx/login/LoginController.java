/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meilfx.login;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import meilfx.login.sql.LoginSql;
import static meilfx.login.sql.LoginSql.*;

/**
 * FXML Controller class
 *
 * @author luca
 */
public class LoginController implements Initializable {
    
    
    @FXML
    Button btnEntra = new Button();
    @FXML
    TextField txtEmail = new TextField();
    @FXML
    PasswordField txtPassword = new PasswordField();

    public static LoginSql Login = new LoginSql();
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        btnEntra.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  {
                try {
                    btnEntraAction();
                } catch (IOException ex) {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        txtPassword.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  {
                try {
                    btnEntraAction();
                } catch (IOException ex) {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
    }    
    
    
    public void btnEntraAction() throws IOException{
        System.out.println("entra premuto");
        
        String email = txtEmail.getText();
        String password = txtPassword.getText();
        
        Alert alert = new Alert(AlertType.ERROR);
        alert.setHeaderText(null);
        
        Stage stage;
        Parent node;
        Scene scene;

        int tipoAccesso = verificaUtente(email, password);
        switch(tipoAccesso){
            case 0 :  // accesso negato.
                alert.setTitle("Accesso negato");
                alert.setContentText("Email o password errata");
                alert.showAndWait();
                break;
                
            case 1 : // amministratore
                Login.setDatiAccesso(email);
                
                stage = (Stage) btnEntra.getScene().getWindow();
                node = FXMLLoader.load(getClass().getResource("/meilfx/elencoditte/ElencoDitte.fxml"));
                scene = new Scene( node);
                stage.setScene(scene);
                stage.setMaximized(true);
                stage.show();
                
                break;
            case 2 : // utente
                Login.setDatiAccesso(email);
                
                stage = (Stage) btnEntra.getScene().getWindow();
                node = FXMLLoader.load(getClass().getResource("/meilfx/elencoditte/ElencoDitte.fxml"));
                scene = new Scene( node);
                stage.setScene(scene);
                stage.setMaximized(true);
                stage.show();

                break;
                
            default : // errore 
                alert.setTitle("Errore accesso");
                alert.setContentText("Contattare amministratore.");
                alert.showAndWait();
                
                break;
        }
        
        
        
    }
    
}
