//﻿package idec.util;
package meilfx.utility;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.MessageFormat;

public class ManageTemplates {

	public String convertStreamToString(InputStream is) throws IOException {
		//
		// To convert the InputStream to String we use the
		// Reader.read(char[] buffer) method. We iterate until the
		// Reader return -1 which means there's no more data to
		// read. We use the StringWriter class to produce the string.
		//
		if (is != null) {
			Writer writer = new StringWriter();

			char[] buffer = new char[1024];
			try {
				Reader reader = new BufferedReader(new InputStreamReader(is,
						"UTF-8"));
				int n;
				while ((n = reader.read(buffer)) != -1) {
					writer.write(buffer, 0, n);
				}
			} finally {
				is.close();
			}
			return writer.toString();
		} else {
			return "";
		}
	}

	/**
	 * Restitusce il file presente nel classPath specificato
	 * 
	 * @param classPath path della classe che si desidera leggere
	 * Per il corretto funzionamento si deve trattare di un normale file di test (e non di una classe Java)
	 * che risiede però nello spazio delle classi e che verrà in certo senso compilata insieme alle classi (.class).
	 * Il file di testo a differenza delle classi non verrà ovviamente compilato.
	 * 
	 * ex: "/it/mirkocompagnoni/restful/templates/template.txt" 
	 * @return
	 */
	public String getTemplate(String classPath) {
		String result = "";

		InputStream stream = ManageTemplates.class
				.getResourceAsStream(classPath);
		try {
			result = convertStreamToString(stream);
		} catch (IOException e) {
			
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * Esegue il merge fra template e valori.
	 * @param template
	 * @param values
	 * @return
	 */
	public String mergeTemplateValues(String templateClassPath, Object[] values){
		String result   = "";
		String template = "";
		
		template = getTemplate(templateClassPath);
		
		if ( template != null && !template.trim().equals("") ){
			result = MessageFormat.format(template, values);
		}
		
		return result;
	}
	
	
	
	/**
	 * Main di prova
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("********* Begin ManageTempletes ************");
		ManageTemplates manageTempletes = new ManageTemplates();
       
		Object[] values = { "parm1", "parm2","parm3","parm4","param5" };
		System.out.println(manageTempletes.mergeTemplateValues("/idec/template/syscommand/restoreDB.txt", values));
		
		System.out.println("********* End   ManageTempletes ************");

	}

}
