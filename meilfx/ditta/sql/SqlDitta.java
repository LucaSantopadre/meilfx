/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meilfx.ditta.sql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.ObservableList;
import meilfx.connect.Connessione;
import meilfx.creaDitta.model.MdlDitta;
import meilfx.utility.Functions;

/**
 *
 * @author luca
 */
public class SqlDitta {
    
    
    	public static String CAMPI_PRIVATI = 
		"persona_fisica, " +
		"cognome, " +
		"nome, " +
                "ragione_sociale, " +
		"codiceFiscale, " +
		"partitaIva";
    
    
    
    public static String getCodiceDitta(String str)
	{
		String aux = null;

		try {
			String sql = "show databases";
			Statement stmt = Connessione.getStatement();
			ResultSet rs = stmt.executeQuery(sql);
			ArrayList<String> codici = new ArrayList<>();
			while (rs.next())
			{
				String codice = rs.getString(1);
				codice = codice.toUpperCase();
				str = str.toUpperCase();

				if(meilfx.utility.Functions.isCodiceDitta(codice))
				{
					//i codici sono ordinati,basta prendere l'ultimo
					//array codici per controllare che il codice scelto non sia gia occupato
					if(codice.startsWith(str))
					{
						codici.add(codice);
						aux = codice;	
					}

				}
			}
			boolean codiceLibero = false;
			int x = 0;
			if (aux != null) {
				x = Integer.parseInt(aux.substring(2));
			}

			while(!codiceLibero)
			{
				x++;
				if (x < 10)
					aux = str + "00" + x;
				else if (x < 100)
					aux = str + "0" + x;
				else
					aux = str + x;

				if(!codici.contains(aux))
					codiceLibero = true;
			}

		}
		catch (SQLException sqle)
		{
			sqle.printStackTrace();
		}
		return aux;


	}
    
    
    
    
        public static List<String> getCodiciDitte()
        {
                List<String> codici = new ArrayList<>();

		try {
			String sql = "show databases";
			Statement stmt = Connessione.getStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			while (rs.next())
			{
				String codice = rs.getString(1);
				codice = codice.toUpperCase();
				//codici.add(codice);

				if(meilfx.utility.Functions.isCodiceDitta(codice))
				{
                                    codici.add(codice);	
				}
			}

		}
		catch (SQLException sqle)
		{
			sqle.printStackTrace();
		}
		return codici;

	}

    
        
        
        public static boolean InserisciPrivato(MdlDitta ditta)
	{
                
		String sql = "insert into " + ditta.getCod_ditta() + ".2018az(" + CAMPI_PRIVATI + ") values (" +
		"'N', '" +
		ditta.getCognome() + "', '" +
		ditta.getNome() + "', '" +
                ditta.getRagioneSociale()+ "', '" +
		ditta.getCodice_fiscale() + "', '" +
		ditta.getPartita_iva() + "')";
                
                System.out.println("esecuzione query: "+sql);
		
		Functions.EseguiQuery(sql, true);
		
		
		sql = "insert into " + ditta.getCod_ditta() + ".2055az (punto_lavoro_fiscale_codice_punto,punto_lavoro_fiscale_codice_punto_descrizione) values ('0', 'SEDE')"; 
		
                System.out.println("esecuzione query: "+sql);
                
                return Functions.EseguiQuery(sql, true);
                

	}
        
}
