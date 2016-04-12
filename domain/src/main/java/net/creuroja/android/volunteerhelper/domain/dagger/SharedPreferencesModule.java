package net.creuroja.android.volunteerhelper.domain.dagger;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import dagger.Module;
import dagger.Provides;

@Module
public class SharedPreferencesModule {
	private final Context context;

	public SharedPreferencesModule(Context context) {
		this.context = context;
	}

	@Provides SharedPreferences providePreferences() {
		return PreferenceManager.getDefaultSharedPreferences(context);
	}
}
