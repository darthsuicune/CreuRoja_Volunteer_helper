package net.creuroja.android.volunteerhelper.domain.locations;

import android.content.SharedPreferences;

import javax.inject.Inject;

public class LocationsIndexControllerImpl implements LocationsIndexController {

	private static final String VIEW_MODE = "view_mode";
	private final LocationsPresenter.Factory presenterFactory;
	private LocationsPresenter presenter;
	private final LocationsHolder holder;
	private final LocationsIndexActivityInterface activity;
	private final SharedPreferences prefs;

	@Inject public LocationsIndexControllerImpl(LocationsPresenter.Factory presenterFactory,
												LocationsHolder holder,
												LocationsIndexActivityInterface activity,
												SharedPreferences prefs) {
		this.presenterFactory = presenterFactory;
		this.holder = holder;
		this.activity = activity;
		this.prefs = prefs;
	}

	@Override public void viewAs(ViewMode viewMode) {
		prefs.edit().putString(VIEW_MODE, viewMode.name()).apply();
		presenter = presenterFactory.create(viewMode);
		holder.registerListener(presenter);
		activity.attach(holder);
		activity.attach(presenter);
	}

	@Override public void viewLocations() {
		ViewMode mode = ViewMode.valueOf(ViewMode.class,
				prefs.getString(VIEW_MODE, ViewMode.MAPBOX.name()));
		viewAs(mode);
	}
}
