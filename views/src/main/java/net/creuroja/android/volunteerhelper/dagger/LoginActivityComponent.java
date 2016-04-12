package net.creuroja.android.volunteerhelper.dagger;

import net.creuroja.android.volunteerhelper.domain.dagger.AccountManagerModule;
import net.creuroja.android.volunteerhelper.domain.dagger.ServerModule;
import net.creuroja.android.volunteerhelper.login.LoginActivity;

import dagger.Component;

@Component(modules = {ServerModule.class, LoginActivityModule.class, AccountManagerModule.class})
public interface LoginActivityComponent {
	void inject(LoginActivity activity);
}
