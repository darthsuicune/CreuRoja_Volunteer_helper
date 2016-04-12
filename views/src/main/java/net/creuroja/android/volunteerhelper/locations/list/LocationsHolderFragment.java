package net.creuroja.android.volunteerhelper.locations.list;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import net.creuroja.android.volunteerhelper.domain.db.ServicesContract;
import net.creuroja.android.volunteerhelper.domain.locations.Location;
import net.creuroja.android.volunteerhelper.domain.locations.LocationType;
import net.creuroja.android.volunteerhelper.domain.locations.LocationsHolder;

import java.util.ArrayList;
import java.util.List;

import ollie.Ollie;

public class LocationsHolderFragment extends Fragment implements LocationsHolder {
	private static final int LOADER_LOCATIONS = 1;
	List<LocationsListener> listeners = new ArrayList<>();
	List<Location> locations;
	List<LocationType> locationTypes;

	public static LocationsHolder newInstance() {
		return new LocationsHolderFragment();
	}

	@Override public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
	}

	@Override public void onAttach(Context context) {
		super.onAttach(context);
		getLoaderManager().restartLoader(LOADER_LOCATIONS, null, new LocationsLoaderHelper());
	}

	@Override public List<Location> locations() {
		return locations;
	}

	@Override public List<LocationType> availableTypes() {
		return locationTypes;
	}

	@Override public void registerListener(LocationsListener listener) {
		listeners.add(listener);
	}

	@Override public void notifyListeners() {
		for (LocationsListener listener : listeners) {
			listener.onListUpdated(locations);
		}
	}

	private class LocationsLoaderHelper implements LoaderManager.LoaderCallbacks<Cursor> {
		@Override public Loader<Cursor> onCreateLoader(int id, Bundle args) {
			Uri uri = ServicesContract.Locations.URI;
			return new CursorLoader(getContext(), uri, null, null, null, null);
		}

		@Override public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
			if (data != null) {
				locations = Ollie.processCursor(Location.class, data);
				notifyListeners();
			}
		}

		@Override public void onLoaderReset(Loader<Cursor> loader) {

		}
	}
}
