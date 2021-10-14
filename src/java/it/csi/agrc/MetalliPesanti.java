package it.csi.agrc;

import it.csi.cuneo.*;

import java.sql.*;

/**
 * <p>Title: Agrichim - Front Office</p>
 * <p>Description: Richiesta analisi chimiche su campioni biologici agrari</p>
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>Company: CSI Piemonte - Progettazione e Sviluppo - Cuneo</p>
 * @author Nadia B.
 * @version 1.0.0
 */

public class MetalliPesanti extends BeanDataSource
{
  private long idRichiesta;
  private String ferroTotale;
  private String manganeseTotale;
  private String zincoTotale;
  private String rameTotale;
  private String piomboTotale;
  private String cromoTotale;
  private String boroTotale;
  private String nichelTotale;
  private String cadmioTotale;
  private String stronzioTotale;
  private String altroMetalloTotale;
  
  public MetalliPesanti()
  {
  }

  public MetalliPesanti(long idRichiesta, String ferroTotale, String manganeseTotale, String zincoTotale, String rameTotale, String piomboTotale, String cromoTotale, String boroTotale, String nichelTotale, String cadmioTotale, String stronzioTotale, String altroMetalloTotale)
  {
    this.idRichiesta = idRichiesta;
    this.ferroTotale = ferroTotale;
    this.manganeseTotale = manganeseTotale;
    this.zincoTotale = zincoTotale;
    this.rameTotale = rameTotale;
    this.piomboTotale = piomboTotale;
    this.cromoTotale = cromoTotale;
    this.boroTotale = boroTotale;
    this.nichelTotale = nichelTotale;
    this.cadmioTotale = cadmioTotale;
    this.stronzioTotale = stronzioTotale;
    this.altroMetalloTotale = altroMetalloTotale;
  }

  public long getIdRichiesta()
  {
    return this.idRichiesta;
  }
  public void setIdRichiesta( long newIdRichiesta )
  {
    this.idRichiesta = newIdRichiesta;
  }

  public String getFerroTotale()
	{
		return ferroTotale;
	}

	public void setFerroTotale(String newFerroTotale)
	{
    if (newFerroTotale != null) ferroTotale = newFerroTotale.replace(',','.');
    else this.ferroTotale = newFerroTotale;
	}

	public String getManganeseTotale()
	{
		return manganeseTotale;
	}

	public void setManganeseTotale(String newManganeseTotale)
	{
    if (newManganeseTotale != null) manganeseTotale = newManganeseTotale.replace(',','.');
    else this.manganeseTotale = newManganeseTotale;
	}

	public String getZincoTotale()
	{
		return zincoTotale;
	}

	public void setZincoTotale(String newZincoTotale)
	{
    if (newZincoTotale != null) zincoTotale = newZincoTotale.replace(',','.');
    else this.zincoTotale = newZincoTotale;
	}

	public String getRameTotale()
	{
		return rameTotale;
	}

	public void setRameTotale(String newRameTotale)
	{
    if (newRameTotale != null) rameTotale = newRameTotale.replace(',','.');
    else this.rameTotale = newRameTotale;
	}

	public String getPiomboTotale()
	{
		return piomboTotale;
	}

	public void setPiomboTotale(String newPiomboTotale)
	{
    if (newPiomboTotale != null) piomboTotale = newPiomboTotale.replace(',','.');
    else this.piomboTotale = newPiomboTotale;
	}

	public String getCromoTotale()
	{
		return cromoTotale;
	}

	public void setCromoTotale(String newCromoTotale)
	{
    if (newCromoTotale != null) cromoTotale = newCromoTotale.replace(',','.');
    else this.cromoTotale = newCromoTotale;
	}

	public String getBoroTotale()
	{
		return boroTotale;
	}

	public void setBoroTotale(String newBoroTotale)
	{
    if (newBoroTotale != null) boroTotale = newBoroTotale.replace(',','.');
    else this.boroTotale = newBoroTotale;
	}

	public String getNichelTotale()
	{
		return nichelTotale;
	}

	public void setNichelTotale(String newNichelTotale)
	{
    if (newNichelTotale != null) nichelTotale = newNichelTotale.replace(',','.');
    else this.nichelTotale = newNichelTotale;
	}

	public String getCadmioTotale()
	{
		return cadmioTotale;
	}

	public void setCadmioTotale(String newCadmioTotale)
	{
    if (newCadmioTotale != null) cadmioTotale = newCadmioTotale.replace(',','.');
    else this.cadmioTotale = newCadmioTotale;
	}

	public String getStronzioTotale()
	{
		return stronzioTotale;
	}

	public void setStronzioTotale(String newStronzioTotale)
	{
    if (newStronzioTotale != null) stronzioTotale = newStronzioTotale.replace(',','.');
    else this.stronzioTotale = newStronzioTotale;
	}

	public String getAltroMetalloTotale()
	{
		return altroMetalloTotale;
	}

	public void setAltroMetalloTotale(String newAltroMetalloTotale)
	{
    if (newAltroMetalloTotale != null) altroMetalloTotale = newAltroMetalloTotale.replace(',','.');
    else this.altroMetalloTotale = newAltroMetalloTotale;
	}

	/**
   * Questo metodo va a leggere il record della tabella METALLI_PESANTI
   * con idRichiesta uguale a qullo memorizzato nelll'attributo idRichiesta.
   * Il contenuto del record viene memorizzato all'interno del bean
   * @return se trova un record restituisce true, altrimenti false
   * @throws Exception
   * @throws SQLException
   */
  public boolean select() throws Exception, SQLException
  {
    if (! isConnection())
    {
      throw new Exception("Riferimento a DataSource non inizializzato");
    }

    Connection conn = null;
    Statement stmt = null;
    StringBuffer query = new StringBuffer("");

    try
    {
      conn = getConnection();
      stmt = conn.createStatement();

      query = new StringBuffer(" SELECT ID_RICHIESTA, FERRO_TOTALE, MANGANESE_TOTALE, ");
      query.append(" ZINCO_TOTALE, RAME_TOTALE, PIOMBO_TOTALE, ");
      query.append(" CROMO_TOTALE, BORO_TOTALE, NICHEL_TOTALE, ");
      query.append(" CADMIO_TOTALE, STRONZIO_TOTALE, ALTRO_METALLO_TOTALE ");
      query.append(" FROM METALLI_PESANTI ");
      query.append(" WHERE ID_RICHIESTA = ");
      query.append(getIdRichiesta());
      //CuneoLogger.debug(this, query.toString());

      ResultSet rs = stmt.executeQuery(query.toString());
      String temp;
      if (rs.next())
      {
      	setIdRichiesta(rs.getLong("ID_RICHIESTA"));
      	
        temp = rs.getString("FERRO_TOTALE");
        if (temp != null) temp = Utili.nf3.format(Double.parseDouble(temp));
        setFerroTotale(temp);

        temp = rs.getString("MANGANESE_TOTALE");
        if (temp != null) temp = Utili.nf3.format(Double.parseDouble(temp));
        setManganeseTotale(temp);

        temp = rs.getString("ZINCO_TOTALE");
        if (temp != null) temp = Utili.nf3.format(Double.parseDouble(temp));
        setZincoTotale(temp);

        temp = rs.getString("RAME_TOTALE");
        if (temp != null) temp = Utili.nf3.format(Double.parseDouble(temp));
        setRameTotale(temp);

        temp = rs.getString("PIOMBO_TOTALE");
        if (temp != null) temp = Utili.nf3.format(Double.parseDouble(temp));
        setPiomboTotale(temp);

        temp = rs.getString("CROMO_TOTALE");
        if (temp != null) temp = Utili.nf3.format(Double.parseDouble(temp));
        setCromoTotale(temp);

        temp = rs.getString("BORO_TOTALE");
        if (temp != null) temp = Utili.nf3.format(Double.parseDouble(temp));
        setBoroTotale(temp);

        temp = rs.getString("NICHEL_TOTALE");
        if (temp != null) temp = Utili.nf3.format(Double.parseDouble(temp));
        setNichelTotale(temp);

        temp = rs.getString("CADMIO_TOTALE");
        if (temp != null) temp = Utili.nf3.format(Double.parseDouble(temp));
        setCadmioTotale(temp);

        temp = rs.getString("STRONZIO_TOTALE");
        if (temp != null) temp = Utili.nf3.format(Double.parseDouble(temp));
        setStronzioTotale(temp);

        temp = rs.getString("ALTRO_METALLO_TOTALE");
        if (temp != null) temp = Utili.nf3.format(Double.parseDouble(temp));
        setAltroMetalloTotale(temp);

        return true;
      }
      else
    	{
    		return false;
    	}
    }
    catch (java.sql.SQLException ex)
    {
      this.getAut().setQuery("select della classe MetalliPesanti");
      this.getAut().setContenutoQuery(query.toString());
      throw (ex);
    }
    catch (Exception e)
    {
      this.getAut().setQuery("select della classe MetalliPesanti"
                            +": non è una SQLException ma una Exception"
                            +" generica");
      this.getAut().setContenutoQuery(query.toString());
      throw (e);
    }
    finally
    {
      stmt.close();
      if (conn != null)
    	{
    		conn.close();
    	}
    }
  }
}