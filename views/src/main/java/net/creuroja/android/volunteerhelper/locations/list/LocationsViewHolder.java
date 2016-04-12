package net.creuroja.android.volunteerhelper.locations.list;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import net.creuroja.android.volunteerhelper.R;
import net.creuroja.android.volunteerhelper.domain.locations.Location;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LocationsViewHolder extends RecyclerView.ViewHolder {

	@Bind(R.id.location_name) TextView nameView;

	public LocationsViewHolder(View itemView) {
		super(itemView);
		ButterKnife.bind(this, itemView);
	}

	public void setLocation(Location location, View.OnClickListener listener) {
		nameView.setText(location.name);
		itemView.setOnClickListener(listener);
	}
}
