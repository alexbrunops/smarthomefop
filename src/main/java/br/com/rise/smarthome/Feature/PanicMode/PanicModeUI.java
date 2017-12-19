package br.com.rise.smarthome.Feature.PanicMode;

import br.com.rise.smarthome.BaseComponents.BaseUI;
import br.com.rise.smarthome.Home.Main;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class PanicModeUI extends BaseUI {

	private JToggleButton tglStartStop;

	private PanicMode panicMode;

	public PanicModeUI() {

		panicMode = (PanicMode) Main.getHouseInstance().getFeatureByType(PanicMode.class);

		setLayout(null);
		setForClass(PanicMode.class);
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.RAISED, null, null), "Feature Action", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 204)));
		panel.setBounds(6, 77, 438, 90);
		add(panel);
		panel.setLayout(null);

		tglStartStop = new JToggleButton("Start Feature");
		tglStartStop.setBounds(133, 35, 161, 29);
		panel.add(tglStartStop);

		tglStartStop.addActionListener(e -> {
			if (tglStartStop.isSelected()) {
				tglStartStop.setText("Stop Feature");
				String commandArray[] = {"1"};
				panicMode.proceedActions(commandArray);
				//panicMode.proceedActions(null);
			} else {
				tglStartStop.setText("Start Feature");
				String commandArray[] = {"0"};
				panicMode.proceedActions(commandArray);

			}
		});

	}

}
