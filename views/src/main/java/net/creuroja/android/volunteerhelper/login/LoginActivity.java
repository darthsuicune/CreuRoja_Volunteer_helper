package net.creuroja.android.volunteerhelper.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.dlgdev.views.activities.helper.AccountAuthenticatorActivity;

import net.creuroja.android.volunteerhelper.R;
import net.creuroja.android.volunteerhelper.dagger.DaggerLoginActivityComponent;
import net.creuroja.android.volunteerhelper.dagger.LoginActivityComponent;
import net.creuroja.android.volunteerhelper.dagger.LoginActivityModule;
import net.creuroja.android.volunteerhelper.domain.dagger.AccountManagerModule;
import net.creuroja.android.volunteerhelper.domain.login.LoginActivityInterface;
import net.creuroja.android.volunteerhelper.domain.login.LoginController;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AccountAuthenticatorActivity implements LoginActivityInterface {

	LoginActivityComponent component;
	@Inject LoginController controller;

	@Bind(R.id.login_progress) View progressView;
	@Bind(R.id.login_form) View loginForm;
	@Bind(R.id.email) EditText usernameView;
	@Bind(R.id.password) EditText passwordView;

	@Override protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		component = DaggerLoginActivityComponent.builder()
				.loginActivityModule(new LoginActivityModule(this))
				.accountManagerModule(new AccountManagerModule(this)).build();
		component.inject(this);
		setContentView(R.layout.activity_login);
		setupViews();
	}

	void setupViews() {
		ButterKnife.bind(this);
		passwordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
				if (id == R.id.login || id == EditorInfo.IME_NULL) {
					attemptLogin();
					return true;
				}
				return false;
			}
		});
	}

	@OnClick(R.id.login_button) public void attemptLogin() {
		String username, password;
		if (checkLength(usernameView, R.string.error_username_too_short) &&
			checkLength(passwordView, R.string.error_invalid_password)) {
			username = usernameView.getText().toString();
			password = passwordView.getText().toString();
			InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(passwordView.getWindowToken(), 0);
			showProgress(true);
			controller.attemptLogin(username, password);
		}
	}

	boolean checkLength(EditText view, int errorResId) {
		if (view.getText().length() < 2) {
			view.setError(getString(errorResId));
			view.requestFocus();
			return false;
		}
		return true;
	}

	void showProgress(boolean show) {
		progressView.setVisibility((show) ? View.VISIBLE : View.GONE);
		loginForm.setVisibility((show) ? View.GONE : View.VISIBLE);
	}

	@Override public void successfulLogin(Bundle bundle) {
		showProgress(false);
		this.setAccountAuthenticatorResult(bundle);
		setResult(RESULT_OK);
		finish();
	}

	@Override public void failedLogin(int errorMessageResId) {
		showProgress(false);
		passwordView.setError(getString(errorMessageResId));
		passwordView.setText("");
		passwordView.requestFocus();
	}
}
