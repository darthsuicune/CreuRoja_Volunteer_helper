package net.creuroja.android.volunteerhelper.domain.locations;

import net.creuroja.android.volunteerhelper.domain.volunteer_services.VolunteerService;

import java.util.ArrayList;
import java.util.List;

public class LocationsResponse {
	int id;
	String name;
	String description;
	String address;
	String phone;
	String latitude;
	String longitude;
	String locationType;
	boolean active;
	String updatedAt;
	List<VolunteerService> activeServices = new ArrayList<>();
}
