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
		List<Send> sends = Send.find("isShow", true).fetch(12);
		render(sends);
	}

	public static void search(String yx) {
		List<Send> sends = Send.find("toEmail", yx).fetch();
		render("@wallet", sends);
	}
}
