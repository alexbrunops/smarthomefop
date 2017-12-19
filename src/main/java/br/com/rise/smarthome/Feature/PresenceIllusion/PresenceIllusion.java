package br.com.rise.smarthome.Feature.PresenceIllusion;

import br.com.rise.smarthome.BaseComponents.BaseFeature;
import br.com.rise.smarthome.BaseComponents.BaseUI;
import br.com.rise.smarthome.Devices.Led;
import br.com.rise.smarthome.Feature.MandatoryFeature;
import br.com.rise.smarthome.Feature.UserIllumination.UserIllumination;
import org.apache.commons.collections.CollectionUtils;

import java.util.ConcurrentModificationException;
import java.util.Random;

@MandatoryFeature
public class PresenceIllusion extends BaseFeature {

	private PresenceIllusionThread presenceIllusionThread;
	private static PresenceIllusion presenceIllusion = null;

	protected PresenceIllusion() {}

	public static PresenceIllusion getInstance(UserIllumination userIllumination) {
		if (presenceIllusion == null) {
			presenceIllusion = new PresenceIllusion();
			presenceIllusion.setActive(true);
			presenceIllusion.setName("Presence Illusion");
			presenceIllusion.addRequiredFeature(userIllumination);
		}
		return presenceIllusion;
	}

	public static void destroy() {
		presenceIllusion = null;
	}

	@Override public BaseUI getFeatureUI() {
		return new PresenceIllusionUI();
	}

	@Override public void proceedActions(String[] args) {
		// if args == null
		//    Proceed feature until deactivate command
		// else
		// [0] - 1 to activate; 0 to deactivate
		// [1] - time to automaticaly deactivate the feature (-1 to only deactivate when command)
		// [2] - interval time to change the state of a sorted led

		UserIllumination userIllumination = (UserIllumination) CollectionUtils.get(getRequiredFeatures(), 0);

		if (CollectionUtils.isNotEmpty(userIllumination.getLeds()) && isActive()) {
			if (args == null) {
				presenceIllusionThread = new PresenceIllusionThread(userIllumination);
				presenceIllusionThread.start();
			} else {

				if (args[0].equals("1")) {
					presenceIllusionThread = new PresenceIllusionThread(Integer.parseInt(args[1]), Integer.parseInt(args[2]), userIllumination);
				}

				if (args[0].equals("0") && presenceIllusionThread != null && presenceIllusionThread.isAlive()) {
					presenceIllusionThread.finhishAction();
				}

			}
		}
	}

	private class PresenceIllusionThread extends Thread{
		private UserIllumination userIllumination;
		private int intervalTime;
		private int timeToStop;
		private boolean shouldInterrupt = false;

		public PresenceIllusionThread(int timeToStop, int intervalTime, UserIllumination userIllumination) {
			this.timeToStop = timeToStop;
			this.intervalTime = intervalTime;
			this.userIllumination = userIllumination;
		}

		public PresenceIllusionThread(UserIllumination userIllumination){
			this(-1, 60000, userIllumination);
		}

		@Override
		public synchronized void run(){
			while(!shouldInterrupt && timeToStop != 0) {
				try{
					if(CollectionUtils.isNotEmpty(userIllumination.getLeds())) {
						synchronized (userIllumination) {
							String instructionsArray[] = {String.valueOf(
									userIllumination.getLeds().get(new Random().nextInt(userIllumination.getLeds().size())).getPin()), "-1"};
							userIllumination.proceedActions(instructionsArray);
						}
					}

					try {
						sleep(intervalTime);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					timeToStop--;
				} catch (ConcurrentModificationException ex){
					ex.printStackTrace();
				}
			}
			for (Led led : userIllumination.getLeds()) {
				String[] args = {String.valueOf(led.getPin()), "0"};
				userIllumination.proceedActions(args);
			}
		}

		protected void finhishAction(){
			shouldInterrupt = true;
		}

	}
}
