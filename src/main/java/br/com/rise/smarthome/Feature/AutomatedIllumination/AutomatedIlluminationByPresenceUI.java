package br.com.rise.smarthome.Feature.AutomatedIllumination;

import br.com.rise.smarthome.BaseComponents.BaseUI;

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
import br.com.rise.smarthome.Devices.Hardware;
import br.com.rise.smarthome.Devices.Led;
import br.com.rise.smarthome.Devices.PresenceSensor;


public class AutomatedIlluminationByPresenceUI extends BaseUI {

    private static final long serialVersionUID = 4435596811596503762L;
    private JComboBox<Led> cmbAvailableLeds;
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
        tglActivateFeature.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(tglActivateFeature.isSelected()){
                    tglActivateFeature.setText("Stop Feature");
                    automatedIlluminationByPresence.setActive(true);
                }else{
                    tglActivateFeature.setText("Start Feature");
                    automatedIlluminationByPresence.setActive(false);
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
        cmbAvailableLeds = new JComboBox<Led>();
        updateAvaliableCombo();
        cmbAvailableLeds.addActionListener(new ActionListener() {
            public synchronized void actionPerformed(ActionEvent e) {
                Led led = (Led) cmbAvailableLeds.getSelectedItem();
                if(!automatedIlluminationByPresence.getLedsToAutomate().contains(led)){
                    automatedIlluminationByPresence.getLedsToAutomate().add(led);
                    updateLedsToAutomateCombo();
                }
            }
        });
        cmbAvailableLeds.setBounds(125, 85, 293, 30);
        panel_1.add(cmbAvailableLeds);

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
        cmbSensor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PresenceSensor presenceSensor = (PresenceSensor) cmbSensor.getSelectedItem();
                automatedIlluminationByPresence.setPresenceSensor(presenceSensor);
            }
        });
        cmbSensor.setBounds(125, 36, 293, 27);
        panel_1.add(cmbSensor);
    }

    private void updateAvaliableCombo() {
        ArrayList<Led> leds = automatedIlluminationByPresence.getLeds();
        Led[] ledArray= new Led[leds.size()];
        int i=0;
        for (Led led : leds) {
            ledArray[i] = (Led) led;
            i++;
        }
        cmbAvailableLeds.setModel(new DefaultComboBoxModel<Led>(ledArray));
    }

    private void updateLighSensorCombo() {
        ArrayList<Hardware> hardwares = Main.getHouseInstance().getAllHardwareByType(PresenceSensor.class);
        PresenceSensor[] presenceSensors= new PresenceSensor[hardwares.size()];
        int i=0;
        for (Hardware hardware : hardwares) {
            presenceSensors[i] = (PresenceSensor) hardware;
            i++;
        }
        cmbSensor.setModel(new DefaultComboBoxModel<PresenceSensor>(presenceSensors));
    }

    private void updateLedsToAutomateCombo() {
        ArrayList<Led> leds = automatedIlluminationByPresence.getLedsToAutomate();
        Led[] ledArray= new Led[leds.size()];
        int i=0;
        for (Led led : leds) {
            ledArray[i] = (Led) led;
            i++;
        }
        cmbCurrentLeds.setModel(new DefaultComboBoxModel<Led>(ledArray));
    }
}
