package br.com.rise.smarthome.Feature.AutomatedWindowControl;

import br.com.rise.smarthome.BaseComponents.BaseUI;
import br.com.rise.smarthome.Devices.AutomaticWindow;
import br.com.rise.smarthome.Devices.Hardware;
import br.com.rise.smarthome.Devices.TemperatureSensor;
import br.com.rise.smarthome.Feature.AdaptableFeature;
import br.com.rise.smarthome.Feature.UserAirConditionerControl.UserAirConditionerControl;
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

public class AutomatedWindowControlUI extends BaseUI {

	private JComboBox<AutomaticWindow> cmbAvaliableAutomaticWindows;
	private JComboBox<AutomaticWindow> cmbCurrentAutomaticWindows;
	private AutomatedWindowControl automatedWindowControl;
	private JComboBox<TemperatureSensor> cmbSensor;
	private JToggleButton tglActivateFeature;

	public AutomatedWindowControlUI() {
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				updateAvaliableCombo();
				updateAutomaticWindowsToAutomateCombo();
				updateTemperatureSensorCombo();
			}
		});
		automatedWindowControl = (AutomatedWindowControl) Main.getHouseInstance().getFeatureByType(AutomatedWindowControl.class);
		setForClass(AutomatedWindowControl.class);
		setLayout(null);
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.RAISED, null, null), "Feature Action", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 204)));
		panel.setBounds(12, 260, 438, 120);
		add(panel);
		panel.setLayout(null);

		tglActivateFeature = new JToggleButton("Start Feature");
		tglActivateFeature.setBounds(125, 56, 161, 29);
		tglActivateFeature.addActionListener(e -> {
			if(tglActivateFeature.isSelected()){
				tglActivateFeature.setText("Stop Feature");
				automatedWindowControl.setActive(true);
			}else{
				tglActivateFeature.setText("Start Feature");
				automatedWindowControl.setActive(false);
			}
		});
		panel.add(tglActivateFeature);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Feature Management", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 204)));
		panel_1.setLayout(null);
		panel_1.setBounds(12, 22, 438, 220);
		add(panel_1);

		JLabel lblAutomaticWindowPin = new JLabel("Avaliable Automatic Windows:");
		lblAutomaticWindowPin.setBounds(6, 90, 220, 16);
		panel_1.add(lblAutomaticWindowPin);
		cmbAvaliableAutomaticWindows = new JComboBox<AutomaticWindow>();
		updateAvaliableCombo();
		cmbAvaliableAutomaticWindows.addActionListener(new ActionListener() {

			public synchronized void actionPerformed(ActionEvent e) {
				AutomaticWindow automaticWindow = (AutomaticWindow) cmbAvaliableAutomaticWindows.getSelectedItem();
				if(!automatedWindowControl.getAutomaticWindowsToAutomate().contains(automaticWindow)){
					automatedWindowControl.getAutomaticWindowsToAutomate().add(automaticWindow);
					updateAutomaticWindowsToAutomateCombo();
				}
			}
		});
		cmbAvaliableAutomaticWindows.setBounds(210, 85, 210, 30);
		panel_1.add(cmbAvaliableAutomaticWindows);

		JLabel lblCurrentAutomaticWindows = new JLabel("Automatic Windows to Automate:");
		lblCurrentAutomaticWindows.setBounds(6, 143, 220, 16);
		panel_1.add(lblCurrentAutomaticWindows);

		cmbCurrentAutomaticWindows = new JComboBox<AutomaticWindow>();
		updateAutomaticWindowsToAutomateCombo();
		cmbCurrentAutomaticWindows.addActionListener(new ActionListener() {

			public synchronized void actionPerformed(ActionEvent e) {
				AutomaticWindow automaticWindow = (AutomaticWindow) cmbCurrentAutomaticWindows.getSelectedItem();
				automatedWindowControl.getAutomaticWindowsToAutomate().remove(automaticWindow);
				updateAutomaticWindowsToAutomateCombo();
			}
		});
		cmbCurrentAutomaticWindows.setBounds(210, 135, 210, 30);
		panel_1.add(cmbCurrentAutomaticWindows);

		JLabel lblWhen = new JLabel("Clicking in a current Automatic Window combo item you remove them to the feature.");
		lblWhen.setForeground(Color.RED);
		lblWhen.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		lblWhen.setBounds(6, 180, 412, 16);
		panel_1.add(lblWhen);

		JLabel lblAvaliableSensors = new JLabel("Avaliable Sensors:");
		lblAvaliableSensors.setBounds(6, 40, 114, 16);
		panel_1.add(lblAvaliableSensors);

		cmbSensor = new JComboBox<TemperatureSensor>();
		cmbSensor.addActionListener(e -> {
			TemperatureSensor temperatureSensor = (TemperatureSensor) cmbSensor.getSelectedItem();
			automatedWindowControl.setTemperatureSensor(temperatureSensor);
		});
		cmbSensor.setBounds(125, 36, 293, 27);
		panel_1.add(cmbSensor);
	}

	private void updateAvaliableCombo() {
		ArrayList<AutomaticWindow> automaticWindows = automatedWindowControl.getAutomaticWindowsToAutomate();
		AutomaticWindow[] automaticWindowArray = automaticWindows.toArray(new AutomaticWindow[automaticWindows.size()]);
		cmbAvaliableAutomaticWindows.setModel(new DefaultComboBoxModel<AutomaticWindow>(automaticWindowArray));
	}

	private void updateTemperatureSensorCombo() {
		ArrayList<Hardware> hardwares = Main.getHouseInstance().getAllHardwareByType(TemperatureSensor.class);
		TemperatureSensor[] temperatureSensors = hardwares.toArray(new TemperatureSensor[hardwares.size()]);
		cmbSensor.setModel(new DefaultComboBoxModel<TemperatureSensor>(temperatureSensors));
	}

	private void updateAutomaticWindowsToAutomateCombo() {
		ArrayList<AutomaticWindow> automaticWindows = automatedWindowControl.getAutomaticWindowsToAutomate();
		AutomaticWindow[] automaticWindowArray = automaticWindows.toArray(new AutomaticWindow[automaticWindows.size()]);
		cmbCurrentAutomaticWindows.setModel(new DefaultComboBoxModel<AutomaticWindow>(automaticWindowArray));
	}

}
