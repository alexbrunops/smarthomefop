package br.com.rise.smarthome.Home;

import br.com.rise.smarthome.BaseComponents.BaseFeature;

import javax.swing.*;
import java.awt.*;

public class AlternativeFeatureSelectionDialog extends JDialog {

	private BaseFeature selectedFeature;
	private BaseFeature featureBase1;
	private BaseFeature featureBase2;

	public AlternativeFeatureSelectionDialog(BaseFeature feature1, BaseFeature feature2) {
		this.featureBase1 = feature1;
		this.featureBase2 = feature2;
		setModal(true);
		setResizable(false);
		setTitle("RiSE Smart Home - Alternative Feature Selection");
		setBounds(100, 100, 400, 300);
		getContentPane().setLayout(null);

		Label label = new Label("An alternative to the feature you have added");
		label.setBounds(25, 10, 291, 30);
		getContentPane().add(label);

		JButton btnNewButton = new JButton("");
		btnNewButton.addActionListener(e -> {
			selectedFeature = featureBase1;
			setVisible(false);
		});
		btnNewButton.setBounds(95, 93, 230, 40);
		getContentPane().add(btnNewButton);
		btnNewButton.setText(feature1.getName());
		btnNewButton.setToolTipText(feature1.getName());

		JButton button = new JButton("");
		button.addActionListener(e -> {
			selectedFeature = featureBase2;
			setVisible(false);
		});
		button.setBounds(95, 193, 230, 40);
		getContentPane().add(button);
		button.setText(feature2.getName());
		button.setToolTipText(feature2.getName());

		Label label_1 = new Label("has been previously added.");
		label_1.setBounds(25, 35, 291, 20);
		getContentPane().add(label_1);

		Label label_2 = new Label("Please choose one.");
		label_2.setBounds(25, 60, 291, 20);
		getContentPane().add(label_2);

	}

	public BaseFeature getSelectedFeature() {
		return selectedFeature;
	}
	
}
