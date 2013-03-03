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
		user.uuid=UUIDUtil.generate();
		user.create();

		Logger.info("register" + user.activated);
		// 放致session中
		session.put("user", user.email);

		// System.out.println("session"+session.get("user"));
		flash.success("Welcome, " + user.email);

		// 发送激活邮件
                Mails.activate(user);
		 Passport.notactivate(user.email);
		render("@index");
	}
    public static void activate(String uuid){

       Logger.info("用户开始进行激活,uuid"+uuid);
       User user=User.find("uuid",uuid).first(); 
      Logger.info("start activate:"+user.activated+"id:"+user.id);
       if(user!=null ) {
          // 激活成功
	user.activated=true;
	// user.merge();
	// user.refresh();
	user.save();
        Logger.info("user id:"+user.id);
//        Application.index();
	render("@activatesuccess");

}else{
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
 //      Logger.info("=========logining"+user.email +"  :" + user.password);
//       Application.index();
}
	public static void logout() {
		session.clear();
		Application.index();
	}
    public static void isLogin(String email,String password) {
          User user=User.find("byEmailAndPasswordAndDeleted",email,password,false).first();
          // 用户名or 密码错误
	  if(user==null ) {
          render("@login");
	}
		else {


          // 用户是否激活
//          user=User.find("byEmailAndActivatedAndDeleted",email,false,false).first();

	  // 未激活
	  if(user.activated==false ){
		  session.put("user",user.email);
		  // 跳至激活页面
		  render("@notactivate",email);
	  } 

        session.put("user",user.email);
        // flash.success("Welcome," + user.email);
        Application.index();
}
}
}
