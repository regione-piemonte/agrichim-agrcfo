package it.csi.cuneo;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;


/**
 * Title:        Agrichim - Front Office
 * Description:  Richiesta analisi chimiche su campioni biologici agrari
 * Copyright:    Copyright (c) 2003
 * Company:      CSI Piemonte - Progettazione e Sviluppo - Cuneo
 * @author Michele Piantà, Piergiorgio Chiriotti
 * @version 1.0.0
 */

/**
 * Questa classe contiene i metodi necessari a costruire una email e a
 * spedirla
 */
public class Mail
{
      private String host = "<SMTP_SERVER_HOSTNAME>"; // Stringa che contiene l'indirizzo
      // del server che permette di spedire le email

      private String eMailSender;     // contiene l'indirizzo email del mittente
      private String eMailReceiver[]; // contiene gli indirizzi email dei destinatari
      private String subject;         // subject della email
      private String contenutoEmail;  // contiene il corpo della email


      /**
       * Unico costruttore della classe, presenta quattro parametri che
       * corrispondono agli indirizzi email del mittente e del destinatario
       * ed al nome ed al cognome del mittente
       */
      public Mail()
      {
      }


      /**
       * Questo metodo compone il corpo della email.
       */
      public void preparaMail(String subject,String query, String contenutoQuery,
                              String eccezioneEmail)
      {
              this.subject=subject;
              if (subject==null) subject="";
              if (query==null) query="";
              if (contenutoQuery==null) contenutoQuery="";
              if (eccezioneEmail==null) eccezioneEmail="";
              this.contenutoEmail=query+"\n\n"+contenutoQuery+"\n\n"+eccezioneEmail;
              //this.contenutoEmail="<HTML><BODY><H1>"+query+"</H1>\n<P>"+contenutoQuery+"\n<P>"+eccezioneEmail+"</BODY></HTML>";
              //CuneoLogger.debug(this,this.contenutoEmail);
      }

      /**
       * Questo metodo è quello che spedisce l' email. Restituisce true nel
       * caso non ci siano stati problemi, restituisce false se l'email non
       * è stata spedita
       */
      public boolean inviaMail()
      {
              try
              {
                      // create some properties and get the default Session
                      Properties props = new Properties();
                      props.put("mail.smtp.host", host);


                      Session session = Session.getDefaultInstance(props, null);

                      // crea il messaggio
                      Message msg = new MimeMessage(session);
                      msg.setFrom(new InternetAddress(eMailSender));

                      // E' richiesto un vettore di indirizzi perché una generica email
                      // può avere più destinatari!!
                      InternetAddress[] address = new InternetAddress[eMailReceiver.length];
                      for (int i=0;i<address.length;i++)
                        address[i]=new InternetAddress(eMailReceiver[i]);
                      msg.setRecipients(Message.RecipientType.TO, address);
                      /**
                       * E' possibile settare oltre al destinatario anche la copia
                       * carbone e la copia carbone nascosta
                       **/
                      //Message.RecipientType.CC copia carbone
                      //Message.RecipientType.BCC copia carbone nascaosta
                      msg.setSubject(subject);
                      msg.setSentDate(new Date());
                      msg.setText(contenutoEmail);

                      //spedisce il messaggio
                      Transport.send(msg);
              }
              catch(Exception e)
              {
                      /**
                * Se si entra in questo blocco vuol dire che è stata generata
                * una eccezione e che quindi l'email non è stata spedita
                    */
                      CuneoLogger.debug(this,"\nMail.inviaMail(): eccezione!\n"+e.toString());
                      return false;
              }
              return true; // l'email è stata spedita
      }

    public void setEMailSender(String newEMailSender)
    {
      eMailSender = newEMailSender;
    }

    public void setEMailReceiver(String newEMailReceiver[])
    {
      int count=0;
      for (count=0;count<newEMailReceiver.length;count++)
        if ("".equals(newEMailReceiver[count])) break;
      eMailReceiver = new String [count];
      for (count=0;count<eMailReceiver.length;count++)
        eMailReceiver[count] = newEMailReceiver[count];
    }

    public void setHost(String host)
    {
      this.host = host;
    }
}













