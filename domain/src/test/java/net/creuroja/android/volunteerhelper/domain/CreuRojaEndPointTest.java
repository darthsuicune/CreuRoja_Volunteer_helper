package net.creuroja.android.volunteerhelper.domain;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;

import net.creuroja.android.volunteerhelper.domain.locations.LocationsResponse;
import net.creuroja.android.volunteerhelper.domain.login.LoginResponse;
import net.creuroja.android.volunteerhelper.domain.volunteer_services.VolunteerService;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class CreuRojaEndPointTest {
	public static final String LOCAL_SERVER = "http://localhost/rails/";
	public static final String EMAIL = "usuario@prueba.com";
	public static final String PASS = "123456";

	CreuRojaEndPoint endPoint;

	@Before public void setUp() throws Exception {
		Retrofit retrofit = getRetrofitEndPoint();
		endPoint = retrofit.create(CreuRojaEndPoint.class);
	}

	public static Retrofit getRetrofitEndPoint() {HttpLoggingInterceptor logger =
			new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
				@Override public void log(String message) {
					System.out.println(("OkHttpLogging: " + message));
				}
			});
		logger.setLevel(HttpLoggingInterceptor.Level.BODY);
		GsonConverterFactory gson = GsonConverterFactory.create(new GsonBuilder()
				.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
				.create());
		OkHttpClient client = new OkHttpClient.Builder().addInterceptor(logger).build();
		return new Retrofit.Builder()
						.baseUrl(LOCAL_SERVER)
						.addConverterFactory(gson)
						.client(client)
						.build();

	}

	@Test public void testLogin() throws Exception {
		LoginResponse resp = getLoginResponse();
		assertNotNull(resp.token);
		assertNotNull(resp.user);
		assertNotNull(resp.user.email);
	}

	private LoginResponse getLoginResponse() throws IOException {
		return endPoint.login(EMAIL, PASS).execute().body();
	}

	@Test public void testGetLocations() throws Exception {
		List<LocationsResponse> resp = endPoint.getLocations(getToken(), "0").execute().body();
		assertNotNull(resp);
		assertTrue(resp.size() > 50);
	}

	private String getToken() throws IOException {
		return "Token token=" + getLoginResponse().token;
	}

	@Test public void testGetLocationsWithUpdateTime() throws Exception {
		List<LocationsResponse> resp =
				endPoint.getLocations(getToken(), "2015-01-16T20:43:00.000Z").execute().body();
		assertNotNull(resp);
		assertTrue(resp.size() < 50);
		assertTrue(resp.size() > 0);
	}

	@Test public void testGetServices() throws Exception {
		List<VolunteerService> resp = endPoint.getServices(getToken(), "0").execute().body();
		assertNotNull(resp);
	}
}