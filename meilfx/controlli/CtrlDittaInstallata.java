package meilfx.controlli;


import javafx.scene.control.Alert;
import meilfx.ditta.sql.SqlDitta;

public class CtrlDittaInstallata {
	
	public static final String NAME = "CtrlDittaInstallata";
	
	
	
	public static String getNewCodiceDitta(String cognome)
	{
		String aux = "";
		
		String str = cognome.substring(0,2);
		aux = SqlDitta.getCodiceDitta(str);
		
		if ((aux==null) || (aux.equals("")))
		{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Errore");
                    alert.setHeaderText(null);
                    alert.setContentText("ERRORE " + NAME + ".2: Nuovo codice ditta non trovato;\nUscita procedura in corso...");
                    alert.showAndWait();
	
                    aux = "";
		}
		return aux;
	}

}
