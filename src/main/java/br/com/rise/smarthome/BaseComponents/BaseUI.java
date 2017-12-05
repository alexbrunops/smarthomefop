package br.com.rise.smarthome.BaseComponents;

import javax.swing.*;

public abstract class BaseUI extends JPanel {

	private Class<? extends BaseFeature> forClass;

	public BaseUI() {
		setLayout(null);
	}

	protected void setForClass(Class<? extends BaseFeature> clazz) {
		this.forClass = clazz;
	}

	public boolean isForClass(Class<? extends BaseFeature> clazz) {
		return forClass.equals(clazz);
	}

}
