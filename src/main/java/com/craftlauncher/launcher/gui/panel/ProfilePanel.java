package com.craftlauncher.launcher.gui.panel;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.craftlauncher.launcher.exception.CraftenLogicException;
import com.craftlauncher.launcher.gui.MainController;
import com.craftlauncher.launcher.logic.Facade;
import com.craftlauncher.launcher.logic.auth.MinecraftUser;
import com.craftlauncher.launcher.logic.manager.TranslationManager;

import de.craften.ui.swingmaterial.MaterialButton;
import de.craften.ui.swingmaterial.MaterialColor;
import de.craften.ui.swingmaterial.MaterialComboBox;
import de.craften.ui.swingmaterial.MaterialShadow;
import de.craften.ui.swingmaterial.Roboto;

@SuppressWarnings("serial")
public class ProfilePanel extends JPanel {
	private static final Logger LOGGER = LogManager.getLogger(ProfilePanel.class);

	public ProfilePanel() {
		setBackground(Color.WHITE);
		setLayout(null);
	}

	public void init() {
		removeAll();
		addProfileInformation();
		repaint();

		if(Facade.getInstance().isQuickPlay())
			MainController.getInstance().play();
	}

	private void addProfileInformation() {
		MinecraftUser user = null;
		try {
			user = Facade.getInstance().getUser();
		}
		catch(CraftenLogicException e) {
			LOGGER.error("Grab username error", e);
		}

		if(user != null) {
			// PlayerName
			JLabel playerName = new JLabel(user.getUsername());
			playerName.setFont(Roboto.MEDIUM.deriveFont(20f));
			playerName.setSize(240, 60);
			playerName.setLocation(68, 2);
			playerName.setVerticalAlignment(JLabel.TOP);
			playerName.setHorizontalAlignment(JLabel.CENTER);
			playerName.setForeground(MaterialColor.LIGHT_BLACK);
			add(playerName);

			JLabel playerMail = new JLabel(user.getEmail());
			playerMail.setFont(Roboto.REGULAR.deriveFont(12f));
			playerMail.setSize(240, 60);
			playerMail.setLocation(68, 26);
			playerMail.setVerticalAlignment(JLabel.TOP);
			playerMail.setHorizontalAlignment(JLabel.CENTER);
			playerMail.setForeground(MaterialColor.MIN_BLACK);
			add(playerMail);
		}

		// TODO this logic should not be here
		try {
			if(Facade.getInstance().getMinecraftArguments().containsKey("version"))
				Facade.getInstance().setMinecraftVersion(Facade.getInstance().getMinecraftArgument("version"));
			else
				// TODO this is a pretty hacky way to get the latest stable version; it should be possible to check if a
				// version is a release using the json data
				for(String v : Facade.getInstance().getMinecraftVersions())
				if(v.matches("\\d+\\.\\d+(\\.\\d+)?")) {
				Facade.getInstance().setMinecraftVersion(v);
				break;
				}
		}
		catch(CraftenLogicException e) {
			LOGGER.error(e);
		}

		final JButton playButton = new MaterialButton();
		playButton.setText(TranslationManager.getString("playBtn"));
		playButton.setBackground(MaterialColor.CYAN_500);
		playButton.setForeground(Color.WHITE);
		playButton.setSize(240 + MaterialShadow.OFFSET_LEFT + MaterialShadow.OFFSET_RIGHT,
				36 + MaterialShadow.OFFSET_TOP + MaterialShadow.OFFSET_BOTTOM);
		playButton.setLocation(68 - MaterialShadow.OFFSET_LEFT, 88 - MaterialShadow.OFFSET_TOP);
		playButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		playButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				MainController.getInstance().play();
			}
		});
		add(playButton);
		try {
			// Auto-Connect IP
			if(Facade.getInstance().getMinecraftArguments().containsKey("server")) {
				String serverAddress = Facade.getInstance().getMinecraftArgument("server");
				if(serverAddress.length() > 21)
					serverAddress = serverAddress.substring(0, 20) + "\u2026";
				playButton.setText(TranslationManager.getString("joinServerBtn", serverAddress));
			}
		}
		catch(CraftenLogicException e) {
			LOGGER.error("Could not get server argument", e);
		}

		final MaterialButton logoutButton = new MaterialButton();
		logoutButton.setText(TranslationManager.getString("logoutBtn"));
		logoutButton.setForeground(MaterialColor.CYAN_500);
		logoutButton.setRippleColor(MaterialColor.CYAN_500);
		logoutButton.setBackground(MaterialColor.TRANSPARENT);
		logoutButton.setType(MaterialButton.Type.FLAT);
		logoutButton.setSize(240 + MaterialShadow.OFFSET_LEFT + MaterialShadow.OFFSET_RIGHT,
				36 + MaterialShadow.OFFSET_TOP + MaterialShadow.OFFSET_BOTTOM);
		logoutButton.setLocation(68 - MaterialShadow.OFFSET_LEFT, 132 - MaterialShadow.OFFSET_TOP);
		logoutButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		logoutButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				MainController.getInstance().logout();
			}
		});
		add(logoutButton);

		// Version
		try {

			final MaterialComboBox<String> versions = new MaterialComboBox<>();
			for(String version : Facade.getInstance().getMinecraftVersions())
				versions.addItem(version);
			versions.setSelectedItem(Facade.getInstance().getMinecraftVersion().getVersion());
			versions.setSize(125, 42);
			versions.setLocation(68, logoutButton.getY() + logoutButton.getHeight() - 3);
			versions.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent actionEvent) {
					try {
						Facade.getInstance().setMinecraftVersion(String.valueOf(versions.getSelectedItem()));
					}
					catch(CraftenLogicException e) {
						LOGGER.error("Could not set version", e);
					}
				}
			});
			versions.setVisible(false);
			add(versions);

			final JLabel versionLabel = new JLabel(TranslationManager.getString("versionLabel",
					Facade.getInstance().getMinecraftVersion().getVersion()));
			versionLabel.setFont(Roboto.REGULAR.deriveFont(12f));
			versionLabel.setSize(125, 30);
			versionLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			versionLabel.setLocation(68, logoutButton.getY() + logoutButton.getHeight() + 5);
			versionLabel.setForeground(MaterialColor.MIN_BLACK);
			versionLabel.setHorizontalAlignment(JLabel.LEFT);
			versionLabel.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					versionLabel.setVisible(false);
					versions.setVisible(true);
				}

				@Override
				public void mouseEntered(MouseEvent mouseEvent) {
					versionLabel.setForeground(MaterialColor.CYAN_500);
				}

				@Override
				public void mouseExited(MouseEvent mouseEvent) {
					versionLabel.setForeground(MaterialColor.MIN_BLACK);
				}
			});
			add(versionLabel);
		}
		catch(CraftenLogicException e) {
			LOGGER.error("Could not get versions", e);
		}

		// RAM
		try {
			String ram;
			if(Facade.getInstance().getMinecraftArguments().containsKey("xmx"))
				ram = Facade.getInstance().getMinecraftArgument("xmx").toUpperCase();
			else
				ram = "~" + Runtime.getRuntime().maxMemory() / 1024 / 1024 + "M";
			JLabel ramLabel = new JLabel(TranslationManager.getString("ramLabel", ram));
			ramLabel.setFont(Roboto.REGULAR.deriveFont(12f));
			ramLabel.setSize(240, 30);
			ramLabel.setLocation(68, logoutButton.getY() + logoutButton.getHeight() + 5);
			ramLabel.setForeground(MaterialColor.MIN_BLACK);
			ramLabel.setHorizontalAlignment(JLabel.RIGHT);
			add(ramLabel);
		}
		catch(CraftenLogicException e) {
			LOGGER.error("Could not get RAM", e);
		}
	}
}
