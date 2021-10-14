package it.csi.agrc;

/**
 * <p>Title: Agrichim - Front Office</p>
 * <p>Description: Richiesta analisi chimiche su campioni biologici agrari</p>
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>Company: CSI Piemonte - Progettazione e Sviluppo - Cuneo</p>
 * @author Nadia B.
 * @version 1.0.0
 */

public class CodiceMisuraPsr
{
  public String codiceMisuraPsr;
  public String descrizioneMisuraPsr;
  public String motivoObbligatorio;
  public String dataInizioValidita;
  public String dataFineValidita;

  public CodiceMisuraPsr ()
  {
  }
  
  public CodiceMisuraPsr(String codiceMisuraPsr, String descrizioneMisuraPsr, String motivoObbligatorio, String dataInizioValidita, String dataFineValidita)
  {
    this.codiceMisuraPsr = codiceMisuraPsr;
    this.descrizioneMisuraPsr = descrizioneMisuraPsr;
    this.motivoObbligatorio = motivoObbligatorio;
    this.dataInizioValidita = dataInizioValidita;
    this.dataFineValidita = dataFineValidita;
  }

	public String getCodiceMisuraPsr()
	{
		return codiceMisuraPsr;
	}

	public void setCodiceMisuraPsr(String codiceMisuraPsr)
	{
		this.codiceMisuraPsr = codiceMisuraPsr;
	}

	public String getDescrizioneMisuraPsr()
	{
		return descrizioneMisuraPsr;
	}

	public void setDescrizioneMisuraPsr(String descrizioneMisuraPsr)
	{
		this.descrizioneMisuraPsr = descrizioneMisuraPsr;
	}

	public String getMotivoObbligatorio()
	{
		return motivoObbligatorio;
	}

	public void setMotivoObbligatorio(String motivoObbligatorio)
	{
		this.motivoObbligatorio = motivoObbligatorio;
	}

	public String getDataInizioValidita()
	{
		return dataInizioValidita;
	}

	public void setDataInizioValidita(String dataInizioValidita)
	{
		this.dataInizioValidita = dataInizioValidita;
	}

	public String getDataFineValidita()
	{
		return dataFineValidita;
	}

	public void setDataFineValidita(String dataFineValidita)
	{
		this.dataFineValidita = dataFineValidita;
	}
}