package view;

import java.util.Hashtable;
import java.util.Observable;
import java.util.Observer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import model.TaxiServiceModel;
import utils.Utilities;

public class TaxiServiceView implements Observer {

	TaxiServiceModel taxiServiceModel_;
	int numberOfWindows_;
	public JFrame mainFrame_;
	private JButton taxiButton_;
	private JButton groupButton_;
	private JSlider speedSlider_;
	final static boolean shouldFill = true;
	final static boolean shouldWeightX = true;
	final static boolean RIGHT_TO_LEFT = false;

	public TaxiServiceView(TaxiServiceModel taxiServiceModel) {
		taxiServiceModel_ = taxiServiceModel;
		taxiServiceModel.addObserver(this);

		// Buttons
		taxiButton_ = new JButton("Add Taxi");
		groupButton_ = new JButton("Add Group");

		// Slider
		speedSlider_ = new JSlider();
		speedSlider_.setMajorTickSpacing(10);
		speedSlider_.setMinorTickSpacing(1);
		speedSlider_.setMaximum(10000);
		speedSlider_.setMinimum(0);
		speedSlider_.setValue(1000);
		speedSlider_.setInverted(false);
		speedSlider_.setPaintTicks(true);
		speedSlider_.setPaintTrack(true);
		speedSlider_.setPaintLabels(true);
		Hashtable<Integer, JLabel> labelTable = new Hashtable<Integer, JLabel>();
		labelTable.put(new Integer(0), new JLabel("Fast"));
		labelTable.put(new Integer(10000), new JLabel("Slow"));
		speedSlider_.setLabelTable(labelTable);
		speedSlider_.setPreferredSize(new Dimension(500, 50));

		Utilities.setDefaultSize(12);
	}

	public void createMainWindow() {
		// set up observers
		TaxiListView taxiListView = new TaxiListView();
		taxiServiceModel_.addTaxiObserver(taxiListView);

		WindowListView windowListView = new WindowListView(taxiServiceModel_.getWindowList());
		taxiServiceModel_.addWindowObserver(windowListView);

		GroupListView groupListView = new GroupListView();
		taxiServiceModel_.addGroupObserver(groupListView);

		JourneyListView journeyListView = new JourneyListView();
		taxiServiceModel_.addJourneyObserver(journeyListView);

		CheapestJourneyListView cheapestJourneyListView = new CheapestJourneyListView();
		taxiServiceModel_.addCheapestJourneyObserver(cheapestJourneyListView);

		DearestJourneyListView dearestJourneyListView = new DearestJourneyListView();
		taxiServiceModel_.addDearestJourneyObserver(dearestJourneyListView);

		FreeTaxisListView freeTaxisView = new FreeTaxisListView();
		taxiServiceModel_.addFreeTaxisObserver(freeTaxisView);

		mainFrame_ = new JFrame("Main Simulation Window");
		mainFrame_.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame_.getContentPane().setLayout(new BorderLayout());

		JPanel panelNorth = new JPanel();
		panelNorth.setLayout(new BorderLayout());
		JPanel panelLogo = new JPanel();
		ImageIcon icono = new ImageIcon("data/logo.png");
		JLabel imagen = new JLabel("");
		imagen.setIcon(icono);
		panelLogo.setBackground(new Color(248, 179, 35));
		panelLogo.add(imagen);
		panelNorth.add(panelLogo, BorderLayout.NORTH);

		JPanel panelOptions = new JPanel();
		panelOptions.setLayout(new FlowLayout());
		panelOptions.add(taxiButton_);
		panelOptions.add(groupButton_);
		panelOptions.add(speedSlider_);
		panelNorth.add(panelOptions, BorderLayout.SOUTH);

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(1, 1));
		JTabbedPane tabbedPane = new JTabbedPane();
		JPanel panel1 = new JPanel(false);
		panel1.setLayout(new GridLayout(1, 0));
		JPanel listPanel = new JPanel();
		listPanel.add(taxiListView);
		listPanel.add(windowListView);
		listPanel.add(groupListView);

		listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));

		panel1.add(listPanel);

		panel1.add(journeyListView);
		tabbedPane.addTab("Main", panel1);
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
		JPanel panel2 = new JPanel();
		panel2.setLayout(new GridLayout(0, 1));
		panel2.add(cheapestJourneyListView);
		panel2.add(dearestJourneyListView);
		panel2.add(freeTaxisView);
		tabbedPane.addTab("Reports", panel2);
		tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);
		mainPanel.add(tabbedPane);

		mainFrame_.add(panelNorth, BorderLayout.NORTH);
		mainFrame_.add(mainPanel, BorderLayout.CENTER);
		// Display the window.
		mainFrame_.pack();
		mainFrame_.setExtendedState(JFrame.MAXIMIZED_BOTH);
		mainFrame_.setVisible(true);
	}

	/**
	 * Gets the add taxi button
	 * @return
	 */
	public JButton getAddTaxiButton() {
		return taxiButton_;
	}

	/**
	 * Gets the add group button
	 * @return
	 */
	public JButton getAddGroupButton() {
		return groupButton_;
	}

	/**
	 * Gets the speed slider
	 * @return
	 */
	public JSlider getSpeedSlider() {
		return speedSlider_;
	}

	/**
	 * Disables the add taxi button
	 */
	public void disableAddTaxiButton() {
		taxiButton_.setEnabled(false);
	}

	/**
	 * Disables the add group button
	 */
	public void disableAddGroupButton() {
		groupButton_.setEnabled(false);
	}

	@Override
	public void update(Observable o, Object arg) {

	}
}
