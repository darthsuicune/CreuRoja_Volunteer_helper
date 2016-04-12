package net.creuroja.android.volunteerhelper.domain.login;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.NetworkErrorException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import javax.inject.Inject;

public class CreuRojaAuthenticator extends AbstractAccountAuthenticator {
	private AccountManager manager;

	@Inject public CreuRojaAuthenticator(Context context, AccountManager manager) {
		super(context);
		this.manager = manager;
	}

	@Override public Bundle addAccount(AccountAuthenticatorResponse response, String accountType,
									   String authTokenType, String[] requiredFeatures,
									   Bundle options) throws NetworkErrorException {
		if (manager.getAccountsByType(AccountHelper.ACCOUNT_TYPE).length > 0) {
			return null; //Only one account is allowed
		}
		return prepareAuthenticatorActivity(response);
	}

	@Override public Bundle getAuthToken(AccountAuthenticatorResponse response, Account account,
										 String tokenType, Bundle bundle)
			throws NetworkErrorException {
		String authToken = manager.peekAuthToken(account, tokenType);
		Bundle result;
		if (TextUtils.isEmpty(authToken)) {
			result = prepareAuthenticatorActivity(response);
		} else {
			result = new Bundle();
			result.putString(AccountManager.KEY_ACCOUNT_NAME, account.name);
			result.putString(AccountManager.KEY_ACCOUNT_TYPE, account.type);
			result.putString(AccountManager.KEY_AUTHTOKEN, authToken);
		}
		return result;
	}

	@Override
	public Bundle editProperties(AccountAuthenticatorResponse response, String accountType) {
		return null;
	}

	@Override
	public Bundle confirmCredentials(AccountAuthenticatorResponse response, Account account,
									 Bundle options) throws NetworkErrorException {
		return null;
	}

	@Override public String getAuthTokenLabel(String s) {
		return null;
	}

	@Override
	public Bundle updateCredentials(AccountAuthenticatorResponse r, Account account, String s,
									Bundle bundle) throws NetworkErrorException {
		return null;
	}

	@Override
	public Bundle hasFeatures(AccountAuthenticatorResponse r, Account account, String[] strings)
			throws NetworkErrorException {
		return null;
	}

	private Bundle prepareAuthenticatorActivity(AccountAuthenticatorResponse response) {
		final Bundle result = new Bundle();
		final Intent intent = new Intent("net.creuroja.android.volunteerhelper.AUTHENTICATE");
		intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);
		result.putParcelable(AccountManager.KEY_INTENT, intent);
		return result;
	}
}
