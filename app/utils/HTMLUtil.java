package utils;
import java.util.UUID;

public class HTMLUtil {
   public static String enterToBr(String content) {
	return content.replaceAll("\r\n","<br />");
}
} 
