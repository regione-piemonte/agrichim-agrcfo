<%@ page isErrorPage="true" %>
<%@ page isThreadSafe="true" %>

<%
  it.csi.jsf.htmpl.Htmpl templ=null ;
%>

<%@ include file="/jsp/controllaLogin.inc" %>
<%
  aut.setEccezione(exception);
  session.setAttribute("aut",aut);
%>

<%--
  Questo codice HTML provvede a:
    1) ricaricare la finestra chiamante con la pagina di errore
    2) richiamare in primo piano la finestra chiamante
    3) chiudere il popup
--%>
<HTML>
<HEAD>
<script>
  function chiudi()
  {
    if (!window.opener.window.opener)
    {
      window.opener.location='../view/errore.jsp';
      window.opener.focus();
      window.close();
    }
    else
    {
      window.opener.window.opener.location='../view/errore.jsp';
      window.opener.window.opener.focus();
      window.opener.close();
      window.close();
    }
  }
</script>
</HEAD>
<BODY onLoad="chiudi();">
</BODY>
</HTML>


