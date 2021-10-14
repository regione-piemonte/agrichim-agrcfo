package it.csi.agrc;

import it.csi.cuneo.*;
import java.sql.*;
//import it.csi.jsf.web.pool.*;

/**
 * <p>Title: Agrichim - Front Office</p>
 * <p>Description: Richiesta analisi chimiche su campioni biologici agrari</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: CSI Piemonte - Progettazione e Sviluppo - Cuneo</p>
 * @author Michele Piantà, Piergiorgio Chiriotti
 * @version 1.0.0
 */

public class GranulometriaMetodoBojoucos extends BeanDataSource
{
  private long idRichiesta;
  private String letturaDensita115;
  private String letturaDensita130;
  private String letturaDensita145;
  private String densitaSoluzFondo1;
  private String temperatura1;
  private String fattoreTempGranulare1;
  private String diametro115;
  private String diametro130;
  private String diametro145;
  private String densitaLorda5012;
  private String densitaLorda5013;
  private String densitaLorda5023;
  private String densitaLorda50;
  private String sabbia;
  private String letturaDensita930;
  private String letturaDensita10;
  private String letturaDensita1045;
  private String densitaSoluzFondo2;
  private String temperatura2;
  private String fattoreTempGranulare2;
  private String diametro930;
  private String diametro10;
  private String diametro1045;
  private String densitaLorda2045;
  private String densitaLorda2046;
  private String densitaLorda2056;
  private String densitaLorda20;
  private String limoGrosso;
  private String letturaDensita17;
  private String letturaDensita1830;
  private String letturaDensita20;
  private String densitaBianco17;
  private String densitaBianco1830;
  private String densitaBianco20;
  private String densitaMediaBianco3;
  private String temperatura17;
  private String temperatura1830;
  private String temperatura20;
  private String temperaturaMedia3;
  private String fattoreTempGranulare3;
  private String diametro17;
  private String diametro1830;
  private String diametro20;
  private String densitaLorda278;
  private String densitaLorda279;
  private String densitaLorda289;
  private String densitaLorda2;
  private String limoFine;
  private String argilla1;
  private String limo;
  private String argilla2;

  public GranulometriaMetodoBojoucos ()
  {
  }
  public GranulometriaMetodoBojoucos ( long idRichiesta, String letturaDensita115, String letturaDensita130, String letturaDensita145, String densitaSoluzFondo1, String temperatura1, String fattoreTempGranulare1, String diametro115, String diametro130, String diametro145, String densitaLorda5012, String densitaLorda5013, String densitaLorda5023, String densitaLorda50, String sabbia, String letturaDensita930, String letturaDensita10, String letturaDensita1045, String densitaSoluzFondo2, String temperatura2, String fattoreTempGranulare2, String diametro930, String diametro10, String diametro1045, String densitaLorda2045, String densitaLorda2046, String densitaLorda2056, String densitaLorda20, String limoGrosso, String letturaDensita17, String letturaDensita1830, String letturaDensita20, String densitaBianco17, String densitaBianco1830, String densitaBianco20, String densitaMediaBianco3, String temperatura17, String temperatura1830, String temperatura20, String temperaturaMedia3, String fattoreTempGranulare3, String diametro17, String diametro1830, String diametro20, String densitaLorda278, String densitaLorda279, String densitaLorda289, String densitaLorda2, String limoFine, String argilla1, String limo, String argilla2 )
  {
    this.idRichiesta=idRichiesta;
    this.letturaDensita115=letturaDensita115;
    this.letturaDensita130=letturaDensita130;
    this.letturaDensita145=letturaDensita145;
    this.densitaSoluzFondo1=densitaSoluzFondo1;
    this.temperatura1=temperatura1;
    this.fattoreTempGranulare1=fattoreTempGranulare1;
    this.diametro115=diametro115;
    this.diametro130=diametro130;
    this.diametro145=diametro145;
    this.densitaLorda5012=densitaLorda5012;
    this.densitaLorda5013=densitaLorda5013;
    this.densitaLorda5023=densitaLorda5023;
    this.densitaLorda50=densitaLorda50;
    this.sabbia=sabbia;
    this.letturaDensita930=letturaDensita930;
    this.letturaDensita10=letturaDensita10;
    this.letturaDensita1045=letturaDensita1045;
    this.densitaSoluzFondo2=densitaSoluzFondo2;
    this.temperatura2=temperatura2;
    this.fattoreTempGranulare2=fattoreTempGranulare2;
    this.diametro930=diametro930;
    this.diametro10=diametro10;
    this.diametro1045=diametro1045;
    this.densitaLorda2045=densitaLorda2045;
    this.densitaLorda2046=densitaLorda2046;
    this.densitaLorda2056=densitaLorda2056;
    this.densitaLorda20=densitaLorda20;
    this.limoGrosso=limoGrosso;
    this.letturaDensita17=letturaDensita17;
    this.letturaDensita1830=letturaDensita1830;
    this.letturaDensita20=letturaDensita20;
    this.densitaBianco17=densitaBianco17;
    this.densitaBianco1830=densitaBianco1830;
    this.densitaBianco20=densitaBianco20;
    this.densitaMediaBianco3=densitaMediaBianco3;
    this.temperatura17=temperatura17;
    this.temperatura1830=temperatura1830;
    this.temperatura20=temperatura20;
    this.temperaturaMedia3=temperaturaMedia3;
    this.fattoreTempGranulare3=fattoreTempGranulare3;
    this.diametro17=diametro17;
    this.diametro1830=diametro1830;
    this.diametro20=diametro20;
    this.densitaLorda278=densitaLorda278;
    this.densitaLorda279=densitaLorda279;
    this.densitaLorda289=densitaLorda289;
    this.densitaLorda2=densitaLorda2;
    this.limoFine=limoFine;
    this.argilla1=argilla1;
    this.limo=limo;
    this.argilla2=argilla2;
  }


  /**
   * Questo metodo va a leggere il record della tabella GRANULOMETRIA_METODO_BOJOUCOS
   * con idRichiesta uguale a qullo memorizzato nelll'attributo idRichiesta.
   * Il contenuto del record viene memorizzato all'interno del bean
   * @return se trova un record restituisce true, altrimenti false
   * @throws Exception
   * @throws SQLException
   */
  public boolean select() throws Exception, SQLException
  {
    if (!isConnection())
      throw new Exception("Riferimento a DataSource non inizializzato");
    Connection conn=null;
    Statement stmt =null;
    StringBuffer query=new StringBuffer("");
    try
    {
      conn=getConnection();
      stmt = conn.createStatement();
      query = new StringBuffer("");
      query.append("SELECT * FROM GRANULOMETRIA_METODO_BOJOUCOS ");
      query.append("WHERE ID_RICHIESTA = ");
      query.append(getIdRichiesta());
      //CuneoLogger.debug(this,query.toString());
      ResultSet rs=stmt.executeQuery(query.toString());
      String temp;
      if (rs.next())
      {
        temp=rs.getString("LETTURA_DENSITA_1_15");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setLetturaDensita115(temp);

        temp=rs.getString("LETTURA_DENSITA_1_30");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setLetturaDensita130(temp);

        temp=rs.getString("LETTURA_DENSITA_1_45");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setLetturaDensita145(temp);

        temp=rs.getString("DENSITA_SOLUZ_FONDO_1");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setDensitaSoluzFondo1(temp);

        temp=rs.getString("TEMPERATURA_1");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setTemperatura1(temp);

        temp=rs.getString("FATTORE_TEMP_GRANULARE_1");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setFattoreTempGranulare1(temp);

        temp=rs.getString("DIAMETRO_1_15");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setDiametro115(temp);

        temp=rs.getString("DIAMETRO_1_30");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setDiametro130(temp);

        temp=rs.getString("DIAMETRO_1_45");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setDiametro145(temp);

        temp=rs.getString("DENSITA_LORDA_50_1_2");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setDensitaLorda5012(temp);

        temp=rs.getString("DENSITA_LORDA_50_1_3");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setDensitaLorda5013(temp);

        temp=rs.getString("DENSITA_LORDA_50_2_3");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setDensitaLorda5023(temp);

        temp=rs.getString("DENSITA_LORDA_50");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setDensitaLorda50(temp);

        temp=rs.getString("SABBIA");
        if (temp!=null) temp=Utili.nf1.format(Double.parseDouble(temp));
        this.setSabbia(temp);

        temp=rs.getString("LETTURA_DENSITA_9_30");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setLetturaDensita930(temp);

        temp=rs.getString("LETTURA_DENSITA_10");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setLetturaDensita10(temp);

        temp=rs.getString("LETTURA_DENSITA_10_45");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setLetturaDensita1045(temp);

        temp=rs.getString("DENSITA_SOLUZ_FONDO_2");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setDensitaSoluzFondo2(temp);

        temp=rs.getString("TEMPERATURA_2");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setTemperatura2(temp);

        temp=rs.getString("FATTORE_TEMP_GRANULARE_2");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setFattoreTempGranulare2(temp);

        temp=rs.getString("DIAMETRO_9_30");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setDiametro930(temp);

        temp=rs.getString("DIAMETRO_10");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setDiametro10(temp);

        temp=rs.getString("DIAMETRO_10_45");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setDiametro1045(temp);

        temp=rs.getString("DENSITA_LORDA_20_4_5");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setDensitaLorda2045(temp);


        temp=rs.getString("DENSITA_LORDA_20_4_6");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setDensitaLorda2046(temp);

        temp=rs.getString("DENSITA_LORDA_20_5_6");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setDensitaLorda2056(temp);

        temp=rs.getString("DENSITA_LORDA_20");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setDensitaLorda20(temp);

        temp=rs.getString("LIMO_GROSSO");
        if (temp!=null) temp=Utili.nf1.format(Double.parseDouble(temp));
        this.setLimoGrosso(temp);

        temp=rs.getString("LETTURA_DENSITA_17");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setLetturaDensita17(temp);

        temp=rs.getString("LETTURA_DENSITA_18_30");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setLetturaDensita1830(temp);

        temp=rs.getString("LETTURA_DENSITA_20");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setLetturaDensita20(temp);

        temp=rs.getString("DENSITA_BIANCO_17");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setDensitaBianco17(temp);

        temp=rs.getString("DENSITA_BIANCO_18_30");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setDensitaBianco1830(temp);

        temp=rs.getString("DENSITA_BIANCO_20");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setDensitaBianco20(temp);

        temp=rs.getString("DENSITA_MEDIA_BIANCO_3");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setDensitaMediaBianco3(temp);

        temp=rs.getString("TEMPERATURA_17");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setTemperatura17(temp);

        temp=rs.getString("TEMPERATURA_18_30");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setTemperatura1830(temp);

        temp=rs.getString("TEMPERATURA_20");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setTemperatura20(temp);

        temp=rs.getString("TEMPERATURA_MEDIA_3");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setTemperaturaMedia3(temp);

        temp=rs.getString("FATTORE_TEMP_GRANULARE_3");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setFattoreTempGranulare3(temp);

        temp=rs.getString("DIAMETRO_17");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setDiametro17(temp);

        temp=rs.getString("DIAMETRO_18_30");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setDiametro1830(temp);

        temp=rs.getString("DIAMETRO_20");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setDiametro20(temp);

        temp=rs.getString("DENSITA_LORDA_2_7_8");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setDensitaLorda278(temp);

        temp=rs.getString("DENSITA_LORDA_2_7_9");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setDensitaLorda279(temp);

        temp=rs.getString("DENSITA_LORDA_2_8_9");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setDensitaLorda289(temp);

        temp=rs.getString("DENSITA_LORDA_2");
        if (temp!=null) temp=Utili.nf3.format(Double.parseDouble(temp));
        this.setDensitaLorda2(temp);

        temp=rs.getString("LIMO_FINE");
        if (temp!=null) temp=Utili.nf1.format(Double.parseDouble(temp));
        this.setLimoFine(temp);

        temp=rs.getString("ARGILLA_1");
        if (temp!=null) temp=Utili.nf1.format(Double.parseDouble(temp));
        this.setArgilla1(temp);

        temp=rs.getString("LIMO");
        if (temp!=null) temp=Utili.nf1.format(Double.parseDouble(temp));
        this.setLimo(temp);

        temp=rs.getString("ARGILLA_2");
        if (temp!=null) temp=Utili.nf1.format(Double.parseDouble(temp));
        this.setArgilla2(temp);

        return true;
      }
      else return false;
    }
    catch(java.sql.SQLException ex)
    {
      this.getAut().setQuery("select della classe GranulometriaMetodoBojoucos");
      this.getAut().setContenutoQuery(query.toString());
      throw (ex);
    }
    catch(Exception e)
    {
      this.getAut().setQuery("select della classe GranulometriaMetodoBojoucos"
                            +": non è una SQLException ma una Exception"
                            +" generica");
      this.getAut().setContenutoQuery(query.toString());
      throw (e);
    }
    finally
    {
      stmt.close();
      if (conn!=null) conn.close();
    }
  }


  public long getIdRichiesta()
  {
    return this.idRichiesta;
  }
  public void setIdRichiesta( long newIdRichiesta )
  {
    this.idRichiesta = newIdRichiesta;
  }

  public String getLetturaDensita115()
  {
    return this.letturaDensita115;
  }
  public void setLetturaDensita115( String newLetturaDensita115 )
  {
    if (newLetturaDensita115!=null) letturaDensita115=newLetturaDensita115.replace(',','.');
    else this.letturaDensita115 = newLetturaDensita115;
  }

  public String getLetturaDensita130()
  {
    return this.letturaDensita130;
  }
  public void setLetturaDensita130( String newLetturaDensita130 )
  {
    if (newLetturaDensita130!=null) letturaDensita130=newLetturaDensita130.replace(',','.');
    else  this.letturaDensita130 = newLetturaDensita130;
  }

  public String getLetturaDensita145()
  {
    return this.letturaDensita145;
  }
  public void setLetturaDensita145( String newLetturaDensita145 )
  {
    if (newLetturaDensita145!=null) letturaDensita145=newLetturaDensita145.replace(',','.');
    else this.letturaDensita145 = newLetturaDensita145;
  }

  public String getDensitaSoluzFondo1()
  {
    return this.densitaSoluzFondo1;
  }
  public void setDensitaSoluzFondo1( String newDensitaSoluzFondo1 )
  {
    if (newDensitaSoluzFondo1!=null) densitaSoluzFondo1=newDensitaSoluzFondo1.replace(',','.');
    else this.densitaSoluzFondo1 = newDensitaSoluzFondo1;
  }

  public String getTemperatura1()
  {
    return this.temperatura1;
  }
  public void setTemperatura1( String newTemperatura1 )
  {
    if (newTemperatura1!=null) temperatura1=newTemperatura1.replace(',','.');
    else this.temperatura1 = newTemperatura1;
  }

  public String getFattoreTempGranulare1()
  {
    return this.fattoreTempGranulare1;
  }
  public void setFattoreTempGranulare1( String newFattoreTempGranulare1 )
  {
    if (newFattoreTempGranulare1!=null) fattoreTempGranulare1=newFattoreTempGranulare1.replace(',','.');
    else this.fattoreTempGranulare1 = newFattoreTempGranulare1;
  }

  public String getDiametro115()
  {
    return this.diametro115;
  }
  public void setDiametro115( String newDiametro115 )
  {
    if (newDiametro115!=null) diametro115=newDiametro115.replace(',','.');
    else this.diametro115 = newDiametro115;
  }

  public String getDiametro130()
  {
    return this.diametro130;
  }
  public void setDiametro130( String newDiametro130 )
  {
    if (newDiametro130!=null) diametro130=newDiametro130.replace(',','.');
    else this.diametro130 = newDiametro130;
  }

  public String getDiametro145()
  {
    return this.diametro145;
  }
  public void setDiametro145( String newDiametro145 )
  {
    if (newDiametro145!=null) diametro145=newDiametro145.replace(',','.');
    else this.diametro145 = newDiametro145;
  }

  public String getDensitaLorda5012()
  {
    return this.densitaLorda5012;
  }
  public void setDensitaLorda5012( String newDensitaLorda5012 )
  {
    if (newDensitaLorda5012!=null) densitaLorda5012=newDensitaLorda5012.replace(',','.');
    else this.densitaLorda5012 = newDensitaLorda5012;
  }

  public String getDensitaLorda5013()
  {
    return this.densitaLorda5013;
  }
  public void setDensitaLorda5013( String newDensitaLorda5013 )
  {
    if (newDensitaLorda5013!=null) densitaLorda5013=newDensitaLorda5013.replace(',','.');
    else this.densitaLorda5013 = newDensitaLorda5013;
  }

  public String getDensitaLorda5023()
  {
    return this.densitaLorda5023;
  }
  public void setDensitaLorda5023( String newDensitaLorda5023 )
  {
    if (newDensitaLorda5023!=null) densitaLorda5023=newDensitaLorda5023.replace(',','.');
    else this.densitaLorda5023 = newDensitaLorda5023;
  }

  public String getDensitaLorda50()
  {
    return this.densitaLorda50;
  }
  public void setDensitaLorda50( String newDensitaLorda50 )
  {
    if (newDensitaLorda50!=null) densitaLorda50=newDensitaLorda50.replace(',','.');
    else this.densitaLorda50 = newDensitaLorda50;
  }

  public String getSabbia()
  {
    return sabbia;
  }

  public String getSabbiaTotalePDF()
  {
    if (sabbia==null) return "";
    else
    {
      sabbia=sabbia.replace(',','.');
      sabbia=Utili.nf1.format(Double.parseDouble(sabbia));
      sabbia=sabbia.replace('.',',');
      return sabbia;
    }
  }
  public void setSabbia( String newSabbia )
  {
    if (newSabbia!=null) sabbia=newSabbia.replace(',','.');
    else this.sabbia = newSabbia;
  }

  public String getLetturaDensita930()
  {
    return this.letturaDensita930;
  }
  public void setLetturaDensita930( String newLetturaDensita930 )
  {
    if (newLetturaDensita930!=null) letturaDensita930=newLetturaDensita930.replace(',','.');
    else this.letturaDensita930 = newLetturaDensita930;
  }

  public String getLetturaDensita10()
  {
    return this.letturaDensita10;
  }
  public void setLetturaDensita10( String newLetturaDensita10 )
  {
    if (newLetturaDensita10!=null) letturaDensita10=newLetturaDensita10.replace(',','.');
    else this.letturaDensita10 = newLetturaDensita10;
  }

  public String getLetturaDensita1045()
  {
    return this.letturaDensita1045;
  }
  public void setLetturaDensita1045( String newLetturaDensita1045 )
  {
    if (newLetturaDensita1045!=null) letturaDensita1045=newLetturaDensita1045.replace(',','.');
    else this.letturaDensita1045 = newLetturaDensita1045;
  }

  public String getDensitaSoluzFondo2()
  {
    return this.densitaSoluzFondo2;
  }
  public void setDensitaSoluzFondo2( String newDensitaSoluzFondo2 )
  {
    if (newDensitaSoluzFondo2!=null) densitaSoluzFondo2=newDensitaSoluzFondo2.replace(',','.');
    else this.densitaSoluzFondo2 = newDensitaSoluzFondo2;
  }

  public String getTemperatura2()
  {
    return this.temperatura2;
  }
  public void setTemperatura2( String newTemperatura2 )
  {
    if (newTemperatura2!=null) temperatura2=newTemperatura2.replace(',','.');
    else this.temperatura2 = newTemperatura2;
  }

  public String getFattoreTempGranulare2()
  {
    return this.fattoreTempGranulare2;
  }
  public void setFattoreTempGranulare2( String newFattoreTempGranulare2 )
  {
    if (newFattoreTempGranulare2!=null) fattoreTempGranulare2=newFattoreTempGranulare2.replace(',','.');
    else this.fattoreTempGranulare2 = newFattoreTempGranulare2;
  }

  public String getDiametro930()
  {
    return this.diametro930;
  }
  public void setDiametro930( String newDiametro930 )
  {
    if (newDiametro930!=null) diametro930=newDiametro930.replace(',','.');
    else this.diametro930 = newDiametro930;
  }

  public String getDiametro10()
  {
    return this.diametro10;
  }
  public void setDiametro10( String newDiametro10 )
  {
    if (newDiametro10!=null) diametro10=newDiametro10.replace(',','.');
    else this.diametro10 = newDiametro10;
  }

  public String getDiametro1045()
  {
    return this.diametro1045;
  }
  public void setDiametro1045( String newDiametro1045 )
  {
    if (newDiametro1045!=null) diametro1045=newDiametro1045.replace(',','.');
    else this.diametro1045 = newDiametro1045;
  }

  public String getDensitaLorda2045()
  {
    return this.densitaLorda2045;
  }
  public void setDensitaLorda2045( String newDensitaLorda2045 )
  {
    if (newDensitaLorda2045!=null) densitaLorda2045=newDensitaLorda2045.replace(',','.');
    else this.densitaLorda2045 = newDensitaLorda2045;
  }

  public String getDensitaLorda2046()
  {
    return this.densitaLorda2046;
  }
  public void setDensitaLorda2046( String newDensitaLorda2046 )
  {
    if (newDensitaLorda2046!=null) densitaLorda2046=newDensitaLorda2046.replace(',','.');
    else this.densitaLorda2046 = newDensitaLorda2046;
  }

  public String getDensitaLorda2056()
  {
    return this.densitaLorda2056;
  }
  public void setDensitaLorda2056( String newDensitaLorda2056 )
  {
    if (newDensitaLorda2056!=null) densitaLorda2056=newDensitaLorda2056.replace(',','.');
    else this.densitaLorda2056 = newDensitaLorda2056;
  }

  public String getDensitaLorda20()
  {
    return this.densitaLorda20;
  }
  public void setDensitaLorda20( String newDensitaLorda20 )
  {
    if (newDensitaLorda20!=null) densitaLorda20=newDensitaLorda20.replace(',','.');
    else this.densitaLorda20 = newDensitaLorda20;
  }

  public String getLimoGrossoPDF()
  {
    if (limoGrosso==null) return "";
    else
    {
      limoGrosso=limoGrosso.replace(',','.');
      limoGrosso=Utili.nf1.format(Double.parseDouble(limoGrosso));
      limoGrosso=limoGrosso.replace('.',',');
      return limoGrosso;
    }
  }
  public String getLimoGrosso()
  {
    return this.limoGrosso;
  }
  public void setLimoGrosso( String newLimoGrosso )
  {
    if (newLimoGrosso!=null) limoGrosso=newLimoGrosso.replace(',','.');
    else this.limoGrosso = newLimoGrosso;
  }

  public String getLetturaDensita17()
  {
    return this.letturaDensita17;
  }
  public void setLetturaDensita17( String newLetturaDensita17 )
  {
    if (newLetturaDensita17!=null) letturaDensita17=newLetturaDensita17.replace(',','.');
    else this.letturaDensita17 = newLetturaDensita17;
  }

  public String getLetturaDensita1830()
  {
    return this.letturaDensita1830;
  }
  public void setLetturaDensita1830( String newLetturaDensita1830 )
  {
    if (newLetturaDensita1830!=null) letturaDensita1830=newLetturaDensita1830.replace(',','.');
    else this.letturaDensita1830 = newLetturaDensita1830;
  }

  public String getLetturaDensita20()
  {
    return this.letturaDensita20;
  }
  public void setLetturaDensita20( String newLetturaDensita20 )
  {
    if (newLetturaDensita20!=null) letturaDensita20=newLetturaDensita20.replace(',','.');
    else this.letturaDensita20 = newLetturaDensita20;
  }

  public String getDensitaBianco17()
  {
    return this.densitaBianco17;
  }
  public void setDensitaBianco17( String newDensitaBianco17 )
  {
    if (newDensitaBianco17!=null) densitaBianco17=newDensitaBianco17.replace(',','.');
    else this.densitaBianco17 = newDensitaBianco17;
  }

  public String getDensitaBianco1830()
  {
    return this.densitaBianco1830;
  }
  public void setDensitaBianco1830( String newDensitaBianco1830 )
  {
    if (newDensitaBianco1830!=null) densitaBianco1830=newDensitaBianco1830.replace(',','.');
    else this.densitaBianco1830 = newDensitaBianco1830;
  }

  public String getDensitaBianco20()
  {
    return this.densitaBianco20;
  }
  public void setDensitaBianco20( String newDensitaBianco20 )
  {
    if (newDensitaBianco20!=null) densitaBianco20=newDensitaBianco20.replace(',','.');
    else this.densitaBianco20 = newDensitaBianco20;
  }

  public String getDensitaMediaBianco3()
  {
    return this.densitaMediaBianco3;
  }
  public void setDensitaMediaBianco3( String newDensitaMediaBianco3 )
  {
    if (newDensitaMediaBianco3!=null) densitaMediaBianco3=newDensitaMediaBianco3.replace(',','.');
    else this.densitaMediaBianco3 = newDensitaMediaBianco3;
  }

  public String getTemperatura17()
  {
    return this.temperatura17;
  }
  public void setTemperatura17( String newTemperatura17 )
  {
    if (newTemperatura17!=null) temperatura17=newTemperatura17.replace(',','.');
    else this.temperatura17 = newTemperatura17;
  }

  public String getTemperatura1830()
  {
    return this.temperatura1830;
  }
  public void setTemperatura1830( String newTemperatura1830 )
  {
    if (newTemperatura1830!=null) temperatura1830=newTemperatura1830.replace(',','.');
    else this.temperatura1830 = newTemperatura1830;
  }

  public String getTemperatura20()
  {
    return this.temperatura20;
  }
  public void setTemperatura20( String newTemperatura20 )
  {
    if (newTemperatura20!=null) temperatura20=newTemperatura20.replace(',','.');
    else this.temperatura20 = newTemperatura20;
  }

  public String getTemperaturaMedia3()
  {
    return this.temperaturaMedia3;
  }
  public void setTemperaturaMedia3( String newTemperaturaMedia3 )
  {
    if (newTemperaturaMedia3!=null) temperaturaMedia3=newTemperaturaMedia3.replace(',','.');
    else this.temperaturaMedia3 = newTemperaturaMedia3;
  }

  public String getFattoreTempGranulare3()
  {
    return this.fattoreTempGranulare3;
  }
  public void setFattoreTempGranulare3( String newFattoreTempGranulare3 )
  {
    if (newFattoreTempGranulare3!=null) fattoreTempGranulare3=newFattoreTempGranulare3.replace(',','.');
    else this.fattoreTempGranulare3 = newFattoreTempGranulare3;
  }

  public String getDiametro17()
  {
    return this.diametro17;
  }
  public void setDiametro17( String newDiametro17 )
  {
    if (newDiametro17!=null) diametro17=newDiametro17.replace(',','.');
    else this.diametro17 = newDiametro17;
  }

  public String getDiametro1830()
  {
    return this.diametro1830;
  }
  public void setDiametro1830( String newDiametro1830 )
  {
    if (newDiametro1830!=null) diametro1830=newDiametro1830.replace(',','.');
    else this.diametro1830 = newDiametro1830;
  }

  public String getDiametro20()
  {
    return this.diametro20;
  }
  public void setDiametro20( String newDiametro20 )
  {
    if (newDiametro20!=null) diametro20=newDiametro20.replace(',','.');
    else this.diametro20 = newDiametro20;
  }

  public String getDensitaLorda278()
  {
    return this.densitaLorda278;
  }
  public void setDensitaLorda278( String newDensitaLorda278 )
  {
    if (newDensitaLorda278!=null) densitaLorda278=newDensitaLorda278.replace(',','.');
    else this.densitaLorda278 = newDensitaLorda278;
  }

  public String getDensitaLorda279()
  {
    return this.densitaLorda279;
  }
  public void setDensitaLorda279( String newDensitaLorda279 )
  {
    if (newDensitaLorda279!=null) densitaLorda279=newDensitaLorda279.replace(',','.');
    else this.densitaLorda279 = newDensitaLorda279;
  }

  public String getDensitaLorda289()
  {
    return this.densitaLorda289;
  }
  public void setDensitaLorda289( String newDensitaLorda289 )
  {
    if (newDensitaLorda289!=null) densitaLorda289=newDensitaLorda289.replace(',','.');
    else this.densitaLorda289 = newDensitaLorda289;
  }

  public String getDensitaLorda2()
  {
    return this.densitaLorda2;
  }
  public void setDensitaLorda2( String newDensitaLorda2 )
  {
    if (newDensitaLorda2!=null) densitaLorda2=newDensitaLorda2.replace(',','.');
    else this.densitaLorda2 = newDensitaLorda2;
  }

  public String getLimoFinePDF()
  {
    if (limoFine==null) return "";
    else
    {
      limoFine=limoFine.replace(',','.');
      limoFine=Utili.nf1.format(Double.parseDouble(limoFine));
      limoFine=limoFine.replace('.',',');
      return limoFine;
    }
  }

  public String getLimoFine()
  {
    return this.limoFine;
  }
  public void setLimoFine( String newLimoFine )
  {
    if (newLimoFine!=null) limoFine=newLimoFine.replace(',','.');
    else this.limoFine = newLimoFine;
  }

  public String getArgilla1()
  {
    return this.argilla1;
  }
  public void setArgilla1( String newArgilla1 )
  {
    if (newArgilla1!=null) argilla1=newArgilla1.replace(',','.');
    else this.argilla1 = newArgilla1;
  }


  public String getLimo()
  {
    return this.limo;
  }
  public String getLimoTotalePDF()
  {
    if (limo==null) return "";
    else
    {
      limo=limo.replace(',','.');
      limo=Utili.nf1.format(Double.parseDouble(limo));
      limo=limo.replace('.',',');
      return limo;
    }
  }
  public void setLimo( String newLimo )
  {
    if (newLimo!=null) limo=newLimo.replace(',','.');
    else this.limo = newLimo;
  }

  public String getArgilla2()
  {
    return this.argilla2;
  }
  public void setArgilla2( String newArgilla2 )
  {
    if (newArgilla2!=null) argilla2=newArgilla2.replace(',','.');
    else this.argilla2 = newArgilla2;
  }

  public String getArgillaPDF()
  {
    if (argilla1==null && argilla2==null) return "";
    else
    {
      String argilla;
      if (argilla1==null) argilla=argilla2;
      else argilla=argilla1;
      argilla=argilla.replace(',','.');
      argilla=Utili.nf1.format(Double.parseDouble(argilla));
      argilla=argilla.replace('.',',');
      return argilla;
    }
  }

}