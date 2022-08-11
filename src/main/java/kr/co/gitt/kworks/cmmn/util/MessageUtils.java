package kr.co.gitt.kworks.cmmn.util;

import java.util.Locale;

import org.springframework.context.support.MessageSourceAccessor;

public class MessageUtils {

	private static MessageSourceAccessor messageSourceAccessor;
	
	public void setMessageSourceAccessor(MessageSourceAccessor messageSourceAccessor) {
		MessageUtils.messageSourceAccessor = messageSourceAccessor;
	}
	
	public static String getMessage(String key) {
		return messageSourceAccessor.getMessage(key, Locale.getDefault());
	}
	
}
