package br.com.rise.smarthome.Home;

import br.com.rise.smarthome.BaseComponents.BaseFeature;
import br.com.rise.smarthome.Feature.AdaptableFeature;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class AutomatedFeaturesRunnable implements Runnable {

	private ArrayList<BaseFeature> features;

	public AutomatedFeaturesRunnable(ArrayList<BaseFeature> features) {
		this.features = features;
	}

	@Override public void run() {
		while(true) {
			synchronized (features) {
				try{
					for(BaseFeature f : features) {
						if (f instanceof AdaptableFeature) {
							AdaptableFeature autonomicFeature = (AdaptableFeature) f;
							autonomicFeature.proceedActions();
						}
					}
				}catch (ConcurrentModificationException e) {
					System.out.println(e.getStackTrace());
				}
			}
		}
	}

	public ArrayList<BaseFeature> getFeatures() {
		return features;
	}

	public void setFeatures(ArrayList<BaseFeature> features) {
		this.features = features;
	}

}
