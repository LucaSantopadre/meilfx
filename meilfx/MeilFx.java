/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meilfx;

import java.util.Optional;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import meilfx.connect.Connessione;



/**
 *
 * @author luca
 */
public class MeilFx extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        // creo la connessione al db
        Connessione.getConnection();
        // carico la schermata in fxml
        Parent root = FXMLLoader.load(getClass().getResource("login/login.fxml"));
        
//        //tramite questi passaggi arrivo al bottone che è nel pannello in basso del root
//        Parent node = (Parent) root.getChildrenUnmodifiable().get(0);
//        node.getChildrenUnmodifiable().get(0);
//        Button registra = (Button) node.getChildrenUnmodifiable().get(0);
//        // 
        
        //setto la scene con il pannello root (fxml)
        Scene scene = new Scene(root);
        
        stage.setTitle("MeilFx");
        stage.setScene(scene);
        stage.setMaximized(true);
        
        stage.show();
        
        //metodo che gestisce i tasti funzione 
        scene.setOnKeyPressed((KeyEvent event) -> {
            switch (event.getCode()) {
                case ENTER: System.out.println("INVIO / REGISTRA");  break;
                case ESCAPE: System.out.println("ESC") ;  messaggioChiudi(stage); ; break;
                case F2: System.out.println("AVANTI"); break;
                case F1: System.out.println("INDIETRO"); break;
            }
        });
                
        messaggioChiudi(stage);
    }

    
    
    
    
    public void messaggioChiudi(Stage primaryStage){
        		        // ************* metodo messaggio chiudi
        primaryStage.setOnCloseRequest((WindowEvent event) -> {
            event.consume();
            
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Conferma chiusura");
            alert.setHeaderText("Sei sicuro di voler uscire dal programma?");
            alert.initOwner(primaryStage);
            
            ButtonType buttonTypeNo = new ButtonType("NO");
            ButtonType buttonTypeSi = new ButtonType("SI");
            
            
            alert.getButtonTypes().setAll(buttonTypeNo,buttonTypeSi);
            
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == buttonTypeSi){
                Platform.exit();
            }
        });
		        // *************fine metodo messaggio chiudi
    }
       
  
    
    
    public static void main(String[] args) {

        launch(args);
    }
    
}
