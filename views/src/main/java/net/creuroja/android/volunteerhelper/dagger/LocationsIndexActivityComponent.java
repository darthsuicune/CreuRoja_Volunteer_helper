package net.creuroja.android.volunteerhelper.dagger;


import net.creuroja.android.volunteerhelper.domain.dagger.AccountManagerModule;
import net.creuroja.android.volunteerhelper.domain.dagger.SharedPreferencesModule;
import net.creuroja.android.volunteerhelper.locations.list.LocationsIndexActivity;

import dagger.Component;

@Component(modules = {LocationsIndexActivityModule.class, AccountManagerModule.class,
					  SharedPreferencesModule.class})
public interface LocationsIndexActivityComponent {
	void inject(LocationsIndexActivity activity);
}
