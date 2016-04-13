package net.creuroja.android.volunteerhelper.domain.locations;

import android.content.ContentValues;

import net.creuroja.android.volunteerhelper.domain.volunteer_services.VolunteerService;

import java.util.List;

import ollie.Model;
import ollie.annotation.Column;
import ollie.annotation.Table;

import static net.creuroja.android.volunteerhelper.domain.db.ServicesContract.Locations.ACTIVE;
import static net.creuroja.android.volunteerhelper.domain.db.ServicesContract.Locations.ADDRESS;
import static net.creuroja.android.volunteerhelper.domain.db.ServicesContract.Locations.DESCRIPTION;
import static net.creuroja.android.volunteerhelper.domain.db.ServicesContract.Locations.LATITUDE;
import static net.creuroja.android.volunteerhelper.domain.db.ServicesContract.Locations.LOCATION_NAME;
import static net.creuroja.android.volunteerhelper.domain.db.ServicesContract.Locations.LOCATION_TYPE;
import static net.creuroja.android.volunteerhelper.domain.db.ServicesContract.Locations.LONGITUDE;
import static net.creuroja.android.volunteerhelper.domain.db.ServicesContract.Locations.PHONE;
import static net.creuroja.android.volunteerhelper.domain.db.ServicesContract.Locations.REMOTE_ID;
import static net.creuroja.android.volunteerhelper.domain.db.ServicesContract.Locations.TABLE_NAME;
import static net.creuroja.android.volunteerhelper.domain.db.ServicesContract.Locations.UPDATED_AT;

@Table(TABLE_NAME)
public class Location extends Model {
	@Column(REMOTE_ID) public Integer remoteId;
	@Column(LOCATION_NAME) public String name;
	@Column(DESCRIPTION) public String description;
	@Column(ADDRESS) public String address;
	@Column(PHONE) public String phone;
	@Column(LATITUDE) public Double latitude = 0.0;
	@Column(LONGITUDE) public Double longitude = 0.0;
	@Column(LOCATION_TYPE) public LocationType type;
	@Column(ACTIVE) public Boolean active = true;
	@Column(UPDATED_AT) public String updatedAt;

	public List<VolunteerService> services;

	public Location() {}

	public Location(LocationsResponse original) {
		this.remoteId = original.id;
		this.name = original.name;
		this.description = original.description;
		this.address = original.address;
		this.phone = original.phone;
		this.latitude = Double.parseDouble(original.latitude);
		this.longitude = Double.parseDouble(original.longitude);
		this.type = LocationType.fromValue(original.locationType);
		this.active = original.active;
		this.updatedAt = original.updatedAt;
		this.services = original.activeServices;
	}

	public ContentValues asValues() {
		ContentValues values = new ContentValues(15);
		values.put(_ID, id);
		values.put(REMOTE_ID, remoteId);
		values.put(LOCATION_NAME, name);
		values.put(DESCRIPTION, description);
		values.put(ADDRESS, address);
		values.put(PHONE, phone);
		values.put(LATITUDE, latitude);
		values.put(LONGITUDE, longitude);
		values.put(LOCATION_TYPE, type.value);
		values.put(ACTIVE, active);
		values.put(UPDATED_AT, updatedAt);
		return values;
	}
}
