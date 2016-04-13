package net.creuroja.android.volunteerhelper.domain.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import net.creuroja.android.volunteerhelper.domain.dagger.AccountManagerModule;
import net.creuroja.android.volunteerhelper.domain.dagger.DaggerSyncServiceComponent;
import net.creuroja.android.volunteerhelper.domain.dagger.SharedPreferencesModule;
import net.creuroja.android.volunteerhelper.domain.dagger.SyncServiceComponent;
import net.creuroja.android.volunteerhelper.domain.dagger.SyncServiceModule;

import javax.inject.Inject;

public class SyncService extends Service {
	@Inject SyncAdapter syncAdapter;
	SyncServiceComponent component;

	private static final Object syncAdapterLock = new Object();

	@Override public void onCreate() {
		super.onCreate();
		component = DaggerSyncServiceComponent.builder()
				.syncServiceModule(new SyncServiceModule(this))
				.sharedPreferencesModule(new SharedPreferencesModule(this))
				.accountManagerModule(new AccountManagerModule(this))
				.build();

		synchronized (syncAdapterLock) {
			component.inject(this);
		}
	}

	@Nullable @Override public IBinder onBind(Intent intent) {
		return syncAdapter.getSyncAdapterBinder();
	}
}
