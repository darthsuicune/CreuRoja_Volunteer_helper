package net.creuroja.android.volunteerhelper.dagger;

import net.creuroja.android.volunteerhelper.R;
import net.creuroja.android.volunteerhelper.domain.locations.LocationsHolder;
import net.creuroja.android.volunteerhelper.domain.locations.LocationsIndexActivityInterface;
import net.creuroja.android.volunteerhelper.domain.locations.LocationsIndexController;
import net.creuroja.android.volunteerhelper.domain.locations.LocationsIndexControllerImpl;
import net.creuroja.android.volunteerhelper.domain.locations.LocationsPresenter;
import net.creuroja.android.volunteerhelper.domain.locations.ViewMode;
import net.creuroja.android.volunteerhelper.locations.list.GoogleMapListPresenter;
import net.creuroja.android.volunteerhelper.locations.list.ListPresenter;
import net.creuroja.android.volunteerhelper.locations.list.LocationsHolderFragment;
import net.creuroja.android.volunteerhelper.locations.list.LocationsIndexActivity;
import net.creuroja.android.volunteerhelper.locations.list.MapboxListPresenter;

import dagger.Module;
import dagger.Provides;

@Module public class LocationsIndexActivityModule {
	static final double DEFAULT_LATITUDE = 41.3958;
	static final double DEFAULT_LONGITUDE = 2.1739;
	static final int DEFAULT_ZOOM = 12;

	LocationsIndexActivity activity;

	public LocationsIndexActivityModule(LocationsIndexActivity activity) {
		this.activity = activity;
	}

	@Provides public LocationsPresenter.Factory provideLocationsPresenter() {
		return new LocationsPresenter.Factory() {
			@Override public LocationsPresenter create(ViewMode viewMode) {
				switch (viewMode) {
					case MAPBOX:
						return MapboxListPresenter
								.newInstance(activity.getString(R.string.mapbox_api_key),
										DEFAULT_LATITUDE, DEFAULT_LONGITUDE, DEFAULT_ZOOM);
					case GOOGLE_MAP:
						return new GoogleMapListPresenter();
					case LIST:
					default:
						return new ListPresenter();
				}
			}
		};
	}

	@Provides public LocationsHolder provideHolder() {
		return LocationsHolderFragment.newInstance();
	}

	@Provides LocationsIndexActivityInterface provideActivityInterface() {
		return activity;
	}

	@Provides LocationsIndexController provideLocationsController(
			LocationsIndexControllerImpl controller) {
		return controller;
	}
}
