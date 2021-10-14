<%
 /**
  * In questo modo il nostro applicativo potrà funzionare sia con Tomcat,
  * utilizzando BeanGenericPool, sia con WebLogic utilizzando il suo
  * DataSource
  */
  Object dataSource;
//   if (Utili.POOLMAN)
//   {
//     dataSource = (it.csi.jsf.web.pool.BeanGenericPool)application.getAttribute("poolBean");
//   }
//   else
//   {
    dataSource = (javax.sql.DataSource)application.getAttribute("dataSourceBean");
//   }
%>
