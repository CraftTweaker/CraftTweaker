package com.blamejared.crafttweaker.api.zencode.impl.preprocessors;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotations.Preprocessor;
import com.blamejared.crafttweaker.api.logger.LogLevel;
import com.blamejared.crafttweaker.api.zencode.IPreprocessor;
import com.blamejared.crafttweaker.api.zencode.PreprocessorMatch;
import com.blamejared.crafttweaker.api.zencode.impl.FileAccessSingle;
import net.minecraftforge.fml.ModList;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Predicate;

@Preprocessor
public class SnippingPreprocessor implements IPreprocessor {
	/**
	 * called whenever a Snip preprocessor is hit.<br>
	 * If any of these return true for the given String[] then everything up until the next {@code #snip end} will be snipped.
	 *
	 * The Predicates will test the remainder of the preprocessor call (everything after {@code #snip } up to the end of the line, split by one whitespace {@code " "}
	 */
	private final List<Predicate<String[]>> headers;

	public SnippingPreprocessor() {
		headers = new ArrayList<>(2);
		headers.add(s -> "start".equals(s[0]));
		headers.add(s -> s.length > 1 && "modnotloaded".equals(s[0]) && !ModList.get().isLoaded(s[1]));
	}


	@Override
	public String getName() {
		return "snip";
	}

	@Nullable
	@Override
	public String getDefaultValue() {
		return null;
	}

	@Override
	public int getPriority() {
		return 20;
	}

	@Override
	public boolean apply(@Nonnull FileAccessSingle file, @Nonnull List<PreprocessorMatch> preprocessorMatches) {
		final SortedSet<Integer> starts = new TreeSet<>();
		final SortedSet<Integer> ends = new TreeSet<>();

		outer:
		for (PreprocessorMatch preprocessorMatch : preprocessorMatches) {
			final String[] content = preprocessorMatch.getContent().split(" ");
			if(content.length < 1)
				continue;

			for (Predicate<String[]> header : headers) {
				if(header.test(content)) {
					starts.add(preprocessorMatch.getLine());
					continue outer;
				}
			}

			if("end".equals(content[0])) {
				ends.add(preprocessorMatch.getLine());
			} else {
				CraftTweakerAPI.log(LogLevel.WARNING, file.getFileName(), preprocessorMatch.getLine(), "Invalid snip arguments: " + preprocessorMatch.getLine());
			}
		}


		final ListIterator<String> iterator = file.getFileContents().listIterator();
		int line = 0;
		boolean snipping = false;
		boolean snippingStarted = false;

		while(iterator.hasNext()) {
			++line;
			if(starts.contains(line)) {
				snipping = true;
				snippingStarted = true;
			}

			final StringBuilder lineContent = new StringBuilder(iterator.next());

			if(snipping) {
				final int fromChar;
				if (snippingStarted) {
					fromChar = lineContent.indexOf("#" + getName());
					snippingStarted = false;
				} else fromChar = 0;

				final int toChar;
				if(ends.contains(line)) {
					final String statement = "#" + getName() + " end";
					toChar = lineContent.indexOf(statement) + statement.length();
					snipping = false;
				} else toChar = lineContent.length();


				final String replaceWith = String.join("", Collections.nCopies( toChar - fromChar, " "));
				iterator.set(lineContent.replace(fromChar, toChar, replaceWith).toString());
			}
		}

		return true;
	}
}
