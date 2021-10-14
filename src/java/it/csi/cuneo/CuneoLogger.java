package it.csi.cuneo;

import java.util.*;
import org.apache.log4j.*;

public class CuneoLogger
{
  private static HashMap logMapper = new HashMap();

  /**
   * Valorizza il nome dell'applicativo e restituisce un reference al
   * logger in uso. Se il nome del logger non è valido viene utilizzato
   * quello generico definito nelle costanti di AgriConstants.
   *
   * @param thrower
   * @return
   */
  private static Logger getLogger(Object thrower) {
    Logger logger = null;
    try {
      String appName = (String)logMapper.get(thrower.getClass().getPackage().getName());
      if (appName == null)
        throw new Exception();
      logger = Logger.getLogger(appName);
      if (logger == null)
        throw new Exception();
    }
    catch (Exception ex) {
      logger = Logger.getLogger(Constants.LOGGER_BASE);
    }
    return logger;
  }

  /**
   * Livello Info di logger
   *
   * @param thrower
   * @param msg
   */
  public static void info(Object thrower, Object msg) {
    getLogger(thrower).info(" ["+thrower.getClass().getName()+"] " + msg);
  }

  /**
   * Livello Info di debug
   *
   * @param thrower
   * @param msg
   */
  public static void debug(Object thrower, Object msg) {
    getLogger(thrower).debug(" ["+thrower.getClass().getName()+"] " + msg);
  }

  /**
   * Livello Info di warning
   *
   * @param thrower
   * @param msg
   */
  public static void warn(Object thrower, Object msg) {
    getLogger(thrower).warn(" ["+thrower.getClass().getName()+"] " + msg);
  }

  /**
   * Livello Info di error
   *
   * @param thrower
   * @param msg
   */
  public static void error(Object thrower, Object msg) {
    getLogger(thrower).error(" ["+thrower.getClass().getName()+"] " + msg);
  }

  /**
   * Livello Info di fatal
   *
   * @param thrower
   * @param msg
   */
  public static void fatal(Object thrower, Object msg) {
    getLogger(thrower).fatal(" ["+thrower.getClass().getName()+"] " + msg);
  }

  /**
   * Restituisce true se il livello di debug è abilitato
   *
   * @param thrower
   * @return
   */
  public static boolean isDebugEnabled(Object thrower) {
    return getLogger(thrower).isDebugEnabled();
  }

  /**
   * Restituisce true se il livello di Info è abilitato
   *
   * @param thrower
   * @return
   */
  public static boolean isInfoEnabled(Object thrower) {
    return getLogger(thrower).isInfoEnabled();
  }

  /**
   * Restituisce true se il livello selezionato da parametro è abilitato
   *
   * @param thrower
   * @return
   */
  public static boolean isEnabledFor(Object thrower, Priority priority) {
    return getLogger(thrower).isEnabledFor(priority);
  }

  /**
   * Costruzione del mapping del logger
   */
  static {
    logMapper.put("it.csi.cuneo", Constants.LOGGER_BASE);
    logMapper.put("it.csi.cuneo.servlet", Constants.LOGGER_BASE);
    logMapper.put("it.csi.agrc", Constants.LOGGER_BASE);
    logMapper.put("it.csi.agrc.servlet", Constants.LOGGER_BASE);
  }
}

