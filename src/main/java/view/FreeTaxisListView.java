package view;

import java.awt.GridLayout;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import model.Taxi;
import model.TaxiList;
import utils.Utilities;

public class FreeTaxisListView extends JPanel implements Observer {
	private JList<Taxi> jList_;
	private JScrollPane scroll_;

	public FreeTaxisListView() {
		Utilities.setDefaultSize(10);
		Utilities.setBorder(this, "Free taxis after journeys");
		jList_ = new JList<Taxi>();
		scroll_ = new JScrollPane(jList_);
		Utilities.setScroll(this, scroll_);
		GridLayout layout = new GridLayout(1, 1);
		layout.setHgap(5);
		layout.setVgap(5);
		setLayout(layout);
	}

	/**
	 * Updates the list of the free taxis
	 * @param list
	 */
	private void updateList(TaxiList list) {
		List<Taxi> objDeque = list.getFreeTaxis();
		Taxi[] objArray = new Taxi[objDeque.size()];
		objDeque.toArray(objArray);
		jList_.setListData(objArray);
	}

	/**
	 * Update call from observable
	 */
	@Override
	public void update(Observable o, Object arg) {
		TaxiList list = (TaxiList) o;
		updateList(list);
	}
}
