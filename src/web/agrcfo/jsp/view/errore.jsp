<%@ page isErrorPage="true" %>
<%@ page import="it.csi.cuneo.*,it.csi.jsf.htmpl.*" isThreadSafe="true" %>

<%
  Htmpl templ =
  HtmplFactory.getInstance(application).getHtmpl("/jsp/layout/errore.htm");
%>

<%@ include file="../controllaLogin.inc" %>

<%
  Throwable eccezioneLocale = aut.getEccezione();
  aut.setEccezione(null);
  session.setAttribute("aut",aut);

  if ( eccezioneLocale == null)
    eccezioneLocale = exception;

  templ.bset("rigaErrore1","Siamo spiacenti, si è verificato un errore di sistema.");
  templ.bset("rigaErrore2","Si prega di ritentare tra qualche istante.");
  templ.bset("rigaErrore3","Se l'errore dovesse ripresentarsi, contattare l'assistenza.");

  try
  {

    java.io.CharArrayWriter cw = new java.io.CharArrayWriter();
    java.io.PrintWriter pw = new java.io.PrintWriter(cw,true);
    eccezioneLocale.printStackTrace(pw);
    eccezioneLocale.printStackTrace(System.err);

    /**
     * La parte di codice seguente è utilizzata per preparare e spedire l'email
    */
    it.csi.cuneo.Mail mail=new it.csi.cuneo.Mail();
    mail.setEMailSender(beanParametriApplication.getMailMittente());
    mail.setEMailReceiver(beanParametriApplication.getMailDestinatario());
    mail.setHost(beanParametriApplication.getHostServerPosta());
    mail.preparaMail("["+beanParametriApplication.getNomeServerWebApplication()+"]   "+eccezioneLocale.getMessage(),aut.getQuery(),aut.getContenutoQuery(),cw.toString());
    /*
     * Con le sucessive inizializzazioni a "" di query e contenutoQuery
     * si evita che un'eccezione non dovuta ad una query riporti dati
     * non corretti
     */
    aut.setQuery("");
    aut.setContenutoQuery("");
    session.setAttribute("aut",aut);
    //if(beanParametriApplication.getNomeServerWebApplication().toUpperCase().indexOf("TEST")<0)
    	//CuneoLogger.debug(this,"\nerrore.jsp - invio mail: "+mail.inviaMail());
    }
  catch(Exception e)
  {
    CuneoLogger.debug(this,"\nerrore.jsp - ERRORE IMPREVISTO!\n");
    e.printStackTrace();

    String urlStartApplication = (aut.isPA()?
                                  beanParametriApplication.getUrlStartApplicationPA():
                                  beanParametriApplication.getUrlStartApplicationSP());

    session.removeAttribute("aut");
    response.sendRedirect(urlStartApplication);
  }
%>

<%-- HTMPL genera ora la pagina web e si finalizza così lo stato stazionario --%>

<%= templ.text() %>
