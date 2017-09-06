package view;

import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import model.Window;
import model.WindowList;
import utils.Utilities;

public class WindowListView extends JPanel implements Observer {
	HashMap<Integer, JList<String>> windows_;

	/**
	 * Creates the window list view
	 * @param windowList
	 */
	public WindowListView(WindowList windowList) {
		Utilities.setDefaultSize(10);
		Utilities.setBorder(this, "Windows");
		windows_ = new HashMap<Integer, JList<String>>();

		JPanel panelNorth = new JPanel();
		panelNorth.setLayout(new GridLayout(2, 3));

		// Creates the windows
		for (Window window : windowList.getList()) {
			JList<String> jListWindow = new JList<String>();
			Utilities.setBorder(jListWindow, "Window " + window.getWindowNumber());
			windows_.put(window.getWindowNumber(), jListWindow);
			updateWindow(window);

			JScrollPane scrollNorth_ = new JScrollPane(jListWindow);
			Utilities.setScroll(panelNorth, scrollNorth_);
			panelNorth.add(scrollNorth_);
		}

		add(panelNorth);

		GridLayout layout = new GridLayout(1, 1);
		layout.setHgap(5);
		layout.setVgap(5);
		setLayout(layout);
	}

	private void updateWindow(Window window) {
		JList<String> jListWindow = windows_.get(window.getWindowNumber());
		jListWindow.setListData(window.toStringArrayForWindow());
	}

	@Override
	public void update(Observable o, Object arg) {
		WindowList list = (WindowList) o;
		for (Window window : list.getList()) {
			updateWindow(window);
		}
	}
}
