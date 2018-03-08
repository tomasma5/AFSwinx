package com.tomscz.af.showcase.application;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * This is application context of this application. It holds variable and
 * settings, which will be used in entire application
 * 
 * @author Martin Tomasek (martin@toms-cz.com)
 * 
 * @since 1.0.0.
 */
public class ApplicationContext {

	public static final String APP_CONFIG_FILE = "application.properties";

	private static ApplicationContext instance;

	// private SecurityContext securityContext = new
	// ShowcaseSecurity("sa2","jaina",true);

	private SecurityContext securityContext;

	private String uiProxyApplicationUuid;

	private String uiProxyUrl;

	private ResourceBundle localization;

	public static synchronized ApplicationContext getInstance() {
		if (instance == null) {
			instance = new ApplicationContext();
		}
		return instance;
	}

	public void changeLocalization(String localizationName)
			throws FileNotFoundException {
		ResourceBundle tempLocalization = ResourceBundle
				.getBundle(localizationName);
		if (tempLocalization != null) {
			localization = tempLocalization;
		} else {
			throw new FileNotFoundException("The localization file named "
					+ localizationName + " was not found.");
		}
	}

	private void loadUIProxyApplicationUuid() throws IOException {
		Properties applicationProperties = new Properties();
		InputStream propInput = getClass().getClassLoader().getResourceAsStream(APP_CONFIG_FILE);
		applicationProperties.load(propInput);
		this.uiProxyApplicationUuid = applicationProperties.getProperty("proxy.uuid");
	}


	public void loadUIProxyUrl() throws IOException {
		Properties applicationProperties = new Properties();
		InputStream propInput = getClass().getClassLoader().getResourceAsStream(APP_CONFIG_FILE);
		applicationProperties.load(propInput);
		this.uiProxyUrl = applicationProperties.getProperty("proxy.url");
	}

	public String getUiProxyUrl() {
		return uiProxyApplicationUuid;
	}

	public String getUiProxyApplicationUuid() throws IOException {
		if(uiProxyApplicationUuid == null || uiProxyApplicationUuid.isEmpty()) {
			loadUIProxyApplicationUuid();
		}
		return uiProxyApplicationUuid;
	}

	public ResourceBundle getLocalization() {
		return localization;
	}

	public SecurityContext getSecurityContext() {
		return securityContext;
	}

	public void setSecurityContext(SecurityContext securityContext) {
		this.securityContext = securityContext;
	}

}
