package br.com.rise.smarthome.Feature.UserIllumination;

import br.com.rise.smarthome.BaseComponents.BaseUI;
import br.com.rise.smarthome.Devices.Hardware;
import br.com.rise.smarthome.Devices.Led;
import br.com.rise.smarthome.Home.Main;
import org.apache.commons.collections.CollectionUtils;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;

public class UserIlluminationUI extends BaseUI {

	private JComboBox<Led> cmbAvaliableLeds;
	private JComboBox<Led> cmbCurrentLeds;
	private UserIllumination userIllumination;
	private JComboBox<Led> cmbLedsAction;

	public UserIlluminationUI() {

		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				updateAvaliableCombo();
				updateCurrentCombo();
			}
		});

		userIllumination = (UserIllumination) Main.getHouseInstance().getFeatureByType(UserIllumination.class);

		setLayout(null);
		setForClass(UserIllumination.class);
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.RAISED, null, null), "Feature Action", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 204)));
		panel.setBounds(12, 220, 438, 120);
		add(panel);
		panel.setLayout(null);

		JLabel lblLed = new JLabel("Led:");
		lblLed.setBounds(6, 35, 26, 16);
		panel.add(lblLed);

		cmbLedsAction = new JComboBox<Led>();
		cmbLedsAction.setBounds(43, 30, 370, 30);
		panel.add(cmbLedsAction);

		JButton btnSwitchOn = new JButton("Switch On");
		btnSwitchOn.addActionListener(e -> {
			Led led = (Led)cmbLedsAction.getSelectedItem();
			if(led != null){
				String[]args = {String.valueOf(led.getPin()),"1"};
				userIllumination.proceedActions(args);
				cmbAvaliableLeds.repaint();
				cmbCurrentLeds.repaint();
				cmbLedsAction.repaint();
			} else {
				JOptionPane.showMessageDialog(null, "Select a Led.");
			}
		});
		btnSwitchOn.setBounds(43, 70, 110, 25);
		panel.add(btnSwitchOn);

		JButton btnSwitchOff = new JButton("Switch Off");
		btnSwitchOff.addActionListener(e -> {
			Led led = (Led)cmbLedsAction.getSelectedItem();
			if(led != null){
				String[]args = {String.valueOf(led.getPin()),"0"};
				userIllumination.proceedActions(args);
				cmbAvaliableLeds.repaint();
				cmbCurrentLeds.repaint();
				cmbLedsAction.repaint();
			} else {
				JOptionPane.showMessageDialog(null, "Select a Led.");
			}
		});
		btnSwitchOff.setBounds(300, 70, 110, 25);
		panel.add(btnSwitchOff);

		JButton btnStateChange = new JButton("State Change");
		btnStateChange.addActionListener(e -> {
			Led led = (Led)cmbLedsAction.getSelectedItem();
			if(led != null){
				String[]args = {String.valueOf(led.getPin()),"-1"};
				userIllumination.proceedActions(args);
				cmbAvaliableLeds.repaint();
				cmbCurrentLeds.repaint();
				cmbLedsAction.repaint();
			} else {
				JOptionPane.showMessageDialog(null, "Select a Led.");
			}
		});
		btnStateChange.setBounds(167, 70, 110, 25);
		panel.add(btnStateChange);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Feature Management", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 204)));
		panel_1.setLayout(null);
		panel_1.setBounds(12, 18, 438, 171);
		add(panel_1);

		JLabel lblLedPin = new JLabel("Avaliable Leds:");
		lblLedPin.setBounds(6, 35, 101, 16);
		panel_1.add(lblLedPin);
		cmbAvaliableLeds = new JComboBox<Led>();
		updateAvaliableCombo();
		cmbAvaliableLeds.addActionListener(new ActionListener() {
			public synchronized void actionPerformed(ActionEvent e) {
				Led led = (Led) cmbAvaliableLeds.getSelectedItem();
				if(!userIllumination.getLeds().contains(led)){
					userIllumination.getLeds().add(led);
					updateCurrentCombo();
				}
			}
		});
		cmbAvaliableLeds.setBounds(103, 30, 315, 30);
		panel_1.add(cmbAvaliableLeds);

		JLabel lblCurrentLeds = new JLabel("Current Leds:");
		lblCurrentLeds.setBounds(6, 103, 101, 16);
		panel_1.add(lblCurrentLeds);

		cmbCurrentLeds = new JComboBox<Led>();
		updateCurrentCombo();
		cmbCurrentLeds.addActionListener(new ActionListener() {
			public synchronized void actionPerformed(ActionEvent e) {
				Led led = (Led) cmbCurrentLeds.getSelectedItem();
				led.deactivate();
				userIllumination.getLeds().remove(led);
				updateCurrentCombo();
			}
		});
		cmbCurrentLeds.setBounds(103, 95, 315, 30);
		panel_1.add(cmbCurrentLeds);

		JLabel lblWhen = new JLabel("Clicking in a current led combo item you remove them to the feature.");
		lblWhen.setForeground(Color.RED);
		lblWhen.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		lblWhen.setBounds(6, 135, 412, 16);
		panel_1.add(lblWhen);
		
	}

	private void updateAvaliableCombo() {

		ArrayList<Hardware> hardwares = Main.getHouseInstance().getAllHardwareByType(Led.class);
		Led[] leds = hardwares.toArray(new Led[hardwares.size()]);
		cmbAvaliableLeds.setModel(new DefaultComboBoxModel<Led>(leds));

	}

	private void updateCurrentCombo() {

		if(CollectionUtils.isNotEmpty(userIllumination.getLeds())) {
			Led[] leds = userIllumination.getLeds().toArray(new Led[userIllumination.getLeds().size()]);
			cmbCurrentLeds.setModel(new DefaultComboBoxModel<Led>(leds));
			cmbLedsAction.setModel(new DefaultComboBoxModel<Led>(leds));
		}else{
			cmbCurrentLeds.setModel(new DefaultComboBoxModel<Led>());
			cmbLedsAction.setModel(new DefaultComboBoxModel<Led>());
		}

	}
	
}
