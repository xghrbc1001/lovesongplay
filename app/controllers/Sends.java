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

		if(validation.hasErrors()){
			flash.error("oops","错误");
			System.out.println("================= has errors");
			//	index();
			//render(email);
			// Application.render("@index",email);
			Application.index();
		} else {
			System.out.println("--------------no validation errors");
		}

		// render(email);	
		//	render("@send",email);
		toSend(email);

	}

	public static void toSend(String email) {
		Send send=new Send();
		send.toEmail=email;
		render("@send",send,email);
	}

	public static void send(Send send) {
                Logger.info("===========start send"+send.isShow);
		User user=connected();
                send.user= user;
		send.status=0;
		boolean bol=send.create();
		// render("@inbox");
		
		// 匿名发送邮件
		Mails.sendContent(send);

		// 跳转至收件箱
		Inboxs.inbox();
	}

        // 猜猜他是谁,跳至guess页面
	public static void toGuess(String uuid) {
		// Send send=Send.findById(uuid);
		Logger.info("uuid",uuid);
		Send send=Send.find("id",uuid).first();
		if(send==null ){
		// 没有
		Logger.info("没有此send");
}

		Logger.info("send:"+send);
		Logger.info("chance:"+ send.toEmail);
		render("@guess",send,uuid);	
	}

	public static void guess(String uuid,String email) {
		//Send send=Send.findById(uuid);
	
		Send send=Send.find("id",uuid).first();
		if(send==null ) {
			// 没有此邮件
}
		// check
 		// 猜机会己用完	
		if(send.chance < 1) {
			// message
		}

		if(email.equals(send.user.email)) {
			// 设置状态成功
			send.status=2;
			send.save();
		}else{
	          // 将猜机会减1
		  send.chance=send.chance-1;
		}

		
		render(send);
}

}
