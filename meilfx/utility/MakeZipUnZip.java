package meilfx.utility;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

//import org.apache.commons.io.IOUtils;

public class MakeZipUnZip {
 
    private static ZipFile archive;
    
    
    private static final void zipDirectoryService(File directory, File base,
            ZipOutputStream zos) throws IOException {
        File[] files = directory.listFiles();
 
        byte[] buffer = new byte[8192];
        int read = 0;
 
        for (int i = 0, n = files.length; i < n; i++) {
            if (files[i].isDirectory()) {
                zipDirectoryService(files[i], base, zos);
            } else {
                FileInputStream in = new FileInputStream(files[i]);
                ZipEntry entry = new ZipEntry(files[i].getPath().substring(
                        base.getPath().length() + 1));
                zos.putNextEntry(entry);
                while (-1 != (read = in.read(buffer))) {
                    zos.write(buffer, 0, read);
                }
                in.close();
            }
        }
    }
    
    private static final void zipFileService(File file,  ZipOutputStream zos) 
    		throws IOException {
        byte[] buffer = new byte[8192];
        int read = 0;
            	
        FileInputStream in = new FileInputStream(file);
        ZipEntry entry = new ZipEntry(  CheckHostOS.getFilename( file.getPath()) );
        zos.putNextEntry(entry);
        while (-1 != (read = in.read(buffer))) {
            zos.write(buffer, 0, read);
        }
        in.close();

    }

    
    
    public static final void zipDirectory(String directoryPathString, String zipFilePathString) throws IOException {
    	
    	File directoryPath = new File(directoryPathString);
    	File zipFilePath   = new File(zipFilePathString);
    	
    	ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFilePath));
        zipDirectoryService(directoryPath, directoryPath, zos);
        zos.close();
    }

    
    public static final void zipFile(String filePathString, String zipFilePathString) throws IOException {
    	File filePath  = new File(filePathString);
    	File zipFilePath = new File(zipFilePathString);
    	
        ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFilePath));
        zipFileService(filePath, zos);
        zos.close();
    }
   
    
    public static final void unzip(String zipFilePathString, String extractToPathString) throws IOException {
    	File zipFilePath = new File(zipFilePathString);
    	File extractToPath = new File(extractToPathString);
    	
        archive = new ZipFile(zipFilePath);
        Enumeration<?> e = archive.entries();
        
        while (e.hasMoreElements()) {
            ZipEntry entry = (ZipEntry) e.nextElement();
            File file = new File(extractToPath, entry.getName());
            if (entry.isDirectory() && !file.exists()) {
                file.mkdirs();
            } else {
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
 
                InputStream in = archive.getInputStream(entry);
                BufferedOutputStream out = new BufferedOutputStream(
                        new FileOutputStream(file));
 
                byte[] buffer = new byte[8192];
                int read;
 
                while (-1 != (read = in.read(buffer))) {
                    out.write(buffer, 0, read);
                }
                in.close();
                out.close();
            }
        }
    }
    
    
    /**
     * Esegue la compressione di una stringa e la restituisce compressa.
     * @param str
     * @return
     * @throws IOException
     */
    public static String zipString(String str) throws IOException {
		double originalStringlength   = 0;
		double zippedStringlength     = 0;
		double zippedPercentageSize = 0;
  	
    	
        if (str == null || str.length() == 0) {
            return str;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip = new GZIPOutputStream(out);
        gzip.write(str.getBytes());
        gzip.close();
        String outStr = out.toString("ISO-8859-1");
        
        
		originalStringlength   		= str.length();
		zippedStringlength 			= outStr.length();
		zippedPercentageSize      = Math.round( (zippedStringlength/originalStringlength)*100 );
		System.out.println("** zippedPercentageSize 	= " + zippedPercentageSize + "%");
       
        
        
        return outStr;
     }
    
    /**
     * Esegue l'operazione inversa di "zipString"
     * @param str
     * @return
     * @throws IOException
     */
    public static String unzipString(String str) throws IOException {
        if (str == null || str.length() == 0) {
            return str;
        }
        GZIPInputStream gis = new GZIPInputStream(new ByteArrayInputStream(str.getBytes("ISO-8859-1")));
        BufferedReader bf = new BufferedReader(new InputStreamReader(gis, "ISO-8859-1"));
        String outStr = "";
        String line;
        while ((line=bf.readLine())!=null) {
          outStr += line;
        }
        return outStr;
     }


    
    public static byte[] zipByteArray(byte[] content){
		double originalStringlength   = 0;
		double zippedStringlength     = 0;
		double zippedPercentageSize = 0;
    	
    	
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try{
            GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream);
            gzipOutputStream.write(content);
            gzipOutputStream.close();
        } catch(IOException e){
            throw new RuntimeException(e);
        }
        
		originalStringlength   		= content.length;
		zippedStringlength 			= byteArrayOutputStream.size();
		zippedPercentageSize      = Math.round( (zippedStringlength/originalStringlength)*100 );
		System.out.println("** zippedPercentageSize 	= " + zippedPercentageSize + "%");

        return byteArrayOutputStream.toByteArray();
    }

//    luca
//    public static byte[] unzipByteArray(byte[] contentBytes){
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//        try{
//            IOUtils.copy(new GZIPInputStream(new ByteArrayInputStream(contentBytes)), out);
//        } catch(IOException e){
//            throw new RuntimeException(e);
//        }
//        return out.toByteArray();
//    }

    
    
     
	public static void main(String args[]) throws IOException {
        //String rootDir = "/home/idec/workspace/IdecServer/resources/script/fi001.dump";
        //String sampleZipFile = "/home/idec/temp/fi001.dump.zip";
        //zipDirectory(rootDir, sampleZipFile);
        
        //zipFile(rootDir, sampleZipFile);
		
		String originalString 	= "";
		String zippedString 	= "";
		String unzippedString 	= "";
		
		
		
		System.out.println("************************ Begin MakeZipUnZip ***************************");
		originalString 				= "rO0ABXNyACB0ZXN0b2JqZWN0c2VyaWFsaWF6YXRpb24uUGVyc29uYQAAAAAAAAABAgADTAAHY29nbm9tZXQAEkxqYXZhL2xhbmcvU3RyaW5nO0wABWVtYWlscQB+AAFMAARub21lcQB+AAF4cHQACkNvbXBhZ25vbml0ABltaXJrb2NvbXBhZ25vbmlAbGliZXJvLml0dAAFTWlya28=";
		zippedString 				= zipString(originalString);
		unzippedString				= unzipString(zippedString);
		System.out.println("** originalString 			= " + originalString );
		System.out.println("** zippedString 			= " + zippedString );
		System.out.println("** unzippedString 			= " + unzippedString );
		System.out.println("************************ End   MakeZipUnZip ***************************");
		
		
        
    }
    
    
}