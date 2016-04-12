package net.creuroja.android.volunteerhelper.domain.db;

import net.creuroja.android.volunteerhelper.domain.locations.LocationType;

import ollie.TypeAdapter;

public class LocationTypeTypeAdapter extends TypeAdapter<LocationType, String> {
	@Override public String serialize(LocationType value) {
		return value.value;
	}

	@Override public LocationType deserialize(String value) {
		return LocationType.fromValue(value);
	}
}