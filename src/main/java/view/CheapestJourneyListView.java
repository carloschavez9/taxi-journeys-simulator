package view;

import java.awt.GridLayout;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import model.Journey;
import model.JourneyList;
import utils.Utilities;

public class CheapestJourneyListView extends JPanel implements Observer {
	private JList<Journey> jList_;
	private JScrollPane scroll_;

	public CheapestJourneyListView() {
		Utilities.setDefaultSize(10);
		Utilities.setBorder(this, "10 Cheapest Journeys");
		jList_ = new JList<Journey>();
		scroll_ = new JScrollPane(jList_);
		Utilities.setScroll(this, scroll_);
		GridLayout layout = new GridLayout(1, 1);
		layout.setHgap(5);
		layout.setVgap(5);
		setLayout(layout);
	}

	/**
	 * Updates the list of the cheapest journeys
	 * @param list
	 */
	private void updateList(JourneyList list) {
		List<Journey> objDeque = list.getCheapestNJourneys(10);
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
