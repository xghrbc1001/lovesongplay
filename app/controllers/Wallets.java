package controllers;

import java.util.*;
import notifiers.*;
import play.Logger;
import play.data.validation.*;
import play.mvc.*;
import models.*;
import utils.*;

public class Wallets extends Application {

	public static void wallet() {
		List<Send> sends = Send.find("isShow", true).fetch(10);
		render(sends);
	}

	public static void search(String email) {
		List<Send> sends = Send.find("toEmail", email).fetch();
		render("@wallet", sends);
	}
}
