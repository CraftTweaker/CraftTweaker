package com.blamejared.crafttweaker_annotation_processors.processors.document.shared.util;

import org.intellij.lang.annotations.Language;
import org.jetbrains.annotations.Contract;

import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringReplaceUtil {
	@Contract("null, _, _ -> null")
	public static String replaceAll(String s, @Language("regexp") String regex, Function<String, String> mappingFunction) {
		if(s==null) {
			return null;
		}
		final Matcher matcher = Pattern.compile(regex).matcher(s);
		final StringBuffer stringBuffer = new StringBuffer();

		while(matcher.find()) {
			matcher.appendReplacement(stringBuffer, mappingFunction.apply(matcher.group()));
		}
		matcher.appendTail(stringBuffer);
		return stringBuffer.toString();
	}

	@Contract("null, _, _ -> null")
	public static String replaceWithMatcher(String s, @Language("regexp") String regex, Function<Matcher, String> mappingFunction) {
		if(s==null)
			return null;
		final Matcher matcher = Pattern.compile(regex).matcher(s);
		final StringBuffer stringBuffer = new StringBuffer();

		while(matcher.find()) {
			matcher.appendReplacement(stringBuffer, mappingFunction.apply(matcher));
		}
		matcher.appendTail(stringBuffer);
		return stringBuffer.toString();
	}
}
