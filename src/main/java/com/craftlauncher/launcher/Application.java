package com.craftlauncher.launcher;

import static javax.swing.SwingUtilities.invokeLater;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.craftlauncher.launcher.exception.CraftenLogicException;
import com.craftlauncher.launcher.gui.MainController;
import com.craftlauncher.launcher.logic.Facade;
import com.craftlauncher.util.OSHelper;
import com.tngtech.configbuilder.ConfigBuilder;

/**
 * Craften Launcher main class.
 * Logs several infos about the system and starts / initializes the logic and gui.
 *
 * @author redbeard
 * @author saschb2b
 */
class Application {
	private static final Logger LOGGER = LogManager.getLogger(Application.class);

	/**
	 * The entry point of the application.
	 *
	 * @param args command line arguments
	 * @throws CraftenLogicException if launching the application fails
	 */
	public static void main(String[] args) throws CraftenLogicException {
		System.setProperty("awt.useSystemAAFontSettings", "on");
		System.setProperty("swing.aatext", "true");

		logSystemInfo();
		Config config = ConfigBuilder.on(Config.class).withCommandLineArgs(args).build();
		Facade.getInstance().init(config);
		startGUI();
	}

	/**
	 * Logs several system information.
	 */
	private static void logSystemInfo() {
		LOGGER.info("Launcher started!");

		Package objPackage = Application.class.getPackage();
		LOGGER.info("Launcher version: " + objPackage.getSpecificationVersion());

		LOGGER.debug("OS : " + System.getProperty("os.name"));
		LOGGER.debug("OS Arch: " + OSHelper.getOSArch());
		LOGGER.debug("OS Java Arch : " + System.getProperty("os.arch"));
		LOGGER.debug("OS Version : " + System.getProperty("os.version"));
		LOGGER.debug("Username : " + System.getProperty("user.name"));
		LOGGER.debug("Java Vendor : " + System.getProperty("java.vendor"));
		LOGGER.debug("Java Version : " + System.getProperty("java.version"));
		LOGGER.debug("Java Home : " + System.getProperty("java.home"));
		LOGGER.debug("Java Classpath : " + System.getProperty("java.class.path"));
		LOGGER.debug("Available processors (cores): " + Runtime.getRuntime().availableProcessors());
		LOGGER.debug("Total memory (bytes): " + Runtime.getRuntime().totalMemory());
		LOGGER.info("Date: " + new SimpleDateFormat("dd.MM.yy").format(new Date()));
	}

	/**
	 * Starts the gui by invoking the gui manager.
	 */
	private static void startGUI() {
		LOGGER.debug("Starting GUI");

		invokeLater(new Runnable() {
			@Override
			public void run() {
				MainController.getInstance().openMainWindow();
			}
		});
	}
}
