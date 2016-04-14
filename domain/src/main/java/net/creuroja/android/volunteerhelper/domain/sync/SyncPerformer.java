package net.creuroja.android.volunteerhelper.domain.sync;

import android.content.ContentResolver;
import android.net.Uri;
import android.util.Log;

import net.creuroja.android.volunteerhelper.domain.CreuRojaEndPoint;
import net.creuroja.android.volunteerhelper.domain.db.ServicesContract;
import net.creuroja.android.volunteerhelper.domain.locations.Location;
import net.creuroja.android.volunteerhelper.domain.locations.LocationsResponse;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import ollie.query.Select;
import retrofit2.Response;

public class SyncPerformer {
	private static final String SYNC_ADAPTER_TAG = "Creu Roja Sync Adapter";

	CreuRojaEndPoint server;

	@Inject public SyncPerformer(CreuRojaEndPoint server) {
		this.server = server;
	}

	public Response<List<LocationsResponse>> initSync(String authToken, String lastUpdateTime)
			throws IOException {
		String tokenAuthString = "Token token=" + authToken;
		return server.getLocations(tokenAuthString, lastUpdateTime).execute();
	}

	public boolean isValidResponse(Response<List<LocationsResponse>> response) {
		return response.isSuccess();
	}

	public void storeUpdates(List<LocationsResponse> response, ContentResolver cr) {
		Uri uri = ServicesContract.Locations.URI;
		for (LocationsResponse location : response) {
			if (location.active) {
				if (Select.from(Location.class)
							.where(ServicesContract.Locations.REMOTE_ID + "=?", location.id).fetch()
							.size() > 0) {
					cr.update(uri, location.asValues(), ServicesContract.Locations.REMOTE_ID + "=?",
							new String[] {Integer.toString(location.id)});
				} else {
					cr.insert(uri, location.asValues());
				}
			} else {
				cr.delete(uri, ServicesContract.Locations._ID + "=?",
						new String[]{Long.toString(location.id)});
			}
		}
	}

	public void logError(Response<List<LocationsResponse>> response) throws IOException {
		Log.d(SYNC_ADAPTER_TAG,
				String.format("Error code %d while performing sync. " + "Error body: %s",
						response.code(), response.errorBody().string()));
		if (response.code() == 401) {
			Log.d(SYNC_ADAPTER_TAG,
					"Unauthorized. Should remove all stored data and force a new login");
		}
	}
}
