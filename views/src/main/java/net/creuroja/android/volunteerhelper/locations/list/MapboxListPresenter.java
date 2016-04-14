package net.creuroja.android.volunteerhelper.locations.list;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.constants.MapboxConstants;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.MapboxMapOptions;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.SupportMapFragment;

import net.creuroja.android.volunteerhelper.domain.locations.Location;
import net.creuroja.android.volunteerhelper.domain.locations.LocationsIndexActivityInterface;
import net.creuroja.android.volunteerhelper.domain.locations.LocationsPresenter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapboxListPresenter extends SupportMapFragment
		implements LocationsPresenter, OnMapReadyCallback {

	MapboxMap map;
	Map<Location, Marker> markers = new HashMap<>();
	LocationsIndexActivityInterface activity;
	MarkerListener listener = new MarkerListener();
	private List<Location> locations;

	public static MapboxListPresenter newInstance(String apikey, double initialLat,
												  double initialLong, int initialZoom) {
		MapboxListPresenter presenter = new MapboxListPresenter();
		MapboxMapOptions options = new MapboxMapOptions();
		options.accessToken(apikey);
		options.attributionEnabled(true);
		options.camera(new CameraPosition.Builder().target(new LatLng(initialLat, initialLong))
				.zoom(initialZoom).build());
		Bundle bundle = new Bundle();
		bundle.putParcelable(MapboxConstants.FRAG_ARG_MAPBOXMAPOPTIONS, options);
		presenter.setArguments(bundle);
		return presenter;
	}

	@Override public void onAttach(Context context) {
		super.onAttach(context);
		getMapAsync(this);
		activity = (LocationsIndexActivityInterface) context;
	}

	@Override public void onDetach() {
		super.onDetach();
		activity = null;
	}

	@Override public void onMapReady(MapboxMap mapboxMap) {
		map = mapboxMap;
		map.setOnMarkerClickListener(listener);
		if (locations != null) {
			onListUpdated(locations);
			locations = null;
		}
	}

	@Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
									   Bundle savedInstanceState) {
		View v = super.onCreateView(inflater, container, savedInstanceState);
		getMapAsync(this);
		return v;
	}

	@Override public void onListUpdated(List<Location> locations) {
		if(map == null) {
			this.locations = locations;
			return;
		}
		for (Location location : locations) {
			Marker marker;
			if (markers.containsKey(location)) {
				marker = markers.get(location);
				marker.setPosition(new LatLng(location.latitude, location.longitude));
				map.updateMarker(marker);
			} else {
				MarkerOptions options = new MarkerOptions();
				options.position(new LatLng(location.latitude, location.longitude));
				options.title(location.name);
				marker = map.addMarker(options);
				markers.put(location, marker);
			}
			listener.addLocation(location, marker);
		}
	}

	private class MarkerListener implements MapboxMap.OnMarkerClickListener {
		Map<Marker, Location> locations = new HashMap<>();

		public void addLocation(Location location, Marker marker) {
			locations.put(marker, location);
		}

		@Override public boolean onMarkerClick(@NonNull Marker marker) {
			activity.displayLocationDetails(locations.get(marker));
			return true;
		}
	}
}
