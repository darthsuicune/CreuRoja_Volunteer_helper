package net.creuroja.android.volunteerhelper.domain.locations;

public enum LocationType {
	TERRESTRE("terrestre"), MARITIMO("maritimo"), ASAMBLEA("asamblea");

	public final String value;

	LocationType(String value) {
		this.value = value;
	}

	public static LocationType fromValue(String value) {
		switch(value) {
			case "asamblea":
				return ASAMBLEA;
			case "maritimo":
				return MARITIMO;
			case "terrestre":
			default:
				return TERRESTRE;
		}
	}
}
