package notifiers;

import org.apache.commons.mail.*;
import play.*;
import play.mvc.*;
import java.util.*;
import models.*;

public class Mails extends Mailer {
	public static void activate(User user) {
		setSubject("邮件注册激活");
		addRecipient(user.email);
		setFrom("ijavamm@qq.com");
		// setFrom("JavaMM@qq.com"); // 报501错误
		send(user);
	}

	public static void sendContent(Send send) {
		setSubject("有人暗恋你");
		addRecipient(send.toEmail);
		setFrom("ijavamm@qq.com");
		// setFrom("JavaMM@qq.com"); // 报501错误
		send(send);
	}

	public static void lostPassword(User user) {
		String newpassword = user.password;
		setFrom("Robot <robot@thecompany.com>");
		setSubject("Your password has been reset");
		addRecipient(user.email);
		send(user, newpassword);
	}
}
