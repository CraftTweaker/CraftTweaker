/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package stanhebben.zenscript.util;

import stanhebben.zenscript.expression.Expression;
import stanhebben.zenscript.type.ZenType;
import stanhebben.zenscript.type.natives.IJavaMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Stanneke
 */
public class StringUtil {
    private StringUtil() {
    }

    public static String join(String[] values, String separator) {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (String value : values) {
            if (first) {
                first = false;
            } else {
                result.append(separator);
            }
            result.append(value);
        }
        return result.toString();
    }

    public static String join(Iterable<String> values, String separator) {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (String value : values) {
            if (first) {
                first = false;
            } else {
                result.append('.');
            }
            result.append(value);
        }
        return result.toString();
    }

    /**
     * If a set of methods is available and none matches, this method creates a
     * suitable message.
     *
     * @param methods   matching methods
     * @param arguments calling arguments
     * @return return value
     */
    public static String methodMatchingError(List<IJavaMethod> methods, Expression... arguments) {
        if (methods.isEmpty()) {
            return "no method with that name available";
        } else {
            StringBuilder message = new StringBuilder();
            if (methods.size() == 1) {
                message.append("a method ");
            } else {
                message.append(methods.size()).append(" methods ");
            }
            message.append("available but none matches the parameters (");
            boolean first = true;
            for (Expression value : arguments) {
                if (first) {
                    first = false;
                } else {
                    message.append(", ");
                }
                message.append(value.getType().toString());
            }
            message.append(")");
            message.append("\nAvailable methods:\n");
            methods.forEach(me -> {
                boolean firstVal = true;
                for (ZenType type : me.getParameterTypes()) {
                    if (firstVal) {
                        firstVal = false;
                    } else {
                        message.append(", ");
                    }
                    message.append(type.toJavaClass().getSimpleName());
                }
                message.append("\n");
            });
            return message.toString();
        }
    }

    /**
     * Splits a string in parts, given a specified delimiter.
     *
     * @param value     string to be split
     * @param delimiter delimiter
     * @return
     */
    public static String[] split(String value, char delimiter) {
        if (value == null)
            return null;

        ArrayList<String> result = new ArrayList<String>();
        int start = 0;
        outer:
        for (int i = 0; i < value.length(); i++) {
            if (value.charAt(i) == delimiter) {
                result.add(value.substring(start, i));
                start = i + 1;
            }
        }
        result.add(value.substring(start));
        return result.toArray(new String[result.size()]);
    }

    /**
     * Processes a doc comment into paragraphs.
     *
     * @param value input value
     * @return output paragraphs
     */
    public static String[] splitParagraphs(String value) {
        String[] lines = split(value, '\n');
        for (int i = 0; i < lines.length; i++) {
            lines[i] = lines[i].trim();
        }
        ArrayList<String> output = new ArrayList<String>();
        StringBuilder current = new StringBuilder();
        for (int i = 0; i < lines.length; i++) {
            if (lines[i].length() == 0) {
                if (current.length() > 0) {
                    output.add(current.toString());
                    current = new StringBuilder();
                }
            } else {
                current.append(' ').append(lines[i]);
            }
        }
        if (current.length() > 0) {
            output.add(current.toString());
        }

        return output.toArray(new String[output.size()]);
    }

    // ########################
    // ### String utilities ###
    // ########################

    /**
     * unescape_perl_string()
     * <p>
     * Tom Christiansen <tchrist@perl.com> Sun Nov 28 12:55:24 MST 2010
     * <p>
     * It's completely ridiculous that there's no standard unescape_java_string
     * function. Since I have to do the damn thing myself, I might as well make
     * it halfway useful by supporting things Java was too stupid to consider in
     * strings:
     * <p>
     * => "?" items are additions to Java string escapes but normal in Java
     * regexes
     * <p>
     * => "!" items are also additions to Java regex escapes
     * <p>
     * Standard singletons: ?\a ?\e \f \n \r \t
     * <p>
     * NB: \b is unsupported as backspace so it can pass-through to the regex
     * translator untouched; I refuse to make anyone doublebackslash it as
     * doublebackslashing is a Java idiocy I desperately wish would die out.
     * There are plenty of other ways to write it:
     * <p>
     * \cH, \12, \012, \x08 \x{8}, \u0008, \U00000008
     * <p>
     * Octal escapes: \0 \0N \0NN \N \NN \NNN Can range up to !\777 not \377
     * <p>
     * TODO: add !\o{NNNNN} last Unicode is 4177777 maxint is 37777777777
     * <p>
     * Control chars: ?\cX Means: ord(X) ^ ord('@')
     * <p>
     * Old hex escapes: \xXX unbraced must be 2 xdigits
     * <p>
     * Perl hex escapes: !\x{XXX} braced may be 1-8 xdigits NB: proper Unicode
     * never needs more than 6, as highest valid codepoint is 0x10FFFF, not
     * maxint 0xFFFFFFFF
     * <p>
     * Lame Java escape: \[IDIOT JAVA PREPROCESSOR]uXXXX must be exactly 4
     * xdigits;
     * <p>
     * I can't write XXXX in this comment where it belongs because the damned
     * Java Preprocessor can't mind its own business. Idiots!
     * <p>
     * Lame Python escape: !\UXXXXXXXX must be exactly 8 xdigits
     * <p>
     * TODO: Perl translation escapes: \Q \U \L \E \[IDIOT JAVA PREPROCESSOR]u
     * \l These are not so important to cover if you're passing the result to
     * Pattern.compile(), since it handles them for you further downstream. Hm,
     * what about \[IDIOT JAVA PREPROCESSOR]u?
     *
     * @param oldstr
     * @return
     */
    public static String unescapeString(String oldstr) {
        if ((oldstr.charAt(0) != '"' || oldstr.charAt(oldstr.length() - 1) != '"')
                && (oldstr.charAt(0) != '\'' || oldstr.charAt(oldstr.length() - 1) != '\'')) {
            // TODO: error
            // throw new TweakerExecuteException("Not a valid string constant: "
            // + oldstr);
        }
        oldstr = oldstr.substring(1, oldstr.length() - 1);

		/*
         * In contrast to fixing Java's broken regex charclasses, this one need
		 * be no bigger, as unescaping shrinks the string here, where in the
		 * other one, it grows it.
		 */

        StringBuilder newstr = new StringBuilder(oldstr.length());

        boolean saw_backslash = false;

        for (int i = 0; i < oldstr.length(); i++) {
            int cp = oldstr.codePointAt(i);
            if (oldstr.codePointAt(i) > Character.MAX_VALUE) {
                i++;
                /**** WE HATES UTF-16! WE HATES IT FOREVERSES!!! ****/
            }

            if (!saw_backslash) {
                if (cp == '\\') {
                    saw_backslash = true;
                } else {
                    newstr.append(Character.toChars(cp));
                }
                continue; /* switch */
            }

            if (cp == '\\') {
                saw_backslash = false;
                newstr.append('\\');
                newstr.append('\\');
                continue; /* switch */
            }

            switch (cp) {

                case 'r':
                    newstr.append('\r');
                    break; /* switch */

                case 'n':
                    newstr.append('\n');
                    break; /* switch */

                case 'f':
                    newstr.append('\f');
                    break; /* switch */

				/* PASS a \b THROUGH!! */
                case 'b':
                    newstr.append("\\b");
                    break; /* switch */

                case 't':
                    newstr.append('\t');
                    break; /* switch */

                case 'a':
                    newstr.append('\007');
                    break; /* switch */

                case 'e':
                    newstr.append('\033');
                    break; /* switch */

				/*
                 * A "control" character is what you get when you xor its
				 * codepoint with '@'==64. This only makes sense for ASCII, and
				 * may not yield a "control" character after all.
				 * 
				 * Strange but true: "\c{" is ";", "\c}" is "=", etc.
				 */
                case 'c': {
                    if (++i == oldstr.length()) {
                        // TODO: error
                        // throw new TweakerExecuteException("trailing \\c");
                    }
                    cp = oldstr.codePointAt(i);
					/*
					 * don't need to grok surrogates, as next line blows them up
					 */
                    if (cp > 0x7f) {
                        // TODO: error
                        // throw new TweakerExecuteException(
                        // "expected ASCII after \\c");
                    }
                    newstr.append(Character.toChars(cp ^ 64));
                    break; /* switch */
                }

                case '8':
                case '9':
                    // TODO: error
                    // throw new TweakerExecuteException("illegal octal digit");
					/* NOTREACHED */

					/*
					 * may be 0 to 2 octal digits following this one so back up
					 * one for fallthrough to next case; unread this digit and
					 * fall through to next case.
					 */
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                    --i;
					/* FALLTHROUGH */

					/*
					 * Can have 0, 1, or 2 octal digits following a 0 this
					 * permits larger values than octal 377, up to octal 777.
					 */
                case '0': {
                    if (i + 1 == oldstr.length()) {
						/* found \0 at end of string */
                        newstr.append(Character.toChars(0));
                        break; /* switch */
                    }
                    i++;
                    int digits = 0;
                    int j;
                    for (j = 0; j <= 2; j++) {
                        if (i + j == oldstr.length()) {
                            break; /* for */
                        }
						/* safe because will unread surrogate */
                        int ch = oldstr.charAt(i + j);
                        if (ch < '0' || ch > '7') {
                            break; /* for */
                        }
                        digits++;
                    }
                    if (digits == 0) {
                        --i;
                        newstr.append('\0');
                        break; /* switch */
                    }
                    int value = 0;
                    try {
                        value = Integer
                                .parseInt(oldstr.substring(i, i + digits), 8);
                    } catch (NumberFormatException nfe) {
                        // TODO: error
                        // throw new TweakerExecuteException(
                        // "invalid octal value for \\0 escape");
                    }
                    newstr.append(Character.toChars(value));
                    i += digits - 1;
                    break; /* switch */
                } /* end case '0' */

                case 'x': {
                    if (i + 2 > oldstr.length()) {
                        // TODO: error
                        // throw new TweakerExecuteException(
                        // "string too short for \\x escape");
                    }
                    i++;
                    boolean saw_brace = false;
                    if (oldstr.charAt(i) == '{') {
						/* ^^^^^^ ok to ignore surrogates here */
                        i++;
                        saw_brace = true;
                    }
                    int j;
                    for (j = 0; j < 8; j++) {

                        if (!saw_brace && j == 2) {
                            break; /* for */
                        }

						/*
						 * ASCII test also catches surrogates
						 */
                        int ch = oldstr.charAt(i + j);
                        if (ch > 127) {
                            // TODO: error
                            // throw new TweakerExecuteException(
                            // "illegal non-ASCII hex digit in \\x escape");
                        }

                        if (saw_brace && ch == '}') {
                            break; /* for */
                        }

                        if (!((ch >= '0' && ch <= '9') || (ch >= 'a' && ch <= 'f') || (ch >= 'A' && ch <= 'F'))) {
                            // TODO: error
                            // throw new TweakerExecuteException(String.format(
                            // "illegal hex digit #%d '%c' in \\x", ch, ch));
                        }

                    }
                    if (j == 0) {
                        // TODO: error
                        // throw new TweakerExecuteException(
                        // "empty braces in \\x{} escape");
                    }
                    int value = 0;
                    try {
                        value = Integer.parseInt(oldstr.substring(i, i + j), 16);
                    } catch (NumberFormatException nfe) {
                        // TODO: error
                        // throw new TweakerExecuteException(
                        // "invalid hex value for \\x escape");
                    }
                    newstr.append(Character.toChars(value));
                    if (saw_brace) {
                        j++;
                    }
                    i += j - 1;
                    break; /* switch */
                }

                case 'u': {
                    if (i + 4 > oldstr.length()) {
                        // TODO: error
                        // throw new TweakerExecuteException(
                        // "string too short for \\u escape");
                    }
                    i++;
                    int j;
                    for (j = 0; j < 4; j++) {
						/* this also handles the surrogate issue */
                        if (oldstr.charAt(i + j) > 127) {
                            // TODO: error
                            // throw new TweakerExecuteException(
                            // "illegal non-ASCII hex digit in \\u escape");
                        }
                    }
                    int value = 0;
                    try {
                        value = Integer.parseInt(oldstr.substring(i, i + j), 16);
                    } catch (NumberFormatException nfe) {
                        // TODO: error
                        // throw new TweakerExecuteException(
                        // "invalid hex value for \\u escape");
                    }
                    newstr.append(Character.toChars(value));
                    i += j - 1;
                    break; /* switch */
                }

                case 'U': {
                    if (i + 8 > oldstr.length()) {
                        // TODO: error
                        // throw new TweakerExecuteException(
                        // "string too short for \\U escape");
                    }
                    i++;
                    int j;
                    for (j = 0; j < 8; j++) {
						/* this also handles the surrogate issue */
                        if (oldstr.charAt(i + j) > 127) {
                            // TODO: error
                            // throw new TweakerExecuteException(
                            // "illegal non-ASCII hex digit in \\U escape");
                        }
                    }
                    int value = 0;
                    try {
                        value = Integer.parseInt(oldstr.substring(i, i + j), 16);
                    } catch (NumberFormatException nfe) {
                        // TODO: error
                        // throw new TweakerExecuteException(
                        // "invalid hex value for \\U escape");
                    }
                    newstr.append(Character.toChars(value));
                    i += j - 1;
                    break; /* switch */
                }

                default:
                    newstr.append('\\');
                    newstr.append(Character.toChars(cp));
					/*
					 * say(String.format(
					 * "DEFAULT unrecognized escape %c passed through", cp));
					 */
                    break; /* switch */

            }
            saw_backslash = false;
        }

		/* weird to leave one at the end */
        if (saw_backslash) {
            newstr.append('\\');
        }

        return newstr.toString();
    }

    /*
     * Return a string "U+XX.XXX.XXXX" etc, where each XX set is the xdigits of
     * the logical Unicode code point. No bloody brain-damaged UTF-16 surrogate
     * crap, just true logical characters.
     */
    private static String uniplus(String s) {
        if (s.length() == 0) {
            return "";
        }
		/* This is just the minimum; sb will grow as needed. */
        StringBuilder sb = new StringBuilder(2 + 3 * s.length());
        sb.append("U+");
        for (int i = 0; i < s.length(); i++) {
            sb.append(String.format("%X", s.codePointAt(i)));
            if (s.codePointAt(i) > Character.MAX_VALUE) {
                i++;
                /**** WE HATES UTF-16! WE HATES IT FOREVERSES!!! ****/
            }
            if (i + 1 < s.length()) {
                sb.append(".");
            }
        }
        return sb.toString();
    }
}
