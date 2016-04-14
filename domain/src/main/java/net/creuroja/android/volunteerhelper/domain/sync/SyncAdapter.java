package net.creuroja.android.volunteerhelper.domain.sync;

import android.accounts.Account;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SyncResult;
import android.os.Bundle;

import net.creuroja.android.volunteerhelper.domain.locations.LocationsResponse;
import net.creuroja.android.volunteerhelper.domain.login.AccountHelper;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Response;

public class SyncAdapter extends AbstractThreadedSyncAdapter {
	private static final String LAST_UPDATE_TIME = "last update time";
	public static final String AUTHORITY = "net.creuroja.android.volunteerhelper";

	AccountHelper accountHelper;
	SyncPerformer performer;
	SharedPreferences prefs;

	@Inject
	public SyncAdapter(Context context, AccountHelper accountHelper, SyncPerformer performer,
					   SharedPreferences prefs) {
		super(context, true);
		this.performer = performer;
		this.accountHelper = accountHelper;
		this.prefs = prefs;
	}

	@Override public void onPerformSync(Account account, Bundle extras, String authority,
										ContentProviderClient resolver, SyncResult syncResult) {
		try {
			String lastUpdateTime = prefs.getString(LAST_UPDATE_TIME, "");
			String currentTime = "";
			String authToken = accountHelper.getAuthToken();
			Response<List<LocationsResponse>> response =
					performer.initSync(authToken, lastUpdateTime);
			if (performer.isValidResponse(response)) {
				performer.storeUpdates(response.body(), getContext().getContentResolver());
				prefs.edit().putString(LAST_UPDATE_TIME, currentTime).apply();
			} else {
				performer.logError(response);
			}
		} catch (IOException | AuthenticatorException | OperationCanceledException e) {
			e.printStackTrace();
		}
	}
}
