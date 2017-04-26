package meilfx.utility;

import java.io.File;

/**
 * Questa classe controlla il sistema operativo 
 * host dove gira Java
 * @author ubuntu
 *
 */
public class CheckHostOS {
	
	public static final int UNDEFINED 	= 0;
	public static final int LINUX 		= 1;
	public static final int MAC 		= 2;
	public static final int WINDOWS 	= 3;
	
	public CheckHostOS(){
		
	}
	
    public static int getHostOs(){
     int result = 0;	
     String osJavaParameter = "";
     
     osJavaParameter =  System.getProperty("os.name"); 
     //System.out.println("osJavaParameter = " + osJavaParameter);
     
     result = UNDEFINED;
     if  ( osJavaParameter != null ){
    	if ( osJavaParameter.startsWith("Linux") ){
    		result = LINUX;
    	}
    	else
    	if ( osJavaParameter.startsWith("Mac") ){
    	
    		result = MAC;
    	}
    	else
    	if ( osJavaParameter.startsWith("Windows") ){
    		result = WINDOWS;
    	}

    	 
    	 
     }
     
     
     
     return result;
    }	

    
    

    public static boolean isUndefined(){
        boolean result  = false;
        int  hostOs     = 0; 	
        
        hostOs = getHostOs();
        if ( hostOs == UNDEFINED ){
      	  result  = true; 
        }
        
        return result;	
      }
   
    
    
    public static boolean isWindows(){
      boolean result  = false;
      int  hostOs     = 0; 	
      
      hostOs = getHostOs();
      if ( hostOs == WINDOWS ){
    	  result  = true; 
      }
      
      return result;	
    }
    

    public static boolean isLinux(){
        boolean result  = false;
        int  hostOs     = 0; 	
        
        hostOs = getHostOs();
        if ( hostOs == LINUX ){
      	  result  = true; 
        }
        
        return result;	
      }
   
    
    public static boolean isMac(){
        boolean result  = false;
        int  hostOs     = 0; 	
        
        hostOs = getHostOs();
        if ( hostOs == MAC ){
      	  result  = true; 
        }
        
        return result;	
      }
    
 
     
    public static String getHostOsString(){
        String result = "";
        
        if ( isUndefined() ){
          result = "undefined";	
        }
        else
        if ( isLinux() ){
          result = "linux";	
        }
        else
        if ( isMac() ){
          result = "mac";	
        }
        else
        if ( isWindows() ){
          result = "windows";	
        }
        
        return result;	
       }
   
 
    public static String getCorrectOsSlash(){
      String result = "";
      
      result = File.separator;
      
      return result;
    }
    
    public static String dos2Unix(String text){
        String result = "";

        //Si normalizzano le andate a capo
        if ( text != null ){
            result = text.replaceAll("\r\n", "\n"); 
        }
        
        return result;
      }

    
    public static StringBuffer dos2Unix(StringBuffer text){
        StringBuffer result = new StringBuffer();
        String textString   = "";
        
        
        if ( text != null ){
            textString = text.toString();

            //Si normalizzano le andate a capo
        	textString = textString.replaceAll("\r\n", "\n");
        	result.append(textString);
        }
        
        return result;
      }
   
    
    public static String applySlashUnix(String text){
        String result = "";

        //Si normalizzano le andate a capo
        if ( text != null ){
            result = text.replaceAll("\\", "/"); 
        }
        
        return result;
      }
    

    
    public static String getFilename(String filepath){
        String result = "";
        String correctSlash  = "";
        int lastIndexOfCorrectSlash = 0;
        int filepathLength = 0;
        
        if ( filepath != null ){
        	correctSlash  = getCorrectOsSlash();
        	lastIndexOfCorrectSlash = filepath.lastIndexOf(correctSlash); 
        	filepathLength = filepath.length();
        	if ( (lastIndexOfCorrectSlash != -1) && ( lastIndexOfCorrectSlash < filepathLength ) ){
            	result = filepath.substring(lastIndexOfCorrectSlash + 1, filepathLength);
        	}else{
        		result = filepath;
        	}
        }
        
        
        
        return result;
      }
    
    
    
    public static void main(String[] args) {
 
        
    	System.out.println(" ****** Begin  CheckHostOS ***********");
    	System.out.println(" CheckHostOS.getHostOs() 	= " + CheckHostOS.getHostOs());
    	System.out.println(" checkHostOS.isLinux() 		= " + CheckHostOS.isLinux());
    	System.out.println(" checkHostOS.isMac()		= " + CheckHostOS.isMac());
    	System.out.println(" checkHostOS.isWindows() 	= " + CheckHostOS.isWindows());
    	System.out.println(" checkHostOS.getFilename('/home/idec/file.txt') 	= " + CheckHostOS.getFilename("/home/idec/file.txt"));
    	
    	System.out.println(" ****** End   CheckHostOS ***********");

    }    
    
	
}
