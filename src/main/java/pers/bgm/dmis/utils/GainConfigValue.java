package pers.bgm.dmis.utils;

import org.apache.log4j.Logger;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Administrator on 2017/4/20.
 */
public class GainConfigValue {
    public static String getValue(String propertyName) {
        String propertyValue = null;
        Properties properties = new Properties();

        try {
            InputStream inputStream = new BufferedInputStream(new FileInputStream("src\\main\\resources\\Constant.properties"));
            properties.load(inputStream);
        } catch (Exception e) {

            Logger logger = LoggerUtil.getLogger();
            logger.error(e.getMessage());
            logger.debug(e.getMessage());
        }

        if ((propertyValue = properties.getProperty(propertyName)) != null) {
            return propertyValue;
        } else {
            Logger logger = LoggerUtil.getLogger();
            logger.error("未找到属性名 " + propertyName + " 对应的属性值！");
            logger.debug("未找到属性名 " + propertyName + " 对应的属性值！");
            return null;
        }
    }
}
