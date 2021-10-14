package it.csi.cuneo;
import java.security.*;

/**
 * Title:        Agrichim - Front Office
 * Description:  Richiesta analisi chimiche su campioni biologici agrari
 * Copyright:    Copyright (c) 2003
 * Company:      CSI Piemonte - Progettazione e Sviluppo - Cuneo
 * @author Michele Piantà, Piergiorgio Chiriotti
 * @version 1.0.0
 */

public class AlgorithmDecoder
{
  private String phrase = "";
  private MessageDigest md;

  /**
   * Crea un'istanza della classe utilizzando un algoritmo di crittografia.<br>
   * Utilizzo es.: AlgorithmDecoder algorithmDecoder = new AlgorithmDecoder("MD5").
   *
   * @param algorithm algoritmo scelto tra i tipi supportati (MD5, SHA, SHA-1).
   */
  public AlgorithmDecoder(String algorithm)
  throws NoSuchAlgorithmException
  {
    try
    {
      md = MessageDigest.getInstance(algorithm);
    }
    catch (NoSuchAlgorithmException noAlgoritmo)
    {
      throw(noAlgoritmo);
    }
  }

  /**
   * Crea un'istanza della classe utilizzando un algoritmo di crittografia.<br>
   * Permette inoltre di utilizzare una passphrase privata.
   * Utilizzo es.: AlgorithmDecoder algorithmDecoder = new AlgorithmDecoder("MD5","frase segreta").
   *
   * @param algorithm algoritmo scelto tra i tipi supportati (MD5, SHA, SHA-1).
   * @param passphrase frase segreta che costituisce chiave privata.
   */
  public AlgorithmDecoder(String algorithm, String passphrase)
  throws NoSuchAlgorithmException
  {
    try
    {
      md = MessageDigest.getInstance(algorithm);
      phrase = passphrase;
    }
    catch (NoSuchAlgorithmException noAlgoritmo)
    {
      throw(noAlgoritmo);
    }
  }

  private String byteArrayToHexString(byte in[])
  {
    int onebyte;

    int i = 0;
    if (in == null || in.length <= 0)
      return null;

    StringBuffer out = new StringBuffer(in.length * 2);

    while (i < in.length)
    {
      onebyte = ((0x000000ff & in[i]) | 0xffffff00);
      out.append(Integer.toHexString(onebyte).substring(6));
      i++;
    }

    return new String(out);
  }

  /**
   * Metodo che permette il controllo di autenticità dei dati ricevuti.
   *
   * @param message stringa contenente i parametri da crittografare, concatenati (param1+param2+...).
   * @param hash stringa crittografata ricevuta con funzione di controllo.
   * @return boolean (true se la stringa crittografata ottenuta è uguale a quella passata come parametro)
   */
  public boolean verifyHash(String message, String hash)
  {
    if (!phrase.equals(""))
      message += phrase;
    md.update(message.getBytes());
    byte[] hash_calcolato_in_byte = md.digest();
    String hash_calcolato_in_stringa = byteArrayToHexString(hash_calcolato_in_byte);

    //confronto tra le due stringhe
    if (hash_calcolato_in_stringa.equals(hash))
      return true;
    else
      return false;
  }

  /**
   * Ottiene la stringa crittografata applicando, alla stringa passata
   * come parametro, l'algoritmo di cifratura.
   *
   * @param message stringa contenente i parametri ricevuti da crittografare, concatenati (param1+param2+...).
   * @return stringa crittografata
   */
  public String getHash(String message)
  {
    if (!phrase.equals(""))
      message += phrase;
    md.update(message.getBytes());
    byte[] hash_calcolato_in_byte = md.digest();
    String hash_calcolato_in_stringa = byteArrayToHexString(hash_calcolato_in_byte);
    return hash_calcolato_in_stringa;
  }
}
