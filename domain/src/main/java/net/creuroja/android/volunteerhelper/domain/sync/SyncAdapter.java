package net.creuroja.android.volunteerhelper.domain.sync;

import android.accounts.Account;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentProviderOperation;
import android.content.Context;
import android.content.OperationApplicationException;
import android.content.SharedPreferences;
import android.content.SyncResult;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;

import net.creuroja.android.volunteerhelper.domain.CreuRojaEndPoint;
import net.creuroja.android.volunteerhelper.domain.db.ServicesContract;
import net.creuroja.android.volunteerhelper.domain.locations.Location;
import net.creuroja.android.volunteerhelper.domain.locations.LocationsResponse;
import net.creuroja.android.volunteerhelper.domain.login.AccountHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Response;

public class SyncAdapter extends AbstractThreadedSyncAdapter {
	public static final String AUTHORITY = "net.creuroja.android.volunteerhelper";
	private static final String SYNC_ADAPTER_TAG = "Creu Roja Sync Adapter";
	private static final String LAST_UPDATE_TIME = "last update time";
	CreuRojaEndPoint server;
	SharedPreferences prefs;
	AccountHelper accountHelper;

	@Inject public SyncAdapter(Context context, CreuRojaEndPoint server, SharedPreferences prefs,
							   AccountHelper accountHelper) {
		super(context, true);
		this.server = server;
		this.prefs = prefs;
		this.accountHelper = accountHelper;
	}

	@Override public void onPerformSync(Account account, Bundle extras, String authority,
										ContentProviderClient resolver, SyncResult syncResult) {
		try {
			Log.e("HEY!", "IM here!!!");
			String auth = accountHelper.getAuthToken();
			String lastUpdateTime = prefs.getString(LAST_UPDATE_TIME, "");
			String currentTime = "";
			prefs.edit().putString(LAST_UPDATE_TIME, currentTime).apply();
			Response<List<LocationsResponse>> response =
					server.getLocations(auth, lastUpdateTime).execute();
			verifyResponse(response, resolver);
		} catch (IOException | AuthenticatorException | OperationCanceledException e) {
			e.printStackTrace();
		}
	}

	private void verifyResponse(Response<List<LocationsResponse>> response,
								ContentProviderClient resolver) throws IOException {
		if (response.isSuccess()) {
			saveResults(response.body(), resolver);
		} else {
			Log.d(SYNC_ADAPTER_TAG,
					String.format("Error code %d while performing sync. " + "Error body: %s",
							response.code(), response.errorBody().string()));
			if(response.code() == 401) {

			}
		}
	}

	private void saveResults(List<LocationsResponse> response, ContentProviderClient resolver) {
		ArrayList<ContentProviderOperation> ops = new ArrayList<>();
		for (int i = 0, len = response.size(); i < len; i++) {
			Location location = new Location(response.get(i));
			ContentProviderOperation op;
			if(location.active) {
				op = ContentProviderOperation.newUpdate(ServicesContract.Locations.URI)
						.build();
			} else {
				op = ContentProviderOperation.newDelete(ServicesContract.Locations.URI)
						.build();
			}
			ops.add(op);
		}
		//Fail silently.
		try {
			resolver.applyBatch(ops);
		} catch (RemoteException | OperationApplicationException e) {
			e.printStackTrace();
		}
	}
}
