/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meilfx.ammin.utenti.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import meilfx.connect.Connessione;
import static meilfx.constants.Constant.DATABASE;

/**
 *
 * @author luca
 */
public class UtentiSql {
    
    public static List<String> getUtenti() {
        List<String> listaDitte = new ArrayList<>();

        try {
            String query = "SELECT email \n" +
                        "FROM "+DATABASE+".utenti \n" +
                        "WHERE id >= 2000";
            Connection conn = Connessione.getConnection();
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            System.out.println(preparedStmt);
            ResultSet rs = preparedStmt.executeQuery();

            while (rs.next()) {
                String email = rs.getString(1);
                listaDitte.add(email);
            }

        } catch (SQLException sqle) {
        }
        return listaDitte;

    }
    
}
