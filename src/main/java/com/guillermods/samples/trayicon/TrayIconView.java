package com.guillermods.samples.trayicon;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.io.IOException;

import javax.imageio.ImageIO;

public class TrayIconView implements Runnable {

	private TrayIcon trayIcon;

	private Image image = null;

	private PopupMenu popup;

	private MenuItem closeItem;

	public void run() {
		if (SystemTray.isSupported()) {
			try {
				this.image = ImageIO.read(TrayIconView.class.getResourceAsStream("/img/office-material.png"));
			} catch (IOException e) {

				e.printStackTrace();
			}

			SystemTray tray = SystemTray.getSystemTray();

			this.popup = new PopupMenu();
			this.closeItem = new MenuItem("Close");

			this.closeItem.addActionListener(ActionListener -> {
				System.exit(0);
			});

			this.popup.add(closeItem);
			trayIcon = new TrayIcon(image, "Application", this.popup);

			try {
				tray.add(trayIcon);
			} catch (AWTException e) {

				e.printStackTrace();
			}
		}

	}

	public static void main(String args[]) {


		Thread thread = new Thread(new TrayIconView());
		thread.start();
	}

}
