package com.craftlauncher.launcher.gui.panel;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * A panel that displays an image.
 */
public class ImagePanel extends JPanel {
	private transient BufferedImage image;

	public ImagePanel(BufferedImage image) {
		this.image = image;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				repaint();
			}
		});
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
	}
}
