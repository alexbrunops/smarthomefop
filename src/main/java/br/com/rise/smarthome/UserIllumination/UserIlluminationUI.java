package br.com.rise.smarthome.UserIllumination;

import br.com.rise.smarthome.BaseComponents.BaseUI;
import br.com.rise.smarthome.Devices.Hardware;
import br.com.rise.smarthome.Devices.Led;
import br.com.rise.smarthome.Home.Main;
import org.apache.commons.collections.CollectionUtils;

import javax.swing.*;
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
