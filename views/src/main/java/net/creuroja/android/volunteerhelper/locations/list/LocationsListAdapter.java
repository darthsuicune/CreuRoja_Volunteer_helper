package net.creuroja.android.volunteerhelper.locations.list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.creuroja.android.volunteerhelper.R;
import net.creuroja.android.volunteerhelper.domain.locations.Location;
import net.creuroja.android.volunteerhelper.domain.locations.LocationsIndexActivityInterface;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class LocationsListAdapter extends RecyclerView.Adapter<LocationsViewHolder> {
	private List<Location> locations = new ArrayList<>();
	private LocationsIndexActivityInterface activity;

	@Inject public LocationsListAdapter(LocationsIndexActivityInterface activity) {
		this.activity = activity;
	}

	@Override public LocationsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View v = LayoutInflater.from(parent.getContext())
				.inflate(R.layout.view_holder_locations, parent, false);
		return new LocationsViewHolder(v);
	}

	@Override public void onBindViewHolder(final LocationsViewHolder holder, int position) {
		holder.setLocation(locations.get(position), new View.OnClickListener() {
			@Override public void onClick(View view) {
				activity.displayLocationDetails(locations.get(holder.getAdapterPosition()));
			}
		});
	}

	@Override public int getItemCount() {
		return locations.size();
	}

	public void update(List<Location> locations) {
		this.locations = locations;
		notifyDataSetChanged();
	}
}
