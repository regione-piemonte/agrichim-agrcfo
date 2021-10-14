package it.csi.agrc;

import it.csi.cuneo.*;
import java.util.*;
import java.sql.*;


/**
 * <p>Title: Agrichim - Front Office</p>
 * <p>Description: Richiesta analisi chimiche su campioni biologici agrari</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: CSI Piemonte - Progettazione e Sviluppo - Cuneo</p>
 * @author Michele Piantà, Piergiorgio Chiriotti
 * @version 1.0.0
 */

public class Province extends BeanDataSource
{
  public void fill(Vector id, Vector sigla)
      throws Exception, SQLException
  {
    if (!isConnection())
           throw new Exception("Riferimento a DataSource non inizializzato");
    Connection conn=this.getConnection();
    StringBuffer query=new StringBuffer("");
    Statement stmt = null;
    try
    {
        stmt = conn.createStatement();
        query.append("SELECT ID_PROVINCIA, SIGLA_PROVINCIA,0 AS ORDINE ");
        query.append("FROM PROVINCIA ");
        query.append("WHERE ID_REGIONE='01' ");
        query.append("UNION ");
        query.append("SELECT ID_PROVINCIA, 'STATO ESTERO' AS SIGLA_PROVINCIA,1 AS ORDINE ");
		query.append("FROM PROVINCIA ");
		query.append("WHERE SIGLA_PROVINCIA='EE' ");
        query.append("UNION ");
        query.append("SELECT ID_PROVINCIA, SIGLA_PROVINCIA,2 AS ORDINE ");
        query.append("FROM PROVINCIA ");
        query.append("WHERE ID_REGIONE<>'01' ");
        query.append("AND SIGLA_PROVINCIA<>'EE' ");
        query.append("ORDER BY ORDINE,SIGLA_PROVINCIA ");

        ResultSet rset = stmt.executeQuery(query.toString());
        while (rset.next())
        {
          id.add(rset.getString("ID_PROVINCIA"));
          sigla.add(rset.getString("SIGLA_PROVINCIA"));
        }
        rset.close();
        stmt.close();
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("fill della classe Province");
      this.getAut().setContenutoQuery(query.toString());
      ex.printStackTrace();
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("fill della classe Province"
                            +": non è una SQLException ma una Exception"
                            +" generica");
      e.printStackTrace();
      throw (e);
    }
    finally
    {
      if (conn!=null) conn.close();
    }
  }

}