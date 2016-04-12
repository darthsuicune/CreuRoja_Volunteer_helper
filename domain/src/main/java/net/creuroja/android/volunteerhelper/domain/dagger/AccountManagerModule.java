package net.creuroja.android.volunteerhelper.domain.dagger;

import android.accounts.AccountManager;
import android.content.Context;

import dagger.Module;
import dagger.Provides;

@Module
public class AccountManagerModule {
	Context context;

	public AccountManagerModule(Context context) {
		this.context = context;
	}

	@Provides AccountManager provideAccountManager() {
		return AccountManager.get(context);
	}
}
