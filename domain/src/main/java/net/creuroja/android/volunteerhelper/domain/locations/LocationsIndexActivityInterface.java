package net.creuroja.android.volunteerhelper.domain.locations;

public interface LocationsIndexActivityInterface {
	void displayLocationDetails(Location location);

	void searchForLocation(String string);

	void calculateDirectionsTo(double latitude, double longitude);

	void attach(LocationsHolder holder);
	void attach(LocationsPresenter presenter);
}
