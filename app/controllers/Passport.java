package controllers;

import java.util.*;
import play.data.validation.*;
import play.mvc.*;
import models.*;

import notifiers.*;
import play.Logger;
import utils.*;

public class Passport extends Application {

	public static void register() {
		render();
	}

	public static void saveUser(User user, String verifyPassword) {
		// input check
		validation.required(user.email);
		validation.email(user.email);
		validation.min(user.password,6);
		if (validation.hasErrors()) {
			Logger.info("用户注册输入有错误");
			render("@register");
		}
	
		// 此邮件是否被注册过	
		User userTemp=User.find("byEmailAndDeleted",user.email,false).first();
		if (userTemp != null ) {
			flash.error("此邮件己被注册");
		        render("@register");	
		}

		user.uuid = UUIDUtil.generate();
		user.create();

		Logger.info("register" + user.activated);

		// 放致session中
		session.put("user", user.email);

		// 发送激活邮件
		Mails.activate(user);
		flash.success("己成功注册激活邮件至您邮件中:" + user.email);
		Passport.notactivate(user.email);
	}

	public static void activate(String uuid) {

		Logger.info("用户开始进行激活,uuid" + uuid);
		User user = User.find("uuid", uuid).first();
		Logger.info("start activate:" + user.activated + "id:" + user.id);
		if (user != null) {
			// 激活成功
			user.activated = true;
			user.save();
			Logger.info("user id:" + user.id);
			render("@activatesuccess");

		} else {
			Application.index();
		}
	}

	public static void activatesucess() {
		render();
	}

	public static void notactivate(String email) {
		render(email);
	}

	public static void login() {
		render();
	}

	public static void logout() {
		session.clear();
		Application.index();
	}

	public static void isLogin(String email, String password) {
		User user = User.find("byEmailAndPasswordAndDeleted", email, password,
				false).first();
		// 用户名or 密码错误
		if (user == null) {
			render("@login");
		} else {

			// 未激活
			if (user.activated == false) {
				session.put("user", user.email);
				// 跳至激活页面
				render("@notactivate", email);
			}

			session.put("user", user.email);
			Application.index();
		}
	}
}
