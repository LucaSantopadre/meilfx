/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meilfx.creaDitta;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import meilfx.controlli.CtrlDittaInstallata;
import meilfx.creaDitta.model.MdlDitta;
import meilfx.ditta.sql.MysqlDatabase;
import static meilfx.ditta.sql.SqlDitta.InserisciPrivato;
import static meilfx.ditta.sql.SqlDitta.getCodiciDitte;
import static meilfx.utility.ControllaCFIS_PIVA.ControllaCF;
import static meilfx.utility.ControllaCFIS_PIVA.ControllaPIVA;

/**
 *
 * @author luca
 */
public class MeilFxController implements Initializable {
    
    @FXML
    RadioButton rdoImpresaIndividuale;
    @FXML
    RadioButton rdoSocieta;
    @FXML
    RadioButton rdoNuovo;
    @FXML
    RadioButton rdoModifica;
   
    
    @FXML
    TextField txtCognome;
    @FXML
    TextField txtNome;
    @FXML
    TextField txtRagioneSociale;
    @FXML
    TextField txtCodFiscale;
    @FXML
    TextField txtPartitaIva;
    
    @FXML
    ComboBox<String> cmbCodDitta;
    
    @FXML
    Button btnRegistra;
    
    
    MdlDitta dittaCreata = new MdlDitta();
    
    
    
    public void tipoNuovoModificaListener(){
        
        if (rdoNuovo.isSelected())
        {
            clearAllTextField();
            cmbCodDitta.setDisable(true);
            cmbCodDitta.setPromptText("");
        }
        if(rdoModifica.isSelected())
        {
            clearAllTextField();
            cmbCodDitta.setDisable(false);
            cmbCodDitta.setPromptText("inserisci codice per cercare");
            // richiamo il metodo che mostra i codice delle ditte (tramite query SHOW DATABASES)
            ObservableList<String> codDitte = FXCollections.observableArrayList(getCodiciDitte());
            
          
            cmbCodDitta.setItems(codDitte);
            
        }
        
    }
    
    
    
    
    //***** metodo del radio button Impresa individuale o socità
    public void tipoDittaListener(){
        if (rdoImpresaIndividuale.isSelected())
        {
            clearAllTextField();
            txtRagioneSociale.setDisable(true);
            txtNome.setDisable(false);
            txtCognome.setDisable(false);
            txtCognome.requestFocus();
            
        }
        if (rdoSocieta.isSelected())
        {
            
            clearAllTextField();
            txtCognome.setDisable(true);
            txtNome.setDisable(true);
            txtRagioneSociale.setDisable(false);
            txtRagioneSociale.requestFocus();
        }
    }
    
    
    //***** metodo che controlla cognome e crea il codice della ditta
    private String setCodiceCognome()
    {
        String aux = this.txtCognome.getText();
        String cod_ditta = null;
        if (aux.length()<2)
        {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Errore inserimento");
            alert.setHeaderText(null);
            alert.setContentText("Il cognome deve contenere almeno 2 caratteri.");
            alert.showAndWait();
            this.txtCognome.requestFocus();
        } else 
                {
                    cod_ditta = CtrlDittaInstallata.getNewCodiceDitta(this.txtCognome.getText());
                    if (!cod_ditta.equals(""))
                         this.cmbCodDitta.setValue(cod_ditta);
                }
        return cod_ditta;
    }
    
    
    
    //***** metodo che controlla la ragione sociale e crea il codice della ditta
    private String setCodiceRagioneSociale()
    {
        String aux = this.txtRagioneSociale.getText();
        String cod_ditta = null;
        if (aux.length()<2)
        {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Errore inserimento");
            alert.setHeaderText(null);
            alert.setContentText("La Ragione Sociale deve contenere almeno 2 caratteri.");
            alert.showAndWait();
            this.txtRagioneSociale.requestFocus();
        } else 
                {
                    cod_ditta = CtrlDittaInstallata.getNewCodiceDitta(this.txtRagioneSociale.getText());
                    if (!cod_ditta.equals(""))
                         this.cmbCodDitta.setValue(cod_ditta);
                }
        return cod_ditta;
    }
    
    
    
    
    public void clearAllTextField(){
        this.cmbCodDitta.setValue(null);
        this.txtCodFiscale.clear();
        this.txtCognome.clear();
        this.txtNome.clear();
        this.txtPartitaIva.clear();
        this.txtRagioneSociale.clear();
    }
    
    
    
    
    //***** metodo del bottone registra
    public void registraAction()
    {  
        System.out.println("bottone registra premuto");
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        
        if(this.rdoNuovo.isSelected())
        {
            if(creaDitta() == true)
            {
                MysqlDatabase mysqlDatabase = new MysqlDatabase("localhost ", "3306", "idecadmin", "meil21");
                try {
                    // metodo che richiama il crea db , e lo crea tramite un file zip salvato nella cartella resources
                    mysqlDatabase.recoveryDatabase((String) this.cmbCodDitta.getValue(), "nuovaDitta" );
                    
                    alert.setTitle("OK");
                    alert.setHeaderText(null);
                    alert.setContentText("Ditta creata con successo.\n\nCodice ditta:  " + this.dittaCreata.getCod_ditta());
                    alert.showAndWait();
                    
                    InserisciPrivato(this.dittaCreata);
                    
                    clearAllTextField();
                    
                    

                } catch (Exception ex) {
                    Logger.getLogger(MeilFxController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        else  
            if (this.rdoModifica.isSelected())
            {
                alert.setTitle("OK");
                alert.setHeaderText(null);
                alert.setContentText("modifica " + this.dittaCreata.getCod_ditta());
                alert.showAndWait();
            }
        
    }
    
    
    
    
    
    
    	public boolean creaDitta()
	{
            dittaCreata = this.getDitta();

            return dittaCreata  !=  null; 
        
        /*boolean aux = CtrlInvioDitta.CreaDitta(ditta);
        if (aux)
        {
        this.txtEmail.setEnabled(true);
        this.btnInvia.setEnabled(true);
        }*/ //if (this.rdoNuovo.isSelected())
        //SqlCreazioneDitta.CreaDB(dittaCreata);
        //System.out.println(dittaCreata.getCodice_fiscale());
        //this.rbNuova.setSelected(true);
        //this.nuovo_modifica_action();
		
	}
    
    
    
    
    
    
    public MdlDitta getDitta()
	{
		MdlDitta aux = new MdlDitta();
                String codDitta;
                

                
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Errore inserimento");
                alert.setHeaderText(null);
                
                
                //**codice ditta+cognome se è un impresa individuale
                //** codice ditta+ragione sociale se è una società
                if (rdoImpresaIndividuale.isSelected())
                {
                    // setto il tipo ditta   0= impresa individuale
                    aux.setTipo_ditta(0);
                    // esegue il controllo sul campo cognome e tira fuori il codice della ditta
                    codDitta=setCodiceCognome();
                    if (codDitta!=null)
                    {
                        // setto il codice creato su aux(dittaCreata)
                        aux.setCod_ditta(codDitta);
                        // Cognome
                        aux.setCognome(this.txtCognome.getText()); 
                    }
                    else  
                        return null;
                    
                 
                //altrimenti se si selezione societèà..
                }
                else    if(rdoSocieta.isSelected())
                        {
                            // setto il tipo ditta   1= societa
                            aux.setTipo_ditta(1);
                            // esegue il controllo sul campo ragione sociale e tira fuori il codice della ditta
                            codDitta=setCodiceRagioneSociale(); 
                            if (codDitta!=null)
                            {
                                // setto il codice creato su aux(dittaCreata)
                                aux.setCod_ditta(codDitta);
                                //ragione socialeù
                                aux.setRagioneSociale(this.txtRagioneSociale.getText());
                            }
                            else  
                                return null;
                         }
                
                	
		
		// Nome
		aux.setNome(this.txtNome.getText());
		
		
		// Codice fiscale 
                //che può essere di 11 o 16 cifre 
                // si esegue un controllo sull'immissione dell'utente
		if (this.txtCodFiscale.getText().equals("") )
		{
			alert.setContentText("Il codice fiscale è un campo obbligatorio.");
                        alert.showAndWait();
			this.txtCodFiscale.requestFocus();
    
			return null;
                        
		}      
                else   
                    
                    if (this.txtCodFiscale.getText().length()==16)
                    {
                         // controllo il codice fiscale. se il metodo ritorna una stringa vuota , è corretto
                        if ( ! ControllaCF(txtCodFiscale.getText()).equals("")           )
                            {
                                // mostro l'errore nel codice fiscale
                                alert.setContentText(ControllaCF(txtCodFiscale.getText()));
                                alert.showAndWait();
                                this.txtCodFiscale.requestFocus();

                                return null;
                            } 
                                    // setto il modello della ditta con il codice fiscale
                            else    aux.setCodice_fiscale(this.txtCodFiscale.getText());
                    }
                    
                
                
		
                
                
		
		// Partita Iva
		if (this.txtPartitaIva.getText().equals(""))
		{
			alert.setContentText("La partita IVA è un campo obbligatorio.");
                        alert.showAndWait();
			this.txtPartitaIva.requestFocus();
			return null;
		}
                else    if ( ! ControllaPIVA(txtPartitaIva.getText()).equals("")           )
                        {
                            // mostro l'errore nella partita iva
                            alert.setContentText(ControllaPIVA(txtPartitaIva.getText()));
                            alert.showAndWait();
                            this.txtPartitaIva.requestFocus();
                            
                            return null;
                        } 
                                // setto il modello della ditta con la partita iva
                        else    aux.setPartita_iva(this.txtPartitaIva.getText());
	
				
		
		
		
                System.out.println("********** DATI DITTA :");
                System.out.println("tipo ditta :  " + aux.getTipo_ditta());
                System.out.println("descrizione :  " + aux.getDescrizione());
                System.out.println("codice ditta :  " + aux.getCod_ditta());
		System.out.println("codice fiscale :  " + aux.getCodice_fiscale());
                System.out.println("cognome :  " + aux.getCognome());
                System.out.println("nome :  " + aux.getNome());
                System.out.println("partita iva :  " + aux.getPartita_iva());
                System.out.println("ragione sociale :  " + aux.getRagioneSociale());
                System.out.println("id ditta :  " + aux.getId_ditta());
                System.out.println("**********FINE DATI DITTA :");
		return aux;
	}
      
 
        
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
  
        tipoNuovoModificaListener();
        tipoDittaListener();
        
        txtCognome.requestFocus();
        
    }    
    
}
