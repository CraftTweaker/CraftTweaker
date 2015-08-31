/*
 * This file is subject to the license.txt file in the main folder
 * of this project.
 */

package stanhebben.zenscript.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author Stan Hebben
 */
public class NFA {
	public static final int NOFINAL = Integer.MIN_VALUE;
	public static final int EPSILON = Integer.MIN_VALUE + 1;

	private final NFAState initial;

	private HashMap<NodeSet, DFA.DFAState> converted;

	/**
	 * Creates a new NFA with the specified initial state.
	 *
	 * @param initial initial state
	 */
	public NFA(NFAState initial) {
		this.initial = initial;
	}

	/**
	 * Creates a new NFA from the specified regular expression.
	 *
	 * Note: the regular expression implementation is not complete. Shorthand
	 * character classes (\s, \w, \d), hexadecimal and unicode escape sequences
	 * and unicode properties are not implemented.
	 * 
	 * Anchors, lazy plus operators, lookbehind and lookforward are not
	 * implemented since they cannot be implemented in an NFA.
	 *
	 * @param regexp regular expression
	 */
	public NFA(String regexp) {
		Partial main = processRegExp(new CharStream(regexp));
		initial = new NFAState();
		initial.addTransition(main.tailLabel, main.tail);
		main.head.setFinal(1);
	}

	/**
	 * Converts an array of regular expressions to an NFA. Each regular
	 * expression can have its own final class. The length of both arrays must
	 * match.
	 *
	 * @param regexp regular expression array
	 * @param finals final classes
	 */
	public NFA(String[] regexp, int[] finals) {
		initial = new NFAState();

		Partial[] partials = new Partial[regexp.length];
		for (int i = 0; i < regexp.length; i++) {
			partials[i] = processRegExp(new CharStream(regexp[i]));
			partials[i].head.setFinal(finals[i]);
			initial.addTransition(partials[i].tailLabel, partials[i].tail);
		}
	}

	/**
	 * Converts this NFA to a DFA. The resulting DFA is not optimal. If the NFA
	 * is ambiguous, the token with lowest value will receive preference.
	 *
	 * @return this NFA as DFA
	 */
	public DFA toDFA() {
		converted = new HashMap<NodeSet, DFA.DFAState>();
		HashSet<NFAState> closure = new HashSet<NFAState>();
		this.initial.closure(closure);
		DFA.DFAState init = convert(new NodeSet(closure));

		DFA dfs = new DFA(init);
		return dfs;
	}

	// //////////////////
	// Private methods
	// //////////////////

	/* Converts a set of possible states to a DFA state. */
	private DFA.DFAState convert(NodeSet nodes) {
		if (!converted.containsKey(nodes)) {
			DFA.DFAState node = new DFA.DFAState();
			converted.put(nodes, node);

			HashSet<Integer> edgeSet = new HashSet<Integer>();
			for (NFAState n : nodes.nodes) {
				n.alphabet(edgeSet);
			}
			for (int i : edgeSet) {
				HashSet<NFAState> edge = new HashSet<NFAState>();
				for (NFAState n : nodes.nodes) {
					n.edge(i, edge);
				}
				NodeSet set = new NodeSet(edge);
				node.addTransition(i, convert(set));
			}
			int finalCode = DFA.NOFINAL;
			for (NFAState n : nodes.nodes) {
				if (n.finalCode != NOFINAL) {
					if (finalCode != DFA.NOFINAL) {
						finalCode = Math.min(n.finalCode, finalCode);
					} else {
						finalCode = n.finalCode;
					}
				}
			}
			node.setFinal(finalCode);

			return node;
		}
		return converted.get(nodes);
	}

	/* Processes a regular expression */
	private Partial processRegExp(CharStream stream) {
		Partial partial = processRegExp0(stream);
		if (stream.optional('|')) {
			ArrayList<Partial> partials = new ArrayList<Partial>();
			partials.add(partial);
			partials.add(processRegExp0(stream));
			while (stream.optional('|')) {
				partials.add(processRegExp0(stream));
			}
			NFAState head = new NFAState();
			NFAState tail = new NFAState();
			for (Partial p : partials) {
				tail.addTransition(p.tailLabel, p.tail);
				p.head.addTransition(EPSILON, head);
			}
			return new Partial(EPSILON, tail, head);
		} else {
			return partial;
		}
	}

	/* Processes an element of an alternation clause */
	private Partial processRegExp0(CharStream stream) {
		Partial partial = processRegExp1(stream);
		while (!stream.peek(')') && !stream.peek('|') && stream.hasMore()) {
			Partial partial2 = processRegExp1(stream);
			partial.head.addTransition(partial2.tailLabel, partial2.tail);
			partial = new Partial(partial.tailLabel, partial.tail, partial2.head);
		}
		return partial;
	}

	/* Processes a partial with optional repetition */
	private Partial processRegExp1(CharStream stream) {
		Partial partial = processPartial(stream);
		if (stream.optional('*')) {
			NFAState node = new NFAState();
			partial.head.addTransition(EPSILON, node);
			node.addTransition(partial.tailLabel, partial.tail);
			return new Partial(EPSILON, node, node);
		} else if (stream.optional('+')) {
			NFAState node = new NFAState();
			partial.head.addTransition(EPSILON, node);
			node.addTransition(partial.tailLabel, partial.tail);
			return new Partial(partial.tailLabel, partial.tail, node);
		} else if (stream.optional('?')) {
			NFAState node = new NFAState();
			node.addTransition(EPSILON, partial.head);
			node.addTransition(partial.tailLabel, partial.tail);
			return new Partial(EPSILON, node, partial.head);
		} else if (stream.optional('{')) {
			int amount = processInt(stream);
			if (amount < 0)
				throw new IllegalArgumentException("Repitition count expected");
			if (stream.optional(',')) {
				int amount2 = processInt(stream);
				stream.required('}');
				if (amount2 < 0) {
					// unbounded
					Partial[] duplicates = new Partial[amount];
					for (int i = 0; i < duplicates.length - 1; i++) {
						duplicates[i] = partial.duplicate();
					}
					duplicates[amount - 1] = partial;
					for (int i = 0; i < duplicates.length - 1; i++) {
						duplicates[i].head.addTransition(duplicates[i + 1].tailLabel, duplicates[i + 1].tail);
					}
					duplicates[amount - 1].head.addTransition(duplicates[amount - 1].tailLabel, duplicates[amount - 1].tail);
					return new Partial(duplicates[0].tailLabel, duplicates[0].tail, duplicates[amount - 1].head);
				} else {
					Partial[] duplicates = new Partial[amount2];
					for (int i = 0; i < duplicates.length - 1; i++) {
						duplicates[i] = partial.duplicate();
					}
					duplicates[amount2 - 1] = partial;
					for (int i = 0; i < duplicates.length - 1; i++) {
						duplicates[i].head.addTransition(duplicates[i + 1].tailLabel, duplicates[i + 1].tail);
					}
					for (int i = amount; i < amount2; i++) {
						if (i == 0) {
							/*
							 * insert additional node before the chain because
							 * minimal repeat is 0
							 */
							NFAState additional = new NFAState();
							additional.addTransition(duplicates[0].tailLabel, duplicates[0].tail);
							additional.addTransition(EPSILON, duplicates[amount2 - 1].head);
							duplicates[0].tailLabel = EPSILON;
							duplicates[0].tail = additional;
						} else {
							duplicates[i - 1].head.addTransition(EPSILON, duplicates[amount2 - 1].head);
						}
					}
					return new Partial(duplicates[0].tailLabel, duplicates[0].tail, duplicates[amount2 - 1].head);
				}
			} else {
				stream.required('}');

				Partial[] duplicates = new Partial[amount];
				for (int i = 0; i < duplicates.length - 1; i++) {
					duplicates[i] = partial.duplicate();
				}
				duplicates[amount - 1] = partial;
				for (int i = 0; i < duplicates.length - 1; i++) {
					duplicates[i].head.addTransition(duplicates[i + 1].tailLabel, duplicates[i + 1].tail);
				}
				return new Partial(duplicates[0].tailLabel, duplicates[0].tail, duplicates[amount - 1].head);
			}
		} else {
			return partial;
		}
	}

	/*
	 * Processes a part of a regular expression, which can be a character,
	 * expression between brackets, a dot or a character list
	 */
	private Partial processPartial(CharStream stream) {
		if (stream.optional('(')) {
			Partial result = processRegExp(stream);
			stream.required(')');
			return result;
		} else if (stream.optional('[')) {
			NFAState head = new NFAState();
			NFAState tail = new NFAState();

			IteratorI iter = processCharList(stream).iterator();
			while (iter.hasNext()) {
				tail.addTransition(iter.next(), head);
			}
			stream.required(']');
			return new Partial(EPSILON, tail, head);
		} else if (stream.optional('.')) {
			NFAState head = new NFAState();
			NFAState tail = new NFAState();

			for (int i = 0; i <= 256; i++) {
				tail.addTransition(i, head);
			}
			return new Partial(EPSILON, tail, head);
		} else {
			NFAState node = new NFAState();
			return new Partial(processChar(stream), node, node);
		}
	}

	/* Processes a character list */
	private HashSetI processCharList(CharStream stream) {
		boolean invert = stream.optional('^');
		HashSetI base = new HashSetI();
		do {
			processCharPartial(base, stream);
		} while (!stream.peek(']'));
		if (invert) {
			HashSetI result = new HashSetI();
			for (int i = 0; i <= 256; i++) {
				if (!base.contains(i))
					result.add(i);
			}
			return result;
		} else {
			return base;
		}
	}

	/*
	 * Processes a character partial, which can be a single character or a range
	 * of characters.
	 */
	private void processCharPartial(HashSetI out, CharStream stream) {
		if (stream.optional('.')) {
			for (int i = 0; i <= 256; i++) {
				out.add(i);
			}
		} else {
			int from = processChar(stream);
			if (stream.optional('-')) {
				int to = processChar(stream);
				for (int i = from; i <= to; i++) {
					out.add(i);
				}
			} else {
				out.add(from);
			}
		}
	}

	/* Processes a single character */
	private int processChar(CharStream stream) {
		if (stream.optional('\\')) {
			if (stream.optional('e'))
				return -1;
			if (stream.optional('r'))
				return '\r';
			if (stream.optional('n'))
				return '\n';
			if (stream.optional('t'))
				return '\t';
			if (stream.optional('['))
				return '[';
			if (stream.optional(']'))
				return ']';
			if (stream.optional('('))
				return '(';
			if (stream.optional(')'))
				return ')';
			if (stream.optional('.'))
				return '.';
			if (stream.optional('+'))
				return '+';
			if (stream.optional('-'))
				return '-';
			if (stream.optional('\\'))
				return '\\';
			if (stream.optional('{'))
				return '{';
			if (stream.optional('}'))
				return '}';
			if (stream.optional('?'))
				return '?';
			if (stream.optional('*'))
				return '*';
			if (stream.optional('~'))
				return '~';
			if (stream.optional('|'))
				return '|';
			if (stream.optional('^'))
				return '^';
			throw new IllegalArgumentException("Invalid character: " + stream.next());
		} else {
			if (stream.peek('[')
					|| stream.peek(']')
					|| stream.peek('(')
					|| stream.peek(')')
					|| stream.peek('{')
					|| stream.peek('}')
					|| stream.peek('.')
					|| stream.peek('-')
					|| stream.peek('+')
					|| stream.peek('?')
					|| stream.peek('*')) {
				throw new IllegalArgumentException("Invalid character: " + stream.next());
			} else {
				return stream.next();
			}
		}
	}

	/*
	 * Processes an optional integer, returns -1 if the next character do not
	 * represent an integer
	 */
	private int processInt(CharStream stream) {
		int data = stream.optional('0', '9') - '0';
		if (data < 0)
			return -1;
		char ch = stream.optional('0', '9');
		while (ch != 0) {
			data = data * 10 + (ch - '0');
			ch = stream.optional('0', '9');
		}
		return data;
	}

	// ///////////////////////
	// Public inner classes
	// ///////////////////////

	/**
	 * Represents an NFA state.
	 */
	public static class NFAState {
		private static int counter = 1;

		private ArrayList<Transition> transitions;
		private ArrayList<NFAState> closure;
		private int index;
		private int finalCode = NOFINAL;

		/**
		 * Creates a new state.
		 */
		public NFAState() {
			transitions = new ArrayList<Transition>();
			index = counter++;
		}

		/**
		 * Adds a transition.
		 *
		 * @param label transition label
		 * @param next next state
		 */
		public void addTransition(int label, NFAState next) {
			transitions.add(new Transition(label, next));
		}

		/**
		 * Sets the final state of this state.
		 *
		 * @param finalCode
		 */
		public void setFinal(int finalCode) {
			if (this.finalCode == finalCode)
				return;
			this.finalCode = finalCode;

			for (Transition t : transitions) {
				if (t.label == EPSILON) {
					t.next.setFinal(finalCode);
				}
			}
		}

		// //////////////////
		// Private methods
		// //////////////////

		/**
		 * Determines the (partial) closure of this state.
		 *
		 * @param output output to store the closure in
		 */
		private void closure(HashSet<NFAState> output) {
			for (NFAState node : closure())
				output.add(node);
		}

		/**
		 * Calculates the (full) closure of this state.
		 *
		 * @return this state's closure
		 */
		private ArrayList<NFAState> closure() {
			if (closure == null) {
				HashSet<NFAState> tmp = new HashSet<NFAState>();
				tmp.add(this);
				for (Transition transition : transitions) {
					if (transition.label == EPSILON) {
						if (!tmp.contains(transition.next)) {
							tmp.add(transition.next);
							transition.next.closure(tmp);
						}
					}
				}
				closure = new ArrayList<NFAState>();
				for (NFAState node : tmp)
					closure.add(node);
			}
			return closure;
		}

		/**
		 * Calculates the possible set of states after a transition with the
		 * specified value
		 *
		 * @param label transition label
		 * @param output possible set of states (out)
		 */
		private void edge(int label, HashSet<NFAState> output) {
			for (Transition transition : transitions) {
				if (transition.label == label) {
					if (!output.contains(transition.next)) {
						output.add(transition.next);
						transition.next.closure(output);
					}
				}
			}
		}

		/**
		 * Calculates the alphabet of this state.
		 *
		 * @param output alphabet (out)
		 */
		private void alphabet(HashSet<Integer> output) {
			for (NFAState node : closure()) {
				for (Transition t : node.transitions) {
					if (t.label != EPSILON) {
						output.add(t.label);
					}
				}
			}
		}

		// //////////////
		// Inner class
		// //////////////

		/* Represents a transition */
		private class Transition {
			private int label;
			private NFAState next;

			public Transition(int input, NFAState next) {
				this.label = input;
				this.next = next;
			}
		}
	}

	/* Hashable set of nodes */
	private class NodeSet {
		private NFAState[] nodes;

		public NodeSet(HashSet<NFAState> nodes) {
			this.nodes = new NFAState[nodes.size()];
			int i = 0;
			for (NFAState node : nodes)
				this.nodes[i++] = node;
			Arrays.sort(this.nodes, new Comparator<NFAState>() {
				public int compare(NFAState o1, NFAState o2) {
					return o1.index - o2.index;
				}
			});
		}

		@Override
		public int hashCode() {
			return Arrays.hashCode(nodes);
		}

		@Override
		public boolean equals(Object other) {
			if (other.getClass() != NodeSet.class)
				return false;
			return Arrays.equals(nodes, ((NodeSet) other).nodes);
		}
	}

	/**
	 * Contains a partially parsed regular expression. Has a head and a tail.
	 *
	 * The tail is a transition and thus has a label and a next node. The head
	 * is the 'final' state of the partial.
	 */
	private static class Partial {
		private int tailLabel;
		private NFAState tail;
		private NFAState head;

		/**
		 * Creates a new partial
		 * 
		 * @param tailLabel tail label
		 * @param tail tail node
		 * @param head head node
		 */
		public Partial(int tailLabel, NFAState tail, NFAState head) {
			this.tailLabel = tailLabel;
			this.tail = tail;
			this.head = head;
		}

		/**
		 * Duplicates the NFA of this partial.
		 *
		 * @return a duplicate of this partial
		 */
		public Partial duplicate() {
			HashMap<NFAState, NFAState> nodes = new HashMap<NFAState, NFAState>();

			Queue<NFAState> todo = new LinkedList<NFAState>();
			todo.add(tail);
			nodes.put(tail, new NFAState());
			while (!todo.isEmpty()) {
				NFAState node = todo.poll();
				for (NFAState.Transition t : node.transitions) {
					if (!nodes.containsKey(t.next)) {
						nodes.put(t.next, new NFAState());
						todo.add(t.next);
					}
					nodes.get(node).addTransition(t.label, nodes.get(t.next));
				}
			}

			return new Partial(tailLabel, nodes.get(tail), nodes.get(head));
		}
	}
}
