package com.guillermods.samples.trayicon;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Clase principal
 * 
 * @author gdiazs
 *
 */
public class TrayIconView implements Runnable {

	private static final String LOCK_FILEPATH = System.getProperty("java.io.tmpdir") + File.separator + "bac.lock";
	private static final File lock = new File(LOCK_FILEPATH);
	private static boolean locked = false;

	private TrayIcon trayIcon;

	private Image image = null;

	private PopupMenu popup;

	private MenuItem closeItem;

	/**
	 * Se crea un hilo para mostrar el ícono
	 */
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
		try {
			if (lock()) {
				Thread thread = new Thread(new TrayIconView());
				thread.start();
			} else {
				System.exit(0);
			}
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	/**
	 * Crea un lock file para validar si la applicación se encuentra en
	 * ejecución
	 * 
	 * @return
	 * @throws IOException
	 */
	public static boolean lock() throws IOException {
		if (locked)
			return true;

		if (lock.exists())
			return false;

		lock.createNewFile();
		lock.deleteOnExit();
		locked = true;
		return true;
	}

}
