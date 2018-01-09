package br.com.rise.smarthome.Feature.AutomatedIlluminationByPresence;

import br.com.rise.smarthome.BaseComponents.BaseUI;
import br.com.rise.smarthome.Devices.Led;
import br.com.rise.smarthome.Devices.PresenceSensor;
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

public class AutomatedIlluminationByPresenceUI extends BaseUI {

	private JComboBox<Led> cmbAvaliableLeds;
	private JComboBox<Led> cmbCurrentLeds;
	private AutomatedIlluminationByPresence automatedIlluminationByPresence;
	private JComboBox<PresenceSensor> cmbSensor;
	private JToggleButton tglActivateFeature;

	public AutomatedIlluminationByPresenceUI() {
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				updateAvaliableCombo();
				updateLedsToAutomateCombo();
				updateLighSensorCombo();
			}
		});
		automatedIlluminationByPresence = (AutomatedIlluminationByPresence) Main.getHouseInstance().getFeatureByType(AutomatedIlluminationByPresence.class);
		setForClass(AutomatedIlluminationByPresence.class);
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
				automatedIlluminationByPresence.setActive(true);
			}else{
				tglActivateFeature.setText("Start Feature");
				automatedIlluminationByPresence.setActive(false);
			}
		});
		panel.add(tglActivateFeature);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Feature Management", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 204)));
		panel_1.setLayout(null);
		panel_1.setBounds(12, 22, 438, 220);
		add(panel_1);

		JLabel lblLedPin = new JLabel("Avaliable Leds:");
		lblLedPin.setBounds(6, 90, 101, 16);
		panel_1.add(lblLedPin);
		cmbAvaliableLeds = new JComboBox<Led>();
		updateAvaliableCombo();
		cmbAvaliableLeds.addActionListener(new ActionListener() {
			public synchronized void actionPerformed(ActionEvent e) {
				Led led = (Led) cmbAvaliableLeds.getSelectedItem();
				if(!automatedIlluminationByPresence.getLedsToAutomate().contains(led)){
					automatedIlluminationByPresence.getLedsToAutomate().add(led);
					updateLedsToAutomateCombo();
				}
			}
		});
		cmbAvaliableLeds.setBounds(125, 85, 293, 30);
		panel_1.add(cmbAvaliableLeds);

		JLabel lblCurrentLeds = new JLabel("Leds to Automate:");
		lblCurrentLeds.setBounds(6, 143, 138, 16);
		panel_1.add(lblCurrentLeds);

		cmbCurrentLeds = new JComboBox<Led>();
		updateLedsToAutomateCombo();
		cmbCurrentLeds.addActionListener(new ActionListener() {
			public synchronized void actionPerformed(ActionEvent e) {
				Led led = (Led) cmbCurrentLeds.getSelectedItem();
				automatedIlluminationByPresence.getLedsToAutomate().remove(led);
				updateLedsToAutomateCombo();
			}
		});
		cmbCurrentLeds.setBounds(125, 135, 293, 30);
		panel_1.add(cmbCurrentLeds);

		JLabel lblWhen = new JLabel("Clicking in a current led combo item you remove them to the feature.");
		lblWhen.setForeground(Color.RED);
		lblWhen.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		lblWhen.setBounds(6, 180, 412, 16);
		panel_1.add(lblWhen);

		JLabel lblAvaliableSensors = new JLabel("Avaliable Sensors:");
		lblAvaliableSensors.setBounds(6, 40, 114, 16);
		panel_1.add(lblAvaliableSensors);

		cmbSensor = new JComboBox<PresenceSensor>();
		cmbSensor.addActionListener(e -> {
			PresenceSensor presenceSensor = (PresenceSensor) cmbSensor.getSelectedItem();
			automatedIlluminationByPresence.setPresenceSensor(presenceSensor);
		});
		cmbSensor.setBounds(125, 36, 293, 27);
		panel_1.add(cmbSensor);
	}

	private void updateAvaliableCombo() {
		cmbAvaliableLeds.setModel(new DefaultComboBoxModel<Led>(CollectionUtils.isNotEmpty(automatedIlluminationByPresence.getLeds())
				? automatedIlluminationByPresence.getLeds().toArray(new Led[]{}) : new Led[]{}));
	}

	private void updateLighSensorCombo() {
		cmbSensor.setModel(new DefaultComboBoxModel<PresenceSensor>(Main.getHouseInstance().getAllHardwareByType(PresenceSensor.class).toArray(new PresenceSensor[]{})));
	}

	private void updateLedsToAutomateCombo() {
		cmbCurrentLeds.setModel(new DefaultComboBoxModel<Led>(CollectionUtils.isNotEmpty(automatedIlluminationByPresence.getLedsToAutomate())
				? automatedIlluminationByPresence.getLedsToAutomate().toArray(new Led[]{}) : new Led[]{}));
	}
	
}
