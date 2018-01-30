package br.com.rise.smarthome.Feature.PresenceIllusion;

import br.com.rise.smarthome.BaseComponents.BaseUI;
import br.com.rise.smarthome.Home.Main;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.DateFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PresenceIllusionUI extends BaseUI {

	private JToggleButton tglStartStop;
	private PresenceIllusion presenceIllusion;

	public PresenceIllusionUI() {

		presenceIllusion = (PresenceIllusion) Main.getHouseInstance().getFeatureByType(PresenceIllusion.class);

		setLayout(null);
		setForClass(PresenceIllusion.class);
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.RAISED, null, null), "Feature Action", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 204)));
		panel.setBounds(6, 77, 438, 130);
		add(panel);
		panel.setLayout(null);

		tglStartStop = new JToggleButton("Start Feature");
		tglStartStop.setBounds(133, 35, 161, 29);
		panel.add(tglStartStop);

		JSpinner spinnerStart = new JSpinner();
		SpinnerDateModel spinnerDateModel = new SpinnerDateModel();
		spinnerDateModel.setCalendarField(Calendar.MINUTE);
		spinnerStart.setModel(spinnerDateModel);
		JSpinner.DateEditor editor = new JSpinner.DateEditor(spinnerStart, "HH:mm:ss");
		DateFormatter formatter = (DateFormatter) editor.getTextField().getFormatter();
		formatter.setAllowsInvalid(false);
		formatter.setOverwriteMode(true);

		spinnerStart.setEditor(editor);
		spinnerStart.setBounds(100, 70, 90, 30);
		panel.add(spinnerStart);

		JSpinner spinnerStop = new JSpinner();
		SpinnerDateModel spinnerStopDataModel = new SpinnerDateModel();
		spinnerStopDataModel.setCalendarField(Calendar.MINUTE);
		spinnerStop.setModel(spinnerStopDataModel);
		JSpinner.DateEditor editorStop = new JSpinner.DateEditor(spinnerStop, "HH:mm:ss");
		DateFormatter formatterStop = (DateFormatter) editorStop.getTextField().getFormatter();
		formatterStop.setAllowsInvalid(false);
		formatterStop.setOverwriteMode(true);

		spinnerStop.setEditor(editorStop);
		spinnerStop.setBounds(200, 70, 90, 30);
		panel.add(spinnerStop);

		tglStartStop.addActionListener((ActionEvent e) -> {

			if (tglStartStop.isSelected()) {
				spinnerStart.setEnabled(false);
				spinnerStop.setEnabled(false);
				tglStartStop.setText("Stop Feature");

				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
				Date dateStart = (Date) spinnerStart.getValue();
				Date dateStop = (Date) spinnerStop.getValue();

				long diff = dateStart.getTime() - dateStop.getTime();

				presenceIllusion.proceedActions(new String[]{"1", String.valueOf(diff), sdf.format(dateStop)});
			} else {
				spinnerStart.setEnabled(true);
				spinnerStop.setEnabled(true);
				tglStartStop.setText("Start Feature");
				presenceIllusion.proceedActions(new String[]{"0"});
			}

		});

	}
	
}
