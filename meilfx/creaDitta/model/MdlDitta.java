package meilfx.creaDitta.model;

public class MdlDitta {
	
	private int id_ditta = 0;
	private String cod_ditta = "";
	private String cognome = "";
	private String nome = "";
        private String ragioneSociale = "";
	private String codice_fiscale = "";
	private String partita_iva = "";
	//private String data_scadenza_ditta = idec.Constant.DEFAULT_DATA;
	private boolean nuova_installazione = true;
        private int tipo_ditta = -1;   // 0= impresa individuale      1= societa
	
	
	//luca
	public String getDescrizione()
	{
		String aux = cod_ditta.toUpperCase()+ "_";
                    // se tipo ditta == 0 (impresa individuale)
                    if (this.tipo_ditta==0)
                    {
                       if ((this.cognome!=null) && (!this.cognome.equals("")))
				aux += this.cognome+ "  ";
			if ((this.nome!=null) && (!this.nome.equals("")))
				aux += this.nome; 
                    }
                    // altrimenti se == 1 (societa)
                    else  if (this.tipo_ditta==1)
                            {
                               if ((this.ragioneSociale!=null) && (!this.ragioneSociale.equals("")))
                                        aux += this.ragioneSociale+ "  ";
                            }

		return aux;
	}
	
        
        public int getTipo_ditta() {
		return tipo_ditta;
	}
	public void setTipo_ditta(int tipo_ditta) {
		this.tipo_ditta = tipo_ditta;
	}
        
        

	public int getId_ditta() {
		return id_ditta;
	}
	public void setId_ditta(int id_ditta) {
		this.id_ditta = id_ditta;
	}
        
        
	public String getCod_ditta() {
		return cod_ditta.toLowerCase();
	}
	public void setCod_ditta(String cod_ditta) {
		this.cod_ditta = cod_ditta;
	}
        
        
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
        
        
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
        
        
        public String getRagioneSociale() {
		return ragioneSociale;
	}
	public void setRagioneSociale(String ragioneSociale) {
		this.ragioneSociale = ragioneSociale;
	}
        
	public String getCodice_fiscale() {
		return codice_fiscale;
	}
	public void setCodice_fiscale(String codice_fiscale) {
		this.codice_fiscale = codice_fiscale;
	}
        
        
	public String getPartita_iva() {
		return partita_iva;
	}
	public void setPartita_iva(String partita_iva) {
		this.partita_iva = partita_iva;
	}
	
        
	public boolean isNuova_installazione() {
		return nuova_installazione;
	}
	public void setNuova_installazione(boolean nuova_installazione) {
		this.nuova_installazione = nuova_installazione;
	}
        
        
//	public String getData_scadenza_ditta() {
//		return data_scadenza_ditta;
//	}
//	public void setData_scadenza_ditta(String data_scadenza_ditta) {
//		this.data_scadenza_ditta = data_scadenza_ditta;
//	}
	
	

}
