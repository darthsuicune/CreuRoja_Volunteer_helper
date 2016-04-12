package net.creuroja.android.volunteerhelper.dagger;

import net.creuroja.android.volunteerhelper.domain.login.LoginActivityInterface;
import net.creuroja.android.volunteerhelper.domain.login.LoginController;
import net.creuroja.android.volunteerhelper.domain.login.LoginControllerImpl;
import net.creuroja.android.volunteerhelper.login.LoginActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class LoginActivityModule {

	private LoginActivity activity;

	public LoginActivityModule(LoginActivity activity) {
		this.activity = activity;
	}

	@Provides LoginController provideController(LoginControllerImpl controller) {
		return controller;
	}

	@Provides LoginActivityInterface provideLoginActivity() {
		return activity;
	}
}
