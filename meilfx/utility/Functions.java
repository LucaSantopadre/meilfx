/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meilfx.utility;

import java.sql.SQLException;
import java.sql.Statement;
import meilfx.connect.Connessione;

/**
 *
 * @author luca
 */
public class Functions {
    
    public static boolean isCodiceDitta(String codDitta) {

		if (codDitta.length() != 5)
			return false;

		if (Character.isLetter(codDitta.charAt(0)))
			if (Character.isLetter(codDitta.charAt(1)))
				if (Character.isDigit(codDitta.charAt(2)))
					if (Character.isDigit(codDitta.charAt(3)))
						if (Character.isDigit(codDitta.charAt(4)))
							return true;

		return false;
	}
    
    
    
    	public static boolean EseguiQuery(String sql, boolean bMostraErrore) {
		boolean aux = false;
		try {
			// Dichiarazioni SQL
			Statement stmt = Connessione.getConnection().createStatement();
			stmt.execute(sql);

			stmt.close();
			aux = true;
		} catch (SQLException sqle) {
			if (!bMostraErrore)
				sqle.printStackTrace();
			else {
				sqle.printStackTrace();
				javax.swing.JOptionPane.showMessageDialog(null,
						"Errore nell'esecuzione della query:\n" + sql + "\n"
								+ sqle.getMessage());

			}
		}
		return aux;
	}
    
    
}
