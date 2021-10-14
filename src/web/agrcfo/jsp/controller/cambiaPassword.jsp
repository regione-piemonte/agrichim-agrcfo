<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="java.net.*,java.io.*,java.security.*" isThreadSafe="true" %>

<jsp:useBean
  id="beanParametriApplication"
  scope="application"
  class="it.csi.agrc.BeanParametri">
</jsp:useBean>

<%
  String userid=request.getParameter("userid");
  String oldPassword=request.getParameter("oldPassword");
  String newPassword1=request.getParameter("newPassword1");
  String newPassword2=request.getParameter("newPassword2");
  /*
  CuneoLogger.debug(this,"\ncontroller/cambiaPassword.jsp");
  CuneoLogger.debug(this,"userid: "+userid);
  CuneoLogger.debug(this,"oldPassword: "+oldPassword);
  CuneoLogger.debug(this,"newPassword1: "+newPassword1);
  CuneoLogger.debug(this,"newPassword2: "+newPassword2);
  */

  // Costruzione dei dati
  String data = URLEncoder.encode("userid") + "=" + URLEncoder.encode(userid);
  data += "&" + URLEncoder.encode("oldPassword") + "=" + URLEncoder.encode(oldPassword);
  data += "&" + URLEncoder.encode("newPassword1") + "=" + URLEncoder.encode(newPassword1);
  data += "&" + URLEncoder.encode("newPassword2") + "=" + URLEncoder.encode(newPassword2);

  // Spedizione dei dati
  // URL da parametrizzare in web.xml (sono http, non https)
  BufferedReader rd = it.csi.cuneo.Utili.accediURL("http://<WEB_SERVER_HOST:PORT>/cgi-bin/iride_sp/servizi/cambiopwd.cgi");

  String line, errorCode=null;
  int index;
  while ((line = rd.readLine()) != null) {
    // Process line...
    index = line.toUpperCase().indexOf("STATO:");
    if (index != -1)
    {
      errorCode = line.substring(index+7,index+10);
      break;
    }
  }
  rd.close();

  /*
  Stato: 501, Input Error - Non sono stati indicati alcuni dati necessari al cambio password
  Stato: 502, Input Error - La nuova password non puo' essere nulla
  Stato: 503, Input Error - Errore nella conferma dellla password. Controllare che la password sia stata inserita correttamente
  Stato: 504, Input Error - Attenzione, non inserire una password uguale allo username
  Stato: 512, Login Error - Utente non trovato, possibile errore in username
  Stato: 513, Login Error - La vecchia password risulta errata
  Stato: DB024, DB Error - execute fallito : ORA-01401: inserted value too large for column (DBD ERROR: OCIStmtExecute) - update sp_carte set password='012345678' where NUM_CARTA='E99994'
  */

  if (errorCode==null)
  {
    response.sendRedirect(beanParametriApplication.getUrlHomePageApplication());
  }
  else
  {
    %>
      <jsp:forward page="/jsp/view/cambiaPassword.jsp">
      <jsp:param name="errorCode" value="<%= errorCode %>" />
      </jsp:forward>
    <%
  }
%>
