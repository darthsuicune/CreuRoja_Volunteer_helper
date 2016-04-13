package net.creuroja.android.volunteerhelper.domain.sync;

import android.content.ContentProviderClient;
import android.content.ContentProviderOperation;
import android.net.Uri;
import android.os.Build;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.creuroja.android.volunteerhelper.domain.CreuRojaEndPoint;
import net.creuroja.android.volunteerhelper.domain.CreuRojaEndPointTest;
import net.creuroja.android.volunteerhelper.domain.locations.LocationsResponse;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class SyncPerformerTest {
	SyncPerformer performer;
	CreuRojaEndPoint server;

	@Before public void setUp() throws Exception {
		server = CreuRojaEndPointTest.getRetrofitEndPoint().create(CreuRojaEndPoint.class);
		performer = new SyncPerformer(server);
	}

	@Test public void initSyncRequestsTheResponseFromTheServerAndReceivesAProperAnswer()
			throws Exception {
		String token = obtainToken();
		Response<List<LocationsResponse>> response = performer.initSync(token, null);
		assertTrue(response.isSuccess());
	}

	private String obtainToken() throws IOException {
		String user = "usuario@prueba.com";
		String password = "123456";
		return server.login(user, password).execute().body().token;
	}

	@Test public void testStoreUpdates() throws Exception {
		List<LocationsResponse> responseList = createSomeLocationsResponse();
		ContentProviderClient client = mock(ContentProviderClient.class);
		Uri uri = mock(Uri.class);
		performer.storeUpdates(responseList, client, uri);
		verify(client).applyBatch(argThat(matchesAlistWith(10, 2)));
	}

	private Matcher<ArrayList<ContentProviderOperation>> matchesAlistWith(final int updates,
																		  final int deletes) {
		return new TypeSafeMatcher<ArrayList<ContentProviderOperation>>() {

			@Override protected boolean matchesSafely(ArrayList<ContentProviderOperation> items) {
				if (items.size() != (updates + deletes)) {
					return false;
				}
				int actives = 0, inactives = 0;
				for (ContentProviderOperation op : items) {
					if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && op.isUpdate()) {
						actives++;
					} else {
						inactives++;
					}
				}
				return (updates == actives && deletes == inactives);
			}

			@Override public void describeTo(Description description) {

			}
		};
	}

	private List<LocationsResponse> createSomeLocationsResponse() {
		//12 locations, 10 active 2 inactive
		String response = "[" +
						  "{\"id\":84,\"name\":\"Nostrum - Aribau\",\"description\":\"671 660 154\",\"address\":\"Carrer de Aribau, 139\",\"phone\":null,\"latitude\":41.3921,\"longitude\":2.15425,\"location_type\":\"nostrum\",\"active\":true,\"updated_at\":\"2015-04-23T15:05:04.474Z\",\"active_services\":[]}," +
						  "{\"id\":85,\"name\":\"Nostrum - París\",\"description\":\"671 840 348\",\"address\":\"Carrer de Aribau, 186\",\"phone\":null,\"latitude\":41.3936,\"longitude\":2.15256,\"location_type\":\"nostrum\",\"active\":true,\"updated_at\":\"2015-04-23T15:10:27.851Z\",\"active_services\":[]}," +
						  "{\"id\":114,\"name\":\"Oficina Comarcal de Berguedà\",\"description\":\"9\",\"address\":\"Carrer de Barcelona, 26\",\"phone\":null,\"latitude\":42.1003,\"longitude\":1.84579,\"location_type\":\"asamblea\",\"active\":true,\"updated_at\":\"2014-09-29T14:33:31.742Z\",\"active_services\":[]}," +
						  "{\"id\":115,\"name\":\"Oficina Local de Caldes dEstrac\",\"description\":\"9\",\"address\":\"Plaça de Vila, s/n, 4a\",\"phone\":null,\"latitude\":41.5689,\"longitude\":2.52475,\"location_type\":\"asamblea\",\"active\":true,\"updated_at\":\"2014-09-29T14:33:31.774Z\",\"active_services\":[]}," +
						  "{\"id\":116,\"name\":\"Oficina Local de Caldes - Palau - Sant Feliu\",\"description\":\"9\",\"address\":\"Carrer de Anselm Clavé, 5 bis\",\"phone\":null,\"latitude\":41.6288,\"longitude\":2.16748,\"location_type\":\"asamblea\",\"active\":true,\"updated_at\":\"2014-09-29T14:33:31.799Z\",\"active_services\":[]}," +
						  "{\"id\":117,\"name\":\"Oficina Local de Cardona\",\"description\":\"9\",\"address\":\"Passeig Mossèn Joan Riba, 1\",\"phone\":null,\"latitude\":41.9148,\"longitude\":1.67789,\"location_type\":\"asamblea\",\"active\":true,\"updated_at\":\"2014-09-29T14:33:31.806Z\",\"active_services\":[]}," +
						  "{\"id\":118,\"name\":\"Oficina Local de Castelldefels - Gavà - Begues\",\"description\":\"9\",\"address\":\"Avinguda 1 de Maig, 12\",\"phone\":null,\"latitude\":41.2822,\"longitude\":1.9764,\"location_type\":\"asamblea\",\"active\":true,\"updated_at\":\"2014-09-29T14:33:31.814Z\",\"active_services\":[]}," +
						  "{\"id\":119,\"name\":\"Oficina Comarcal de Cerdanyola - Ripollet - Montcada\",\"description\":\"9\",\"address\":\"Avinguda de la Creu Roja, 25-29\",\"phone\":null,\"latitude\":41.4941,\"longitude\":2.14949,\"location_type\":\"asamblea\",\"active\":true,\"updated_at\":\"2014-09-29T14:33:31.823Z\",\"active_services\":[]}," +
						  "{\"id\":120,\"name\":\"Oficina Local de Cornellà de Llobregat\",\"description\":\"9\",\"address\":\"Carrer de Rubió i Ors, 104\",\"phone\":null,\"latitude\":41.3538,\"longitude\":2.06844,\"location_type\":\"asamblea\",\"active\":false,\"updated_at\":\"2014-09-29T14:33:31.844Z\",\"active_services\":[]}," +
						  "{\"id\":121,\"name\":\"Oficina Local dEl Masnou - Alella - Teià\",\"description\":\"9\",\"address\":\"Carretera N-II, Km. 636\",\"phone\":null,\"latitude\":41.4793,\"longitude\":2.31924,\"location_type\":\"asamblea\",\"active\":false,\"updated_at\":\"2014-09-29T14:33:31.856Z\",\"active_services\":[]}," +
						  "{\"id\":122,\"name\":\"Oficina Comarcal dEsplugues de Llobregat - Sant Just Desvern\",\"description\":\"9\",\"address\":\"Carrer de Severo Ochoa, 14, local 2\",\"phone\":null,\"latitude\":41.3758,\"longitude\":2.10024,\"location_type\":\"asamblea\",\"active\":true,\"updated_at\":\"2014-09-29T14:33:31.863Z\",\"active_services\":[]}," +
						  "{\"id\":123,\"name\":\"Oficina Comarcal de Granollers\",\"description\":\"9\",\"address\":\"Carrer de Joan Prim, 38\",\"phone\":null,\"latitude\":41.6108,\"longitude\":2.28963,\"location_type\":\"asamblea\",\"active\":true,\"updated_at\":\"2014-09-29T14:33:31.871Z\",\"active_services\":[]}]";
		return new Gson().fromJson(response, new TypeToken<List<LocationsResponse>>() {
		}.getType());
	}

	@Test public void testLogError() throws Exception {

	}
}