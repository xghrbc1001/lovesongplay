package controllers;

import java.util.*;
import notifiers.*;
import play.Logger;
import play.data.validation.*;
import play.mvc.*;
import models.*;
import utils.*;

public class Inboxs extends Application {
	public static void receive() {
		User user = connected();
		if (user==null) {
			Passport.login();
		}
		List<Send> sends = Send.find("toEmail", user.email).fetch();
		render("@receive", sends);
	}

	public static void inbox() {
		User user = connected();
		if (user==null) {
			Passport.login();
		}
		List<Send> sends = Send.find("user.email", user.email).fetch();
		List<Send> receives = Send.find("toEmail", user.email).fetch();
		render(sends,receives);
	}
}
