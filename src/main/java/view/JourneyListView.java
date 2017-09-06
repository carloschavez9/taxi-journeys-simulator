package view;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import model.Journey;
import model.JourneyList;
import utils.Utilities;

public class JourneyListView extends JPanel implements Observer {
	private JList<Journey> jList_;
	private JScrollPane scroll_;

	public JourneyListView() {
		Utilities.setDefaultSize(10);
		Utilities.setBorder(this, "Journeys");
		jList_ = new JList<Journey>();
		scroll_ = new JScrollPane(jList_);
		Utilities.setScroll(this, scroll_);
		GridLayout layout = new GridLayout(1, 1);
		layout.setHgap(5);
		layout.setVgap(5);
		setLayout(layout);
	}

	private void updateList(JourneyList list) {
		ArrayList<Journey> objDeque = list.getList();
		Journey[] objArray = new Journey[objDeque.size()];
		objDeque.toArray(objArray);
		jList_.setListData(objArray);
	}

	@Override
	public void update(Observable o, Object arg) {
		JourneyList list = (JourneyList) o;
		updateList(list);
	}
}
