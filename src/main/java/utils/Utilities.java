package utils;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.util.Random;
import java.util.Set;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.UIManager;

public class Utilities {

	/**
	 * Generates a random number between the given minimum and maximum numbers
	 * @param min
	 * @param max
	 * @return
	 */
	public static int generateRandomNumber(int min, int max) {
		long seed = System.nanoTime();
		Random rand = new Random(seed);
		return rand.nextInt((max - min) + 1) + min;
	}

	/**
	 * Shows an error
	 * @param message
	 */
	public static void showErrorPopup(JComponent component, String message) {
		JOptionPane.showMessageDialog(component, message, "Error", JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Shows a message
	 * @param message
	 */
	public static void showMessagePopup(JComponent component, String message) {
		JOptionPane.showMessageDialog(component, message, "Message", JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Centers a window on the screen
	 * @param window
	 */
	public static void centerWindow(JFrame window) {
		Dimension dScreen = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension dWindow = window.getSize();

		int xEsquina = (dScreen.width / 2) - (dWindow.width / 2);
		int yEsquina = (dScreen.height / 2) - (dWindow.height / 2);

		window.setLocation(xEsquina, yEsquina);
	}

	/**
	 * Sets a border for a JComponent object
	 * @param component
	 * @param borderTitle
	 */
	public static void setBorder(JComponent component, String borderTitle) {
		component.setBorder(javax.swing.BorderFactory.createTitledBorder(null, borderTitle,
				javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION,
				null, null));
	}

	/**
	 * Sets a scroll for a JComponent object
	 * @param component
	 * @param scroll
	 */
	public static void setScroll(JComponent component, JScrollPane scroll) {
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		component.add(scroll, java.awt.BorderLayout.CENTER);
	}

	/**
	 * Sets the default size of the font
	 * @param size
	 */
	public static void setDefaultSize(int size) {
		Set<Object> keySet = UIManager.getLookAndFeelDefaults().keySet();
		Object[] keys = keySet.toArray(new Object[keySet.size()]);

		for (Object key : keys) {
			if (key != null && key.toString().toLowerCase().contains("font")) {
				Font font = UIManager.getDefaults().getFont(key);
				if (font != null) {
					font = font.deriveFont((float) size);
					UIManager.put(key, font);
				}
			}
		}
	}
}
