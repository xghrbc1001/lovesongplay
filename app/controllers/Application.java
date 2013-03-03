package controllers;

import java.util.*;
import notifiers.*;
import play.Logger;
import play.data.validation.*;
import play.mvc.*;
import models.*;
import utils.*;

public class Application extends Controller {

	@Before
	static void addUser() {
		User user = connected();
		if (user != null) {
			renderArgs.put("user", user);
		}

	}

	static User connected() {
		if (renderArgs.get("user") != null) {
			return renderArgs.get("user", User.class);
		}
		String email = session.get("user");
		if (email != null) {
			return User.find("email", email).first();
		}
		return null;
	}

	@Before(only = { "Sends.send", "Sends.startSend" })
	public static void isUserfulUser() {
		User user = connected();
		// 用户没有登录
		if (user == null) {
			Passport.login();
		}

		if (user.activated == false) {
			Passport.notactivate(user.email);
		}
	}

	public static void index() {
		render();
	}

}
