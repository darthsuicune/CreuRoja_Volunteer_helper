package net.creuroja.android.volunteerhelper.domain.locations;

import java.util.List;

public interface LocationsHolder {
	List<Location> locations();

	List<LocationType> availableTypes();

	void registerListener(LocationsListener listener);

	void notifyListeners();

	interface LocationsListener {
		void onListUpdated(List<Location> locations);
	}
}
