package net.creuroja.android.volunteerhelper.domain.login;

import android.accounts.Account;
import android.content.ContentResolver;
import android.os.Bundle;

import net.creuroja.android.volunteerhelper.domain.CreuRojaEndPoint;
import net.creuroja.android.volunteerhelper.domain.R;
import net.creuroja.android.volunteerhelper.domain.sync.SyncAdapter;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginControllerImpl implements LoginController {

	LoginActivityInterface activity;
	CreuRojaEndPoint server;
	AccountHelper accountHelper;

	@Inject public LoginControllerImpl(LoginActivityInterface activity, CreuRojaEndPoint server,
									   AccountHelper accountHelper) {
		this.activity = activity;
		this.server = server;
		this.accountHelper = accountHelper;
	}

	@Override public void attemptLogin(final String username, final String password) {
		server.login(username, password).enqueue(new Callback<LoginResponse>() {
			@Override
			public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
				success(username, password, response);
			}

			@Override public void onFailure(Call<LoginResponse> call, Throwable t) {
				activity.failedLogin(R.string.error_connection);
			}
		});
	}

	private void success(String username, String password, Response<LoginResponse> response) {
		if(response.isSuccess()) {
			String token = response.body().token;
			Account account = accountHelper.createAccount(username, password, token);
			ContentResolver.requestSync(account, SyncAdapter.AUTHORITY, Bundle.EMPTY);
			ContentResolver.setSyncAutomatically(account, SyncAdapter.AUTHORITY, true);
			activity.successfulLogin(accountHelper.asBundle(username, password, token));
		} else {
			int errorMessageResId;
			switch(response.code()) {
				case 403:
					errorMessageResId = R.string.error_forbidden;
					break;
				case 404:
					errorMessageResId = R.string.error_not_found;
					break;
				case 500:
					errorMessageResId = R.string.error_internal;
					break;
				default:
					errorMessageResId = R.string.error_unknown;
			}
			activity.failedLogin(errorMessageResId);
		}
	}
}
