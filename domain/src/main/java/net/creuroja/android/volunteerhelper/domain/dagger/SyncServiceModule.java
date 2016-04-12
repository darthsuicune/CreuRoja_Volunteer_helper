package net.creuroja.android.volunteerhelper.domain.dagger;

import android.content.Context;

import net.creuroja.android.volunteerhelper.domain.sync.SyncService;

import dagger.Module;
import dagger.Provides;

@Module
public class SyncServiceModule {

	SyncService service;

	public SyncServiceModule(SyncService service) {
		this.service = service;
	}

	@Provides Context provideContext() {
		return service;
	}
}
