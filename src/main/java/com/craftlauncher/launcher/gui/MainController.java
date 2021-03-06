package com.craftlauncher.launcher.gui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.craftlauncher.launcher.exception.CraftenException;
import com.craftlauncher.launcher.exception.CraftenLogicException;
import com.craftlauncher.launcher.logic.Facade;
import com.craftlauncher.launcher.logic.auth.MinecraftUser;
import com.craftlauncher.launcher.logic.manager.TranslationManager;

import de.craften.ui.swingmaterial.toast.TextToast;

/**
 * The controller of the launcher.
 */
public class MainController {
	private static final Logger LOGGER = LogManager.getLogger(MainController.class);
	private static MainController instance;
	private MainWindow mainWindow;

	private String username = "";

	public static MainController getInstance() {
		if(instance == null)
			instance = new MainController();
		return instance;
	}

	public void openMainWindow() {
		mainWindow = new MainWindow();
		mainWindow.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				System.exit(0);
			}
		});
		mainWindow.setVisible(true);

		if(!Facade.getInstance().isForceLogin())
			try {
				MinecraftUser user = Facade.getInstance().getUser();
				if(user != null && user.getEmail() != null && !user.getEmail().isEmpty()) {
					MainController.getInstance().performLogin(user.getEmail(), null);
					displayToast(new TextToast(TranslationManager.getString("welcomeBack", user.getUsername())));
				}
			}
			catch(CraftenException e) {
				LOGGER.info("Automatic login failed", e);
			}
	}

	public void performLogin(String username, char[] password) throws CraftenException {
		if(password != null)
			Facade.getInstance().setUser(username, password);
		Facade.getInstance().authenticateUser();

		mainWindow.showProfile();
	}

	public void performWithoutLogin() {
		mainWindow.showProfileWithoutLogin();
	}

	public void logout() {
		Facade.getInstance().logout();
		mainWindow.reset();
	}

	public void play() {
		mainWindow.showLoadingScreen();
	}

	public void playWithoutLogin(String username) {
		this.username = username;
		mainWindow.showLoadingScreen();
	}

	public void startMinecraft() {
		if(username.equals(""))
			try {
				Facade.getInstance().startMinecraft();
			}
			catch(CraftenLogicException e) {
				LOGGER.error("Could not start Minecraft", e);
			}
		else
			try {
				Facade.getInstance().startMinecraftWithoutLogin(username);
			}
			catch(CraftenLogicException e) {
				LOGGER.error("Could not start Minecraft", e);
			}
	}

	public void displayToast(TextToast toast) {
		mainWindow.toastBar.display(toast);
	}
}
