package meilfx.utility;


import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;


import java.net.URI;
import java.net.URISyntaxException;

public class ManageFiles {

	public boolean copy (String originFilePath, String destinationFilePath){
	  boolean result = false;
	
	  try{
		  result = false;
		  
          File f1 = new File(originFilePath);
          File f2 = new File(destinationFilePath);
          InputStream in = new FileInputStream(f1);

          //For Overwrite the file.
          OutputStream out = new FileOutputStream(f2);

          byte[] buf = new byte[1024];
          int len;
          while ((len = in.read(buf)) > 0){
            out.write(buf, 0, len);
          }
          
          in.close();
          out.close();
          
          result = true;
        }
        catch(FileNotFoundException ex){
          System.err.println(ex.getMessage() + " in the specified directory.");
          result = false;
        }
        catch(IOException e){
          System.err.println(e.getMessage());      
          result = false;
        }		
		
		
	  return result;
	}
	
	
	
	public void openFolder(String folderPath){
		File foler = new File(folderPath); // path to the directory to be opened
        Desktop desktop = null;
        if (Desktop.isDesktopSupported()) {
        desktop = Desktop.getDesktop();
        }

        try {
        desktop.open(foler);
        } catch (IOException e) {
        }	
     }
	

	public void openPdfOnFly(String pdfFilePath){
		if (Desktop.isDesktopSupported()) {
		    try {
		    	
		        File myFile = new File(pdfFilePath);
		        Desktop.getDesktop().open(myFile);
		        
		    } catch (IOException ex) {
		        // no application registered for PDFs
				System.err.println("****************  Error  **********************");
				System.err.println("** Class      : ManageFiles");
				System.err.println("** Method     : openPdfOnFly ");
				System.err.println("** pdfFilePath: " + pdfFilePath);
				System.err.println("** Message    : " + ex.getMessage());
				System.err.println("**************************************************");
		    }
		}
     }
	
    /*
     * Apre una pagina web sul browser di default
     * del sistema operativo.
     * 
     * Ex : openHtmlOnFly("http://www.google.it")
     */
	public void openHtmlOnFly(String htmlUrl){
		if (Desktop.isDesktopSupported()) {
		    try {
		    	
		    	if(Desktop.isDesktopSupported())
		    	{
					Desktop.getDesktop().browse(new URI(htmlUrl));
		    	}
		    	
		    } 
		    catch (URISyntaxException e) {
				System.err.println("****************  Error  **********************");
				System.err.println("** Class      : ManageFiles");
				System.err.println("** Method     : openHtmlOnFly ");
				System.err.println("** htmlUrl    : " + htmlUrl);
				System.err.println("** Message    : " + e.getMessage());
				System.err.println("**************************************************");
			}
		    catch (IOException ex) {

				System.err.println("****************  Error  **********************");
				System.err.println("** Class      : ManageFiles");
				System.err.println("** Method     : openHtmlOnFly ");
				System.err.println("** htmlUrl    : " + htmlUrl);
				System.err.println("** Message    : " + ex.getMessage());
				System.err.println("**************************************************");
		    }
		}
     }
	
	
	
	public void stringToFile(String text, String filePath){
		PrintStream out = null;
		try {
		    try {
				out = new PrintStream(new FileOutputStream(filePath));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		    out.print(text);
		}
		finally {
		    if (out != null) out.close();
		}	
	}
	
	
	public String completeRelativeUrl(String relativeUrl){
		String result = "";
		String contextPath = "";
		
		try {
			contextPath = new File(".").getCanonicalPath();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		result = "file://" + contextPath + "/" + relativeUrl;
		
		return result;
	}

	
	public boolean deleteFile(String filePath){
		boolean result = false;

	    // A File object to represent the filename
	    File f = new File(filePath);

	    // Make sure the file or directory exists and isn't write protected
	    if (!f.exists())
	      throw new IllegalArgumentException(
	          "Delete: no such file or directory: " + filePath);

	    if (!f.canWrite())
	      throw new IllegalArgumentException("Delete: write protected: "
	          + filePath);

	    // If it is a directory, make sure it is empty
	    if (f.isDirectory()) {
	      String[] files = f.list();
	      if (files.length > 0)
	        throw new IllegalArgumentException(
	            "Delete: directory not empty: " + filePath);
	    }

	    // Attempt to delete it
	    result = f.delete();

	    if (!result)
	      throw new IllegalArgumentException("Delete: deletion failed");
		
		
		
		return result;
	}
	
	
	
	public boolean setExecutablePermission(String filePath){
		boolean result = false;
		
		File file = new File(filePath);
		boolean ownerPermissionOK 		= false;
		boolean everybodyPermissionOK 	= false;
		
		
		if (file.exists()) {
			ownerPermissionOK = file.setExecutable(true);
        } else {
            System.err.println("filePath : " + filePath + "  File cannot exists: ");
        }

       if (file.exists()) {
    	   everybodyPermissionOK = file.setExecutable(true,false);
        } else {
            System.err.println("filePath : " + filePath + "  File cannot exists: ");
        }

		if ( ownerPermissionOK && everybodyPermissionOK ){
			result = true;
		}
		
		return result;
	}
	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ManageFiles manageFiles 	= new ManageFiles();

		System.out.println("********* Begin ManageFiles ************");
		
		manageFiles.setExecutablePermission("/home/idec/temp/pippo.sh"); 
	    
		
		System.out.println("********* End   ManageFiles ************");
	}

}
