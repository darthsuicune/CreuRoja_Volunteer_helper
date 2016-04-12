package net.creuroja.android.volunteerhelper.domain.dagger;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

@Module
public class AuthenticatorServiceModule {
	Context context;

	public AuthenticatorServiceModule(Context context) {
		this.context = context;
	}

	@Provides Context provideContext() {
		return context;
	}
}
