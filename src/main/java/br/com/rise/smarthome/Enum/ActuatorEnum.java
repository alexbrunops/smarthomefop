package br.com.rise.smarthome.Enum;

public enum ActuatorEnum {

	AIR_CONDITIONER("Air Conditioner"),
	ALARM("Alarm"),
	AUTOMATIC_WINDOW("Automatic Window"),
	AUTOMATIC_DOOR("Automatic Door"),
	LED("Led");

	private String literalName;

	ActuatorEnum (String literalName){
		this.literalName = literalName;
	}

	public String toString(){
		return literalName;
	}

	public void setLiteralName(String literalName) {
		this.literalName = literalName;
	}

}
