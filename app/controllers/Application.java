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
                        Logger.info("===========================Before");
			User user = connected();
			if(user != null) {
				renderArgs.put("user", user);
                        Logger.info("isactivated:"+user.activated);
			if(user.activated==false){
//		          Passport.notactivate(user.email);
			// Passport.activate(user.uuid);
			}
			}


		}

	static User connected() {
		if(renderArgs.get("user") != null) {
			return renderArgs.get("user", User.class);
		}
		String email = session.get("user");
		if(email != null) {
			return User.find("email", email).first();
		} 
		return null;
	}

	@Before(only={"Sends.send","Sends.startSend"})
	public static void  isUserfulUser() {
		User user=connected();
		Logger.info("Before=========================="+user);
		// 用户没有登录
		if(user==null)
		{
			Passport.login();
		}
		Logger.info("Before==========================user activated:"+user.activated);

		if(user.activated==false) {
			// Passport.activate(user.uuid);
			Passport.notactivate(user.email);
		}
	}
	public static void index() {
		render();
	}



}
