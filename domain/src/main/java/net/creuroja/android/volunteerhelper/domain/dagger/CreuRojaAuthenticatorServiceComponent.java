package net.creuroja.android.volunteerhelper.domain.dagger;

import net.creuroja.android.volunteerhelper.domain.login.CreuRojaAuthenticatorService;

import dagger.Component;

@Component(modules = {AuthenticatorServiceModule.class, AccountManagerModule.class})
public interface CreuRojaAuthenticatorServiceComponent {
	void inject(CreuRojaAuthenticatorService service);
}
