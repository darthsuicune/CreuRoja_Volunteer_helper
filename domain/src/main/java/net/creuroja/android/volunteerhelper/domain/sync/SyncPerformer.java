package net.creuroja.android.volunteerhelper.domain.sync;

import android.content.ContentProviderClient;
import android.content.ContentProviderOperation;
import android.content.OperationApplicationException;
import android.net.Uri;
import android.os.RemoteException;
import android.util.Log;

import net.creuroja.android.volunteerhelper.domain.CreuRojaEndPoint;
import net.creuroja.android.volunteerhelper.domain.db.ServicesContract;
import net.creuroja.android.volunteerhelper.domain.locations.Location;
import net.creuroja.android.volunteerhelper.domain.locations.LocationsResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

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

	public void storeUpdates(List<LocationsResponse> response, ContentProviderClient resolver) {
		storeUpdates(response, resolver, ServicesContract.Locations.URI);
	}

	public void storeUpdates(List<LocationsResponse> response, ContentProviderClient resolver,
							 Uri uri) {
		ArrayList<ContentProviderOperation> ops = new ArrayList<>();
		for (int i = 0, len = response.size(); i < len; i++) {
			Location location = new Location(response.get(i));
			if (location.active) {
				ops.add(ContentProviderOperation.newUpdate(uri)
						.withValues(location.asValues()).build());
			} else {
				ops.add(ContentProviderOperation.newDelete(uri)
						.withSelection(ServicesContract.Locations._ID + "=?",
								new String[]{Long.toString(location.id)}).build());
			}
		}
		//Attempt to save and fail silently.
		try {
			resolver.applyBatch(ops);
		} catch (RemoteException | OperationApplicationException e) {
			e.printStackTrace();
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
