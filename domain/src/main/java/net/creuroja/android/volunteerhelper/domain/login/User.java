package net.creuroja.android.volunteerhelper.domain.login;

public class User {
	public String name;
	public String surname;
	public String email;
	public String phone;
	public String accessToken;
	public String role;
	public String active;
	public String types;

	public User(String accessToken, String active, String email, String name, String phone,
				String role, String surname, String types) {
		this.accessToken = accessToken;
		this.active = active;
		this.email = email;
		this.name = name;
		this.phone = phone;
		this.role = role;
		this.surname = surname;
		this.types = types;
	}
}
