package controllers;

import java.util.*;
import play.data.validation.*;
import play.mvc.*;
import models.*;

import notifiers.*;
import play.Logger;
import utils.*;

public class Sends extends Application {

	public static void startSend(@Required @Email String email) {

		Application.isUserfulUser();

		if (validation.hasErrors()) {
			flash.error("oops", "错误");
			Application.index();
		}
		toSend(email);

	}

	public static void toSend(String email) {
		Send send = new Send();
		send.toEmail = email;
		render("@send", send, email);
	}

	public static void send(Send send) {
		User user = connected();
		send.user = user;
		send.status = 0;
		boolean bol = send.create();

		// 匿名发送邮件
		Mails.sendContent(send);

		// 跳转至收件箱
		Inboxs.inbox();
	}

	// 猜猜他是谁,跳至guess页面
	public static void toGuess(String uuid) {
		Logger.info("uuid", uuid);
		Send send = Send.find("id", uuid).first();
		if (send == null) {
			// 没有
			Logger.info("没有此send");
		}

		Logger.info("send:" + send);
		Logger.info("chance:" + send.toEmail);
		render("@guess", send, uuid);
	}

	public static void guess(String uuid, String email) {
		Send send = Send.find("id", uuid).first();
		if (send == null) {
			// 没有此邮件
			flash.error("没有此邮件");
		}
		// check
		// 猜机会己用完
		if (send.chance < 1) {
			send.status = -1;
			send.save();
			// message
			flash.error("每人只有三次机会哦,您的机会己用完");
		}

		if (email.equals(send.user.email)) {
			// 设置状态成功
			send.status = 2;
			send.save();
			flash.success("哇塞，恭喜，你猜中了，就是他：" + send.user.email);
		} else {
			// 将猜机会减1
			send.chance = send.chance - 1;
			send.status=1;
			send.save();
			flash.error("不正确，总共3次机会，你还有"+send.chance+"次机会");
		}

		render(send);
	}

}
