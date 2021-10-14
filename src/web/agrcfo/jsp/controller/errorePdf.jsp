<%@ page isErrorPage="true" %>

<% exception.printStackTrace(System.err); %>

<HTML>
<HEAD><TITLE>SORPRESA!</TITLE></HEAD>
<body>
<table><tr><td>&nbsp;<br><br><br><br><br><br><br></td></tr></table>
<center>
<a href="../report/anagraficaCampione.pdf?idRichiesta=<%=

request.getParameter("idRichiesta")

%>"><img id="clown" alt="Errore: <%=

exception.getMessage()

%>" src="../controller/errorePdf.jpg" border="0"
onMouseOver="clown.src='../controller/errorePdf2.jpg';"
onMouseOut="clown.src='../controller/errorePdf.jpg';"></a>
</center>
</body>
</HTML>
