package net.creuroja.android.volunteerhelper.domain.locations;

import net.creuroja.android.volunteerhelper.domain.volunteer_services.VolunteerService;

import java.util.List;

import ollie.Model;
import ollie.annotation.Column;
import ollie.annotation.Table;

import static net.creuroja.android.volunteerhelper.domain.db.ServicesContract.Locations.*;

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
}
