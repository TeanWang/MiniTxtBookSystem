package com.qf.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * properties配置文件的管理类 读取配置文件的
 * 
 * 采用单例模式
 * 
 * @author TeanWang
 *
 */
public class ConfigManager {

	private static final ConfigManager config  = new ConfigManager();
	private Properties properties;

	private ConfigManager() {
		try {
			properties = new Properties();
			properties.load(new FileInputStream(Contants.CONFIG_PATH + File.separator + Contants.CONFIG_FILE_NAME));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取ConfigManager对象
	 * 
	 * @return ConfigManager对象
	 */
	public static ConfigManager getInstance() {
		return config;
	}

	/**
	 * 根据配置文件中的key去获取value
	 * 
	 * @param key 键
	 * @return 该key所对应的value
	 */
	public String getValue(String key) {
		return properties.getProperty(key);
	}

}
