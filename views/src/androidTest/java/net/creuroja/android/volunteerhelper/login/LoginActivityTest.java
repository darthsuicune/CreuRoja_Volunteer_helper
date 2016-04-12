package net.creuroja.android.volunteerhelper.login;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import net.creuroja.android.volunteerhelper.R;
import net.creuroja.android.volunteerhelper.domain.login.LoginController;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(AndroidJUnit4.class) public class LoginActivityTest {

	@Rule public ActivityTestRule<LoginActivity> rule = new ActivityTestRule<>(LoginActivity.class);
	LoginController controller;

	@Before public void setUp() throws Exception {
		LoginActivity activity = rule.getActivity();
		controller = mock(LoginController.class);
		activity.controller = controller;
	}

	@Test public void attemptLogin() throws Exception {
		String username = "asdf";
		String password = "123456";
		onView(withId(R.id.email)).perform(typeText(username));
		closeSoftKeyboard();
		onView(withId(R.id.password)).perform(typeText(password));
		closeSoftKeyboard();
		onView(withId(R.id.login_button)).perform(click());
		verify(controller).attemptLogin(username, password);
	}
}