package br.com.rise.smarthome.BaseComponents;

import java.util.ArrayList;

public abstract class BaseFeature {

	private String name;
	private boolean isActive=true;
	private ArrayList<BaseFeature> requiredFeatures;
	private BaseUI featureUI;

	public abstract void proceedActions(String[] args);

	public void addRequiredFeature(BaseFeature feature) {
		if (requiredFeatures == null) {
			requiredFeatures = new ArrayList<BaseFeature>();
		}

		requiredFeatures.add(feature);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean active) {
		isActive = active;
	}

	public ArrayList<BaseFeature> getRequiredFeatures() {
		return requiredFeatures;
	}

	public BaseUI getFeatureUI() {
		return featureUI;
	}

	public void setFeatureUI(BaseUI featureUI) {
		this.featureUI = featureUI;
	}

	@Override public String toString() {
		return getName();
	}

}
