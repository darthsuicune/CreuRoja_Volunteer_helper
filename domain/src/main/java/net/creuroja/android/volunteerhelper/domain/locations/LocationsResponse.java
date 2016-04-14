package net.creuroja.android.volunteerhelper.domain.locations;

import android.content.ContentValues;

import net.creuroja.android.volunteerhelper.domain.volunteer_services.VolunteerService;

import java.util.ArrayList;
import java.util.List;

import static net.creuroja.android.volunteerhelper.domain.db.ServicesContract.Locations.*;

public class LocationsResponse {
	public int id;
	public String name;
	public String description;
	public String address;
	public String phone;
	public String latitude;
	public String longitude;
	public String locationType;
	public boolean active;
	public String updatedAt;
	public List<VolunteerService> activeServices = new ArrayList<>();

	public ContentValues asValues() {
		ContentValues values = new ContentValues(15);
		values.put(_ID, id);
		values.put(REMOTE_ID, id);
		values.put(LOCATION_NAME, name);
		values.put(DESCRIPTION, description);
		values.put(ADDRESS, address);
		values.put(PHONE, phone);
		values.put(LATITUDE, latitude);
		values.put(LONGITUDE, longitude);
		values.put(LOCATION_TYPE, locationType);
		values.put(ACTIVE, active);
		values.put(UPDATED_AT, updatedAt);
		return values;
	}
}
