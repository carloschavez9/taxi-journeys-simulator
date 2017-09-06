package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import model.Destination;
import model.DestinationList;
import model.Group;
import model.GroupList;
import model.Taxi;
import model.TaxiList;
import model.TaxiServiceModel;
import utils.Utilities;
import view.TaxiServiceView;

public class TaxiServiceController {
	private TaxiServiceModel taxiServiceModel_;
	private TaxiServiceView taxiServiceView_;
	private TaxiButtonListener taxiButtonListener_;
	private GroupButtonListener groupButtonListener_;
	private SpeedSlidernListener speedSlidernListener_;

	public TaxiServiceController(TaxiServiceModel taxiServiceModel, TaxiServiceView taxiServiceView) {
		groupButtonListener_ = new GroupButtonListener();
		taxiButtonListener_ = new TaxiButtonListener();
		speedSlidernListener_ = new SpeedSlidernListener();
		taxiServiceModel_ = taxiServiceModel;
		taxiServiceView_ = taxiServiceView;
		Thread taxiServiceModelThread = new Thread(taxiServiceModel_);
		// System.out.println("Simulation Started");
		taxiServiceView_.createMainWindow();
		taxiServiceModelThread.start();

		// Adds listeners to components
		taxiServiceView_.getAddGroupButton().addActionListener(groupButtonListener_);
		taxiServiceView_.getAddTaxiButton().addActionListener(taxiButtonListener_);
		taxiServiceView_.getSpeedSlider().addChangeListener(speedSlidernListener_);
	}

	class TaxiButtonListener implements ActionListener {
		public TaxiButtonListener() {
			super();
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (taxiServiceModel_.isFinished())
				Utilities.showMessagePopup(null, "The simulation has finished");
			else {
				int passengers = Utilities.generateRandomNumber(taxiServiceModel_.getMinTaxiSize(),
						taxiServiceModel_.getMaxTaxiSize());
				TaxiList taxiList = taxiServiceModel_.getTaxiList();
				Taxi taxi = taxiList.getNextDefaultTaxi();
				if (taxi != null) {
					taxi.setMaxPassengers(passengers);
					taxiList.addLast(taxi);
				} else
					taxiServiceView_.disableAddTaxiButton();

				taxiServiceModel_.setTaxiList(taxiList);
			}
		}
	}

	class SpeedSlidernListener implements ChangeListener {
		public SpeedSlidernListener() {
			super();
		}

		@Override
		public void stateChanged(ChangeEvent e) {
			double processingTime = taxiServiceView_.getSpeedSlider().getValue();
			TaxiServiceModel.setProcessingTimeMultiplier(processingTime);
		}
	}

	class GroupButtonListener implements ActionListener {
		public GroupButtonListener() {
			super();
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (taxiServiceModel_.isFinished())
				Utilities.showMessagePopup(null, "The simulation has finished");
			else {
				// Create group list and add elements
				GroupList groupList = taxiServiceModel_.getGroupList();
				DestinationList destinationList = taxiServiceModel_.getDestinationList_();
				int passengers = Utilities.generateRandomNumber(taxiServiceModel_.getMinGroupSize_(),
						taxiServiceModel_.getMaxGroupSize_());
				Destination destination = destinationList.getRandomDestination();
				Group group = new Group(groupList.getNextGroupNumber(), passengers, destination);
				groupList.addLast(group);
				taxiServiceModel_.setGroupList(groupList);
			}
		}
	}
}
