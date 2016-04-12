package net.creuroja.android.volunteerhelper.domain.login;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;

import java.io.IOException;

import javax.inject.Inject;

public class AccountHelper {
	public static final String ACCOUNT_TYPE = "net.creuroja.android.ACCOUNT";

	AccountManager manager;

	@Inject public AccountHelper(AccountManager manager) {
		this.manager = manager;
	}

	public void verifyCredentials(Activity activity, final AuthCallback authCallback) {
		AccountManagerCallback<Bundle> callback = new AccountManagerCallback<Bundle>() {
			@Override public void run(AccountManagerFuture<Bundle> futureResult) {
				new RetrieveAuthTokenTask(futureResult, authCallback).execute();
			}
		};
		manager.getAuthTokenByFeatures(ACCOUNT_TYPE, ACCOUNT_TYPE, null, activity, null, null,
				callback, new Handler());
	}

	public Account createAccount(String username, String password, String token) {
		Account account;
		if (accountExists()) {
			account = manager.getAccountsByType(ACCOUNT_TYPE)[0];
			manager.setPassword(account, password);
			manager.setAuthToken(account, ACCOUNT_TYPE, token);
		} else {
			account = new Account(username, ACCOUNT_TYPE);
			manager.addAccountExplicitly(account, password, null);
			manager.setAuthToken(account, ACCOUNT_TYPE, token);
		}
		return account;
	}

	public Bundle asBundle(String username, String password, String token) {
		Bundle bundle = new Bundle();
		bundle.putString(AccountManager.KEY_ACCOUNT_NAME, username);
		bundle.putString(AccountManager.KEY_PASSWORD, password);
		bundle.putString(AccountManager.KEY_AUTHTOKEN, token);
		bundle.putString(AccountManager.KEY_ACCOUNT_TYPE, ACCOUNT_TYPE);
		return bundle;
	}

	public boolean accountExists() {
		return manager.getAccountsByType(ACCOUNT_TYPE).length > 0;
	}

	public String getAuthToken()
			throws AuthenticatorException, OperationCanceledException, IOException {
		return manager.blockingGetAuthToken(manager.getAccountsByType(ACCOUNT_TYPE)[0],
				ACCOUNT_TYPE,false);
	}

	public interface AuthCallback {
		void validTokenFound();

		void noValidTokenFound();
	}

	public class RetrieveAuthTokenTask extends AsyncTask<Void, Void, String> {

		private AccountManagerFuture<Bundle> result;
		private AuthCallback callback;

		public RetrieveAuthTokenTask(AccountManagerFuture<Bundle> result, AuthCallback callback) {
			this.result = result;
			this.callback = callback;
		}

		@Override protected String doInBackground(Void... voids) {
			try {
				return result.getResult().getString(AccountManager.KEY_AUTHTOKEN);
			} catch (OperationCanceledException | IOException | AuthenticatorException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override protected void onPostExecute(String token) {
			super.onPostExecute(token);
			if (TextUtils.isEmpty(token)) {
				callback.noValidTokenFound();
			} else {
				callback.validTokenFound();
			}
		}
	}
}
