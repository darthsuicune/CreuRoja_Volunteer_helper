package net.creuroja.android.volunteerhelper.domain.db;

import android.net.Uri;
import android.provider.BaseColumns;

import net.creuroja.android.volunteerhelper.domain.locations.Location;

import ollie.OllieProvider;

public class ServicesContract {
	public static final class Locations implements BaseColumns {
		public static final String TABLE_NAME = "locations";
		public static final String LATITUDE = "latitude";
		public static final String LONGITUDE = "longitude";
		public static final String LOCATION_NAME = "location_name";
		public static final String REMOTE_ID = "remote_id";
		public static final String DESCRIPTION = "description";
		public static final String ADDRESS = "address";
		public static final String PHONE = "phone";
		public static final String UPDATED_AT = "updated_at";
		public static final String ACTIVE = "active";
		public static final String LOCATION_TYPE = "location_type";
		public static final Uri URI = OllieProvider.createUri(Location.class);
	}
	public static final class Services implements BaseColumns {
		public static final String TABLE_NAME = "services";
	}

	public static final class Users implements BaseColumns {
		public static final String TABLE_NAME = "users";
		public static final String USER_NAME = "user_name";
	}
}
