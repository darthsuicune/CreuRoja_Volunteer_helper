package net.creuroja.android.volunteerhelper.domain.login;

import android.os.Bundle;

public interface LoginActivityInterface {
	void successfulLogin(Bundle bundle);
	void failedLogin(int code);
}
