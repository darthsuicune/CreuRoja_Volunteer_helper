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
}
