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

	private String connectionFileName;

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

	private void loadConnectionile() throws IOException {
		Properties applicationProperties = new Properties();
		InputStream propInput = getClass().getClassLoader()
				.getResourceAsStream(APP_CONFIG_FILE);
		applicationProperties.load(propInput);
		String environment = applicationProperties
				.getProperty("application.env");
		this.connectionFileName = "connection_" + environment + ".xml";
	}

	public InputStream getConnectionFile() {
		if (connectionFileName == null) {
			try {
				loadConnectionile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return getClass().getClassLoader().getResourceAsStream(
				connectionFileName);
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
