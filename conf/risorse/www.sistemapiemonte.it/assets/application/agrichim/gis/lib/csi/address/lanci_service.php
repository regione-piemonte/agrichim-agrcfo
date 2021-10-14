<?php
class Civico {
  public $enteCommittente; // string
  public $enteFornitore; // string
  public $formatoGeometria; // int
  public $geometria; // string
  public $id; // int
  public $idArco; // int
  public $idL2Ufficiale; // int
  public $indirizzoStampa; // string
  public $numero; // int
  public $precisione; // int
  public $subalterno; // string
  public $x; // double
  public $y; // double
}

class CSIException {
  public $nestedExcClassName; // string
  public $nestedExcMsg; // string
  public $stackTraceMessage; // string
}

class UserException {
}

class SystemException {
}

class CommunicationException {
}

class LanciException {
}

class UnrecoverableException {
}

class Incrocio {
  public $formatoGeometria; // int
  public $geometria; // string
  public $x; // double
  public $y; // double
}

class Arco {
  public $dispariA; // int
  public $dispariDa; // int
  public $id; // int
  public $idL2Secondario; // int
  public $idL2Ufficiale; // int
  public $idL3; // int
  public $pariA; // int
  public $pariDa; // int
}

class EstensioneComune {
  public $formatoGeometria; // int
  public $geometria; // string
  public $idComune; // int
  public $maxX; // int
  public $maxY; // int
  public $mbr; // string
  public $minX; // int
  public $minY; // int
}

class ArcoGeo {
  public $dispariA; // int
  public $dispariDa; // int
  public $formatoGeometria; // int
  public $geometria; // string
  public $id; // int
  public $idL2Secondario; // int
  public $idL2Ufficiale; // int
  public $idL3; // int
  public $pariA; // int
  public $pariDa; // int
}

class CSIPolygon {
  public $SRID; // int
  public $holes; // ArrayOf_tns2_CSILinearRing
  public $shell; // CSILinearRing
}

class CSILineString {
  public $SRID; // int
  public $coords; // ArrayOf_tns2_CSICoordinate
}

class CSILinearRing {
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
  public $childs; // ArrayOf_tns3_InvocationNode
  public $outcome; // Outcome
  public $resource; // CalledResource
  public $startTime; // long
  public $stopTime; // long
}


/**
 * lanciLanciService class
 * 
 *  
 * 
 * @author    {author}
 * @copyright {copyright}
 * @package   {package}
 */
class lanciLanciService extends SoapClient {

  private static $classmap = array(
                                    'Civico' => 'Civico',
                                    'CSIException' => 'CSIException',
                                    'UserException' => 'UserException',
                                    'SystemException' => 'SystemException',
                                    'CommunicationException' => 'CommunicationException',
                                    'LanciException' => 'LanciException',
                                    'UnrecoverableException' => 'UnrecoverableException',
                                    'Incrocio' => 'Incrocio',
                                    'Arco' => 'Arco',
                                    'EstensioneComune' => 'EstensioneComune',
                                    'ArcoGeo' => 'ArcoGeo',
                                    'CSIPolygon' => 'CSIPolygon',
                                    'CSILineString' => 'CSILineString',
                                    'CSILinearRing' => 'CSILinearRing',
                                    'ResourceType' => 'ResourceType',
                                    'CalledResource' => 'CalledResource',
                                    'Outcome' => 'Outcome',
                                    'InvocationNode' => 'InvocationNode',
                                   );

  public function lanciLanciService($wsdl = "http://dev-wls9-07.csi.it:9202/lanciApplLanciWsfad/services/lanciLanci?wsdl", $options = array()) {
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
   * @param int $in0
   * @param string $in1
   * @param int $in2
   * @return Civico
   */
  public function cercaCivico($in0, $in1, $in2) {
    return $this->__soapCall('cercaCivico', array($in0, $in1, $in2),       array(
            'uri' => 'http://dev-wls9-07.csi.it:9202/lanciApplLanciWsfad/services/lanciLanci',
            'soapaction' => ''
           )
      );
  }

  /**
   *  
   *
   * @param int $in0
   * @param int $in1
   * @param int $in2
   * @return ArrayOf_tns1_Incrocio
   */
  public function cercaIncrocioByIdL2($in0, $in1, $in2) {
    return $this->__soapCall('cercaIncrocioByIdL2', array($in0, $in1, $in2),       array(
            'uri' => 'http://dev-wls9-07.csi.it:9202/lanciApplLanciWsfad/services/lanciLanci',
            'soapaction' => ''
           )
      );
  }

  /**
   *  
   *
   * @param int $in0
   * @param int $in1
   * @param int $in2
   * @return ArrayOf_tns1_Incrocio
   */
  public function cercaIncrocioByIdL3($in0, $in1, $in2) {
    return $this->__soapCall('cercaIncrocioByIdL3', array($in0, $in1, $in2),       array(
            'uri' => 'http://dev-wls9-07.csi.it:9202/lanciApplLanciWsfad/services/lanciLanci',
            'soapaction' => ''
           )
      );
  }

  /**
   *  
   *
   * @param int $in0
   * @param int $in1
   * @param int $in2
   * @return ArrayOf_tns1_Incrocio
   */
  public function cercaIncrocioByIdL2AndIdL3($in0, $in1, $in2) {
    return $this->__soapCall('cercaIncrocioByIdL2AndIdL3', array($in0, $in1, $in2),       array(
            'uri' => 'http://dev-wls9-07.csi.it:9202/lanciApplLanciWsfad/services/lanciLanci',
            'soapaction' => ''
           )
      );
  }

  /**
   *  
   *
   * @param int $in0
   * @return ArrayOf_tns1_Arco
   */
  public function cercaArchiByIdComune($in0) {
    return $this->__soapCall('cercaArchiByIdComune', array($in0),       array(
            'uri' => 'http://dev-wls9-07.csi.it:9202/lanciApplLanciWsfad/services/lanciLanci',
            'soapaction' => ''
           )
      );
  }

  /**
   *  
   *
   * @param int $in0
   * @param int $in1
   * @return EstensioneComune
   */
  public function cercaEstensioneComune($in0, $in1) {
    return $this->__soapCall('cercaEstensioneComune', array($in0, $in1),       array(
            'uri' => 'http://dev-wls9-07.csi.it:9202/lanciApplLanciWsfad/services/lanciLanci',
            'soapaction' => ''
           )
      );
  }

  /**
   *  
   *
   * @param int $in0
   * @param int $in1
   * @return ArcoGeo
   */
  public function cercaArcoById($in0, $in1) {
    return $this->__soapCall('cercaArcoById', array($in0, $in1),       array(
            'uri' => 'http://dev-wls9-07.csi.it:9202/lanciApplLanciWsfad/services/lanciLanci',
            'soapaction' => ''
           )
      );
  }

  /**
   *  
   *
   * @param int $in0
   * @param int $in1
   * @return ArrayOf_tns1_ArcoGeo
   */
  public function cercaArchiByIdL2($in0, $in1) {
    return $this->__soapCall('cercaArchiByIdL2', array($in0, $in1),       array(
            'uri' => 'http://dev-wls9-07.csi.it:9202/lanciApplLanciWsfad/services/lanciLanci',
            'soapaction' => ''
           )
      );
  }

  /**
   *  
   *
   * @param CSIPolygon $in0
   * @param int $in1
   * @return ArrayOf_tns1_Civico
   */
  public function cercaCiviciByPolygon(CSIPolygon $in0, $in1) {
    return $this->__soapCall('cercaCiviciByPolygon', array($in0, $in1),       array(
            'uri' => 'http://dev-wls9-07.csi.it:9202/lanciApplLanciWsfad/services/lanciLanci',
            'soapaction' => ''
           )
      );
  }

  /**
   *  
   *
   * @param string $in0
   * @param int $in1
   * @return ArrayOf_tns1_Civico
   */
  public function cercaCiviciByGeometry($in0, $in1) {
    return $this->__soapCall('cercaCiviciByGeometry', array($in0, $in1),       array(
            'uri' => 'http://dev-wls9-07.csi.it:9202/lanciApplLanciWsfad/services/lanciLanci',
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
  public function testResources() {
    return $this->__soapCall('testResources', array(),       array(
            'uri' => 'http://dev-wls9-07.csi.it:9202/lanciApplLanciWsfad/services/lanciLanci',
            'soapaction' => ''
           )
      );
  }

  /**
   *  
   *
   * @param ArrayOf_tns3_CalledResource $in0
   * @return InvocationNode
   */
  public function selfCheck($in0) {
    return $this->__soapCall('selfCheck', array($in0),       array(
            'uri' => 'http://dev-wls9-07.csi.it:9202/lanciApplLanciWsfad/services/lanciLanci',
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
            'uri' => 'http://dev-wls9-07.csi.it:9202/lanciApplLanciWsfad/services/lanciLanci',
            'soapaction' => ''
           )
      );
  }

}

?>
