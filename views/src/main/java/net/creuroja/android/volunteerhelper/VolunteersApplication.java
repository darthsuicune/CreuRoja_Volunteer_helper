package net.creuroja.android.volunteerhelper;

import android.app.Application;

import net.creuroja.android.volunteerhelper.domain.db.ServicesProvider;

import ollie.Ollie;

public class VolunteersApplication extends Application {

	@Override public void onCreate() {
		super.onCreate();
		Ollie.with(this)
				.setName(ServicesProvider.DATABASE_NAME)
				.setVersion(ServicesProvider.DATABASE_VERSION)
				.init();
	}
}
