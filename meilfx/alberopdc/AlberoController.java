/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meilfx.alberopdc;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import static meilfx.alberopdc.sql.Albero2044.*;
import static meilfx.alberopdc.sql.AlberoSql.*;
import meilfx.ditta.Ditta;
import static meilfx.pub.TextFieldVirgola.numeric_Validation;

/**
 * FXML Controller class
 *
 * @author luca
 */
public class AlberoController implements Initializable {
    
    //creo un oggetto ditta , che servirà al momento per il CODICE del database FILUCA
    Ditta ditta = new Ditta();
    
    String idCrea = "";
    
    @FXML
    AnchorPane anchorFiglio = new AnchorPane();

    @FXML
    TreeView<String> albero = new TreeView<>();
    TreeItem<String> root = new TreeItem<>("1.00_Piano dei Conti.1");
    TreeItem<String> selectedItem = new TreeItem<>();

    @FXML
    Tab tabMastroConto = new Tab();
    @FXML
    Tab tab2044mv = new Tab();
    
    @FXML
    Button btnStampa = new Button();
    @FXML
    Button btnCancella = new Button();
    @FXML
    Button btnCambiaPadre = new Button();
    @FXML
    Button btnRegistra = new Button();
    @FXML
    Button btnCambiaOrdine = new Button();
    @FXML
    Button btnInserisci2044= new Button();
    @FXML
    Button btnInserisciTutti2044 = new Button();
    
    @FXML
    Label lblAzione = new Label();
    @FXML 
    Label lbl_idSelVisuale = new Label();

    @FXML
    TextField txtPadreDescrizione = new TextField();
    @FXML
    TextField txtSelDescrizione = new TextField();
    @FXML
    TextField txt_idPadre = new TextField();
    @FXML
    TextField txt_idSel = new TextField();
    @FXML
    TextField txt_IDSel = new TextField();
    @FXML
    TextField txtDescrizione = new TextField();
    @FXML
    TextField txtId = new TextField();
    @FXML
    TextField txt_idVis = new TextField();
    @FXML
    TextField txt_idSelVisuale = new TextField();
    
    @FXML
    ComboBox cmbInserisci2044 = new ComboBox();

    @FXML
    RadioButton rdoCrea = new RadioButton();
    @FXML
    RadioButton rdoModifica = new RadioButton();
    @FXML
    RadioButton rdoCancella = new RadioButton();
    @FXML
    RadioButton rdoMastro = new RadioButton();
    @FXML
    RadioButton rdoConto = new RadioButton();
   

    List<String> lista = new ArrayList<>();

    List<String> listaM1;
    List<String> listaM2;
    List<String> listaM3;
    List<String> listaM4;
    List<String> listaM5;
    List<String> listaCodici;

    List<String> listaMC2;
    List<String> listaMC3;
    List<String> listaMC4;
    List<String> listaMC5;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        ditta.setCodice("filuca");
        //prendo i conti dalla tabella della ditta con cui sono entrato
        cmbInserisci2044.setItems(getContiDitta(ditta.getCodice()));
        
        anchorFiglio.setVisible(false);
        caricaAlbero();
        
        //inizializzo i textField numerici
        txt_idVis.addEventFilter(KeyEvent.KEY_TYPED , numeric_Validation(6));
        txt_idSelVisuale.addEventFilter(KeyEvent.KEY_TYPED , numeric_Validation(6));
        txtId.addEventFilter(KeyEvent.KEY_TYPED , numeric_Validation(3));
        txt_idPadre.addEventFilter(KeyEvent.KEY_TYPED , numeric_Validation(3));
        txt_idSel.addEventFilter(KeyEvent.KEY_TYPED , numeric_Validation(3));
        txt_IDSel.addEventFilter(KeyEvent.KEY_TYPED , numeric_Validation(3));
        
        txt_idVis.setAlignment(Pos.CENTER_RIGHT);
        
        txt_idVis.textProperty().addListener((observable, oldValue, newValue) -> {
             System.out.println("textfield changed from " + oldValue + " to " + newValue);
             if(oldValue.length()==2){
                 txt_idVis.setText(oldValue.concat("."));
             }
             
        });
        
        
    }
    
// *****************************************METODI DELL'ALBERO *********************************
    
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
    
    
    //metodo che prende Monta le tabelle sull'albero + listener dell'albero
    public void caricaAlbero() {
        //***** INIZIO creazione albero
        
        albero.setRoot(root);
        root.setExpanded(true);
        
        listaM1 = getM_01_050();
        addToAlbero(root, listaM1);  //primo add sull'albero(attivo,passivo) ... passo al metodo il padre e la lista

        int x = 1;
        int y = 1;
        int z = 1;
        int w = 1;

        int i;
        for (i = 1; i <= listaM1.size(); i++) {
            String ramo1 = root.getChildren().get(i - 1).getValue(); // nel ramo ho la descizion completa di: xxx.xx_descr.yyy   x=idVisuale  y=id
            String ramo1Index = ramo1.substring(ramo1.indexOf("_"));
            ramo1Index = ramo1Index.substring(ramo1Index.indexOf(".")+1);
            // aggiungo il mastro 2 del libro

            listaM2 = getM_02_049(Integer.parseInt(ramo1Index));
            addToAlbero(root.getChildren().get(i - 1), listaM2);

            int o;
            for (o = 1; o <= listaM2.size(); o++, x++) {
                String ramo2 = root.getChildren().get(i - 1).getChildren().get(o - 1).getValue();
                String ramo2Index = ramo2.substring(ramo2.indexOf("_"));
                ramo2Index = ramo2Index.substring(ramo2Index.indexOf(".")+1);
                //aggiungo il mastro 3 del libro

                listaM3 = getM_03_051(Integer.parseInt(ramo2Index));
                addToAlbero(root.getChildren().get(i - 1).getChildren().get(o - 1), listaM3);
                //aggiungo i figli del mastro 2 

//                listaMC2 = getMc_02(Integer.parseInt(ramo2Index));
//                addToAlbero(root.getChildren().get(i - 1).getChildren().get(o - 1), listaMC2);

                int u;
                for (u = 1; u <= listaM3.size(); u++, y++) {
                    String ramo3 = root.getChildren().get(i - 1).getChildren().get(o - 1).getChildren().get(u - 1).getValue();
                    String ramo3Index = ramo3.substring(ramo3.indexOf("_"));
                    ramo3Index = ramo3Index.substring(ramo3Index.indexOf(".")+1);
                    //aggiungo mastro 4 del libro

                    listaM4 = getM_04_052(Integer.parseInt(ramo3Index));
                    addToAlbero(root.getChildren().get(i - 1).getChildren().get(o - 1).getChildren().get(u - 1), listaM4);
                    //aggiungo i figli del mastro 3

//                    listaMC3 = getMc_03(Integer.parseInt(ramo3Index));
//                    addToAlbero(root.getChildren().get(i - 1).getChildren().get(o - 1).getChildren().get(u - 1), listaMC3);

                    int a;
                    for (a = 1; a <= listaM4.size(); a++, z++) {
                        String ramo4 = root.getChildren().get(i - 1).getChildren().get(o - 1).getChildren().get(u - 1).getChildren().get(a - 1).getValue();
                        String ramo4Index = ramo4.substring(ramo4.indexOf("_"));
                        ramo4Index = ramo4Index.substring(ramo4Index.indexOf(".")+1);
                        //aggiungo mastro 5 del libro

                        listaM5 = getM_05_053(Integer.parseInt(ramo4Index));
                        addToAlbero(root.getChildren().get(i - 1).getChildren().get(o - 1).getChildren().get(u - 1).getChildren().get(a - 1), listaM5);
                        //aggiungo i figli del mastro 4

//                        listaMC4 = getMc_04(Integer.parseInt(ramo4Index));
//                        addToAlbero(root.getChildren().get(i - 1).getChildren().get(o - 1).getChildren().get(u - 1).getChildren().get(a - 1), listaMC4);

                        int e;
                        for (e = 1; e <= listaM5.size(); e++, w++) {
                            String ramo5 = root.getChildren().get(i - 1).getChildren().get(o - 1).getChildren().get(u - 1).getChildren().get(a - 1).getChildren().get(e - 1).getValue();
                            String ramo5Index = ramo5.substring(ramo5.indexOf("_"));
                            ramo5Index = ramo5Index.substring(ramo5Index.indexOf(".")+1);
                            //aggiungo i codici(figli) del mastro 5

//                            listaCodici = getCodici("filuca", Integer.parseInt(ramo5Index));
//                            addToAlbero(root.getChildren().get(i - 1).getChildren().get(o - 1).getChildren().get(u - 1).getChildren().get(a - 1).getChildren().get(e - 1), listaCodici);
                            //aggiungo i figli del mastro 5

//                            listaMC5 = getMc_05(Integer.parseInt(ramo5Index));
//                            addToAlbero(root.getChildren().get(i - 1).getChildren().get(o - 1).getChildren().get(u - 1).getChildren().get(a - 1).getChildren().get(e - 1), listaMC5);
                        }
                    }
                }
            }
        }
        //***** FINE creazione albero

        //*** LISTENER dell'albero
        albero.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                selectedItem = (TreeItem<String>) newValue;
                String id = selectedItem.getValue();
                id = id.substring(id.indexOf("_"));
                id = id.substring(id.indexOf(".")+1);
                txt_idSel.setText(id);
                
                String selDescr = selectedItem.getValue();
                selDescr = selDescr.substring(selDescr.indexOf("_")+1);
                selDescr = selDescr.substring( 0 , selDescr.indexOf("."));
                txtSelDescrizione.setText(selDescr);
                
                if(!selectedItem.equals(root)){
                    
                    String padreDescr = selectedItem.getParent().getValue();
                    padreDescr = padreDescr.substring(padreDescr.indexOf("_")+1);
                    padreDescr = padreDescr.substring(0 , padreDescr.indexOf("."));
                    txtPadreDescrizione.setText(padreDescr);
                    
                    String IDPadre = selectedItem.getParent().getValue();
                    IDPadre = IDPadre.substring(IDPadre.indexOf("_"));
                    IDPadre = IDPadre.substring(IDPadre.indexOf(".")+1);
                    txt_idPadre.setText(IDPadre);
                    
                    txt_IDSel.setText(txt_idPadre.getText());
                }
                

                if (rdoCrea.isSelected()) {
                    int numPadri = getNumeroPadri(selectedItem);
                    try {
                        txtId.setText(getUltimoId(numPadri+1) );  // metto a video l'ultimo id disponibile
                        idCrea = txtId.getText(); // lo inserisco in questa variabile per utilizzarlo nella registrazione
                        
                    } catch (SQLException ex) {
                        Logger.getLogger(AlberoController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
                if(rdoModifica.isSelected()){
                    String idVisuale = selectedItem.getValue(); 
                    idVisuale = idVisuale.substring(0 , idVisuale.indexOf("_"));
                    txt_idSelVisuale.setText(idVisuale);
                }
            }
        });

    }

    //ritorna il numero di padri di un treeItem
    private int getNumeroPadri(TreeItem<String> treeItem) {
        int result;

        if (treeItem != root) {
            TreeItem<String> padre ;
            padre = treeItem;

            for (result = 1; padre.getParent() != root; result++) {
                padre = padre.getParent();
            }
        } else {
            result = 0;
        }
        return result;
    }

    // stampa dell'albero
    private List<String> printChildren(TreeItem<String> root) {
        System.out.println("*** Padre ***   " + root.getValue());
        this.lista.add("*** Padre ***   " + root.getValue());
        for (TreeItem<String> child : root.getChildren()) {
            if (child.getChildren().isEmpty()) {
                System.out.println(child.getValue());
                this.lista.add(child.getValue());
            } else {
                printChildren(child);
            }
        }
        return lista;
    }

    
        // bottone stampa
    public void btnStampaAction() throws IOException {
        Stage currentStage = (Stage) btnStampa.getScene().getWindow();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TEXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showSaveDialog(currentStage);

        if (file != null) {

            Path path = file.toPath();

            Files.write(path, printChildren(root), Charset.forName("UTF-8"));
        }
    }
    
    
//**************************** TAB MASTRO , CONTO *************************************************    

    public void tipoCreaAction() {

        txtDescrizione.setDisable(false);
        txtDescrizione.clear();
        //txtId.clear();
        lblAzione.setVisible(false);

        // ***crea
        if (rdoCrea.isSelected()) {
            rdoMastro.setVisible(true);
            rdoConto.setVisible(true); 
            anchorFiglio.setVisible(true);
            txt_idVis.setText("000.00");
            btnRegistra.setDisable(false);
        } else {
            rdoMastro.setVisible(false);
            rdoConto.setVisible(false);
            anchorFiglio.setVisible(false);
            btnRegistra.setDisable(true);
        }

        //***cancella
        if (rdoCancella.isSelected()) {
            btnCancella.setVisible(true);
            btnRegistra.setDisable(true);
        } else {
            btnCancella.setVisible(false);
            btnRegistra.setDisable(false);
        }
        
        //***modifica
        if(rdoModifica.isSelected()){
            txtId.setDisable(false);
            txtSelDescrizione.setDisable(false);
            txtSelDescrizione.requestFocus();
            //txt_idSel.setDisable(false);
            txt_IDSel.setDisable(false);
            btnRegistra.setDisable(false);
            btnCambiaPadre.setVisible(true);
            lbl_idSelVisuale.setVisible(true);
            txt_idSelVisuale.setVisible(true);
            btnCambiaOrdine.setVisible(true);
        } else {
            txtSelDescrizione.setDisable(true);
            txt_idSel.setDisable(true);
            txt_IDSel.setDisable(true);
            btnCambiaPadre.setVisible(false);
            lbl_idSelVisuale.setVisible(false);
            txt_idSelVisuale.setVisible(false);
            btnCambiaOrdine.setVisible(false);
            if(!rdoConto.isSelected())txtId.setDisable(true);
        }
            

    }



    
    public void btnCambiaPadreAction() throws SQLException{
        String descrizione = txtSelDescrizione.getText();
        String id = txt_idSel.getText();
        String ID = txt_IDSel.getText();

        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Errore inserimento");
        alert.setHeaderText(null);

        if ("".equals(ID)) {
            alert.setContentText("Inserire un ID Padre valido.");
            alert.showAndWait();
            txt_idSelVisuale.requestFocus();
            return;
        }

        int numPadri = getNumeroPadri(selectedItem);

        System.out.println("tabella: m_ " + numPadri);
        System.out.println("descrizione: " + descrizione);
        System.out.println("id: " + id);

        if (numPadri <= 1 ) {
            alert.setContentText("Non è possibile modificare il root dell'albero.");
            alert.showAndWait();
            albero.requestFocus();
            return;
        }

        // modifica sulla tabella.
        if (cambiaPadreM(numPadri, id, ID)) {
            alert.setAlertType(AlertType.INFORMATION);
            alert.setTitle("Inserimento riuscito");
            alert.setHeaderText(null);
            alert.setContentText("id          : " + id
                    + "\ndescrizione : " + descrizione
                    + "\nid  ID Padre : " + ID
                    + "\n\nID padre cambiato con successo.   Premere OK per continuare.");
            alert.showAndWait();

            txtId.clear();

            root.getChildren().clear();
            caricaAlbero();
        }
        
    }
    
    
    public void btnCambiaOrdineAction() throws SQLException {
        
        String descrizione = txtSelDescrizione.getText();
        String id = txt_idSel.getText();
        String idVisuale = txt_idSelVisuale.getText();

        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Errore inserimento");
        alert.setHeaderText(null);

        if ("".equals(idVisuale)) {
            alert.setContentText("Inserire un id visuale valido.");
            alert.showAndWait();
            txt_idSelVisuale.requestFocus();
            return;
        }

        int numPadri = getNumeroPadri(selectedItem);

        System.out.println("tabella: m_ " + numPadri);
        System.out.println("descrizione: " + descrizione);
        System.out.println("id: " + id);

        if (numPadri == 0) {
            alert.setContentText("Non è possibile modificare il root dell'albero.");
            alert.showAndWait();
            albero.requestFocus();
            return;
        }

        // modifica sulla tabella.
        if (modifica_idVisualeM(numPadri, id, idVisuale)) {
            alert.setAlertType(AlertType.INFORMATION);
            alert.setTitle("Inserimento riuscito");
            alert.setHeaderText(null);
            alert.setContentText("id          : " + id
                    + "\ndescrizione : " + descrizione
                    + "\nid  Visuale : " + idVisuale
                    + "\n\n Ordine Cambiato con successo.   Premere OK per continuare.");
            alert.showAndWait();

            txtId.clear();

            root.getChildren().clear();
            caricaAlbero();
        }

    }
    
    
    public void btnRegistraAction() throws SQLException {

        // controllo errori 
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Errore inserimento");
        alert.setHeaderText(null);
        if(selectedItem.getValue()==null){
            alert.setContentText("Posizionarsi su un elemento dell'albero. ");
            alert.showAndWait();
            return;
        }
        if(rdoCrea.isSelected() && !rdoMastro.isSelected() && !rdoConto.isSelected()){
            alert.setContentText("Scegliere mastro o conto");
            alert.showAndWait();
            rdoMastro.requestFocus();
            return;
        }
        
        //inizializzo varibili con dati da inserire
        String idSelezionato = txt_idSel.getText();
        String id = txtId.getText();
        String descrizione = txtDescrizione.getText();
        int numPadri = getNumeroPadri(selectedItem);
        String idVisual = txt_idVis.getText();
        
        //if crea is selected...
        if (rdoCrea.isSelected()) {
            //if conto is selected
            if (rdoConto.isSelected()) {
//                if ("".equals(txtId.getText())) {
//                    alert.setContentText("L' id è un campo obbligatorio");
//                    alert.showAndWait();
//
//                    txtId.requestFocus();
//                } else if ("".equals(txtDescrizione.getText())) {
//                    alert.setContentText("La descrizione è un campo obbligatorio");
//                    alert.showAndWait();
//
//                    txtDescrizione.requestFocus();
//                } else {
//                    System.out.println("Registrazione su tabella Mc" + numPadri);
//                    insertTabellaMc(numPadri, id, descrizione, idSelezionato);
//
//                    txtDescrizione.clear();
//                    txtId.clear();
//
//                    alert.setAlertType(AlertType.INFORMATION);
//                    alert.setTitle("Inserimento riuscito");
//                    alert.setHeaderText(null);
//                    alert.setContentText("premere ok");
//                    alert.showAndWait();
//                }

            }
            //if mastro is selected
            if (rdoMastro.isSelected()) {
                
                if ("".equals(txtDescrizione.getText())) {
                    alert.setContentText("La descrizione è un campo obbligatorio");
                    alert.showAndWait();
                    txtDescrizione.requestFocus();
                    return;
                }else if("".equals(txt_idVis.getText())){
                    alert.setContentText("L' id visuale è un campo obbligatorio");
                    alert.showAndWait();
                    txt_idVis.requestFocus();  
                    return;
                }else {
                    //System.out.println("controllo se puo creare libro" + controllaSePuoCreareLibro(numPadri + 1, idSelezionato));
                    //if (controllaSePuoCreareLibro(numPadri + 1, idSelezionato)) {
                        System.out.println("Registrazione su tabella M_" + numPadri + 1);
                        insertTabellaM(numPadri + 1, descrizione, idVisual, idSelezionato);

                        idCrea = getUltimoId(numPadri+1);
                        int idCreaInt = Integer.parseInt(idCrea)-1;
                        

                        alert.setAlertType(AlertType.INFORMATION);
                        alert.setTitle("Inserimento riuscito");
                        alert.setHeaderText(null);
                        alert.setContentText("id          : "   + idCreaInt
                                            +"\ndescrizione : " + descrizione
                                            +"\nid  Visuale : " + idVisual
                                            + "\n\nCreato con successo.   Premere OK per continuare.");
                        alert.showAndWait();

                        txtId.clear();

//                            TreeItem creato = new TreeItem();
//                            creato.setValue(idCrea + "." + descrizione);
//                            selectedItem.getChildren().add(creato);
                        root.getChildren().clear();
                        caricaAlbero();

//                    } else {
//                        alert.setHeaderText("Impossibile creare questo mastro");
//                        alert.setContentText(selectedItem.getValue() + " contiene dei conti");
//                        alert.showAndWait();
//                    }

                    txtDescrizione.clear();
                    txtId.clear();
                }
            }
        }

        //if modifica is selected ...
        if (rdoModifica.isSelected()) {
            
           if(numPadri==0){
                    alert.setContentText("Non è possibile modificare il root dell'albero.");
                    alert.showAndWait();
                    albero.requestFocus();
                    return;
           }
           
            if(txtSelDescrizione.getText().length()<=3){
                    alert.setContentText("Inserire una descrizione valida.");
                    alert.showAndWait();
                    txtSelDescrizione.requestFocus();
                    return;
            }
           
            
            if (modificaAction()) {
                
                root.getChildren().clear();
                caricaAlbero();
                
                alert.setAlertType(AlertType.INFORMATION);
                alert.setTitle("Inserimento riuscito");
                alert.setHeaderText(null);
                alert.setContentText(txtSelDescrizione.getText() + "\nModificato con successo.  Premere ok per continuare.");
                alert.showAndWait();
            }else {
                alert.setContentText("Errore interno nella modifica dell'elemento.");
                alert.showAndWait();
            }
        }

    }

    public boolean modificaAction() throws SQLException {
        System.out.println(txtSelDescrizione.getText());
        
        int numPadri = getNumeroPadri(selectedItem);
        String descrizione = txtSelDescrizione.getText();
        String ID_esterno = txt_idPadre.getText();
        String id = txt_idSel.getText();

        System.out.println("tabella: m_ " + numPadri);
        System.out.println("descrizione: " + descrizione);
        System.out.println("id: " + id);
        System.out.println("ID_esterno: " + ID_esterno);

        boolean res = modificaDescrizioneM(numPadri, descrizione, id);
        return res;
    }

    
    public void btnCancellaAction() throws SQLException {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        if (selectedItem.isLeaf()) {
            alert.setTitle("Conferma cancellazione");
            alert.setHeaderText(null);
            alert.setContentText("Sei sicuro di procedere con la cancellazione? ");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                // ... user chose OK
                String id_selezionato = txt_idSel.getText();
                int numPadri_tabella = getNumeroPadri(selectedItem);

                cancellaRecord(numPadri_tabella, id_selezionato);

                alert.setAlertType(AlertType.INFORMATION);
                alert.setTitle("Cancellazione ");
                alert.setHeaderText(null);
                alert.setContentText(selectedItem.getValue() + "\n\nCancellato con successo.");
                alert.showAndWait();

                selectedItem.getParent().getChildren().remove(selectedItem);
            }
        } else {
            // ... user chose CANCEL or closed the dialog
            alert.setAlertType(AlertType.ERROR);
            alert.setTitle("Cancellazione ");
            alert.setHeaderText(null);
            alert.setContentText(selectedItem.getValue() + "\n\nHa figli e non può essere cancellato.");
            alert.showAndWait();
        }
    }

//***************************************TAB 2044MV ***********************************************
    public void btnInserisciTutti2044Action() throws SQLException, IOException {

        Alert alert = new Alert(AlertType.ERROR);
        alert.setHeaderText(null);

        //lista dei conti presenti in 2044mx
        ObservableList items = cmbInserisci2044.getItems();
        //creo un file per i non esistenti...
        File file_nonEsiste = new File("NonEsistenti.txt");
        Path path = file_nonEsiste.toPath();

        int i;
        for (i = 0; i < items.size(); i++) {

            String risultato = getContiTabelle((String) items.get(i), ditta.getCodice());
            switch (risultato) {
                case "NON ESISTE":
                    alert.setTitle("Errore ricerca");
                    alert.setContentText("Il conto " + (String) items.get(i) + " non esiste nelle tabelle dei conti");
                    alert.showAndWait();
//                    List<String> lines = Arrays.asList((String) items.get(i)); 
//                    Files.write(file_nonEsiste.toPath(), lines, Charset.forName("UTF-8"), StandardOpenOption.APPEND);
                    break;
                case "DOPPIONE":
                    alert.setAlertType(AlertType.WARNING);
                    alert.setTitle("Errore ricerca");
                    alert.setContentText("Il conto " + (String) items.get(i) + " è duplicato nelle tabelle dei conti");
                    alert.showAndWait();
                    break;
                default:
                    break;
            }
        }

    }

    
    
    public void btnInserisci2044Action() throws SQLException {

        Alert alert = new Alert(AlertType.ERROR);
        alert.setHeaderText(null);
        String contoSel = (String) cmbInserisci2044.getValue();

        String risultato = getContiTabelle(contoSel, ditta.getCodice());
        switch (risultato) {
            case "NON ESISTE":
                alert.setTitle("Errore riInserisci");
                alert.setContentText("Il conto " + contoSel + " non è stato trovat nelle tabelle dei conti");
                alert.showAndWait();
                break;
            case "DOPPIONE":
                alert.setAlertType(AlertType.WARNING);
                alert.setTitle("Errore ricerca");
                alert.setContentText("Il conto " + contoSel + " è duplicato nelle tabelle dei conti");
                alert.showAndWait();
                break;
            default:
                alert.setAlertType(AlertType.INFORMATION);
                alert.setTitle("Inserimento conto riuscito");
                alert.setContentText(risultato);
                alert.showAndWait();
                break;
        }
    }

}
