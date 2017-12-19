package br.com.rise.smarthome.Feature.PanicMode;

import br.com.rise.smarthome.BaseComponents.BaseFeature;
import br.com.rise.smarthome.BaseComponents.BaseUI;
import br.com.rise.smarthome.Devices.Led;
import br.com.rise.smarthome.Feature.MandatoryFeature;
import br.com.rise.smarthome.Feature.UserIllumination.UserIllumination;

@MandatoryFeature
public class PanicMode extends BaseFeature {

	private static PanicMode panicMode = null;
	private PanicModeThread panicModeThread;

	protected PanicMode() {
	}

	public static PanicMode getInstance(UserIllumination userIllumination) {

		if (panicMode == null) {
			panicMode = new PanicMode();
			panicMode.setName("Panic Mode");
			panicMode.addRequiredFeature(userIllumination);
		}

		return panicMode;

	}

	public static void destroy() {
		panicMode = null;
	}

	@Override public BaseUI getFeatureUI() {
		return new PanicModeUI();
	}

	@Override public void proceedActions(String[] args) {
		// [0] - 1 to activate; 0 to deactivate

		if (args[0].equals("1")) {

			if (panicModeThread == null || !panicModeThread.isAlive()) {
				panicModeThread = new PanicModeThread((UserIllumination) getRequiredFeatures().get(0));
				panicModeThread.run();
			}

		} else {

			if (panicModeThread != null || panicModeThread.isAlive()) {
				panicModeThread.finishAction();
			}

		}

	}

	private class PanicModeThread extends Thread {

		private UserIllumination userIllumination;
		private boolean shouldInterrupt = false;

		public PanicModeThread(UserIllumination userIllumination) {
			this.userIllumination = userIllumination;
		}

		@Override public void run() {

			while (!shouldInterrupt) {
				for (Led led: userIllumination.getLeds()) {
					String instructionsArray[] = {String.valueOf(led.getPin()), "0"};
					userIllumination.proceedActions(instructionsArray);
				}

				try {
					sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				for (Led led: userIllumination.getLeds()) {
					String instructionsArray[] = {String.valueOf(led.getPin()), "1"};
					userIllumination.proceedActions(instructionsArray);
				}

				try {
					sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}

		}

		protected void finishAction() {
			shouldInterrupt = true;
		}

	}

}
