package com.blamejared.crafttweaker.api.zencode.impl.preprocessors;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.logger.LogLevel;
import com.blamejared.crafttweaker.api.zencode.IPreprocessor;
import com.blamejared.crafttweaker.api.zencode.PreprocessorMatch;
import com.blamejared.crafttweaker.api.zencode.impl.FileAccessSingle;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

public class SnippingPreprocessor implements IPreprocessor {
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

		for (PreprocessorMatch preprocessorMatch : preprocessorMatches) {
			if(preprocessorMatch.getContent().startsWith("start")) {
				starts.add(preprocessorMatch.getLine());
			} else if(preprocessorMatch.getContent().startsWith("end")) {
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
					fromChar = lineContent.indexOf("#" + getName() + " start");
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
