<%@page import="it.csi.iride2.policy.entity.Identita"%>
<%@ page import="it.csi.csi.porte.InfoPortaDelegata" %>
<%@ page import="it.csi.csi.util.xml.PDConfigReader" %>
<%@ page import="it.csi.csi.porte.proxy.PDProxy" %>
<%@ page import="it.csi.iride2.policy.interfaces.PolicyEnforcerBaseService" %>
<%@page import="it.csi.cuneo.Constants"%>

<%
  String codiceFiscale = request.getParameter("codiceFiscale");
  if (codiceFiscale != null)
  {
    String cognome = request.getParameter("cognome");
    String nome = request.getParameter("nome");
    String password = request.getParameter("password");
    String dominio = request.getParameter("dominio");
    
  	InfoPortaDelegata infoPDPEP = PDConfigReader.read(session.getServletContext().getResourceAsStream("/WEB-INF/defPDPEPEJB.xml"));
 		PolicyEnforcerBaseService iridePEPClient = (PolicyEnforcerBaseService) PDProxy.newInstance(infoPDPEP);
  
  	Identita identita = iridePEPClient.identificaUserPassword(cognome + "." + nome + dominio, password);
    
    session.setAttribute(Constants.SESSION.IDENTITA, identita);
    
    if ("@ipa".equals(dominio))
    {
   		response.sendRedirect("secure/wrup/login.jsp");
    }
    else
    {
    	response.sendRedirect("secure/sisp/login.jsp");
    }
  }
%>

<html>
  <head>
    <title>Agrichim Front Office - Home page di svilupo/test</title>
    <META http-equiv="Content-Type" content="text/html; charset=ISO-8859-15"></META>

    <script>
    nomi = new Array(); //viene creato l'array
		nomi[0]="DEMO 21";
    nomi[1]="DEMO 22";
    nomi[2]="DEMO 24";
    nomi[3]="DEMO 30";    
    /*nomi[0]="Michele";
    nomi[1]="Luca";
    nomi[2]="STEFANO";
    nomi[3]="ROBERTO";*/

    cognomi = new Array(); //viene creato l'array
    cognomi[0]="CSI";
    cognomi[1]="CSI";
    cognomi[2]="CSI";
    cognomi[3]="CSI";
    /*cognomi[0]="Piantà";
    cognomi[1]="Barbero";
    cognomi[2]="DOLZAN";
    cognomi[3]="TONELLO";*/

    function updateFieldsSP()
    {
      var codiceFiscaleSelect=document.getElementById("codiceFiscaleSP");
      var nome=document.getElementById("nomeSP");
      var cognome=document.getElementById("cognomeSP");

      nome.value=nomi[codiceFiscaleSelect.selectedIndex];
      cognome.value=cognomi[codiceFiscaleSelect.selectedIndex];
    }

    function updateFieldsPA()
    {
      var codiceFiscaleSelect=document.getElementById("codiceFiscalePA");
      var nome=document.getElementById("nomePA");
      var cognome=document.getElementById("cognomePA");

   	  nome.value=nomi[codiceFiscaleSelect.selectedIndex+2];
   	  cognome.value=cognomi[codiceFiscaleSelect.selectedIndex+2];
    }
    </script>
  </head>
  <body>
    <h1>Agrichim Front Office<br />Home page di sviluppo/test</h1>
    <p><a href="/agrcfo/jsp/controller/login.jsp">URL SistemaPiemonte</a><br />
    <a href="/agrcfo/jsp/controller/loginPA.jsp?cod_servizio=AGRCFO&fromPortal=INTERNET_RUPAR">URL RUPAR</a></p>
    <h3>Login per test senza sistemi di autenticazione</h3>
    
    <p>SistemaPiemonte</p>
    <form method="POST" action="">
    <select id="codiceFiscaleSP" name="codiceFiscale" onChange="updateFieldsSP();">
      <option value="AAAAAA00A11B000J" selected>CSI.DEMO 21 - Persona</option>
      <option value="AAAAAA00A11C000K">CSI.DEMO 22 - Tecnico</option>
      <option value="AAAAAA00A11E000M">CSI.DEMO 24 - LAR Base</option>
    </select>
    <input type="hidden" id="passwordSP" name="password" value="aaaaaaaa" />
    <input type="hidden" id="dominioSP" name="dominio" value="@sistemapiemonte" />
    <input type="text" id="nomeSP" name="nome" value="DEMO 21" />
    <input type="text" id="cognomeSP" name="cognome" value="CSI" />
    <input type="submit" value="Accedi" />
    </form>

    <p>RUPAR</p>
    <form method="POST" action="">
    <select id="codiceFiscalePA" name="codiceFiscale" onChange="updateFieldsPA();">
      <option value="AAAAAA00A11E000M">CSI.DEMO 24 - LAR Base</option>
      <option value="AAAAAA00A11K000S" selected>CSI.DEMO 30 - LAR Esperto</option>
    </select>
    <input type="hidden" id="passwordPA" name="password" value="PIEMONTE" />
    <input type="hidden" id="dominioPA" name="dominio" value="@ipa" />
    <input type="text" id="nomePA" name="nome" value="DEMO 30" />
    <input type="text" id="cognomePA" name="cognome" value="CSI" />
    <input type="submit" value="Accedi" />
    </form>

  </body>
</html>