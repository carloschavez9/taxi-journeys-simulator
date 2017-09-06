package view;

import java.awt.GridLayout;
import java.util.ArrayDeque;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import model.Group;
import model.GroupList;
import utils.Utilities;

public class GroupListView extends JPanel implements Observer {
	private JList<Group> jList_;
	private JScrollPane scroll_;

	public GroupListView() {
		Utilities.setDefaultSize(10);
		Utilities.setBorder(this, "Groups");
		jList_ = new JList<Group>();
		scroll_ = new JScrollPane(jList_);
		Utilities.setScroll(this, scroll_);
		GridLayout layout = new GridLayout(1, 1);
		layout.setHgap(5);
		layout.setVgap(5);
		setLayout(layout);
	}

	private void updateList(GroupList list) {
		ArrayDeque<Group> objDeque = list.getDeque();
		Group[] objArray = new Group[objDeque.size()];
		objDeque.toArray(objArray);
		jList_.setListData(objArray);
	}

	@Override
	public void update(Observable o, Object arg) {
		GroupList list = (GroupList) o;
		updateList(list);
	}
}
