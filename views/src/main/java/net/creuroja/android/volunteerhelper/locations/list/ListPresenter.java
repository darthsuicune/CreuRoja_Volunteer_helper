package net.creuroja.android.volunteerhelper.locations.list;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.creuroja.android.volunteerhelper.R;
import net.creuroja.android.volunteerhelper.domain.locations.Location;
import net.creuroja.android.volunteerhelper.domain.locations.LocationsIndexActivityInterface;
import net.creuroja.android.volunteerhelper.domain.locations.LocationsPresenter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ListPresenter extends Fragment implements LocationsPresenter {

	@Bind(R.id.location_list) RecyclerView recycler;
	@Bind(R.id.location_list_empty) TextView emptyListText;

	LocationsIndexActivityInterface activity;
	LocationsListAdapter adapter;

	@Override public void onAttach(Context context) {
		super.onAttach(context);
		activity = (LocationsIndexActivityInterface) context;
		setAdapter();
	}

	@Nullable @Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
							 @Nullable Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_locations_list, container, false);
		ButterKnife.bind(this, v);
		return v;
	}

	@Override public void onListUpdated(List<Location> locations) {
		setAdapter();
		recycler.setVisibility((locations.size() == 0) ? View.GONE : View.VISIBLE);
		emptyListText.setVisibility((locations.size() == 0) ? View.VISIBLE : View.GONE);
		adapter.update(locations);
	}

	private void setAdapter() {
		if (isAdded() && adapter == null) {
			adapter = new LocationsListAdapter(activity);
			recycler.setAdapter(adapter);
			recycler.setLayoutManager(new LinearLayoutManager(getContext()));
		}
	}
}
