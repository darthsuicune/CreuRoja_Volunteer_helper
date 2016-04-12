package net.creuroja.android.volunteerhelper.domain.login;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import net.creuroja.android.volunteerhelper.domain.dagger.AccountManagerModule;
import net.creuroja.android.volunteerhelper.domain.dagger.AuthenticatorServiceModule;
import net.creuroja.android.volunteerhelper.domain.dagger.CreuRojaAuthenticatorServiceComponent;
import net.creuroja.android.volunteerhelper.domain.dagger.DaggerCreuRojaAuthenticatorServiceComponent;

import javax.inject.Inject;

public class CreuRojaAuthenticatorService extends Service {
	@Inject CreuRojaAuthenticator auth;

	CreuRojaAuthenticatorServiceComponent component;

	@Override public void onCreate() {
		super.onCreate();
		component = DaggerCreuRojaAuthenticatorServiceComponent.builder()
				.accountManagerModule(new AccountManagerModule(this))
				.authenticatorServiceModule(new AuthenticatorServiceModule(this))
				.build();
		component.inject(this);
	}

	@Nullable @Override public IBinder onBind(Intent intent) {
		return auth.getIBinder();
	}
}
