package br.com.rise.smarthome.Feature.AutomatedIllumination;

import java.util.ArrayList;

import br.com.rise.smarthome.Feature.AdaptableFeature;
import br.com.rise.smarthome.Devices.Led;
import br.com.rise.smarthome.Devices.LightSensor;
import br.com.rise.smarthome.Feature.UserIllumination.UserIllumination;


public class AutomatedIlluminationByLuminosity extends UserIllumination implements AdaptableFeature {

    private ArrayList<Led> ledsToAutomate;
    private LightSensor lightSensor;
    private static AutomatedIlluminationByLuminosity automatedIlluminationByLuminosity = null;

    private AutomatedIlluminationByLuminosity() {}

    public static AutomatedIlluminationByLuminosity getInstance() {
        if(automatedIlluminationByLuminosity == null){
            automatedIlluminationByLuminosity = new AutomatedIlluminationByLuminosity();
            automatedIlluminationByLuminosity.setName("Automated Illumination By Luminosity");
            automatedIlluminationByLuminosity.setLedsToAutomate(new ArrayList<Led>());
            automatedIlluminationByLuminosity.setActive(false);
        }
        return automatedIlluminationByLuminosity;
    }

    public static void destroy() { automatedIlluminationByLuminosity = null; }

    @Override
    public void proceedActions(){
        if(isActive()){
            if(ledsToAutomate != null && lightSensor != null){
                for(Led led : ledsToAutomate) {
                    lightSensor.act(led);
                }
            }
        }
    }

    public ArrayList<Led> getLedsToAutomate() { return ledsToAutomate; }

    public void setLedsToAutomate(ArrayList<Led> ledsToAutomate) { this.ledsToAutomate = ledsToAutomate; }

    public LightSensor getLightSensor() { return lightSensor; }

    public void setLightSensor(LightSensor lightSensor) { this.lightSensor = lightSensor; }

}
