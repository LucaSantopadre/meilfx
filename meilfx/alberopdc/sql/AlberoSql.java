/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meilfx.alberopdc.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import meilfx.connect.Connessione;
import static meilfx.constants.Constant.*;

/**
 *
 * @author luca classe di query sui DATABASES per tirare fuori i dati dalle
 * tabelle del PIANO DEI CONTI
 */
public class AlberoSql {
    
    
        public static String getUltimoId(int numero_padri) throws SQLException{
        
        String query = "";
        switch (numero_padri) {
            case 1: 
                query = "SELECT m_01_050.m_01_id FROM " + DATABASE + ".m_01_050 ORDER BY m_01_050.m_01_id";
                break;
            case 2:
                query = "SELECT m_02_049.m_02_id FROM " + DATABASE + ".m_02_049 ORDER BY m_02_049.m_02_id";
                break;
            case 3:
                query = "SELECT m_03_051.m_03_id FROM " + DATABASE + ".m_03_051 ORDER BY m_03_051.m_03_id";
                break;
            case 4:
                query = "SELECT m_04_052.m_04_id FROM " + DATABASE + ".m_04_052 ORDER BY m_04_052.m_04_id";
                break;
            case 5:
                query = "SELECT m_05_053.m_05_id FROM " + DATABASE + ".m_05_053 ORDER BY m_05_053.m_05_id";
                break;
            case 6:
                System.out.println("get6");
                break;
            default:
                System.out.println("NULL");
                break;
        }

        System.out.println(query);

        Connection conn = Connessione.getConnection();
        PreparedStatement preparedStmt = conn.prepareStatement(query);

        System.out.println(preparedStmt);

        ResultSet rs = preparedStmt.executeQuery();
        
        int valore = 0;
        while(rs.next())
        {
            valore  = rs.getInt(1);
        }
        
        return String.valueOf(++valore);
        
    }
    
        
        
    public static boolean controllaSePuoAvereLibro(String id)
    {
        String valoreId = null ;
        try {
            String sql = "SELECT\n" +
                            "m_01_050.m_01_libro\n" +
                            "FROM\n" +
                            DATABASE+".m_01_050\n" +
                            "WHERE\n" +
                            "m_01_050.m_01_id = 1";
            Statement stmt = Connessione.getStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                valoreId = rs.getString(1);
            }
            
            
                
            
        } catch (SQLException sqle) {
        }
        
        
        return !valoreId.equals("1");
        
    }
    

    
    public static void insertTabellaMc(int numero_padri, String id, String descrizione, String ID_esterno) throws SQLException {
        String query = "";
        switch (numero_padri) {
            case 1:
                System.out.println("NULL");
                break;
            case 2:
                query = "INSERT INTO " + DATABASE + ".mc_02(mc_02_id,mc_02_descr,m_02_ID) VALUES (?,?,?);";
                break;
            case 3:
                query = "INSERT INTO " + DATABASE + ".mc_03(mc_03_id,mc_03_descr,m_03_ID) VALUES (?,?,?);";
                break;
            case 4:
                query = "INSERT INTO " + DATABASE + ".mc_04(mc_04_id,mc_04_descr,m_04_ID) VALUES (?,?,?);";
                break;
            case 5:
                query = "INSERT INTO " + DATABASE + ".mc_05(mc_05_id,mc_05_descr,m_05_ID) VALUES (?,?,?);";
                break;
            case 6:
                System.out.println("get6");
                break;
            default:
                System.out.println("NULL");
                break;
        }

        System.out.println(query);

        Connection conn = Connessione.getConnection();
        PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.setString(1, id);
        preparedStmt.setString(2, descrizione);
        preparedStmt.setString(3, ID_esterno);

        System.out.println(preparedStmt);

        preparedStmt.execute();

        //conn.close();

    }
    
    
    public static boolean controllaSePuoCreareLibro(int numero_padri, String ID_esterno) throws SQLException{
        
        String query = "";
        switch (numero_padri) {
            case 1: 
                return true;
            case 2:
                query = "SELECT m_02_049.m_02_conto_DEF_1 FROM " + DATABASE + ".m_02_049 WHERE m_02_049.m_02_id ="+ID_esterno+";";
                break;
            case 3:
                query = "SELECT m_03_051.m_03_conto_DEF_1 FROM " + DATABASE + ".m_03_051 WHERE m_03_051.m_03_id ="+ID_esterno+";";
                break;
            case 4:
                query = "SELECT m_04_052.m_04_conto_DEF_1 FROM " + DATABASE + ".m_04_052 WHERE m_04_052.m_04_id ="+ID_esterno+";";
                break;
            case 5:
                query = "SELECT m_05_053.m_05_conto_DEF_1 FROM " + DATABASE + ".m_05_053 WHERE m_05_053.m_05_id ="+ID_esterno+";";
                break;
            case 6:
                System.out.println("get6");
                break;
            default:
                System.out.println("NULL");
                break;
        }

        System.out.println(query);

        Connection conn = Connessione.getConnection();
        PreparedStatement preparedStmt = conn.prepareStatement(query);

        //preparedStmt.setString(1, ID_esterno);

        System.out.println(preparedStmt);

        ResultSet rs = preparedStmt.executeQuery();
        
        int valore = 2;
        while(rs.next())
        {
            valore  = rs.getInt(1);
        }
        
        System.out.println("valore se puo creare libro : "+valore  + " ---legenda(1=false , 2=true)");
        return valore != 1;  
    }
    

    
    
    public static void insertTabellaM(int numero_padri, String descrizione, String idVisual,  String ID_esterno ) throws SQLException {
        String query = "";
        switch (numero_padri) {
            case 1: 
                query = "INSERT INTO " + DATABASE + ".m_01_050(m_01_descr , m_01_id_visual) VALUES (?,?);";
                break;
            case 2:
                query = "INSERT INTO " + DATABASE + ".m_02_049(m_02_descr, m_02_id_visual, m_01_ID) VALUES (?,?,?);";
                break;
            case 3:
                query = "INSERT INTO " + DATABASE + ".m_03_051(m_03_descr, m_03_id_visual, m_02_ID) VALUES (?,?,?);";
                break;
            case 4:
                query = "INSERT INTO " + DATABASE + ".m_04_052(m_04_descr, m_04_id_visual, m_03_ID) VALUES (?,?,?);";
                break;
            case 5:
                query = "INSERT INTO " + DATABASE + ".m_05_053(m_05_descr, m_05_id_visual, m_04_ID) VALUES (?,?,?);";
                break;
            case 6:
                System.out.println("get6");
                break;
            default:
                System.out.println("NULL");
                break;
        }

        System.out.println(query);

        Connection conn = Connessione.getConnection();
        PreparedStatement preparedStmt = conn.prepareStatement(query);

        preparedStmt.setString(1, descrizione);
        preparedStmt.setString(2, idVisual);
        if(numero_padri>1){
            preparedStmt.setString(3, ID_esterno);
        }

        System.out.println(preparedStmt);

        preparedStmt.execute();
        
        
        //conn.close();

    }

    public static List<String> getM_01_050() {
        List<String> descrList = new ArrayList<>();

        try {
            String sql = "SELECT m_01_id, m_01_descr, m_01_id_visual "
                    + "FROM " + DATABASE + ".m_01_050  "
                    + "ORDER BY m_01_050.m_01_id_visual;";
            Statement stmt = Connessione.getStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String id = rs.getString(1);
                String descrizione = rs.getString(2);
                String idVisual = rs.getString(3);
                descrizione = idVisual + "_" + descrizione + "." + id;


                descrList.add(descrizione);
            }

        } catch (SQLException sqle) {
        }
        return descrList;

    }

    public static List<String> getM_02_049(int m_01_ID) {
        List<String> descrList = new ArrayList<>();

        try {
            String sql = "SELECT m_02_id , m_02_descr ,m_02_id_visual \n"
                    + "FROM\n"
                    + "" + DATABASE + ".m_02_049\n"
                    + "INNER JOIN " + DATABASE + ".m_01_050 ON m_02_049.m_01_ID = m_01_050.m_01_id\n"
                    + "WHERE\n"
                    + "m_02_049.m_01_ID =" + m_01_ID + " "
                    + "ORDER BY m_02_049.m_02_id_visual;";
            Statement stmt = Connessione.getStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String id = rs.getString(1);
                String descrizione = rs.getString(2);
                String idVisual = rs.getString(3);
                descrizione = idVisual + "_" + descrizione + "." + id;


                descrList.add(descrizione);
            }

        } catch (SQLException sqle) {
        }
        return descrList;

    }

    public static List<String> getM_03_051(int m_02_ID) {
        List<String> descrList = new ArrayList<>();

        try {
            String sql = "SELECT m_03_id , m_03_descr ,m_03_id_visual \n"
                    + "FROM\n"
                    + "" + DATABASE + ".m_03_051\n"
                    + "INNER JOIN " + DATABASE + ".m_02_049 ON m_03_051.m_02_ID = m_02_049.m_02_id\n"
                    + "INNER JOIN " + DATABASE + ".m_01_050 ON m_02_049.m_01_ID = m_01_050.m_01_id\n"
                    + "WHERE\n"
                    + "m_03_051.m_02_ID = m_02_049.m_02_id AND\n"
                    + "m_03_051.m_02_ID =" + m_02_ID + " "
                    + "ORDER BY m_03_051.m_03_id_visual;";

            Statement stmt = Connessione.getStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String id = rs.getString(1);
                String descrizione = rs.getString(2);
                String idVisual = rs.getString(3);
                descrizione = idVisual + "_" + descrizione + "." + id;


                descrList.add(descrizione);
            }

        } catch (SQLException sqle) {
        }
        return descrList;

    }

    public static List<String> getM_04_052(int m_03_ID) {
        List<String> descrList = new ArrayList<>();

        try {
            String sql = "SELECT m_04_id,m_04_descr,m_04_id_visual \n"
                    + "FROM\n"
                    + "" + DATABASE + ".m_04_052\n"
                    + "INNER JOIN " + DATABASE + ".m_03_051 ON m_04_052.m_03_ID = m_03_051.m_03_id\n"
                    + "INNER JOIN " + DATABASE + ".m_02_049 ON m_03_051.m_02_ID = m_02_049.m_02_id\n"
                    + "INNER JOIN " + DATABASE + ".m_01_050 ON m_02_049.m_01_ID = m_01_050.m_01_id\n"
                    + "WHERE\n"
                    + "m_04_052.m_03_ID = m_03_051.m_03_id AND\n"
                    + "m_04_052.m_03_ID =" + m_03_ID + " "
                    + "ORDER BY m_04_052.m_04_id_visual;";

            Statement stmt = Connessione.getStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String id = rs.getString(1);
                String descrizione = rs.getString(2);
                String idVisual = rs.getString(3);
                descrizione = idVisual + "_" + descrizione + "." + id;


                descrList.add(descrizione);
            }

        } catch (SQLException sqle) {
        }
        return descrList;

    }

    public static List<String> getM_05_053(int m_04_ID) {
        List<String> descrList = new ArrayList<>();

        try {
            String sql = "SELECT m_05_id,m_05_descr,m_05_id_visual \n"
                    + "FROM\n"
                    + "" + DATABASE + ".m_05_053\n"
                    + "INNER JOIN " + DATABASE + ".m_04_052 ON m_05_053.m_04_ID = m_04_052.m_04_id\n"
                    + "INNER JOIN " + DATABASE + ".m_03_051 ON m_04_052.m_03_ID = m_03_051.m_03_id\n"
                    + "INNER JOIN " + DATABASE + ".m_02_049 ON m_03_051.m_02_ID = m_02_049.m_02_id\n"
                    + "INNER JOIN " + DATABASE + ".m_01_050 ON m_02_049.m_01_ID = m_01_050.m_01_id\n"
                    + "WHERE\n"
                    + "m_05_053.m_04_ID = m_04_052.m_04_id AND\n"
                    + "m_05_053.m_04_ID =" + m_04_ID + " "
                    + "ORDER BY m_05_053.m_05_id_visual;";

            Statement stmt = Connessione.getStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String id = rs.getString(1);
                String descrizione = rs.getString(2);
                String idVisual = rs.getString(3);
                descrizione = idVisual + "_" + descrizione + "." + id;


                descrList.add(descrizione);
            }

        } catch (SQLException sqle) {
        }
        return descrList;

    }

    public static List<String> getCodici(String dittaDB, int m_05_ID) {
        List<String> descrList = new ArrayList<>();

        try {
            String sql = "SELECT\n"
                    + dittaDB + ".2048pc.cee_codice_cod , \n"
                    + dittaDB + ".2048pc.cee_codice_cognome ,\n"
                    + dittaDB + ".2048pc.cee_mas4_cod \n"
                    + "FROM\n"
                    + dittaDB + ".2048pc ,\n"
                    + "" + DATABASE + ".m_05_053\n"
                    + "WHERE\n"
                    + dittaDB + ".2048pc.cee_mas4_cod = " + DATABASE + ".m_05_053.m_05_id AND\n"
                    + dittaDB + ".2048pc.cee_mas4_cod =" + m_05_ID + " ;";

            Statement stmt = Connessione.getStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String id = rs.getString(1);
                String descrizione = rs.getString(2);
                //String ID_esterno = rs.getString(3);
                descrizione = id + "." + descrizione ;


                descrList.add(descrizione);
            }

        } catch (SQLException sqle) {
        }
        return descrList;

    }

    public static List<String> getMc_02(int m_02_ID) {
        List<String> descrList = new ArrayList<>();

        try {
            String sql = "SELECT\n"
                    + "mc_02.mc_02_id,\n"
                    + "mc_02.mc_02_descr,\n"
                    + "mc_02.m_02_ID \n"
                    + "FROM\n"
                    + DATABASE + ".mc_02\n"
                    + "INNER JOIN " + DATABASE + ".m_02_049 ON mc_02.m_02_ID = m_02_049.m_02_id\n"
                    + "WHERE\n"
                    + "mc_02.m_02_ID = " + m_02_ID + ";";
            Statement stmt = Connessione.getStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String id = rs.getString(1);
                String descrizione = rs.getString(2);
                //String ID_esterno = rs.getString(3);
                descrizione = id + "." + descrizione ;


                descrList.add(descrizione);
            }

        } catch (SQLException sqle) {
        }
        return descrList;

    }

    public static List<String> getMc_03(int m_03_ID) {
        List<String> descrList = new ArrayList<>();

        try {
            String sql = "SELECT\n"
                    + "mc_03.mc_03_id,\n"
                    + "mc_03.mc_03_descr,\n"
                    + "mc_03.m_03_ID \n"
                    + "FROM\n"
                    + DATABASE + ".mc_03\n"
                    + "INNER JOIN " + DATABASE + ".m_03_051 ON mc_03.m_03_ID = m_03_051.m_03_id\n"
                    + "WHERE\n"
                    + "mc_03.m_03_ID = " + m_03_ID + ";";

            Statement stmt = Connessione.getStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String id = rs.getString(1);
                String descrizione = rs.getString(2);
                //String ID_esterno = rs.getString(3);
                descrizione = id + "." + descrizione;


                descrList.add(descrizione);
            }

        } catch (SQLException sqle) {
        }
        return descrList;

    }

    public static List<String> getMc_04(int m_04_ID) {
        List<String> descrList = new ArrayList<>();

        try {
            String sql = "SELECT\n"
                    + "mc_04.mc_04_id,\n"
                    + "mc_04.mc_04_descr,\n"
                    + "mc_04.m_04_ID \n"
                    + "FROM\n"
                    + DATABASE + ".mc_04\n"
                    + "INNER JOIN " + DATABASE + ".m_04_052 ON mc_04.m_04_ID = m_04_052.m_04_id\n"
                    + "WHERE\n"
                    + "mc_04.m_04_ID = " + m_04_ID + ";";

            Statement stmt = Connessione.getStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String id = rs.getString(1);
                String descrizione = rs.getString(2);
                //String ID_esterno = rs.getString(3);
                descrizione = id + "." + descrizione;


                descrList.add(descrizione);
            }

        } catch (SQLException sqle) {
        }
        return descrList;

    }

    public static List<String> getMc_05(int m_05_ID) {
        List<String> descrList = new ArrayList<>();

        try {
            String sql = "SELECT\n"
                    + "mc_05.mc_05_id,\n"
                    + "mc_05.mc_05_descr,\n"
                    + "mc_05.m_05_ID \n"
                    + "FROM\n"
                    + DATABASE + ".mc_05\n"
                    + "INNER JOIN " + DATABASE + ".m_05_053 ON mc_05.m_05_ID = m_05_053.m_05_id\n"
                    + "WHERE\n"
                    + "mc_05.m_05_ID = " + m_05_ID + ";";

            Statement stmt = Connessione.getStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String id = rs.getString(1);
                String descrizione = rs.getString(2);
                //String ID_esterno = rs.getString(3);
                descrizione = id + "." + descrizione ;


                descrList.add(descrizione);
            }

        } catch (SQLException sqle) {
        }
        return descrList;

    }
    
    
    
    public static void cancellaRecord(int numTabella , String idSelezionato) throws SQLException {
        
        String query = "";
        switch (numTabella) {
            case 1:
                query = "DELETE FROM " + DATABASE + ".m_01_050 WHERE m_01_id= ?";
                break;
            case 2:
                query = "DELETE FROM " + DATABASE + ".m_02_049 WHERE m_02_id= ?";
                break;
            case 3:
                query = "DELETE FROM " + DATABASE + ".m_03_051 WHERE m_03_id= ?";
                break;
            case 4:
                query = "DELETE FROM " + DATABASE + ".m_04_052 WHERE m_04_id= ?";
                break;
            case 5:
                query = "DELETE FROM " + DATABASE + ".m_05_053 WHERE m_05_id= ?";
                break;
            case 6:
                System.out.println("get6");
                break;
            default:
                System.out.println("NULL");
                break;
        }

        System.out.println(query);

        Connection conn = Connessione.getConnection();
        PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.setString(1, idSelezionato);


        System.out.println(preparedStmt);

        preparedStmt.execute();
    }
    
     
     
    public static boolean modificaDescrizioneM(int numero_padri, String descrizione,  String id ) throws SQLException {
        String query = "";
        switch (numero_padri) {
            case 1:
                query = "UPDATE " + DATABASE + ".m_01_050  SET m_01_descr = ?  WHERE m_01_id = ? ;";
                break;
            case 2:
                query = "UPDATE " + DATABASE + ".m_02_049  SET m_02_descr = ?  WHERE m_02_id = ? ;";
                break;
            case 3:
                query = "UPDATE " + DATABASE + ".m_03_051  SET m_03_descr = ?  WHERE m_03_id = ? ;";
                break;
            case 4:
                query = "UPDATE " + DATABASE + ".m_04_052  SET m_04_descr = ?  WHERE m_04_id = ? ;";
                break;
            case 5:
                query = "UPDATE " + DATABASE + ".m_05_053  SET m_05_descr = ?  WHERE m_05_id = ? ;";
                break;
            case 6:
                System.out.println("get6");
                break;
            default:
                System.out.println("NULL");
                break;
        }
        Connection conn = Connessione.getConnection();
        PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.setString(1, descrizione);
        preparedStmt.setString(2, id);

        System.out.println(preparedStmt);

        return !preparedStmt.execute();
        //conn.close();
    }
    
    
        public static boolean modifica_idVisualeM(int numero_padri, String id, String idVisuale) throws SQLException {
        String query = "";
        switch (numero_padri) {
            case 1:
                query = "UPDATE " + DATABASE + ".m_01_050  SET m_01_id_visual = ?  WHERE m_01_id = ? ;";
                break;
            case 2:
                query = "UPDATE " + DATABASE + ".m_02_049  SET m_02_id_visual = ?  WHERE m_02_id = ? ;";
                break;
            case 3:
                query = "UPDATE " + DATABASE + ".m_03_051  SET m_03_id_visual = ?  WHERE m_03_id = ? ;";
                break;
            case 4:
                query = "UPDATE " + DATABASE + ".m_04_052  SET m_04_id_visual = ?  WHERE m_04_id = ? ;";
                break;
            case 5:
                query = "UPDATE " + DATABASE + ".m_05_053  SET m_05_id_visual = ?  WHERE m_05_id = ? ;";
                break;
            case 6:
                System.out.println("get6");
                break;
            default:
                System.out.println("NULL");
                break;
        }
        Connection conn = Connessione.getConnection();
        PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.setString(1, idVisuale);
        preparedStmt.setString(2, id);

        System.out.println(preparedStmt);

        return !preparedStmt.execute();
        //conn.close();
    }
        
        
        public static boolean cambiaPadreM(int numero_padri, String id, String ID) throws SQLException {
        String query = "";
        switch (numero_padri) {
            case 1:
                //caso non esistente , la tabella 1 non ha padri
                //query = "UPDATE " + DATABASE + ".m_01_050  SET m_01_ID = ?  WHERE m_01_id = ? ;";
                System.out.println("caso non esistente , la tabella 1 non ha padri");
                break;
            case 2:
                query = "UPDATE " + DATABASE + ".m_02_049  SET m_01_ID = ?  WHERE m_02_id = ? ;";
                break;
            case 3:
                query = "UPDATE " + DATABASE + ".m_03_051  SET m_02_ID = ?  WHERE m_03_id = ? ;";
                break;
            case 4:
                query = "UPDATE " + DATABASE + ".m_04_052  SET m_03_ID = ?  WHERE m_04_id = ? ;";
                break;
            case 5:
                query = "UPDATE " + DATABASE + ".m_05_053  SET m_04_ID = ?  WHERE m_05_id = ? ;";
                break;
            case 6:
                System.out.println("get6");
                break;
            default:
                System.out.println("NULL");
                break;
        }
        Connection conn = Connessione.getConnection();
        PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.setString(1, ID);
        preparedStmt.setString(2, id);

        System.out.println(preparedStmt);

        return !preparedStmt.execute();
        //conn.close();
    }
     

}
