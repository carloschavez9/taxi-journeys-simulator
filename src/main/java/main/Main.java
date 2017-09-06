package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.*;

import controller.TaxiServiceController;
import model.TaxiServiceModel;
import utils.Logger;
import utils.Logger.LoggerType;
import utils.Utilities;
import view.TaxiServiceView;

/**
 * Main user interface operations
 */
public class Main {

	/**
	 * Main run of the program
	 * @param args
	 */
	public static void main(String[] args) {
		Logger loggerInfo_ = Logger.getInstance(LoggerType.INFO);
		Logger loggerError_ = Logger.getInstance(LoggerType.ERROR);
		Logger loggerDebug_ = Logger.getInstance(LoggerType.DEBUG);
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			String[] labelNames = { "Number of Windows", "Number of Groups", "Minimum Group Size", "Maximum Group Size",
					"Number of Taxis", "Minimum Taxi Capacity", "Maximum Taxi Capacity", "Processing Time Delay" };
			ArrayList<Component> list = new ArrayList<Component>();
			List<Number> values = Arrays.asList(5, 50, 1, 10, 50, 1, 6, 1);

			// Spinner for windows
			SpinnerModel model = new SpinnerNumberModel(values.get(0), 1, 20, 1);
			list.add(new JSpinner(model));

			for (int i = 1; i < values.size(); i++) {
				SpinnerModel modelTemp = new SpinnerNumberModel(values.get(i), 1, 100, 1);
				// Spinner for time delay
				if (i == values.size() - 1)
					modelTemp = new SpinnerNumberModel(values.get(i), 1, 5, 1);
				list.add(new JSpinner(modelTemp));
			}

			// set up window title
			JFrame startFrame = new JFrame("Main");
			startFrame.setResizable(false);
			startFrame.setLayout(new BorderLayout());

			// ensure program ends when window closes
			startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			startFrame.setSize(150, 600);
			// startFrame.setLocation(10, 20);

			// add button panel and result field to the content pane
			Container contentPane = startFrame.getContentPane();
			contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

			JPanel panelMain = new JPanel();
			panelMain.setLayout(new GridLayout(list.size(), 2));

			for (int i = 0; i < list.size(); i++) {
				JLabel label = new JLabel(labelNames[i]);
				panelMain.add(label);
				panelMain.add(list.get(i));
			}
			
			JPanel panelLogo = new JPanel();
			ImageIcon icono = new ImageIcon("data/logo_min.png");
			JLabel imagen = new JLabel("");
			imagen.setIcon(icono);
			panelLogo.setBackground(new Color(248, 179, 35));
			panelLogo.add(imagen);
			startFrame.add(panelLogo, BorderLayout.NORTH);

			startFrame.add(panelMain, BorderLayout.CENTER);

			JPanel panelButtons = new JPanel();
			JButton simulationButton = new JButton("Start Simulation");
			panelButtons.add(simulationButton);
			startFrame.add(panelButtons, BorderLayout.SOUTH);

			// pack and set visible
			startFrame.pack();
			startFrame.setVisible(true);
			Utilities.centerWindow(startFrame);
			simulationButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					startFrame.dispose();
					int numWindows = (Integer) ((JSpinner) list.get(0)).getValue();
					int numGroups = (Integer) ((JSpinner) list.get(1)).getValue();
					int minGroupSize = (Integer) ((JSpinner) list.get(2)).getValue();
					int maxGroupSize = (Integer) ((JSpinner) list.get(3)).getValue();
					int numTaxis = (Integer) ((JSpinner) list.get(4)).getValue();
					int minTaxiSize = (Integer) ((JSpinner) list.get(5)).getValue();
					int maxTaxiSize = (Integer) ((JSpinner) list.get(6)).getValue();
					int processingTimeMultiplier = (Integer) ((JSpinner) list.get(7)).getValue(); // in seconds
					System.out.printf(
							"Running parameters:{ numWindows: %d, numGroups: %d, minGroupSize: %d, "
									+ "maxGroupSize: %d, numTaxis: %d, minTaxiSize: %d, maxTaxiSize: %d, processingTimeMultiplier: %d}",
							numWindows, numGroups, minGroupSize, maxGroupSize, numTaxis, minTaxiSize, maxTaxiSize,
							processingTimeMultiplier);
					loggerDebug_.log(
							"Running parameters:{ numWindows: %d, numGroups: %d, minGroupSize: %d, "
									+ "maxGroupSize: %d, numTaxis: %d, minTaxiSize: %d, maxTaxiSize: %d, processingTimeMultiplier: %d}",
							numWindows, numGroups, minGroupSize, maxGroupSize, numTaxis, minTaxiSize, maxTaxiSize,
							processingTimeMultiplier);
					TaxiServiceModel taxiServiceModel;
					try {
						taxiServiceModel = new TaxiServiceModel(numWindows, numGroups, minGroupSize, maxGroupSize,
								numTaxis, minTaxiSize, maxTaxiSize, processingTimeMultiplier);
						TaxiServiceView taxiServiceView = new TaxiServiceView(taxiServiceModel);
						TaxiServiceController taxiServiceController = new TaxiServiceController(taxiServiceModel,
								taxiServiceView);
						loggerInfo_.log("TaxiService Started");
						// taxiServiceView.setVisible(true);
					} catch (Exception e1) {
						loggerError_.log(e1.toString());
						e1.printStackTrace();
					}
				}
			});
		} catch (Exception e) {
			loggerError_.log(e.toString());
			e.printStackTrace();
		}
	}
}
