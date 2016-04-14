package net.creuroja.android.volunteerhelper.locations.list;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import net.creuroja.android.volunteerhelper.R;
import net.creuroja.android.volunteerhelper.dagger.DaggerLocationsIndexActivityComponent;
import net.creuroja.android.volunteerhelper.dagger.LocationsIndexActivityComponent;
import net.creuroja.android.volunteerhelper.dagger.LocationsIndexActivityModule;
import net.creuroja.android.volunteerhelper.domain.dagger.AccountManagerModule;
import net.creuroja.android.volunteerhelper.domain.dagger.SharedPreferencesModule;
import net.creuroja.android.volunteerhelper.domain.locations.Location;
import net.creuroja.android.volunteerhelper.domain.locations.LocationsHolder;
import net.creuroja.android.volunteerhelper.domain.locations.LocationsIndexActivityInterface;
import net.creuroja.android.volunteerhelper.domain.locations.LocationsIndexController;
import net.creuroja.android.volunteerhelper.domain.locations.LocationsPresenter;
import net.creuroja.android.volunteerhelper.domain.locations.ViewMode;
import net.creuroja.android.volunteerhelper.domain.login.AccountHelper;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LocationsIndexActivity extends AppCompatActivity
		implements LocationsIndexActivityInterface {
	private static final String TAG_HOLDER = "holder";
	private static final String TAG_PRESENTER = "presenter";

	LocationsIndexActivityComponent component;

	@Inject LocationsIndexController controller;
	@Inject AccountHelper accountHelper;

	@Bind(R.id.toolbar) Toolbar toolbar;

	@Override public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		loadComponents();
		accountHelper.verifyCredentials(this, new AccountHelper.AuthCallback() {
			@Override public void validTokenFound() {
				proceed();
			}

			@Override public void noValidTokenFound() {
				finish();
			}
		});
	}

	private void loadComponents() {
		if (component == null) {
			component = DaggerLocationsIndexActivityComponent.builder()
					.accountManagerModule(new AccountManagerModule(this))
					.sharedPreferencesModule(new SharedPreferencesModule(this))
					.locationsIndexActivityModule(new LocationsIndexActivityModule(this))
					.build();
			component.inject(this);
		}
	}

	private void proceed() {
		setContentView(R.layout.activity_locations_index);
		ButterKnife.bind(this);
		setSupportActionBar(toolbar);
		controller.viewLocations();
	}

	@Override public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.locations_index_options, menu);
		return true;
	}

	@Override public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.view_as_list:
				controller.viewAs(ViewMode.LIST);
				return true;
			case R.id.view_as_map_box:
				controller.viewAs(ViewMode.MAPBOX);
				return true;
			case R.id.view_as_map_google:
				controller.viewAs(ViewMode.GOOGLE_MAP);
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}

	}

	@Override public void displayLocationDetails(Location location) {
		Snackbar.make(toolbar, location.name, Snackbar.LENGTH_INDEFINITE).show();
	}

	@Override public void searchForLocation(String string) {

	}

	@Override public void calculateDirectionsTo(double latitude, double longitude) {

	}

	@Override public void attach(LocationsHolder holder) {
		Fragment fragment = (Fragment) holder;
		if (!fragment.isAdded()) {
			getSupportFragmentManager().beginTransaction().add(fragment, TAG_HOLDER).commit();
		}
	}

	@Override public void attach(LocationsPresenter presenter) {
		Fragment fragment = (Fragment) presenter;
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.locations_index_fragment, fragment, TAG_PRESENTER).commit();
	}
}
