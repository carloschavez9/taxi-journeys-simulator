package view;

import java.awt.GridLayout;
import java.util.ArrayDeque;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import model.Taxi;
import model.TaxiList;
import utils.Utilities;

public class TaxiListView extends JPanel implements Observer {
	private JList<Taxi> jList_;
	private JScrollPane scroll_;

	public TaxiListView() {
		Utilities.setDefaultSize(10);
		Utilities.setBorder(this, "Taxis");
		jList_ = new JList<Taxi>();
		scroll_ = new JScrollPane(jList_);
		Utilities.setScroll(this, scroll_);
		GridLayout layout = new GridLayout(1, 1);
		layout.setHgap(5);
		layout.setVgap(5);
		setLayout(layout);
	}

	private void updateList(TaxiList list) {
		ArrayDeque<Taxi> objDeque = list.getDeque();
		Taxi[] objArray = new Taxi[objDeque.size()];
		objDeque.toArray(objArray);
		jList_.setListData(objArray);
	}

	@Override
	public void update(Observable o, Object arg) {
		TaxiList list = (TaxiList) o;
		updateList(list);
	}
}
