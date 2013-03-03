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
		System.out.println("user.email" + user.email);
		List<Send> sends = Send.find("toEmail", user.email).fetch();
		render("@receive", sends);
	}

	public static void inbox() {
		List<Send> sends = Send.findAll();
		for (Send send : sends) {
		}
		render(sends);
	}
}
