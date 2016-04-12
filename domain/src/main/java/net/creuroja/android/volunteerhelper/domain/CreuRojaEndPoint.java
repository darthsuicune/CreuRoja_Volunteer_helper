package net.creuroja.android.volunteerhelper.domain;


import net.creuroja.android.volunteerhelper.domain.locations.LocationsResponse;
import net.creuroja.android.volunteerhelper.domain.login.LoginResponse;
import net.creuroja.android.volunteerhelper.domain.volunteer_services.VolunteerService;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface CreuRojaEndPoint {
	@FormUrlEncoded
	@POST("sessions.json")
	Call<LoginResponse> login(@Field("email") String user, @Field("password") String password);

	@GET("locations.json")
	Call<List<LocationsResponse>> getLocations(@Header("Authorization") String authorization,
			@Query("updated_at") String lastUpdateTime);

	@GET("services.json")
	Call<List<VolunteerService>> getServices(@Header("Authorization") String authorization,
			@Query("updated_at") String lastUpdateTime);
}
