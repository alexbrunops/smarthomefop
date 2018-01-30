package br.com.rise.smarthome.Feature.UserWindowControl;

import br.com.rise.smarthome.BaseComponents.BaseUI;
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

public class UserWindowControlUI extends BaseUI {

	private UserWindowControl userWindowControl;
	private JComboBox<AutomaticWindow> cmbAvaliableWindows;
	private JComboBox<AutomaticWindow> cmbCurrentWindows;
	private JComboBox<AutomaticWindow> cmbWindowsAction;

	public UserWindowControlUI() {
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				updateAvaliableCombo();
				updateCurrentCombo();
			}
		});
		userWindowControl = (UserWindowControl) Main.getHouseInstance().getFeatureByType(UserWindowControl.class);

		setLayout(null);
		setForClass(UserWindowControl.class);
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.RAISED, null, null), "Feature Action", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 204)));
		panel.setBounds(12, 220, 438, 120);
		add(panel);
		panel.setLayout(null);

		JLabel lblWindow = new JLabel("Window :");
		lblWindow.setBounds(6, 35, 80, 16);
		panel.add(lblWindow);

		cmbWindowsAction = new JComboBox<AutomaticWindow>();
		cmbWindowsAction.setBounds(83, 30, 326, 30);
		panel.add(cmbWindowsAction);

		JButton btnSwitchOn = new JButton("Switch On");
		btnSwitchOn.addActionListener(e -> {
			AutomaticWindow automaticWindow = (AutomaticWindow)cmbWindowsAction.getSelectedItem();
			if(automaticWindow!=null){
				String[]args = {String.valueOf(automaticWindow.getPin()),"1"};
				userWindowControl.proceedActions(args);
				cmbAvaliableWindows.repaint();
				cmbCurrentWindows.repaint();
				cmbWindowsAction.repaint();
			}
		});
		btnSwitchOn.setBounds(43, 70, 110, 25);
		panel.add(btnSwitchOn);

		JButton btnSwitchOff = new JButton("Switch Off");
		btnSwitchOff.addActionListener(e -> {
			AutomaticWindow automaticWindow = (AutomaticWindow)cmbWindowsAction.getSelectedItem();
			if(automaticWindow!=null){
				String[]args = {String.valueOf(automaticWindow.getPin()),"0"};
				userWindowControl.proceedActions(args);
				cmbAvaliableWindows.repaint();
				cmbCurrentWindows.repaint();
				cmbWindowsAction.repaint();
			}
		});

		btnSwitchOff.setBounds(300, 70, 110, 25);
		panel.add(btnSwitchOff);

		JButton btnStateChange = new JButton("State Change");
		btnStateChange.addActionListener(e -> {
			AutomaticWindow automaticWindow = (AutomaticWindow)cmbWindowsAction.getSelectedItem();
			if(automaticWindow!=null){
				String[]args = {String.valueOf(automaticWindow.getPin()),"-1"};
				userWindowControl.proceedActions(args);
				cmbAvaliableWindows.repaint();
				cmbCurrentWindows.repaint();
				cmbWindowsAction.repaint();
			}
		});
		btnStateChange.setBounds(167, 70, 110, 25);
		panel.add(btnStateChange);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Feature Management", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 204)));
		panel_1.setLayout(null);
		panel_1.setBounds(12, 18, 438, 171);
		add(panel_1);

		JLabel lblAutomaticWindowPin = new JLabel("Avaliable Windows:");
		lblAutomaticWindowPin.setBounds(6, 35, 150, 16);
		panel_1.add(lblAutomaticWindowPin);
		cmbAvaliableWindows = new JComboBox<AutomaticWindow>();
		updateAvaliableCombo();
		cmbAvaliableWindows.addActionListener(new ActionListener() {
			public synchronized void actionPerformed(ActionEvent e) {
				AutomaticWindow automaticWindow = (AutomaticWindow) cmbAvaliableWindows.getSelectedItem();
				if(!userWindowControl.getAutomaticWindows().contains(automaticWindow)){
					userWindowControl.getAutomaticWindows().add(automaticWindow);
					updateCurrentCombo();
				}
			}
		});
		cmbAvaliableWindows.setBounds(170, 30, 250, 30);
		panel_1.add(cmbAvaliableWindows);

		JLabel lblCurrentWindows = new JLabel("Current Automatic Window:");
		lblCurrentWindows.setBounds(6, 103, 180, 16);
		panel_1.add(lblCurrentWindows);

		cmbCurrentWindows = new JComboBox<AutomaticWindow>();
		updateCurrentCombo();
		cmbCurrentWindows.addActionListener(new ActionListener() {
			public synchronized void actionPerformed(ActionEvent e) {
				AutomaticWindow automaticWindow = (AutomaticWindow) cmbCurrentWindows.getSelectedItem();
				automaticWindow.deactivate();
				userWindowControl.getAutomaticWindows().remove(automaticWindow);
				updateCurrentCombo();
			}
		});
		cmbCurrentWindows.setBounds(170, 95, 250, 30);
		panel_1.add(cmbCurrentWindows);

		JLabel lblWhen = new JLabel("Clicking in a current Window combo item you remove them to the feature.");
		lblWhen.setForeground(Color.RED);
		lblWhen.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		lblWhen.setBounds(6, 135, 412, 16);
		panel_1.add(lblWhen);
	}

	private void updateAvaliableCombo() {
		ArrayList<Hardware> hardwares = Main.getHouseInstance().getAllHardwareByType(AutomaticWindow.class);
		AutomaticWindow[] automaticWindows = hardwares.toArray(new AutomaticWindow[hardwares.size()]);
		cmbAvaliableWindows.setModel(new DefaultComboBoxModel<AutomaticWindow>(automaticWindows));
	}

	private void updateCurrentCombo() {
		if (userWindowControl.getAutomaticWindows().size() > 0) {
			AutomaticWindow[] automaticWindows = userWindowControl.getAutomaticWindows().toArray(new AutomaticWindow[userWindowControl.getAutomaticWindows().size()]);
			cmbCurrentWindows.setModel(new DefaultComboBoxModel<AutomaticWindow>(automaticWindows));
			cmbWindowsAction.setModel(new DefaultComboBoxModel<AutomaticWindow>(automaticWindows));
		} else {
			cmbCurrentWindows.setModel(new DefaultComboBoxModel<AutomaticWindow>());
			cmbWindowsAction.setModel(new DefaultComboBoxModel<AutomaticWindow>());
		}
	}

}
