package br.com.rise.smarthome.Home;

import br.com.rise.smarthome.Devices.Led;
import br.com.rise.smarthome.Devices.LightSensor;
import br.com.rise.smarthome.Enum.ActuatorEnum;
import br.com.rise.smarthome.Enum.SensorEnum;
import org.apache.commons.lang.StringUtils;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HardwareControlTab extends JPanel {

	private JTextField txtSensorPin;
	private JTextField txtActuatorPin;
	private JTextField txtRemovalPin;
	private JCheckBox chkDigitalRemoval;
	private JComboBox<SensorEnum> cmbSensors;
	private JComboBox<ActuatorEnum> cmbActuators;
	private JCheckBox chkDigitalActuator;
	private JCheckBox chkAnalogSensor;

	public HardwareControlTab() {
		setLayout(null);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Sensors", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(12, 16, 438, 100);
		add(panel);
		panel.setLayout(null);

		JLabel lblFeatureType = new JLabel("Type:");
		lblFeatureType.setBounds(6, 29, 34, 16);
		panel.add(lblFeatureType);

		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(e -> {

			if (StringUtils.isEmpty(txtSensorPin.getText())) {
				JOptionPane.showMessageDialog(null, "Select the pin where the actuator is going to be added.");
				return;
			}

			SensorEnum sensorEnum = (SensorEnum) cmbSensors.getSelectedItem();
			switch (sensorEnum) {
			case LIGHT_SENSOR:
				LightSensor lightSensor = new LightSensor(Integer.parseInt(txtSensorPin.getText()), chkAnalogSensor.isSelected());
				Main.getHouseInstance().addHardware(lightSensor);
				break;
//				case PRESENCE_SENSOR:
//					PresenceSensor presenceSensor = new PresenceSensor(Integer.parseInt(txtSensorPin.getText()), chkAnalogSensor.isSelected());
//					Main.getHouseInstance().addHardware(presenceSensor);
//					break;
//				case TEMPERATURE_SENSOR:
//					TemperatureSensor temperatureSensor = new TemperatureSensor(Integer.parseInt(txtSensorPin.getText()), chkAnalogSensor.isSelected());
//					Main.getHouseInstance().addHardware(temperatureSensor);
//					break;
			default:
				break;
			}
			cmbSensors.setSelectedIndex(0);
			txtSensorPin.setText("");
			chkAnalogSensor.setSelected(true);
		});
		btnAdd.setBounds(302, 59, 117, 30);
		panel.add(btnAdd);

		cmbSensors = new JComboBox<SensorEnum>(SensorEnum.values());
		cmbSensors.setBounds(46, 22, 373, 30);
		panel.add(cmbSensors);

		JLabel lblPin = new JLabel("Pin:");
		lblPin.setBounds(6, 59, 28, 16);
		panel.add(lblPin);

		txtSensorPin = new JTextField();
		txtSensorPin.setBounds(46, 53, 44, 28);
		panel.add(txtSensorPin);
		txtSensorPin.setColumns(10);

		chkAnalogSensor = new JCheckBox("Analog");
		chkAnalogSensor.setSelected(true);
		chkAnalogSensor.setBounds(102, 55, 85, 23);
		panel.add(chkAnalogSensor);

		JPanel panel_2 = new JPanel();
		panel_2.setLayout(null);
		panel_2.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Actuators", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_2.setBounds(12, 155, 438, 127);
		add(panel_2);

		JLabel label = new JLabel("Type:");
		label.setBounds(6, 29, 34, 16);
		panel_2.add(label);

		chkDigitalActuator = new JCheckBox("Digital");
		chkDigitalActuator.setSelected(true);
		chkDigitalActuator.setBounds(102, 55, 85, 23);
		panel_2.add(chkDigitalActuator);

		JButton button = new JButton("Add");
		button.addActionListener(e -> {
			if (StringUtils.isEmpty(txtActuatorPin.getText())) {
				JOptionPane.showMessageDialog(null, "Select the pin where the actuator is going to be added.");
				return;
			}

			ActuatorEnum actuatorEnum = (ActuatorEnum) cmbActuators.getSelectedItem();

			switch (actuatorEnum) {
			case LED:
				Led led = new Led(Integer.parseInt(txtActuatorPin.getText()), !chkDigitalActuator.isSelected());
				Main.getHouseInstance().addHardware(led);
				break;

			default:
				JOptionPane.showMessageDialog(null, "Select hardware to be added.");
				break;
			}

			cmbActuators.setSelectedIndex(0);
			txtActuatorPin.setText("");
			chkDigitalActuator.setSelected(true);
		});
		button.setBounds(302, 54, 117, 29);
		panel_2.add(button);

		cmbActuators = new JComboBox<ActuatorEnum>(ActuatorEnum.values());
		cmbActuators.setBounds(46, 20, 373, 30);
		panel_2.add(cmbActuators);

		JLabel label_1 = new JLabel("Pin:");
		label_1.setBounds(6, 59, 28, 16);
		panel_2.add(label_1);

		txtActuatorPin = new JTextField();
		txtActuatorPin.setColumns(10);
		txtActuatorPin.setBounds(46, 53, 44, 28);
		panel_2.add(txtActuatorPin);

		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Hardware Removal", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_1.setBounds(12, 300, 438, 80);
		add(panel_1);

		chkDigitalRemoval = new JCheckBox("Digital");
		chkDigitalRemoval.setBounds(101, 35, 85, 23);
		chkDigitalRemoval.setSelected(true);
		panel_1.add(chkDigitalRemoval);

		JButton btnRemoveHardware = new JButton("Remove Hardware");
		btnRemoveHardware.addActionListener(e -> {

			if (StringUtils.isEmpty(txtRemovalPin.getText())) {
				JOptionPane.showMessageDialog(null, "Select a pin to remove the hardware.");
				return;
			}

			Main.getHouseInstance().
					removeHardware(Integer.parseInt(txtRemovalPin.getText()), !chkDigitalRemoval.isSelected());
			txtRemovalPin.setText("");
			chkDigitalRemoval.setSelected(true);
		});
		btnRemoveHardware.setBounds(290, 35, 142, 29);
		panel_1.add(btnRemoveHardware);

		JLabel label_3 = new JLabel("Pin:");
		label_3.setBounds(6, 35, 28, 16);
		panel_1.add(label_3);

		txtRemovalPin = new JTextField();
		txtRemovalPin.setColumns(10);
		txtRemovalPin.setBounds(46, 35, 44, 28);
		panel_1.add(txtRemovalPin);

	}

}
