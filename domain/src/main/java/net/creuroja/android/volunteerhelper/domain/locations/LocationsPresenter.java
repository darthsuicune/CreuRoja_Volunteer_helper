package net.creuroja.android.volunteerhelper.domain.locations;

public interface LocationsPresenter extends LocationsHolder.LocationsListener {

	interface Factory {
		LocationsPresenter create(ViewMode viewMode);
	}
}
