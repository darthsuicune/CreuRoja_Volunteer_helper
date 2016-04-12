package net.creuroja.android.volunteerhelper.locations.list;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import net.creuroja.android.volunteerhelper.R;
import net.creuroja.android.volunteerhelper.domain.locations.Location;
import net.creuroja.android.volunteerhelper.domain.locations.LocationType;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import ollie.query.Delete;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsNot.not;

@RunWith(AndroidJUnit4.class) public class LocationsIndexActivityTest {

	@Rule public ActivityTestRule<LocationsIndexActivity> rule =
			new ActivityTestRule<>(LocationsIndexActivity.class);

	LocationsIndexActivity activity;

	@Before public void setUp() throws Exception {
		activity = rule.getActivity();
	}

	@After public void tearDown() throws Exception {
		Delete.from(Location.class).execute();
	}

	@Test public void afterLocationsAreAddedTheyGetDisplayed() throws Throwable {
		onView(withId(R.id.location_list)).check(matches(not(isDisplayed())));
		onView(withId(R.id.location_list_empty)).check(matches(isDisplayed()));

		Location location = insertLocation("Name");

		onView(withId(R.id.location_list)).check(matches(isDisplayed()));
		onView(withId(R.id.location_list_empty)).check(matches(not(isDisplayed())));
		onView(withId(R.id.location_name)).check(matches(withText(location.name)));
	}

	@Test public void afterTwoLocationsAreAddedTheyGetDisplayed() throws Throwable {
		onView(withId(R.id.location_list)).check(matches(not(isDisplayed())));
		onView(withId(R.id.location_list_empty)).check(matches(isDisplayed()));

		Location location = insertLocation("Name");
		Location location1 = insertLocation("Name1");

		onView(withId(R.id.location_list)).check(matches(isDisplayed()));
		onView(withId(R.id.location_list_empty)).check(matches(not(isDisplayed())));
		onView(withText(location.name)).check(matches(isDisplayed()));
		onView(withText(location1.name)).check(matches(isDisplayed()));
	}

	public Location insertLocation(String name) {
		Location location = new Location();
		location.name = name;
		location.type = LocationType.ASAMBLEA;
		location.save();
		return location;
	}
}