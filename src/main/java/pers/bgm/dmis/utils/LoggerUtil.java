package pers.bgm.dmis.utils;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * Created by Administrator on 2017/4/20.
 */
public class LoggerUtil {
    public static Logger getLogger() {
        PropertyConfigurator.configure("src\\main\\resources\\log4j.properties");
        Logger logger = Logger.getLogger(LoggerUtil.class);
        return logger;
    }
}
