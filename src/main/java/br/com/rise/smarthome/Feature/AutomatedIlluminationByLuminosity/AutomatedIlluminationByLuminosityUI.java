package br.com.rise.smarthome.Feature.AutomatedIlluminationByLuminosity;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import br.com.rise.smarthome.Home.Main;
import br.com.rise.smarthome.BaseComponents.BaseUI;
import br.com.rise.smarthome.Devices.Hardware;
import br.com.rise.smarthome.Devices.Led;
import br.com.rise.smarthome.Devices.LightSensor;
import org.apache.commons.collections.CollectionUtils;

public class AutomatedIlluminationByLuminosityUI extends BaseUI {

	private JComboBox<Led> cmbAvaliableLeds;
	private JComboBox<Led> cmbCurrentLeds;
	private AutomatedIlluminationByLuminosity automatedIlluminationByLuminosity;
	private JComboBox<LightSensor> cmbSensor;
	private JToggleButton tglActivateFeature;

	public AutomatedIlluminationByLuminosityUI() {
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				updateAvailableCombo();
				updateLedsToAutomateCombo();
				updateLightSensorCombo();
			}
		});

		automatedIlluminationByLuminosity = (AutomatedIlluminationByLuminosity) Main.getHouseInstance().getFeatureByType(AutomatedIlluminationByLuminosity.class);
		setForClass(AutomatedIlluminationByLuminosity.class);
		setLayout(null);
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.RAISED, null, null), "Feature Action", TitledBorder.LEADING, TitledBorder.TOP, null,  new Color(0, 0, 204)));
		panel.setBounds(12, 260, 438, 120);
		add(panel);
		panel.setLayout(null);

		tglActivateFeature = new JToggleButton("Start Feature");
		tglActivateFeature.setBounds(125, 56, 161, 29);
		tglActivateFeature.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(tglActivateFeature.isSelected()){
					tglActivateFeature.setText("Stop Feature");
					automatedIlluminationByLuminosity.setActive(true);
				}else{
					tglActivateFeature.setText("Start Feature");
					automatedIlluminationByLuminosity.setActive(false);
				}
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
		updateAvailableCombo();
		cmbAvaliableLeds.addActionListener(new ActionListener() {
			public synchronized void actionPerformed(ActionEvent e) {
				Led led = (Led) cmbAvaliableLeds.getSelectedItem();
				if(!automatedIlluminationByLuminosity.getLedsToAutomate().contains(led)){
					automatedIlluminationByLuminosity.getLedsToAutomate().add(led);
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
				automatedIlluminationByLuminosity.getLedsToAutomate().remove(led);
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

		JLabel lblAvailableSensors = new JLabel("Avaliable Sensors:");
		lblAvailableSensors.setBounds(6, 40, 114, 16);
		panel_1.add(lblAvailableSensors);

		cmbSensor = new JComboBox<LightSensor>();
		cmbSensor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LightSensor lightSensor = (LightSensor) cmbSensor.getSelectedItem();
				automatedIlluminationByLuminosity.setLightSensor(lightSensor);
			}
		});
		cmbSensor.setBounds(125, 36, 293, 30);
		panel_1.add(cmbSensor);
	}

	private void updateAvailableCombo() {
		cmbAvaliableLeds.setModel(new DefaultComboBoxModel<Led>(CollectionUtils.isNotEmpty(automatedIlluminationByLuminosity.getLeds())
				? automatedIlluminationByLuminosity.getLeds().toArray(new Led[]{}) : new Led[]{}));
	}

	private void updateLightSensorCombo() {
		ArrayList<Hardware> hardwares = Main.getHouseInstance().getAllHardwareByType(LightSensor.class);
		LightSensor[] lightSensors= new LightSensor[hardwares.size()];
		int i=0;
		for (Hardware hardware : hardwares) {
			lightSensors[i] = (LightSensor) hardware;
			i++;
		}
		cmbSensor.setModel(new DefaultComboBoxModel<LightSensor>(lightSensors));
	}

	private void updateLedsToAutomateCombo() {
		cmbCurrentLeds.setModel(new DefaultComboBoxModel<Led>(CollectionUtils.isNotEmpty(automatedIlluminationByLuminosity.getLedsToAutomate())
				? automatedIlluminationByLuminosity.getLedsToAutomate().toArray(new Led[]{}) : new Led[]{}));
	}
}
