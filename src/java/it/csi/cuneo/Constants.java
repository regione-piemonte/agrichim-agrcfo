package it.csi.cuneo;

/**
 * <p>
 * Classe delle costanti applicative.
 * </p>
 * 
 */
public final class Constants
{
  public static final String LOGGER_BASE = "agrichimfo";
  public static final int ID_PROCEDIMENTO_AGCRFO = 58;
  
  //costanti usate nella chiamate dei metodi sigmater
  public static final int SRID=32632;
  public static final double METRI_BUFFER=1;

  public static class SESSION
  {
    public static final String IDENTITA = "identita";
    public static final String IS_PRESENTE_ANAGRAFE = "isPresenteAnagrafe";
  }

  public static class REGIONE
  {
    public static final String PIEMONTE = "01";
  }

  public static class MATERIALE
  {
    public static final String CODICE_MATERIALE_TERRENI = "TER";
  }
}