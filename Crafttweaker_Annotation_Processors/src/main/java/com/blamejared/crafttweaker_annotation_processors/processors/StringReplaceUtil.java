package com.blamejared.crafttweaker_annotation_processors.processors;

import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringReplaceUtil {
	public static String replaceAll(String s, String regex, Function<String, String> mappingFunction) {
		final Matcher matcher = Pattern.compile(regex).matcher(s);
		final StringBuffer stringBuffer = new StringBuffer();

		while(matcher.find()) {
			matcher.appendReplacement(stringBuffer, mappingFunction.apply(matcher.group()));
		}
		matcher.appendTail(stringBuffer);
		return stringBuffer.toString();
	}
}
