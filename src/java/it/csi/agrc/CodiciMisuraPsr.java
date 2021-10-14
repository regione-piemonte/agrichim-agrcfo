package it.csi.agrc;

import it.csi.cuneo.BeanDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

/**
 * <p>Title: Agrichim - Front Office</p>
 * <p>Description: Richiesta analisi chimiche su campioni biologici agrari</p>
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>Company: CSI Piemonte - Progettazione e Sviluppo - Cuneo</p>
 * @author Nadia B.
 * @version 1.0.0
 */

public class CodiciMisuraPsr extends BeanDataSource
{
  private Vector<CodiceMisuraPsr> elenco = new Vector<CodiceMisuraPsr>();

  public CodiciMisuraPsr()
  {
  }

  public CodiciMisuraPsr(Object dataSource, Autenticazione aut)
  {
    this.setDataSource(dataSource);
    this.setAut(aut);
  }
 
  public void fill() throws Exception, SQLException
  {
    if (! isConnection())
    {
      throw new Exception("Riferimento a DataSource non inizializzato");
    }

		Connection conn = this.getConnection();
		StringBuffer query = new StringBuffer("");
		Statement stmt = null;

		try
		{
		   stmt = conn.createStatement();
		
		   query.append("SELECT CODICE_MISURA_PSR, DESCRIZIONE_MISURA_PSR, MOTIVO_OBBLIGATORIO, ");
		   query.append("DATA_INIZIO_VALIDITA, DATA_FINE_VALIDITA ");
		   query.append("FROM CODICE_MISURA_PSR ");
		   query.append("WHERE DATA_FINE_VALIDITA IS NULL ");
		   query.append("ORDER BY ORDINAMENTO");
		
		   String queryStr = query.toString();
		
		   ResultSet rset = stmt.executeQuery(queryStr);
		   while (rset.next())
		   {
		  	 add(new CodiceMisuraPsr(
		    		 											rset.getString("CODICE_MISURA_PSR"),
		                      				rset.getString("DESCRIZIONE_MISURA_PSR"),
		                      				rset.getString("MOTIVO_OBBLIGATORIO"),
		                      				rset.getString("DATA_INIZIO_VALIDITA"),
		                      				rset.getString("DATA_FINE_VALIDITA")
		                      			)
		    	);
		   }

		   rset.close();
		   stmt.close();
		}
		catch (java.sql.SQLException ex)
		{
		 this.getAut().setQuery("fill della classe CodiciMisuraPsr");
		 this.getAut().setContenutoQuery(query.toString());
		 ex.printStackTrace();
		 throw (ex);
		}
		catch (Exception e)
		{
		 this.getAut().setQuery("fill della classe CodiciMisuraPsr"
		                       +": non è una SQLException ma una Exception"
		                       +" generica");
		 e.printStackTrace();
		 throw (e);
		}
		finally
		{
		 if (conn != null) conn.close();
		}
  }

  public int size()
  {
    return elenco.size();
  }

  public CodiceMisuraPsr get(int i)
  {
    return elenco.elementAt(i);
  }

  public void add(CodiceMisuraPsr i)
  {
  	elenco.addElement(i);
  }
}