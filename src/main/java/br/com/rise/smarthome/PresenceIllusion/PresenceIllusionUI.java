package br.com.rise.smarthome.PresenceIllusion;

import br.com.rise.smarthome.BaseComponents.BaseUI;
import br.com.rise.smarthome.Home.Main;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

public class PresenceIllusionUI extends BaseUI {

	private JToggleButton tglStartStop;
	private PresenceIllusion presenceIllusion;

	public PresenceIllusionUI() {

		presenceIllusion = (PresenceIllusion) Main.getHouseInstance().getFeatureByType(PresenceIllusion.class);

		setLayout(null);
		setForClass(PresenceIllusion.class);
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.RAISED, null, null), "Feature Action", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 204)));
		panel.setBounds(6, 77, 438, 90);
		add(panel);
		panel.setLayout(null);

		tglStartStop = new JToggleButton("Start Feature");
		tglStartStop.setBounds(133, 35, 161, 29);
		panel.add(tglStartStop);

		tglStartStop.addActionListener((ActionEvent e) -> {

			if (tglStartStop.isSelected()) {
				tglStartStop.setText("Stop Feature");
				presenceIllusion.proceedActions(null);
			} else {
				tglStartStop.setText("Start Feature");
				presenceIllusion.proceedActions(new String[]{"0"});
			}

		});

	}
	
}
