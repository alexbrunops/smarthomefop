package br.com.rise.smarthome.Home;

import br.com.rise.smarthome.BaseComponents.BaseFeature;
import br.com.rise.smarthome.Feature.MandatoryFeature;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;

public class FeatureControlTab extends JPanel {

	private JComboBox<BaseFeature> cmbAvailableFeature;
	private JComboBox<BaseFeature> cmbCurrentFeatures;
	private JTextField txtSerialKey;

	public FeatureControlTab() {
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				updateAvailableCombo();
				updateCurrentCombo();
			}
		});
		setLayout(null);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Add Feature", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(6, 6, 438, 89);
		add(panel);
		panel.setLayout(null);

		JLabel lblFeatureType = new JLabel("Feature:");
		lblFeatureType.setBounds(6, 29, 57, 16);
		panel.add(lblFeatureType);

		cmbAvailableFeature = new JComboBox<BaseFeature>();
		cmbAvailableFeature.setBounds(64, 13, 355, 50);
		updateAvailableCombo();
		panel.add(cmbAvailableFeature);

		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(e -> {
			BaseFeature newFeature = (BaseFeature) cmbAvailableFeature.getSelectedItem();

			if (newFeature != null) {
				Main.getHouseInstance().addFeature(newFeature);
				updateAvailableCombo();
				updateCurrentCombo();
				Main.updateFeaturesTabs();
			} else {
				JOptionPane.showMessageDialog(null, "Select feature to be added.");
			}
		});
		btnAdd.setBounds(302, 54, 117, 29);
		panel.add(btnAdd);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Unlock New Feature", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_1.setBounds(6, 185, 438, 89);
		add(panel_1);
		panel_1.setLayout(null);

		JLabel lblFeatureKeyCode = new JLabel("Feature Key Code:");
		lblFeatureKeyCode.setBounds(6, 30, 112, 16);
		panel_1.add(lblFeatureKeyCode);

		txtSerialKey = new JTextField();
		txtSerialKey.setBounds(130, 24, 287, 28);
		panel_1.add(txtSerialKey);

		JPanel panel_2 = new JPanel();
		panel_2.setLayout(null);
		panel_2.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Remove Feature", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_2.setBounds(6, 93, 438, 89);
		add(panel_2);

		JLabel label = new JLabel("Feature:");
		label.setBounds(6, 29, 57, 16);
		panel_2.add(label);

		cmbCurrentFeatures = new JComboBox<BaseFeature>();
		cmbCurrentFeatures.setBounds(64, 13, 355, 50);
		updateCurrentCombo();
		panel_2.add(cmbCurrentFeatures);

		JButton btnRemove = new JButton("Remove");
		btnRemove.setBounds(302, 54, 117, 29);
		btnRemove.addActionListener(e -> {
			BaseFeature featureBase = (BaseFeature) cmbCurrentFeatures.getSelectedItem();
			if (featureBase != null) {
				Main.getHouseInstance().removeFeature(featureBase);
				Main.removeFeatureTab(featureBase.getClass());
				updateAvailableCombo();
				updateCurrentCombo();
			} else {
				JOptionPane.showMessageDialog(null, "Select feature to be removed.");
			}
		});
		panel_2.add(btnRemove);
	}

	private void updateAvailableCombo() {
		ArrayList<BaseFeature> featureBaseList = Main.getHouseInstance().getAvailableFeatures();
		BaseFeature[] features= new BaseFeature[featureBaseList.size()];
		int i = 0;
		for (BaseFeature feature : featureBaseList) {
			features[i] = (BaseFeature) feature;
			i++;
		}
		cmbAvailableFeature.setModel(new DefaultComboBoxModel<BaseFeature>(features));
	}

	private void updateCurrentCombo() {
		ArrayList<BaseFeature> featureBaseList = Main.getHouseInstance().getFeatures();
		int cont=0;
		for (BaseFeature feature : featureBaseList) {
			if(!feature.getClass().isAnnotationPresent(MandatoryFeature.class)){
				cont++;
			}
		}
		BaseFeature[] features= new BaseFeature[cont];
		int i=0;
		for (BaseFeature feature : featureBaseList) {
			if(!feature.getClass().isAnnotationPresent(MandatoryFeature.class)){
				features[i] = (BaseFeature) feature;
				i++;
			}
		}
		if(i!=0)
			cmbCurrentFeatures.setModel(new DefaultComboBoxModel<BaseFeature>(features));
		else{
			features= new BaseFeature[0];
			cmbCurrentFeatures.setModel(new DefaultComboBoxModel<BaseFeature>(features));
		}
	}
	
}
