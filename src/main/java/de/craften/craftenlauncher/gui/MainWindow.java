/**
 * CraftenLauncher is an alternative Launcher for Minecraft developed by Mojang.
 * Copyright (C) 2014  Johannes "redbeard" Busch, Sascha "saschb2b" Becker
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * @author saschb2b
 */

package de.craften.craftenlauncher.gui;

import de.craften.craftenlauncher.gui.panel.HeaderPanel;
import de.craften.craftenlauncher.gui.panel.LoadingPanel;
import de.craften.craftenlauncher.gui.panel.LoginPanel;
import de.craften.craftenlauncher.gui.panel.ProfilePanel;
import de.craften.craftenlauncher.logic.Facade;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;

@SuppressWarnings("serial")
public class MainWindow extends JDialog {
    private static final Logger LOGGER = LogManager.getLogger(MainWindow.class);
    private static final long serialVersionUID = 1L;
    private HeaderPanel header;
    private final JPanel body;
    private CardLayout bodyLayout;
    private LoginPanel login;
    private ProfilePanel profile;
    private LoadingPanel loading;

    public MainWindow() {
        super((Dialog) null); //show this window in the taskbar, see http://stackoverflow.com/a/25533860
        try {
            setIconImage(new ImageIcon(getClass().getResource("/images/icon.png")).getImage());
        } catch (NullPointerException e) {
            LOGGER.error("Could not load icon", e);
        }

        //setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setSize(new Dimension(376, 445));
        setLocationRelativeTo(null);
        setTitle("Craften Launcher");
        setResizable(false);

        JPanel content = new JPanel();
        content.setLayout(new BorderLayout());
        add(content);

        header = new HeaderPanel();
        content.add(header, BorderLayout.NORTH);

        bodyLayout = new CardLayout(0, 0);
        body = new JPanel();
        body.setLayout(bodyLayout);
        body.setPreferredSize(new Dimension(getWidth(), getContentPane().getHeight() - header.getHeight()));
        content.add(body, BorderLayout.CENTER);

        addLayers();

        pack();
        centerWindow(this);
        setVisible(true);
    }

    public void reset() {
        header.resetSkin();
        header.setLogoutEnabled(false);
        body.removeAll();
        addLayers();
        bodyLayout.show(body, "login");
    }

    private void addLayers() {
        login = new LoginPanel();
        body.add(login, "login");

        profile = new ProfilePanel();
        body.add(profile, "profile");

        loading = new LoadingPanel();
        body.add(loading, "loading");
        Facade.getInstance().setMinecraftDownloadObserver(loading);

        bodyLayout.show(body, "login");
    }

    public void showProfile() {
        header.setLogoutEnabled(true);
        profile.init();
        bodyLayout.show(body, "profile");
    }

    public void showLoadingScreen() {
        header.setLogoutEnabled(false);
        loading.init();
        bodyLayout.show(body, "loading");
    }

    private void centerWindow(MainWindow frame) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((screenSize.getWidth() - getWidth()) / 2);
        int y = (int) ((screenSize.getHeight() - getHeight()) / 2);
        frame.setLocation(x, y);
    }
}
