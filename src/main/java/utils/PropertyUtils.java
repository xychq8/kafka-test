package utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyUtils {
	private static final Log log = LogFactory.getLog(PropertyUtils.class);
	
	/**
	 * 通过properties[文件名称：fileName],[属性名称：properyName]获取属性值
	 * @param fileName
	 * @param propertyName
	 * @return propertyValue
	 */
	public static String getProperty(String fileName, String propertyName){
		if(fileName == null || fileName.trim().isEmpty()){
			log.error("**********没有找到" + fileName + ".properties文件**********");
			return null;
		}
		
		if(propertyName == null || propertyName.trim().isEmpty()){
			log.error("**********没有找到" + propertyName + "属性**********");
			return null;
		}
		
		Properties props = new Properties();
		String fieldValue = "";
		InputStream is = null;
		try {
			is = PropertyUtils.class.getClassLoader().getResourceAsStream(fileName);
			props.load(is);
			if(!props.containsKey(propertyName)){
				log.error("**********从" + fileName + ".properties文件获取"+ propertyName + "失败**********");
				return fieldValue;
			}
			fieldValue = props.getProperty(propertyName);
			log.info(fileName + "文件" + propertyName + ":" + fieldValue);
		} catch (IOException e) {
			e.printStackTrace();
			log.error("**********从" + fileName + ".properties文件获取"+ propertyName + "失败**********");
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return fieldValue;
	}
}
