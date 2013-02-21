package controllers;
import java.util.*;
import play.data.validation.*;
import play.mvc.*;
import models.*;

public class Application extends Controller {

	@Before
		static void addUser() {
			User user = connected();
			if(user != null) {
				renderArgs.put("user", user);
			}

		}

	static User connected() {
		if(renderArgs.get("user") != null) {
			return renderArgs.get("user", User.class);
		}
		String email = session.get("user");
		System.out.println("^^^^^^^^^^"+email );
		if(email != null) {
			return User.find("email", email).first();
		} 
		return null;
	}
	public static void index() {
		render();
	}

	public static void register() {
		render();
	}

	public static void startSend(@Required @Email String email) {

		System.out.println("=============="+email);

		System.out.println(validation.hasErrors());

		if(validation.hasErrors()){
			flash.error("oops");
			System.out.println("================= has errors");
			//	index();
			//render(email);
			render("@index",email);
		} else {
			System.out.println("--------------no validation errors");
		}

		// render(email);	
		//	render("@send",email);
		toSend(email);

	}

	public static void toSend(String email) {
		System.out.println("send email: " + email);
		render("@send",email);
	}

	public static void send(Send send) {
		send.create();
		render("@inbox");
	}

	public static void guess(String email) {
		render();	
	}
	public static void saveUser(User user, String verifyPassword) {
		System.out.println("user:"+user);
		// validation.required(verifyPassword);
		// validation.equals(verifyPassword, user.password).message("Your password doesn't match");
		// System.out.println("=========================");
		// System.out.println(validation.hasErrors());
		// if(validation.hasErrors()) {
		// 	render("@register", user, verifyPassword);
		// }else{
		// 	System.out.println("=========================");
		// }
		user.create();
		System.out.println("user.email"+user.email);
		session.put("user", user.email);
		System.out.println("session"+session.get("user"));
		flash.success("Welcome, " + user.email);
		render("@index");
	}
	public static void logout() {
		session.clear();
		index();
	}

	public static void inbox() {
		User user=connected();
		System.out.println("user.email"+user.email);
		List sends=Send.findAll();
		System.out.println("sends:"+sends);
		render("@inbox",sends);
	}
}
