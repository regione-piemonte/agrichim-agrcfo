<?php

define("WSDLloto","http://dev-wls9-07.csi.it:9202/lotoApplLotoWsfad/services/lotoLoto?wsdl");

class CSIException {
  public $nestedExcClassName; // string
  public $nestedExcMsg; // string
  public $stackTraceMessage; // string
}

class UnrecoverableException {
}

class SystemException {
}

class CommunicationException {
}

class UserException {
}

class LotoException {
}

class DatiRicercaViaByTerritorio {
  public $codUffEnteFornitore; // boolean
  public $idComune; // int
  public $idComuni; // ArrayOf_xsd_int
  public $idLocalita; // ArrayOf_xsd_int
  public $idProvince; // ArrayOf_xsd_int
  public $idProvincia; // int
  public $idRegione; // int
  public $idRegioni; // ArrayOf_xsd_int
  public $indirizzo; // string
  public $statoVia; // string
}

class ConfigurazioneRicerca {
  public $modalita; // int
  public $ordine; // int
}

class NomeVia {
  public $diramazione; // string
  public $estensione; // string
  public $id; // int
  public $nome; // string
  public $nomeBreve; // string
  public $nomeColloquiale; // string
  public $nomeDiramazione; // string
  public $normalizzato; // string
  public $numero; // string
}

class TipoVia {
  public $id; // int
  public $normalizzato; // string
  public $preposizione; // string
  public $sigla; // string
  public $tipo; // string
}

class Indirizzo {
  public $indirizzo; // string
  public $indirizzoBreve; // string
  public $nome; // NomeVia
  public $normalizzato; // string
  public $tipo; // TipoVia
}

class Regione {
  public $codiceIstat; // string
  public $id; // int
  public $nome; // string
  public $normalizzato; // string
  public $sigla; // string
}

class Provincia {
  public $codiceIstat; // string
  public $id; // int
  public $nome; // string
  public $normalizzato; // string
  public $regione; // Regione
  public $sigla; // string
}

class Comune {
  public $cap; // string
  public $codiceCatasto; // string
  public $codiceIstat; // string
  public $dataFine; // dateTime
  public $dataInizio; // dateTime
  public $id; // int
  public $nome; // string
  public $normalizzato; // string
  public $provincia; // Provincia
}

class Localita {
  public $comune; // Comune
  public $id; // int
  public $nome; // string
  public $normalizzato; // string
}

class ViaComunale {
  public $codiceUfficiale; // string
  public $descrizioneTipoDenom; // string
  public $idL2; // int
  public $indirizzo; // Indirizzo
  public $localita; // Localita
  public $nomeUfficiale; // string
  public $normalizzato; // string
  public $stato; // string
  public $tipoDenominazione; // string
}

class DatiRicercaViaByEnte {
  public $idEnte; // int
  public $idEnti; // ArrayOf_xsd_int
  public $indirizzo; // string
  public $statoVia; // string
}

class Ente {
  public $denominazione; // string
  public $id; // int
  public $normalizzato; // string
  public $sigla; // string
  public $tipoEnte; // string
}

class ViaSovracomunale {
  public $descrizioneTipoDenom; // string
  public $ente; // Ente
  public $idL1; // int
  public $idL1Secondario; // int
  public $idL1Storico; // int
  public $idL3; // int
  public $indirizzo; // Indirizzo
  public $normalizzato; // string
  public $stato; // string
  public $tipoDenominazione; // string
}

class DatiRicercaViaByIstat {
  public $codUffEnteFornitore; // boolean
  public $indirizzo; // string
  public $istatComune; // ArrayOf_soapenc_string
  public $statoVia; // string
}

class ViaComunaleVecchia {
  public $codiceUfficiale; // string
  public $descrizioneTipoDenom; // string
  public $idL2; // int
  public $idL2Rinom; // int
  public $indirizzo; // Indirizzo
  public $localita; // Localita
  public $normalizzato; // string
  public $stato; // string
}

class ViaComunaleRinominata {
  public $nuova; // ViaComunale
  public $vecchia; // ViaComunaleVecchia
}

class DatiRicercaLocalita {
  public $idComune; // int
  public $idComuni; // ArrayOf_xsd_int
  public $idProvince; // ArrayOf_xsd_int
  public $idProvincia; // int
  public $idRegione; // int
  public $idRegioni; // ArrayOf_xsd_int
  public $localita; // string
  public $statoLocalita; // string
}

class DatiRicercaComune {
  public $comune; // string
  public $idProvince; // ArrayOf_xsd_int
  public $idProvincia; // int
  public $idRegione; // int
  public $idRegioni; // ArrayOf_xsd_int
}

class DatiRicercaProvincia {
  public $idRegione; // int
  public $idRegioni; // ArrayOf_xsd_int
  public $provincia; // string
}

class DatiRicercaRegione {
  public $regione; // string
}

class ResourceType {
  public $cod; // string
  public $descr; // string
}

class CalledResource {
  public $codRisorsa; // string
  public $codSistema; // string
  public $tipoRisorsa; // ResourceType
}

class Outcome {
  public $eccezione; // CSIException
  public $messaggio; // string
  public $status; // int
}

class InvocationNode {
  public $childs; // ArrayOf_tns2_InvocationNode
  public $outcome; // Outcome
  public $resource; // CalledResource
  public $startTime; // long
  public $stopTime; // long
}


/**
 * lotoLotoService class
 * 
 *  
 * 
 * @author    {author}
 * @copyright {copyright}
 * @package   {package}
 */
class lotoLotoService extends SoapClient {

  private static $classmap = array(
                                    'CSIException' => 'CSIException',
                                    'UnrecoverableException' => 'UnrecoverableException',
                                    'SystemException' => 'SystemException',
                                    'CommunicationException' => 'CommunicationException',
                                    'UserException' => 'UserException',
                                    'LotoException' => 'LotoException',
                                    'DatiRicercaViaByTerritorio' => 'DatiRicercaViaByTerritorio',
                                    'ConfigurazioneRicerca' => 'ConfigurazioneRicerca',
                                    'NomeVia' => 'NomeVia',
                                    'TipoVia' => 'TipoVia',
                                    'Indirizzo' => 'Indirizzo',
                                    'Regione' => 'Regione',
                                    'Provincia' => 'Provincia',
                                    'Comune' => 'Comune',
                                    'Localita' => 'Localita',
                                    'ViaComunale' => 'ViaComunale',
                                    'DatiRicercaViaByEnte' => 'DatiRicercaViaByEnte',
                                    'Ente' => 'Ente',
                                    'ViaSovracomunale' => 'ViaSovracomunale',
                                    'DatiRicercaViaByIstat' => 'DatiRicercaViaByIstat',
                                    'ViaComunaleVecchia' => 'ViaComunaleVecchia',
                                    'ViaComunaleRinominata' => 'ViaComunaleRinominata',
                                    'DatiRicercaLocalita' => 'DatiRicercaLocalita',
                                    'DatiRicercaComune' => 'DatiRicercaComune',
                                    'DatiRicercaProvincia' => 'DatiRicercaProvincia',
                                    'DatiRicercaRegione' => 'DatiRicercaRegione',
                                    'ResourceType' => 'ResourceType',
                                    'CalledResource' => 'CalledResource',
                                    'Outcome' => 'Outcome',
                                    'InvocationNode' => 'InvocationNode',
                                   );

  public function lotoLotoService($wsdl = WSDLloto, $options = array()) {
    foreach(self::$classmap as $key => $value) {
      if(!isset($options['classmap'][$key])) {
        $options['classmap'][$key] = $value;
      }
    }
    parent::__construct($wsdl, $options);
  }

  /**
   *  
   *
   * @param  
   * @return boolean
   */
  public function testResources() {
    return $this->__soapCall('testResources', array(),       array(
            'uri' => WSDLloto,
            'soapaction' => ''
           )
      );
  }

  /**
   *  
   *
   * @param ArrayOf_tns2_CalledResource $in0
   * @return InvocationNode
   */
  public function selfCheck($in0) {
    return $this->__soapCall('selfCheck', array($in0),       array(
            'uri' => WSDLloto,
            'soapaction' => ''
           )
      );
  }

  /**
   *  
   *
   * @param  
   * @return boolean
   */
  public function hasSelfCheck() {
    return $this->__soapCall('hasSelfCheck', array(),       array(
            'uri' => WSDLloto,
            'soapaction' => ''
           )
      );
  }

  /**
   *  
   *
   * @param DatiRicercaViaByTerritorio $in0
   * @param ConfigurazioneRicerca $in1
   * @param boolean $in2
   * @return ArrayOf_tns1_ViaComunale
   */
  public function cercaVieComunaliByNome(DatiRicercaViaByTerritorio $in0, ConfigurazioneRicerca $in1, $in2) {
    return $this->__soapCall('cercaVieComunaliByNome', array($in0, $in1, $in2),       array(
            'uri' => WSDLloto,
            'soapaction' => ''
           )
      );
  }

  /**
   *  
   *
   * @param DatiRicercaViaByEnte $in0
   * @param ConfigurazioneRicerca $in1
   * @param boolean $in2
   * @return ArrayOf_tns1_ViaSovracomunale
   */
  public function cercaVieSovracomunaliByNome(DatiRicercaViaByEnte $in0, ConfigurazioneRicerca $in1, $in2) {
    return $this->__soapCall('cercaVieSovracomunaliByNome', array($in0, $in1, $in2),       array(
            'uri' => WSDLloto,
            'soapaction' => ''
           )
      );
  }

  /**
   *  
   *
   * @param int $in0
   * @return ViaComunale
   */
  public function cercaViaComunaleById($in0) {
    return $this->__soapCall('cercaViaComunaleById', array($in0),       array(
            'uri' => WSDLloto,
            'soapaction' => ''
           )
      );
  }

  /**
   *  
   *
   * @param int $in0
   * @param boolean $in1
   * @return ViaSovracomunale
   */
  public function cercaViaSovracomunaleById($in0, $in1) {
    return $this->__soapCall('cercaViaSovracomunaleById', array($in0, $in1),       array(
            'uri' => WSDLloto,
            'soapaction' => ''
           )
      );
  }

  /**
   *  
   *
   * @param DatiRicercaViaByIstat $in0
   * @param ConfigurazioneRicerca $in1
   * @param boolean $in2
   * @return ArrayOf_tns1_ViaComunale
   */
  public function cercaVieComunaliByNomeAndIstat(DatiRicercaViaByIstat $in0, ConfigurazioneRicerca $in1, $in2) {
    return $this->__soapCall('cercaVieComunaliByNomeAndIstat', array($in0, $in1, $in2),       array(
            'uri' => WSDLloto,
            'soapaction' => ''
           )
      );
  }

  /**
   *  
   *
   * @param ArrayOf_xsd_int $in0
   * @return ArrayOf_tns1_ViaComunaleRinominata
   */
  public function cercaVieComunaliRinominateByIdL2Uff($in0) {
    return $this->__soapCall('cercaVieComunaliRinominateByIdL2Uff', array($in0),       array(
            'uri' => WSDLloto,
            'soapaction' => ''
           )
      );
  }

  /**
   *  
   *
   * @param DatiRicercaViaByTerritorio $in0
   * @param ConfigurazioneRicerca $in1
   * @return ArrayOf_tns1_ViaComunaleRinominata
   */
  public function cercaVieComunaliRinominateByNome(DatiRicercaViaByTerritorio $in0, ConfigurazioneRicerca $in1) {
    return $this->__soapCall('cercaVieComunaliRinominateByNome', array($in0, $in1),       array(
            'uri' => WSDLloto,
            'soapaction' => ''
           )
      );
  }

  /**
   *  
   *
   * @param string $in0
   * @param ConfigurazioneRicerca $in1
   * @return ArrayOf_tns1_NomeVia
   */
  public function cercaNomiViaByNome($in0, ConfigurazioneRicerca $in1) {
    return $this->__soapCall('cercaNomiViaByNome', array($in0, $in1),       array(
            'uri' => WSDLloto,
            'soapaction' => ''
           )
      );
  }

  /**
   *  
   *
   * @param string $in0
   * @param ConfigurazioneRicerca $in1
   * @return ArrayOf_tns1_TipoVia
   */
  public function cercaTipiViaByNomeCompleto($in0, ConfigurazioneRicerca $in1) {
    return $this->__soapCall('cercaTipiViaByNomeCompleto', array($in0, $in1),       array(
            'uri' => WSDLloto,
            'soapaction' => ''
           )
      );
  }

  /**
   *  
   *
   * @param int $in0
   * @return TipoVia
   */
  public function cercaTipoViaById($in0) {
    return $this->__soapCall('cercaTipoViaById', array($in0),       array(
            'uri' => WSDLloto,
            'soapaction' => ''
           )
      );
  }

  /**
   *  
   *
   * @param int $in0
   * @return string
   */
  public function verificaStatoViaComunale($in0) {
    return $this->__soapCall('verificaStatoViaComunale', array($in0),       array(
            'uri' => WSDLloto,
            'soapaction' => ''
           )
      );
  }

  /**
   *  
   *
   * @param int $in0
   * @param boolean $in1
   * @return string
   */
  public function verificaStatoViaSovracomunale($in0, $in1) {
    return $this->__soapCall('verificaStatoViaSovracomunale', array($in0, $in1),       array(
            'uri' => WSDLloto,
            'soapaction' => ''
           )
      );
  }

  /**
   *  
   *
   * @param string $in0
   * @param ConfigurazioneRicerca $in1
   * @return ArrayOf_tns1_TipoVia
   */
  public function cercaTipiViaByNome($in0, ConfigurazioneRicerca $in1) {
    return $this->__soapCall('cercaTipiViaByNome', array($in0, $in1),       array(
            'uri' => WSDLloto,
            'soapaction' => ''
           )
      );
  }

  /**
   *  
   *
   * @param int $in0
   * @return TipoVia
   */
  public function cercaTipoViaByIdL2($in0) {
    return $this->__soapCall('cercaTipoViaByIdL2', array($in0),       array(
            'uri' => WSDLloto,
            'soapaction' => ''
           )
      );
  }

  /**
   *  
   *
   * @param int $in0
   * @return TipoVia
   */
  public function cercaTipoViaByIdL1($in0) {
    return $this->__soapCall('cercaTipoViaByIdL1', array($in0),       array(
            'uri' => WSDLloto,
            'soapaction' => ''
           )
      );
  }

  /**
   *  
   *
   * @param int $in0
   * @return NomeVia
   */
  public function cercaNomeViaByIdL2($in0) {
    return $this->__soapCall('cercaNomeViaByIdL2', array($in0),       array(
            'uri' => WSDLloto,
            'soapaction' => ''
           )
      );
  }

  /**
   *  
   *
   * @param int $in0
   * @return NomeVia
   */
  public function cercaNomeViaByIdL1($in0) {
    return $this->__soapCall('cercaNomeViaByIdL1', array($in0),       array(
            'uri' => WSDLloto,
            'soapaction' => ''
           )
      );
  }

  /**
   *  
   *
   * @param ArrayOf_xsd_int $in0
   * @return ArrayOf_tns1_ViaComunale
   */
  public function cercaVieComunaliById($in0) {
    return $this->__soapCall('cercaVieComunaliById', array($in0),       array(
            'uri' => WSDLloto,
            'soapaction' => ''
           )
      );
  }

  /**
   *  
   *
   * @param ArrayOf_xsd_int $in0
   * @param boolean $in1
   * @return ArrayOf_tns1_ViaSovracomunale
   */
  public function cercaVieSovracomunaliById($in0, $in1) {
    return $this->__soapCall('cercaVieSovracomunaliById', array($in0, $in1),       array(
            'uri' => WSDLloto,
            'soapaction' => ''
           )
      );
  }

  /**
   *  
   *
   * @param int $in0
   * @return ArrayOf_tns1_ViaSovracomunale
   */
  public function cercaVieSovracomunaliByIdL2($in0) {
    return $this->__soapCall('cercaVieSovracomunaliByIdL2', array($in0),       array(
            'uri' => WSDLloto,
            'soapaction' => ''
           )
      );
  }

  /**
   *  
   *
   * @param int $in0
   * @param string $in1
   * @return ArrayOf_tns1_ViaSovracomunale
   */
  public function cercaVieSovracomunaliByIdL2AndCivico($in0, $in1) {
    return $this->__soapCall('cercaVieSovracomunaliByIdL2AndCivico', array($in0, $in1),       array(
            'uri' => WSDLloto,
            'soapaction' => ''
           )
      );
  }

  /**
   *  
   *
   * @param int $in0
   * @return Localita
   */
  public function cercaLocalitaById($in0) {
    return $this->__soapCall('cercaLocalitaById', array($in0),       array(
            'uri' => WSDLloto,
            'soapaction' => ''
           )
      );
  }

  /**
   *  
   *
   * @param int $in0
   * @return Comune
   */
  public function cercaComuneById($in0) {
    return $this->__soapCall('cercaComuneById', array($in0),       array(
            'uri' => WSDLloto,
            'soapaction' => ''
           )
      );
  }

  /**
   *  
   *
   * @param int $in0
   * @return Provincia
   */
  public function cercaProvinciaById($in0) {
    return $this->__soapCall('cercaProvinciaById', array($in0),       array(
            'uri' => WSDLloto,
            'soapaction' => ''
           )
      );
  }

  /**
   *  
   *
   * @param int $in0
   * @return Regione
   */
  public function cercaRegioneById($in0) {
    return $this->__soapCall('cercaRegioneById', array($in0),       array(
            'uri' => WSDLloto,
            'soapaction' => ''
           )
      );
  }

  /**
   *  
   *
   * @param string $in0
   * @return Comune
   */
  public function cercaComuneByIstat($in0) {
    return $this->__soapCall('cercaComuneByIstat', array($in0),       array(
            'uri' => WSDLloto,
            'soapaction' => ''
           )
      );
  }

  /**
   *  
   *
   * @param string $in0
   * @return Provincia
   */
  public function cercaProvinciaByIstat($in0) {
    return $this->__soapCall('cercaProvinciaByIstat', array($in0),       array(
            'uri' => WSDLloto,
            'soapaction' => ''
           )
      );
  }

  /**
   *  
   *
   * @param string $in0
   * @return Regione
   */
  public function cercaRegioneByIstat($in0) {
    return $this->__soapCall('cercaRegioneByIstat', array($in0),       array(
            'uri' => WSDLloto,
            'soapaction' => ''
           )
      );
  }

  /**
   *  
   *
   * @param ArrayOf_xsd_int $in0
   * @return ArrayOf_tns1_Localita
   */
  public function cercaLocalitaByIdList($in0) {
    return $this->__soapCall('cercaLocalitaByIdList', array($in0),       array(
            'uri' => WSDLloto,
            'soapaction' => ''
           )
      );
  }

  /**
   *  
   *
   * @param ArrayOf_xsd_int $in0
   * @return ArrayOf_tns1_Comune
   */
  public function cercaComuniByIdList($in0) {
    return $this->__soapCall('cercaComuniByIdList', array($in0),       array(
            'uri' => WSDLloto,
            'soapaction' => ''
           )
      );
  }

  /**
   *  
   *
   * @param ArrayOf_xsd_int $in0
   * @return ArrayOf_tns1_Provincia
   */
  public function cercaProvinceByIdList($in0) {
    return $this->__soapCall('cercaProvinceByIdList', array($in0),       array(
            'uri' => WSDLloto,
            'soapaction' => ''
           )
      );
  }

  /**
   *  
   *
   * @param ArrayOf_xsd_int $in0
   * @return ArrayOf_tns1_Regione
   */
  public function cercaRegioniByIdList($in0) {
    return $this->__soapCall('cercaRegioniByIdList', array($in0),       array(
            'uri' => WSDLloto,
            'soapaction' => ''
           )
      );
  }

  /**
   *  
   *
   * @param ArrayOf_soapenc_string $in0
   * @return ArrayOf_tns1_Comune
   */
  public function cercaComuniByIstatList($in0) {
    return $this->__soapCall('cercaComuniByIstatList', array($in0),       array(
            'uri' => WSDLloto,
            'soapaction' => ''
           )
      );
  }

  /**
   *  
   *
   * @param ArrayOf_soapenc_string $in0
   * @return ArrayOf_tns1_Provincia
   */
  public function cercaProvinceByIstatList($in0) {
    return $this->__soapCall('cercaProvinceByIstatList', array($in0),       array(
            'uri' => WSDLloto,
            'soapaction' => ''
           )
      );
  }

  /**
   *  
   *
   * @param ArrayOf_soapenc_string $in0
   * @return ArrayOf_tns1_Regione
   */
  public function cercaRegioniByIstatList($in0) {
    return $this->__soapCall('cercaRegioniByIstatList', array($in0),       array(
            'uri' => WSDLloto,
            'soapaction' => ''
           )
      );
  }

  /**
   *  
   *
   * @param DatiRicercaLocalita $in0
   * @param ConfigurazioneRicerca $in1
   * @return ArrayOf_tns1_Localita
   */
  public function cercaLocalitaByNome(DatiRicercaLocalita $in0, ConfigurazioneRicerca $in1) {
    return $this->__soapCall('cercaLocalitaByNome', array($in0, $in1),       array(
            'uri' => WSDLloto,
            'soapaction' => ''
           )
      );
  }

  /**
   *  
   *
   * @param DatiRicercaComune $in0
   * @param ConfigurazioneRicerca $in1
   * @return ArrayOf_tns1_Comune
   */
  public function cercaComuniByNome(DatiRicercaComune $in0, ConfigurazioneRicerca $in1) {
    return $this->__soapCall('cercaComuniByNome', array($in0, $in1),       array(
            'uri' => WSDLloto,
            'soapaction' => ''
           )
      );
  }

  /**
   *  
   *
   * @param DatiRicercaProvincia $in0
   * @param ConfigurazioneRicerca $in1
   * @return ArrayOf_tns1_Provincia
   */
  public function cercaProvinceByNome(DatiRicercaProvincia $in0, ConfigurazioneRicerca $in1) {
    return $this->__soapCall('cercaProvinceByNome', array($in0, $in1),       array(
            'uri' => WSDLloto,
            'soapaction' => ''
           )
      );
  }

  /**
   *  
   *
   * @param DatiRicercaRegione $in0
   * @param ConfigurazioneRicerca $in1
   * @return ArrayOf_tns1_Regione
   */
  public function cercaRegioniByNome(DatiRicercaRegione $in0, ConfigurazioneRicerca $in1) {
    return $this->__soapCall('cercaRegioniByNome', array($in0, $in1),       array(
            'uri' => WSDLloto,
            'soapaction' => ''
           )
      );
  }

  /**
   *  
   *
   * @param int $in0
   * @return boolean
   */
  public function isComuneInStradario($in0) {
    return $this->__soapCall('isComuneInStradario', array($in0),       array(
            'uri' => WSDLloto,
            'soapaction' => ''
           )
      );
  }

}

?>
