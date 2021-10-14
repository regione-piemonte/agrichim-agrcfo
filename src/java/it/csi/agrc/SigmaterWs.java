package it.csi.agrc;

//import it.csi.jsf.web.pool.*;
import java.util.Base64;

import it.csi.cuneo.BeanDataSource;
import it.csi.cuneo.CuneoLogger;
import it.csi.cuneo.CxfImplAgrcFO;
import it.csi.sigmater.ws.client.sigwgssrvSigwgssrv.SigwgssrvSigwgssrv;
import it.csi.sigmater.ws.client.sigwgssrvSigwgssrv.SigwgssrvSigwgssrvService;
import it.csi.wso2.apiman.oauth2.helper.GenericWrapperFactoryBean;
import it.csi.wso2.apiman.oauth2.helper.OauthHelper;
import it.csi.wso2.apiman.oauth2.helper.TokenRetryManager;
import it.csi.wso2.apiman.oauth2.helper.WsProvider;

/**
 * <p>Title: Agrichim - Front Office</p>
 * <p>Description: Richiesta analisi chimiche su campioni biologici agrari</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: CSI Piemonte - Progettazione e Sviluppo - Cuneo</p>
 * @author Luca Diana
 * @version 1.0.0
 */

public class SigmaterWs extends BeanDataSource
{ 
  
  private static SigwgssrvSigwgssrv sigwgssrvSigwgssrv =null; 
  
  public SigmaterWs()
  {
  }
  public SigmaterWs(Object dataSource, Autenticazione aut)
  {
    this.setDataSource(dataSource);
    this.setAut(aut);
  }

  
  
  public SigwgssrvSigwgssrv bindingSigmaterSigwgssrvWs(BeanParametri beanParametriApplication)
      throws Exception
  {
    CuneoLogger.debug(this,
        "Invocating bindingSigmaterTerreniWs method in SigmaterBean");

    if (sigwgssrvSigwgssrv == null)
    {
      try 
      {
        String wsdl = beanParametriApplication.getSigwgssrvSigwgssrvWsdl();
        sigwgssrvSigwgssrv = new SigwgssrvSigwgssrvService(
          this.getClass().getResource(wsdl)).getSigwgssrvSigwgssrv();

        TokenRetryManager trm = new TokenRetryManager();
  
        String urlApiManager = beanParametriApplication.getTokenApiManagerEndpoint(); 
        String key = beanParametriApplication.getTokenApiManagerKey();
        String secret = beanParametriApplication.getTokenApiManagerSecret();
  
        OauthHelper oauthHelper = new OauthHelper(urlApiManager, key, secret,
            10000); // time out in ms
        trm.setOauthHelper(oauthHelper);
  
        String xAuth = beanParametriApplication.getTokenApiManagerXAuth();
        String encodedString = new String(
            Base64.getEncoder().encodeToString(xAuth.getBytes()));
  
        CuneoLogger.debug(this, "encodedString: " + encodedString);

        WsProvider wsp = new CxfImplAgrcFO(encodedString);
  
        trm.setWsProvider(wsp);
        GenericWrapperFactoryBean gwfb = new GenericWrapperFactoryBean();
        String url = beanParametriApplication.getSigwgssrvSigwgssrvEndpoint();
        gwfb.setEndPoint(url);
        gwfb.setWrappedInterface(SigwgssrvSigwgssrv.class);
        gwfb.setTokenRetryManager(trm);
        // gwfb.setPort(port);
        gwfb.setPort(sigwgssrvSigwgssrv);
        sigwgssrvSigwgssrv = (SigwgssrvSigwgssrv) gwfb.create();

        /*
         * Client client = ClientProxy.getClient(sigalfsrvTerreni); HTTPConduit
         * conduit = (HTTPConduit) client.getConduit(); HTTPClientPolicy policy =
         * conduit.getClient();
         * policy.setConnectionTimeout(SolmrConstants.WS_TIME_OUT);
         * policy.setReceiveTimeout(SolmrConstants.WS_TIME_OUT);
         */
      }
      finally {
      }
    }

    return sigwgssrvSigwgssrv;
  }
}
