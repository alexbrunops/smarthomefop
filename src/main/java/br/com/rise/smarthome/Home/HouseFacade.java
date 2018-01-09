package br.com.rise.smarthome.Home;

import br.com.rise.smarthome.BaseComponents.BaseFeature;
import br.com.rise.smarthome.Devices.Alarm;
import br.com.rise.smarthome.Devices.Hardware;
import br.com.rise.smarthome.Devices.Led;
import br.com.rise.smarthome.Feature.Alarm.AlarmAgainstRobbery;
import br.com.rise.smarthome.Feature.AutomatedIlluminationByLuminosity.AutomatedIlluminationByLuminosity;
import br.com.rise.smarthome.Feature.AutomatedIlluminationByPresence.AutomatedIlluminationByPresence;
import br.com.rise.smarthome.Feature.PanicMode.PanicMode;
import br.com.rise.smarthome.Feature.PresenceIllusion.PresenceIllusion;
import br.com.rise.smarthome.Feature.UserIllumination.UserIllumination;
import org.apache.commons.collections.CollectionUtils;

import javax.swing.*;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

public class HouseFacade {

	private ArrayList<BaseFeature> availableFeatures;
	private ArrayList<BaseFeature> features;
	private ArrayList<Hardware> hardwares;
	private AutomatedFeaturesRunnable automatedFeaturesRunnable;

	public HouseFacade() {

		features = new ArrayList<BaseFeature>();
		hardwares = new ArrayList<Hardware>();
		loadMandatoryFeatures();
		loadAvailableFeatures();
		loadOptionalFeatures();
		automatedFeaturesRunnable = new AutomatedFeaturesRunnable(features);
		(new Thread(automatedFeaturesRunnable)).start();

	}

	private void loadMandatoryFeatures() {
		UserIllumination userIlumination = UserIllumination.getInstance(new ArrayList<Led>());
		addFeature(userIlumination);
		PresenceIllusion presenceIlusion = PresenceIllusion.getInstance(userIlumination);
		addFeature(presenceIlusion);
		PanicMode panicMode = PanicMode.getInstance(userIlumination);
		addFeature(panicMode);
	}

	private void loadOptionalFeatures() {
		AlarmAgainstRobbery alarmAgainstRobbery = AlarmAgainstRobbery.getInstance(new ArrayList<Alarm>());
		addFeature(alarmAgainstRobbery);

//		LockDoors lockDoors = LockDoors.getInstance(new ArrayList<AutomaticDoor>());
//		addFeature(lockDoors);
//		UserAirConditionerControl userAirConditionerControl = UserAirConditionerControl.getInstance(new ArrayList<AirConditioner>());
//		addFeature(userAirConditionerControl);
//		UserWindowControl userWindowControl = UserWindowControl.getInstance(new ArrayList<AutomaticWindow>());
//		addFeature(userWindowControl);
	}

	public void addAvailableFeature(BaseFeature featureBase){
		availableFeatures.add(featureBase);
	}

	private void loadAvailableFeatures() {
		availableFeatures = new ArrayList<BaseFeature>();

		availableFeatures.add(AutomatedIlluminationByPresence.getInstance());
		availableFeatures.add(AutomatedIlluminationByLuminosity.getInstance());
	}
	
	public void addFeature(BaseFeature feature) {
		if (feature != null) {
			if (!features.contains(feature) && evaluationForFeatureHierarchy(feature)) {
				features.add(feature);
			}
		}
	}

	public void removeFeature(BaseFeature feature) {
		if (evaluateForFeatureDependency(feature)) {
			resolveRemotionFeatureHierarchy(feature);
		} else {
			JOptionPane.showMessageDialog(null, "The selected feature cannot be removed. Some other features require them");
		}
	}

	private void resolveRemotionFeatureHierarchy(BaseFeature feature) {
		Class<? extends BaseFeature> featureClass = feature.getClass();

//		if(featureClass.getSuperclass().equals(BaseFeature.class)) {
//			return;
//		}

		// Alternative Features have abstract superclass, so always can be deleted
		if(Modifier.isAbstract(featureClass.getSuperclass().getModifiers())){
			features.remove(feature);
			addAvailableFeature(feature);
		}

		// search for a feature brother
		for (BaseFeature featureBase : features) {
			// A feature brother will be different and has the same super class of the feature to be removed
			if (!featureBase.getClass().equals(featureClass) && featureBase.getClass().getSuperclass().equals(featureClass.getSuperclass())) {
				// If the feature to remove has a brother, we need to exchange all features that require them to your brother
				exchangeRequiredFeature(feature, featureBase);
				features.remove(feature);
				return;
			}
		}

		recreateFatherFeature(feature);
	}

	private void recreateFatherFeature(BaseFeature feature) {
		if (feature instanceof UserIllumination) {
			UserIllumination userIllumination = UserIllumination.getInstance(((UserIllumination)feature).getLeds());
			exchangeRequiredFeature(feature, userIllumination);
			features.remove(feature);
			addFeature(userIllumination);
		}
	}

	private boolean evaluationForFeatureHierarchy(BaseFeature newFeature) {
		for (BaseFeature f : features) {
			
			// If exist any father feature of the new feature added
			if(newFeature.getClass().getSuperclass().equals(f.getClass())){
				keepFeatureState(f, newFeature);
				exchangeRequiredFeature(f, newFeature);
				features.remove(f);
				
				return true;
			}
			
			//If the new feature is brother of a feature previously added. 
			if(f.getClass().getSuperclass().equals(newFeature.getClass().getSuperclass())){
				exchangeBrotherFeaturesData(f, newFeature);
				return true;
			}
			
			// If the new feature is super class of an already added feature so, do nothing
			if(f.getClass().getSuperclass().equals(newFeature.getClass())){
				return false;
			}
			
		}
		
		return true;
	}

	private boolean evaluateForFeatureDependency(BaseFeature feature) {
		// if the feature is child of another feature this always can be deleted
		if (!feature.getClass().getSuperclass().equals(BaseFeature.class)) {
			return true;
		}

		for (BaseFeature featureBase : features) {
			if (CollectionUtils.isNotEmpty(featureBase.getRequiredFeatures())) {
				for (BaseFeature requiredFeature : featureBase.getRequiredFeatures()) {
					if (feature == requiredFeature) {
						return false;
					}
				}
			}
		}

		return true;
	}

	private void exchangeBrotherFeaturesData(BaseFeature featureBase,BaseFeature newFeature) { }

	private void keepFeatureState(BaseFeature oldFeature, BaseFeature newFeature) { }

	private  void exchangeRequiredFeature(BaseFeature oldFeature, BaseFeature newFeature) {
		for (BaseFeature f : features) {
			if (f.getRequiredFeatures() != null && !f.getRequiredFeatures().isEmpty()) {
				for (int i = 0; i < f.getRequiredFeatures().size(); i++) {
					if (f.getRequiredFeatures().get(i) == oldFeature) {
						f.getRequiredFeatures().set(i, newFeature);
					}
				}
			}
		}
	}

	private Hardware findHardware(int pin, boolean isAnalog) {
		for (Hardware h : hardwares) {
			if (h.getPin() == pin && h.isAnalog() == isAnalog) {
				return h;
			}
		}

		return null;
	}

	public void addHardware(Hardware newHardware) {
		if (hardwares.contains(newHardware)){
			JOptionPane.showMessageDialog(null, "Already exists another hardware installed in this pin.\n "
					+ "The action cannot be completed","RiSE SmartHome - INFO",JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		hardwares.add(newHardware);
		JOptionPane.showMessageDialog(null, "Hardware successfully added!",
				"RiSE SmartHome - INFO", JOptionPane.INFORMATION_MESSAGE);
	}

	public void removeHardware(int pin, boolean isAnalog) {
		Hardware anyHardware = findHardware(pin, isAnalog);

		if (hardwares.contains(anyHardware)) {
			removeHardwareFromFeatures(anyHardware);
			hardwares.remove(anyHardware);
			JOptionPane.showMessageDialog(null, "Hardware removed successfully!",
					"RiSE SmartHome - INFO", JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(null, "No have any hardware installed in this pin",
					"RiSE SmartHome - INFO", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	private void removeHardwareFromFeatures(Hardware h) {
		for (BaseFeature f : features) {
			if (h instanceof Led && f instanceof UserIllumination) {
				((UserIllumination) f).getLeds().remove(h);
			}
		}
	}

	public BaseFeature getFeatureByType(Class<? extends BaseFeature> clazz) {
		for (BaseFeature f : features) {
			if (clazz.isInstance(f)) {
				return f;
			}
		}

		return null;
	}

	public ArrayList<Hardware> getAllHardwareByType(Class<? extends Hardware> clazz) {
		ArrayList<Hardware> list = new ArrayList<Hardware>();

		for (Hardware h: hardwares) {
			if (clazz.isInstance(h)) {
				list.add(h);
			}
		}

		return list;

	}

	public ArrayList<BaseFeature> getFeatures() {
		return features;
	}

	public void setFeatures(ArrayList<BaseFeature> features) {
		this.features = features;
	}

	public ArrayList<Hardware> getHardwares() {
		return hardwares;
	}

	public void setHardwares(ArrayList<Hardware> hardwares) {
		this.hardwares = hardwares;
	}

	// TODO: Verificar se é válido.
	public ArrayList<BaseFeature> getAvailableFeatures() {
		return (ArrayList<BaseFeature>) CollectionUtils.subtract(availableFeatures, features);
	}
}
