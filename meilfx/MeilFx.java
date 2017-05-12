package meilfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import meilfx.connect.Connessione;
import meilfx.panels.Messaggi;
import meilfx.panels.Schermo;

/**
 *
 * @author luca
 */
public class MeilFx extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        // creo la connessione al db
        Connessione.getConnection();
        // carico la schermata in fxml ,setto la scene ,e lo stage
        Parent root = FXMLLoader.load(getClass().getResource("login/Login.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("MeilFx");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
        Schermo.STAGE=stage;
        Schermo.SCENE=scene;
        //messaggio di conferma chiusura
        Messaggi.messaggioChiudi(stage);

        //@TODO
//        //metodo che gestisce i tasti funzione 
//        scene.setOnKeyPressed((KeyEvent event) -> {
//            switch (event.getCode()) {
//                case ENTER: System.out.println("INVIO / REGISTRA");  break;
//                case ESCAPE: System.out.println("ESC") ;  messaggioChiudi(stage); ; break;
//                case F2: System.out.println("AVANTI"); break;
//                case F1: System.out.println("INDIETRO"); break;
//            }
//        });
    }

    public static void main(String[] args) {

        launch(args);
    }

}
