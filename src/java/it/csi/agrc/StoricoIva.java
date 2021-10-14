package it.csi.agrc;

import it.csi.cuneo.BeanDataSource;
import it.csi.cuneo.Utili;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * <p>Title: Agrichim - Front Office</p>
 * <p>Description: Richiesta analisi chimiche su campioni biologici agrari</p>
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>Company: CSI Piemonte - Progettazione e Sviluppo - Cuneo</p>
 * @author Nadia B.
 * @version 1.0.0
 */

public class StoricoIva extends BeanDataSource
{
  private String idStoricoIva;
  private String dataInizioValidita;
  private String dataFineValidita;
  private String valore;

  public StoricoIva ()
  {
  }

  /**
   * Costruttore utilizzato per gestire la stampa dello storico
   * */
  public StoricoIva (String idStoricoIva, String dataInizioValidita, String dataFineValidita, String valore)
  {
    this.idStoricoIva = idStoricoIva;
    this.dataInizioValidita = dataInizioValidita;
    this.dataFineValidita = dataFineValidita;
    this.valore = valore;
  }

  /**
   * Questa funzione viene utilizzata per cercare l'iva valida storicizzata, al momento dell'emissione della fattura
   * @param data: data emissione fattura
   * @throws Exception
   * @throws SQLException
   */
  public void selectStoricoIvaByData(Date data)
  throws Exception, SQLException
  {
    if (!isConnection())
      throw new Exception("Riferimento a DataSource non inizializzato");

    Connection conn = null;
    StringBuffer query = new StringBuffer("");
    PreparedStatement stmt = null;

    try
    {
      query = new StringBuffer(" SELECT ID_STORICO_IVA, DATA_INIZIO_VALIDITA, DATA_FINE_VALIDITA, VALORE ");
      query.append(" FROM STORICO_IVA ");
      query.append(" WHERE DATA_INIZIO_VALIDITA <= coalesce(?, now()) ");
      query.append(" AND coalesce(DATA_FINE_VALIDITA, now() + INTERVAL '1 hour') >= coalesce(?, now())");

    	conn = getConnection();
    	stmt = conn.prepareStatement(query.toString());
      
    	stmt.setTimestamp(1, convertDateToTimestamp(data));
    	stmt.setTimestamp(2, convertDateToTimestamp(data));

      ResultSet rset = stmt.executeQuery();
      if (rset.next())
      {
        setIdStoricoIva(rset.getString("ID_STORICO_IVA"));
        setDataInizioValidita(rset.getString("DATA_INIZIO_VALIDITA"));
        setDataFineValidita(rset.getString("DATA_FINE_VALIDITA"));
        setValore(rset.getString("VALORE"));
      }
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("selectStoricoIvaByData della classe StoricoIva");
      this.getAut().setContenutoQuery(query.toString());
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("selectStoricoIvaByData della classe StoricoIva"
                            +": non è una SQLException ma una Exception"
                            +" generica");
      this.getAut().setContenutoQuery(query.toString());
      throw (e);
    }
    finally
    {
      if (conn!=null) conn.close();
    }
  }

	public String getIdStoricoIva() {
		return idStoricoIva;
	}

	public void setIdStoricoIva(String idStoricoIva) {
		this.idStoricoIva = idStoricoIva;
	}

	public String getDataInizioValidita() {
		return dataInizioValidita;
	}

	public void setDataInizioValidita(String dataInizioValidita) {
		this.dataInizioValidita = dataInizioValidita;
	}

	public String getDataFineValidita() {
		return dataFineValidita;
	}

	public void setDataFineValidita(String dataFineValidita) {
		this.dataFineValidita = dataFineValidita;
	}

	public String getValore() {
		return valore;
	}

	public void setValore(String valore) {
		this.valore = valore;
	}

	public String getIvaFormattata()
	{
		try
		{
			BigDecimal bd = new BigDecimal(valore);

			//Non sono presenti decimali
			return Utili.formatNumberPercent("" + bd.intValueExact(), false);
		}
		catch (ArithmeticException ex)
		{
			//Numero con decimali
			return Utili.formatNumberPercent(valore, true);
		}
	}
}