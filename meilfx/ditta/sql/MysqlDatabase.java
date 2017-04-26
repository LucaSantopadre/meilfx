package meilfx.ditta.sql;


import meilfx.utility.CheckHostOS;

import java.io.File;
import java.io.FileOutputStream;
import meilfx.utility.MakeZipUnZip;
import meilfx.utility.ManageFiles;
import meilfx.utility.ManageTemplates;


public class MysqlDatabase {

private String host, port, user, password;
ManageTemplates manageTempletes = null;
ManageFiles manageFiles = null;


public MysqlDatabase(String host, String port, String user, String password) {
    this.host = host;
    this.port = port;
    this.user = user;
    this.password = password;
    
    manageTempletes = new ManageTemplates();
    manageFiles 	= new ManageFiles();
}



public boolean backupDatabase(String databaseName) throws Exception {
	  String[] databaseNameList = new String[1];
	  databaseNameList[0] = databaseName;
	  
	  return backupDatabase(databaseNameList);
}

public boolean backupDatabase(String[] databaseNameList) throws Exception {
	boolean result = false;
    
	StringBuffer batchScript = new StringBuffer();
	int databaseNameListLength = 0;

	String batchCommand = "";
	long beginMillis = 0;
	long endMillis = 0;
	long seconds = 0;
	String hostOsString = "";
	String batchName = "";
	String correctOsSlash = "";
	String scriptPath = "";
	String dumpFilePath = "";
	String zippedDumpFilePath = "";
	
	if ( CheckHostOS.isUndefined() ){
		 //Sistema operativo NON supportato 
		 return false;
	}
	else
	if ( CheckHostOS.isLinux() ){
		 batchName = "backupDB.sh";	
		 batchCommand = "./" + batchName;
	}
	else
	if ( CheckHostOS.isMac() ){
		 batchName = "backupDB.sh";	
		 batchCommand = "./" + batchName;
	}
	else
	if ( CheckHostOS.isWindows() ){
		 batchName    = "backupDB.bat";	
		 batchCommand = "cmd /C  " + batchName;
	}
	hostOsString 	= CheckHostOS.getHostOsString();
	correctOsSlash 	= CheckHostOS.getCorrectOsSlash();
	
    System.out.println("****** Inizio chiamata di sistema : " + batchCommand);
    beginMillis = System.currentTimeMillis();
	
	databaseNameListLength = databaseNameList.length;
	for (int i=0; i<databaseNameListLength; i++ ){
		batchScript = new StringBuffer();
		scriptPath 			= "/resources" + correctOsSlash + "script" + correctOsSlash;
		dumpFilePath 		= scriptPath + databaseNameList[i] + ".dump";
		zippedDumpFilePath 	= scriptPath + databaseNameList[i] + ".dump.zip";
		
		Object[] values = {  correctOsSlash,hostOsString,host,port,user,password,databaseNameList[i] };
		batchScript.append(  manageTempletes.mergeTemplateValues("/meilfx/template/syscommand/backupDB.txt", values) );

		batchScript.append("exit");
		
		//Si normalizzano le andate a capo
		if ( CheckHostOS.isLinux() || CheckHostOS.isMac() ){
			batchScript = CheckHostOS.dos2Unix(batchScript);
		}
		
	    
	    File f = new File(batchName);
	    FileOutputStream fos = new FileOutputStream(f);
	    fos.write(batchScript.toString().getBytes());
	    fos.close();
    
        //Setta il permesso di esecuzione allo script
	    if ( CheckHostOS.isLinux() || CheckHostOS.isMac() ){
	    	manageFiles.setExecutablePermission(batchName);
	    }
	    
	    
	    Process run = Runtime.getRuntime().exec(batchCommand);
	    
	    // Check for ls failure
	    try {
	        if (run.waitFor() != 0) {
	        	//Errore nel processo
	            System.err.println("exit value = " + run.exitValue());
	            result = false;
	            break;
	        }else{
	        	//Processo effettuato con successo
	        	//Creato il file dump
	        	
	        	//Esegue lo zip del file dump
	        	MakeZipUnZip.zipFile(dumpFilePath, zippedDumpFilePath);
	        	
	        	//Cancella il file dump (conservando il solo zip)
	        	manageFiles.deleteFile(dumpFilePath);
	        	
	        	System.out.println(" DB : " + databaseNameList[i] + " \tOK" + "  " + zippedDumpFilePath);
	        	result = true;
	        }
	    } catch (InterruptedException e) {
	        System.err.println(e);
	        result = false;
	        break;
	    }
	    
	}//End For

	
	//Cancella il file batch
	manageFiles.deleteFile(batchName);
	
	
	//Processo effettuato con successo
	if ( result == true ){
	    endMillis  = System.currentTimeMillis();
	    seconds = (endMillis - beginMillis)/1000;
	    System.out.println("****** Fine chiamata di sistema. Tempo (secondi) = " + seconds);
	}
	
    return result;
}


public boolean recoveryDatabase(String databaseName, String dumpName) throws Exception {
	  String[] databaseNameList = new String[1];
	  String[] dumpNameList = new String[1];
	  databaseNameList[0] = databaseName;
	  dumpNameList[0] = dumpName;
	  
	  
	  return recoveryDatabase(databaseNameList,dumpNameList);
	}

public boolean recoveryDatabase(String databaseName) throws Exception {
  String[] databaseNameList = new String[1];
  databaseNameList[0] = databaseName;
  
  return recoveryDatabase(databaseNameList);
}

public boolean recoveryDatabase(String[] databaseNameList) throws Exception {
    boolean result = false;
    
    result = recoveryDatabase(databaseNameList, databaseNameList);
    
    return result;

}

public boolean recoveryDatabase(String[] databaseNameList, String[] dumpNameList) throws Exception {
	boolean result = false;
	StringBuffer batchScript = new StringBuffer();
	int databaseNameListLength = 0;

	String batchCommand = "";
	long beginMillis = 0;
	long endMillis = 0;
	long seconds = 0;
	String hostOsString = "";
	String batchName = "";
	String correctOsSlash = "";
	String scriptPath = "";
	String dumpFilePath = "";
	String zippedDumpFilePath = "";
	
	
	if ( CheckHostOS.isUndefined() ){
		 //Sistema operativo NON supportato 
		 return false;
	}
	else
	if ( CheckHostOS.isLinux() ){
		 batchName = "recoveryDB.sh";	
		 batchCommand = "./" + batchName;
	}
	else
	if ( CheckHostOS.isMac() ){
		 batchName = "recoveryDB.sh";	
		 batchCommand = "./" + batchName;
	}
	else
	if ( CheckHostOS.isWindows() ){
		 batchName    = "recoveryDB.bat";	
		 batchCommand = "cmd /C  " + batchName;
	}
	hostOsString 	= CheckHostOS.getHostOsString();
	correctOsSlash 	= CheckHostOS.getCorrectOsSlash();

    System.out.println("****** Inizio chiamata di sistema : " + batchCommand);
    beginMillis = System.currentTimeMillis();
	
	databaseNameListLength = databaseNameList.length;
	for (int i=0; i<databaseNameListLength; i++ ){
		batchScript = new StringBuffer();
		scriptPath 			= "resources" + correctOsSlash + "script" + correctOsSlash;
		dumpFilePath 		= scriptPath + dumpNameList[i] + ".dump";
		zippedDumpFilePath 	= scriptPath + dumpNameList[i] + ".dump.zip";
		
    	//Esegue l'operazione di unzip del file dump
		MakeZipUnZip.unzip(zippedDumpFilePath, scriptPath);
		
		Object[] values = { correctOsSlash,hostOsString,databaseNameList[i], host,port,user,password,dumpNameList[i] };
		batchScript.append(  manageTempletes.mergeTemplateValues("/meilfx/template/syscommand/recoveryDB.txt", values) );

		batchScript.append("exit");
	
		//Si normalizzano le andate a capo
		if ( CheckHostOS.isLinux() || CheckHostOS.isMac() ){
			batchScript = CheckHostOS.dos2Unix(batchScript);
		}
	
    
	    File f = new File(batchName);
	    FileOutputStream fos = new FileOutputStream(f);
	    fos.write(batchScript.toString().getBytes());
	    fos.close();
    
        //Setta il permesso di esecuzione allo script
	    if ( CheckHostOS.isLinux() || CheckHostOS.isMac() ){
	    	manageFiles.setExecutablePermission(batchName);
	    }
	    
	    
	    Process run = Runtime.getRuntime().exec(batchCommand);
    
	    // Check for ls failure
	    try {
	        if (run.waitFor() != 0) {
	        	//Errore nel processo
	            System.err.println("exit value = " + run.exitValue());
	            result = false;
	            break;
	        }else{
	        	//Processo effettuato con successo
	        	
	        	//Cancella il file dump (conservando il solo zip)
	        	manageFiles.deleteFile(dumpFilePath);
	        	
	        	System.out.println(" DB : " + databaseNameList[i] + " \tOK" + "  " + zippedDumpFilePath);
	        	result = true;
	        }
	    } catch (InterruptedException e) {
	        System.err.println(e);
	        result = false;
	        break;
	    }    
	
	}//End For

	//Cancella il file batch
	manageFiles.deleteFile(batchName);
	
	
	//Processo effettuato con successo
	if ( result == true ){
	    endMillis  = System.currentTimeMillis();
	    seconds = (endMillis - beginMillis)/1000;
	    System.out.println("****** Fine chiamata di sistema. Tempo (secondi) = " + seconds);
	}
	
	
    return result;
}



public static void main(String[] args) {

	//@SuppressWarnings("unused")
	MysqlDatabase mysqlDatabase;
	
	mysqlDatabase = new MysqlDatabase("localhost","3306","idecadmin","meil21");
	
	

    //Esempio di backup database
	try {

		String[] databaseNameList = new String[44];
		databaseNameList[0] = "idec_db"; 
		databaseNameList[1] = "al001"; 
		databaseNameList[2] = "am001"; 
		databaseNameList[3] = "am002"; 
		databaseNameList[4] = "am003"; 
		databaseNameList[5] = "ar001"; 
		databaseNameList[6] = "ar002"; 
		databaseNameList[7] = "be001"; 
		databaseNameList[8] = "br001"; 
		databaseNameList[9] = "ce001"; 
		databaseNameList[10] = "ce003"; 
		databaseNameList[11] = "ce004"; 
		databaseNameList[12] = "cl001"; 
		databaseNameList[13] = "da001"; 
		databaseNameList[14] = "de001"; 
		databaseNameList[15] = "di001"; 
		databaseNameList[16] = "ed001"; 
		databaseNameList[17] = "ed002"; 
		databaseNameList[18] = "eu001"; 
		databaseNameList[19] = "fa001"; 
		databaseNameList[20] = "fb001"; 
		databaseNameList[21] = "fe001"; 
		databaseNameList[22] = "fi001"; 
		databaseNameList[23] = "gb001"; 
		databaseNameList[24] = "ge001"; 
		databaseNameList[25] = "gl001"; 
		databaseNameList[26] = "gr001"; 
		databaseNameList[27] = "ia001"; 
		databaseNameList[28] = "in001"; 
		databaseNameList[29] = "le001"; 
		databaseNameList[30] = "ma001"; 
		databaseNameList[31] = "ma002"; 
		databaseNameList[32] = "mi001"; 
		databaseNameList[33] = "mo001"; 
		databaseNameList[34] = "ne001"; 
		databaseNameList[35] = "om001"; 
		databaseNameList[36] = "pa001"; 
		databaseNameList[37] = "pm001"; 
		databaseNameList[38] = "ro001"; 
		databaseNameList[39] = "sa001"; 
		databaseNameList[40] = "se001"; 
		databaseNameList[41] = "se002"; 
		databaseNameList[42] = "si001"; 
		databaseNameList[43] = "su001"; 
		
		mysqlDatabase.backupDatabase(databaseNameList);

		//mysqlDatabase.backupDatabase("idec_db");
		
	} catch (Exception e) {

		e.printStackTrace();
	}

	

    //Esempio di recovery database
	try {
/*
		String[] databaseNameList = new String[44];
		databaseNameList[0] = "idec_db"; 
		databaseNameList[1] = "al001"; 
		databaseNameList[2] = "am001"; 
		databaseNameList[3] = "am002"; 
		databaseNameList[4] = "am003"; 
		databaseNameList[5] = "ar001"; 
		databaseNameList[6] = "ar002"; 
		databaseNameList[7] = "be001"; 
		databaseNameList[8] = "br001"; 
		databaseNameList[9] = "ce001"; 
		databaseNameList[10] = "ce003"; 
		databaseNameList[11] = "ce004"; 
		databaseNameList[12] = "cl001"; 
		databaseNameList[13] = "da001"; 
		databaseNameList[14] = "de001"; 
		databaseNameList[15] = "di001"; 
		databaseNameList[16] = "ed001"; 
		databaseNameList[17] = "ed002"; 
		databaseNameList[18] = "eu001"; 
		databaseNameList[19] = "fa001"; 
		databaseNameList[20] = "fb001"; 
		databaseNameList[21] = "fe001"; 
		databaseNameList[22] = "fi001"; 
		databaseNameList[23] = "gb001"; 
		databaseNameList[24] = "ge001"; 
		databaseNameList[25] = "gl001"; 
		databaseNameList[26] = "gr001"; 
		databaseNameList[27] = "ia001"; 
		databaseNameList[28] = "in001"; 
		databaseNameList[29] = "le001"; 
		databaseNameList[30] = "ma001"; 
		databaseNameList[31] = "ma002"; 
		databaseNameList[32] = "mi001"; 
		databaseNameList[33] = "mo001"; 
		databaseNameList[34] = "ne001"; 
		databaseNameList[35] = "om001"; 
		databaseNameList[36] = "pa001"; 
		databaseNameList[37] = "pm001"; 
		databaseNameList[38] = "ro001"; 
		databaseNameList[39] = "sa001"; 
		databaseNameList[40] = "se001"; 
		databaseNameList[41] = "se002"; 
		databaseNameList[42] = "si001"; 
		databaseNameList[43] = "su001"; 
		
		mysqlDatabase.recoveryDatabase(databaseNameList);
*/

		//String databaseName = "test";
		//mysqlDatabase.recoveryDatabase("idec_db");
		
	} catch (Exception e) {
		e.printStackTrace();
	}

	
	

}


}

