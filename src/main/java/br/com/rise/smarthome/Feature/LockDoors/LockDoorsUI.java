package br.com.rise.smarthome.Feature.LockDoors;

import br.com.rise.smarthome.BaseComponents.BaseUI;
import br.com.rise.smarthome.Devices.AutomaticDoor;
import br.com.rise.smarthome.Devices.Hardware;
import br.com.rise.smarthome.Home.Main;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;

public class LockDoorsUI extends BaseUI {

	private LockDoors lockDoors;
	private JComboBox<AutomaticDoor> cmbAvaliableDoors;
	private JComboBox<AutomaticDoor> cmbCurrentDoors;
	private JComboBox<AutomaticDoor> cmbDoorsAction;

	public LockDoorsUI() {
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				updateAvaliableCombo();
				updateCurrentCombo();
			}
		});
		lockDoors = (LockDoors) Main.getHouseInstance().getFeatureByType(LockDoors.class);

		setLayout(null);
		setForClass(LockDoors.class);
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.RAISED, null, null), "Feature Action", TitledBorder.LEADING, TitledBorder.TOP, null,  new Color(0, 0, 204)));
		panel.setBounds(12, 220, 438, 120);
		add(panel);
		panel.setLayout(null);

		JLabel lblWindow = new JLabel("Door:");
		lblWindow.setBounds(6, 35, 150, 16);
		panel.add(lblWindow);

		cmbDoorsAction = new JComboBox<AutomaticDoor>();
		cmbDoorsAction.setBounds(108, 30, 300, 30);
		panel.add(cmbDoorsAction);

		JButton btnSwitchOn = new JButton("Switch On");
		btnSwitchOn.addActionListener(e -> {
			AutomaticDoor automaticDoor = (AutomaticDoor)cmbDoorsAction.getSelectedItem();
			if (automaticDoor != null) {
				String[]args = {String.valueOf(automaticDoor.getPin()),"1"};
				lockDoors.proceedActions(args);
				cmbAvaliableDoors.repaint();
				cmbCurrentDoors.repaint();
				cmbDoorsAction.repaint();
			}
		});
		btnSwitchOn.setBounds(43, 70, 110, 25);
		panel.add(btnSwitchOn);

		JButton btnSwitchOff = new JButton("Switch Off");
		btnSwitchOff.addActionListener(e -> {
			AutomaticDoor automaticWindow = (AutomaticDoor)cmbDoorsAction.getSelectedItem();
			if (automaticWindow != null) {
				String[]args = {String.valueOf(automaticWindow.getPin()),"0"};
				lockDoors.proceedActions(args);
				cmbAvaliableDoors.repaint();
				cmbCurrentDoors.repaint();
				cmbDoorsAction.repaint();
			}
		});
		btnSwitchOff.setBounds(300, 70, 110, 25);
		panel.add(btnSwitchOff);


		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Feature Management", TitledBorder.LEADING, TitledBorder.TOP, null,  new Color(0, 0, 204)));
		panel_1.setLayout(null);
		panel_1.setBounds(6, 6, 438, 171);
		add(panel_1);

		JLabel lblAutomaticWindowPin = new JLabel("Avaliable Doors:");
		lblAutomaticWindowPin.setBounds(6, 35, 180, 16);
		panel_1.add(lblAutomaticWindowPin);
		cmbAvaliableDoors = new JComboBox<AutomaticDoor>();
		updateAvaliableCombo();
		cmbAvaliableDoors.addActionListener(new ActionListener() {
			public synchronized void actionPerformed(ActionEvent e) {
				AutomaticDoor automaticWindow = (AutomaticDoor) cmbAvaliableDoors.getSelectedItem();
				if (!lockDoors.getautomaticDoors().contains(automaticWindow)) {
					lockDoors.getautomaticDoors().add(automaticWindow);
					updateCurrentCombo();
				}
			}
		});
		cmbAvaliableDoors.setBounds(170, 30, 250, 30);
		panel_1.add(cmbAvaliableDoors);

		JLabel lblCurrentWindows = new JLabel("Current Doors:");
		lblCurrentWindows.setBounds(6, 96, 180, 16);
		panel_1.add(lblCurrentWindows);

		cmbCurrentDoors = new JComboBox<AutomaticDoor>();
		updateCurrentCombo();
		cmbCurrentDoors.addActionListener(new ActionListener() {
			public synchronized void actionPerformed(ActionEvent e) {
				AutomaticDoor automaticDoor = (AutomaticDoor) cmbCurrentDoors.getSelectedItem();
				automaticDoor.deactivate();
				lockDoors.getautomaticDoors().remove(automaticDoor);
				updateCurrentCombo();
			}
		});
		cmbCurrentDoors.setBounds(170, 95, 250, 30);
		panel_1.add(cmbCurrentDoors);

		JLabel lblWhen = new JLabel("Clicking in a current Window combo item you remove them to the feature.");
		lblWhen.setForeground(Color.RED);
		lblWhen.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		lblWhen.setBounds(6, 135, 412, 16);
		panel_1.add(lblWhen);
	}

	private void updateAvaliableCombo() {
		ArrayList<Hardware> hardwares = Main.getHouseInstance().getAllHardwareByType(AutomaticDoor.class);
		AutomaticDoor[] automaticDoors = hardwares.toArray(new AutomaticDoor[hardwares.size()]);
		cmbAvaliableDoors.setModel(new DefaultComboBoxModel<AutomaticDoor>(automaticDoors));
	}

	private void updateCurrentCombo() {
		if(lockDoors.getautomaticDoors().size()>0){
			AutomaticDoor[] automaticDoors = lockDoors.getautomaticDoors().toArray(new AutomaticDoor[lockDoors.getautomaticDoors().size()]);
			cmbCurrentDoors.setModel(new DefaultComboBoxModel<AutomaticDoor>(automaticDoors));
			cmbDoorsAction.setModel(new DefaultComboBoxModel<AutomaticDoor>(automaticDoors));
		}else{
			cmbCurrentDoors.setModel(new DefaultComboBoxModel<AutomaticDoor>());
			cmbDoorsAction.setModel(new DefaultComboBoxModel<AutomaticDoor>());
		}
	}

}
