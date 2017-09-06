package view;

import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.AbstractTableModel;

import model.Journey;
import model.JourneyList;
import utils.Utilities;

public class DearestJourneyListView extends JPanel implements Observer {
	private JList<Journey> jList_;
	private JScrollPane scroll_;

	public DearestJourneyListView() {
		Utilities.setDefaultSize(10);
		Utilities.setBorder(this, "10 Dearest Journeys");
		jList_ = new JList<Journey>();
		scroll_ = new JScrollPane(jList_);
		Utilities.setScroll(this, scroll_);
		GridLayout layout = new GridLayout(1, 1);
		layout.setHgap(5);
		layout.setVgap(5);
		setLayout(layout);
	}

	/**
	 * Updates the list of the dearest journeys
	 * @param list
	 */
	private void updateList(JourneyList list) {
		List<Journey> objDeque = list.getDearestNJourneys(10);
		Journey[] objArray = new Journey[objDeque.size()];
		objDeque.toArray(objArray);
		jList_.setListData(objArray);
	}

	/**
	 * Update call from observable
	 */
	@Override
	public void update(Observable o, Object arg) {
		JourneyList list = (JourneyList) o;
		updateList(list);
	}
}
