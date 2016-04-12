package net.creuroja.android.volunteerhelper.domain.dagger;

import net.creuroja.android.volunteerhelper.domain.CreuRojaEndPoint;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ServerModule {
	@Provides CreuRojaEndPoint provideEndPoint() {
		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl("https://creuroja.net")
				.addConverterFactory(GsonConverterFactory.create())
				.build();
		return retrofit.create(CreuRojaEndPoint.class);
	}
}
