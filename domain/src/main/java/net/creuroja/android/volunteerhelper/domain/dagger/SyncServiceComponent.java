package net.creuroja.android.volunteerhelper.domain.dagger;

import net.creuroja.android.volunteerhelper.domain.sync.SyncService;

import dagger.Component;

@Component(modules = {SyncServiceModule.class,
					  ServerModule.class,
					  SharedPreferencesModule.class,
					  AccountManagerModule.class})
public interface SyncServiceComponent {
	void inject(SyncService service);
}
