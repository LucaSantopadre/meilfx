/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meilfx.utility;

/**
 *
 * @author luca
 */
public class ControllaCFIS_PIVA {
    
    public static String ControllaCF(String cf) {
	int i, s, c;
	String cf2;
	int setdisp[] = {1, 0, 5, 7, 9, 13, 15, 17, 19, 21, 2, 4, 18, 20,
		11, 3, 6, 8, 12, 14, 16, 10, 22, 25, 24, 23 };
	if( cf.length() == 0 ) return "";
	if( cf.length() != 16 )
		return "La lunghezza del codice fiscale non è corretta:\n"
		+ "il codice fiscale dovrebbe essere lungo\n"
		+ "esattamente 16 caratteri.";
	cf2 = cf.toUpperCase();
	for( i=0; i<16; i++ ){
		c = cf2.charAt(i);
		if( ! ( c>='0' && c<='9' || c>='A' && c<='Z' ) )
			return "Il codice fiscale contiene dei caratteri non validi:\n"
			+ "i soli caratteri validi sono le lettere e le cifre.";
	}
	s = 0;
	for( i=1; i<=13; i+=2 ){
		c = cf2.charAt(i);
		if( c>='0' && c<='9' )
			s = s + c - '0';
		else
			s = s + c - 'A';
	}
	for( i=0; i<=14; i+=2 ){
		c = cf2.charAt(i);
		if( c>='0' && c<='9' )	 c = c - '0' + 'A';
		s = s + setdisp[c - 'A'];
	}
	if( s%26 + 'A' != cf2.charAt(15) )
		return "Il codice fiscale non è corretto:\n"
		+ "il codice di controllo non corrisponde.";
	return "";
}
    
    
    
    
    
    public static String ControllaPIVA(String pi)
    {
	int i, c, s;
	if( pi.length() == 0 )  return "";
	if( pi.length() != 11 )
		return "La lunghezza della partita IVA non è corretta\n"
		+ "la partita IVA dovrebbe essere lunga\n"
		+ "esattamente 11 caratteri.\n";
	for( i=0; i<11; i++ ){
		if( pi.charAt(i) < '0' || pi.charAt(i) > '9' )
			return "La partita IVA contiene dei caratteri non ammessi:\n"
			+ "la partita IVA dovrebbe contenere solo cifre.\n";
	}
	s = 0;
	for( i=0; i<=9; i+=2 )
		s += pi.charAt(i) - '0';
	for( i=1; i<=9; i+=2 ){
		c = 2*( pi.charAt(i) - '0' );
		if( c > 9 )  c = c - 9;
		s += c;
	}
	if( ( 10 - s%10 )%10 != pi.charAt(10) - '0' )
		return "La partita IVA non è valida:\n"
		+ "il codice di controllo non corrisponde.";
	return "";
    }
    
}
