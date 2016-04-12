package net.creuroja.android.volunteerhelper.locations.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.SupportMapFragment;

import net.creuroja.android.volunteerhelper.domain.locations.Location;
import net.creuroja.android.volunteerhelper.domain.locations.LocationsPresenter;

import java.util.List;

public class GoogleMapListPresenter extends SupportMapFragment implements LocationsPresenter {


	@Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
									   Bundle savedInstanceState) {
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override public void onListUpdated(List<Location> locations) {

	}
}
