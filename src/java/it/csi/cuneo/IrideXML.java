package it.csi.cuneo;

import java.io.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;

public class IrideXML
{
  private static final int IRIDE_DATA_ELEMENTS=19;

  public static final int IRIDE_NUM_CARTA=0;
  public static final int IRIDE_COD_FISCALE=1;
  public static final int IRIDE_COGNOME=2;
  public static final int IRIDE_NOME=3;
  public static final int IRIDE_E_MAIL=4;
  public static final int IRIDE_VIA=5;
  public static final int IRIDE_NUM_CIVICO_COMPLETO=6;
  public static final int IRIDE_CAP=7;
  public static final int IRIDE_DESC_COMUNE_RES=8;
  public static final int IRIDE_COD_COMUNE_RES=9;
  public static final int IRIDE_PIANO=10;
  public static final int IRIDE_NUI=11;
  public static final int IRIDE_AZIENDA_PARTITA_IVA=12;
  public static final int IRIDE_AZIENDA_DENOMINAZIONE=13;
  public static final int IRIDE_AZIENDA_INDIRIZZO=14;
  public static final int IRIDE_AZIENDA_COMUNE=15;
  public static final int IRIDE_AZIENDA_CAP=16;
  public static final int IRIDE_AZIENDA_TELEFONO=17;
  public static final int IRIDE_AZIENDA_FAX=18;


  public IrideXML()
  {
  }

  public String[] parse(InputStream is)
      throws javax.xml.parsers.ParserConfigurationException,
             java.io.IOException,
             org.xml.sax.SAXException
  {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    //factory.setValidating(true);
    //factory.setNamespaceAware(true);
    DocumentBuilder builder = factory.newDocumentBuilder();
    Document document = builder.parse( is );
    Node nodeRisposta = document.getFirstChild();

    String irideData[]=new String[IRIDE_DATA_ELEMENTS];
    if(!nodeRisposta.getNodeName().equals("errore"))
      browseNodeList(nodeRisposta.getChildNodes(), irideData, 0);
    return irideData;
  }

  public int browseNodeList(NodeList nodeList, String [] irideData, int indice)
  {
    for (int i = 0; i < nodeList.getLength(); i++)
    {
      Node n = nodeList.item(i);
      if (!"#text".equals(n.getNodeName()))
      {
        if (!"Azienda".equals(n.getNodeName()))
        {
          if(null != n.getFirstChild())
            irideData[indice]=""+n.getFirstChild().getNodeValue();
          indice++;
        }
      }
      if (n.hasChildNodes())
        indice = browseNodeList(n.getChildNodes(),irideData, indice);
    }
    return indice;
  }

}