package io.transwarp;
import java.util.logging.Logger;

/**
 * @author transwarp
 * @date 2019-08-29 下午5:07
 */
public class Log {

    private static Logger logger = Logger.getLogger("log");

    public static  Logger getLogger(){
        return Log.logger;

    }


}
