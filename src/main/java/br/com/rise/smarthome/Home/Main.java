package br.com.rise.smarthome.Home;

import br.com.rise.smarthome.BaseComponents.BaseFeature;
import br.com.rise.smarthome.BaseComponents.BaseUI;
import br.com.rise.smarthome.Devices.ArduinoControl;
import br.com.rise.smarthome.UserIllumination.UserIllumination;
import br.com.rise.smarthome.UserIllumination.UserIlluminationUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Main extends JFrame {

	private static HouseFacade house;
	private static JPanel contentPane;
	private static JTabbedPane tabbedPane;

	public Main() {
		setTitle("RiSE SmartHome - Mission Control");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 470, 349);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(6, 6, 466, 325);
//		FeatureControlTab generalTab = new FeatureControlTab();
//		tabbedPane.addTab("Feature Control", null, generalTab);
//		HardwareControlTab hardwareControlTab = new HardwareControlTab();
//		tabbedPane.addTab("Hardware Control", null, hardwareControlTab);
		contentPane.add(tabbedPane);
	}

	public static void main(String args[]) {
		EventQueue.invokeLater(() -> {
			try {
				ArduinoControl.getInstance();
				house = new HouseFacade();
				Main frame = new Main();
				updateFeaturesTabs();
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public static void updateFeaturesTabs(){
		for (BaseFeature feature : house.getFeatures()) {

//			if(feature instanceof PresenceIlusion){
//				boolean alreadyExist = false;
//				for (int i = 0; i < tabbedPane.getTabCount(); i++) {
//					if(tabbedPane.getComponentAt(i) instanceof BaseUI){
//						BaseUI featureTab = (BaseUI) tabbedPane.getComponentAt(i);
//						if(featureTab.isForClass(PresenceIlusion.class)){
//							alreadyExist = true;
//							break;
//						}
//					}
//				}
//				if(!alreadyExist){
//					PresenceIlusionUI presenceIlusionUI = new PresenceIlusionUI();
//					tabbedPane.addTab("Presence Illusion", null, presenceIlusionUI);
//				}
//			}

			if(feature instanceof UserIllumination){
				boolean alreadyExist = false;
				for (int i = 0; i < tabbedPane.getTabCount(); i++) {
					if (tabbedPane.getComponentAt(i) instanceof BaseUI) {
						BaseUI featureTab = (BaseUI) tabbedPane.getComponentAt(i);

						if (featureTab.isForClass(UserIllumination.class)) {
							alreadyExist = true;
							break;
						}
					}
				}
				if (!alreadyExist) {
					UserIlluminationUI userIlluminationUI = new UserIlluminationUI();
					tabbedPane.addTab("User Illumination", null, userIlluminationUI);
				}
			}

		}
	}

	public static HouseFacade getHouseInstance() {
		return house;
	}

}
