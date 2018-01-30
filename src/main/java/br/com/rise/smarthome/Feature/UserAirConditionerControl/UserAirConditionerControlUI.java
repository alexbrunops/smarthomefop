package br.com.rise.smarthome.Feature.UserAirConditionerControl;

import br.com.rise.smarthome.BaseComponents.BaseUI;
import br.com.rise.smarthome.Devices.AirConditioner;
import br.com.rise.smarthome.Devices.AutomaticWindow;
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

public class UserAirConditionerControlUI extends BaseUI {

	private UserAirConditionerControl userAirConditionerControl;
	private JComboBox<AirConditioner> cmbAvaliableAirConditioners;
	private JComboBox<AirConditioner> cmbCurrentAirConditioners;
	private JComboBox<AirConditioner> cmbAirConditionersAction;

	public UserAirConditionerControlUI() {
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				updateAvaliableCombo();
				updateCurrentCombo();
			}
		});
		userAirConditionerControl = (UserAirConditionerControl) Main.getHouseInstance().getFeatureByType(UserAirConditionerControl.class);

		setLayout(null);
		setForClass(UserAirConditionerControl.class);
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.RAISED, null, null), "Feature Action", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 204)));
		panel.setBounds(12, 220, 438, 120);
		add(panel);
		panel.setLayout(null);

		JLabel lblAirConditioner = new JLabel("Air Conditioner:");
		lblAirConditioner.setBounds(6, 35, 150, 16);
		panel.add(lblAirConditioner);

		cmbAirConditionersAction = new JComboBox<AirConditioner>();
		cmbAirConditionersAction.setBounds(108, 30, 300, 30);
		panel.add(cmbAirConditionersAction);

		JButton btnSwitchOn = new JButton("Switch On");
		btnSwitchOn.addActionListener(e -> {
			AirConditioner airConditioner = (AirConditioner)cmbAirConditionersAction.getSelectedItem();
			if (airConditioner!=null) {
				String[]args = {String.valueOf(airConditioner.getPin()),"1"};
				userAirConditionerControl.proceedActions(args);
				cmbAvaliableAirConditioners.repaint();
				cmbCurrentAirConditioners.repaint();
				cmbAirConditionersAction.repaint();
			}
		});
		btnSwitchOn.setBounds(43, 70, 110, 25);
		panel.add(btnSwitchOn);

		JButton btnSwitchOff = new JButton("Switch Off");
		btnSwitchOff.addActionListener(e -> {
			AirConditioner airConditioner = (AirConditioner)cmbAirConditionersAction.getSelectedItem();
			if (airConditioner != null) {
				String[]args = {String.valueOf(airConditioner.getPin()),"0"};
				userAirConditionerControl.proceedActions(args);
				cmbAvaliableAirConditioners.repaint();
				cmbCurrentAirConditioners.repaint();
				cmbAirConditionersAction.repaint();
			}
		});
		btnSwitchOff.setBounds(300, 70, 110, 25);
		panel.add(btnSwitchOff);

		JButton btnStateChange = new JButton("State Change");
		btnStateChange.addActionListener(e -> {
			AutomaticWindow automaticWindow = (AutomaticWindow)cmbAirConditionersAction.getSelectedItem();
			if (automaticWindow != null) {
				String[]args = {String.valueOf(automaticWindow.getPin()),"-1"};
				userAirConditionerControl.proceedActions(args);
				cmbAvaliableAirConditioners.repaint();
				cmbCurrentAirConditioners.repaint();
				cmbAirConditionersAction.repaint();
			}
		});
		btnStateChange.setBounds(167, 70, 110, 25);
		panel.add(btnStateChange);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Feature Management", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 204)));
		panel_1.setLayout(null);
		panel_1.setBounds(12, 18, 438, 171);
		add(panel_1);

		JLabel lblAirConditionerPin = new JLabel("Avaliable Air Conditioners:");
		lblAirConditionerPin.setBounds(6, 35, 180, 16);
		panel_1.add(lblAirConditionerPin);
		cmbAvaliableAirConditioners = new JComboBox<AirConditioner>();
		updateAvaliableCombo();
		cmbAvaliableAirConditioners.addActionListener(new ActionListener() {
			public synchronized void actionPerformed(ActionEvent e) {
				AirConditioner airConditioner = (AirConditioner) cmbAvaliableAirConditioners.getSelectedItem();
				if(!userAirConditionerControl.getAirConditioners().contains(airConditioner)){
					userAirConditionerControl.getAirConditioners().add(airConditioner);
					updateCurrentCombo();
				}
			}
		});
		cmbAvaliableAirConditioners.setBounds(170, 30, 250, 30);
		panel_1.add(cmbAvaliableAirConditioners);

		JLabel lblCurrentAirConditioners = new JLabel("Current Air Conditioners:");
		lblCurrentAirConditioners.setBounds(6, 96, 180, 16);
		panel_1.add(lblCurrentAirConditioners);

		cmbCurrentAirConditioners = new JComboBox<AirConditioner>();
		updateCurrentCombo();
		cmbCurrentAirConditioners.addActionListener(new ActionListener() {
			public synchronized void actionPerformed(ActionEvent e) {
				AirConditioner airConditioner = (AirConditioner) cmbCurrentAirConditioners.getSelectedItem();
				airConditioner.deactivate();
				userAirConditionerControl.getAirConditioners().remove(airConditioner);
				updateCurrentCombo();
			}
		});
		cmbCurrentAirConditioners.setBounds(170, 95, 250, 30);
		panel_1.add(cmbCurrentAirConditioners);

		JLabel lblWhen = new JLabel("Clicking in a current air Conditioner combo item you remove them to the feature.");
		lblWhen.setForeground(Color.RED);
		lblWhen.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		lblWhen.setBounds(6, 135, 412, 16);
		panel_1.add(lblWhen);
	}

	private void updateAvaliableCombo() {
		ArrayList<Hardware> hardwares = Main.getHouseInstance().getAllHardwareByType(AirConditioner.class);
		AirConditioner[] airConditioners = hardwares.toArray(new AirConditioner[hardwares.size()]);
		cmbAvaliableAirConditioners.setModel(new DefaultComboBoxModel<AirConditioner>(airConditioners));
	}

	private void updateCurrentCombo() {
		if (userAirConditionerControl.getAirConditioners().size() > 0) {
			AirConditioner[] airConditioners = userAirConditionerControl.getAirConditioners().toArray(new AirConditioner[userAirConditionerControl.getAirConditioners().size()]);
			cmbCurrentAirConditioners.setModel(new DefaultComboBoxModel<AirConditioner>(airConditioners));
			cmbAirConditionersAction.setModel(new DefaultComboBoxModel<AirConditioner>(airConditioners));
		} else {
			cmbCurrentAirConditioners.setModel(new DefaultComboBoxModel<AirConditioner>());
			cmbAirConditionersAction.setModel(new DefaultComboBoxModel<AirConditioner>());
		}
	}

}
