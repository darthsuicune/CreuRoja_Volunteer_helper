package net.creuroja.android.volunteerhelper.domain.db;

import ollie.OllieProvider;

public class ServicesProvider extends OllieProvider {
	public static final String DATABASE_NAME = "volunteerservices";
	public static final int DATABASE_VERSION = 1;

	@Override protected String getDatabaseName() {
		return DATABASE_NAME;
	}

	@Override protected int getDatabaseVersion() {
		return DATABASE_VERSION;
	}
}
