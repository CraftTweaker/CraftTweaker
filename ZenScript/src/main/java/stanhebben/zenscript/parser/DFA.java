/*
 * This file is subject to the license.txt file in the main folder
 * of this project.
 */

package stanhebben.zenscript.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Implements a DFA.
 *
 * @author Stan Hebben
 */
public class DFA {
	public static final int NOFINAL = Integer.MIN_VALUE;

	private DFAState initial;

	/**
	 * Constructs a new DFA with the specified initial state.
	 *
	 * @param initial
	 */
	public DFA(DFAState initial) {
		this.initial = initial;
	}

	/**
	 * Compiles this DFA into a more efficient structure.
	 *
	 * @return the compiled DFA
	 */
	public CompiledDFA compile() {
		ArrayList<DFAState> nodeList = new ArrayList<>();
		HashMap<DFAState, Integer> nodes = new HashMap<>();
		nodes.put(initial, 0);
		nodeList.add(initial);

		/* Find all reachable nodes in the dfs */
		int counter = 1;
		Queue<DFAState> todo = new LinkedList<DFAState>();
		todo.add(initial);

		while (!todo.isEmpty()) {
			DFAState current = todo.poll();

			IteratorI it = current.transitions.keys();
			while (it.hasNext()) {
				int k = it.next();
				DFAState next = current.transitions.get(k);
				if (!nodes.containsKey(next)) {
					todo.add(next);
					nodes.put(next, counter++);
					nodeList.add(next);
				}
			}
		}

		/* Compile */
		HashMapII[] transitions = new HashMapII[counter];
		int[] finals2 = new int[counter];

		for (DFAState node : nodeList) {
			int index = nodes.get(node);
			finals2[index] = node.finalCode;

			transitions[index] = new HashMapII();
			IteratorI it = node.transitions.keys();
			while (it.hasNext()) {
				int k = it.next();
				DFAState next = node.transitions.get(k);
				transitions[index].put(k, nodes.get(next));
			}
		}

		return new CompiledDFA(transitions, finals2);
	}

	/**
	 * Generates the minimal version of this DFA.
	 *
	 * @return the minimal DFA
	 */
	public DFA optimize() {
		CompiledDFA compiled = compile();
		HashMapII[] transitions = compiled.transitions;
		int size = transitions.length;

		/* Collect all edges and determine alphabet */
		HashSetI alphabet = new HashSetI();
		for(HashMapII transition : transitions) {
			IteratorI it = transition.keys();
			while(it.hasNext()) {
				int k = it.next();
				alphabet.add(k);
			}
		}

		/* Initialize distinguishing array */
		boolean[][] distinguishable = new boolean[size + 1][size + 1];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				distinguishable[i][j] = compiled.finals[i] != compiled.finals[j];
			}
		}
		for (int i = 0; i < size; i++) {
			distinguishable[i][size] = true;
			distinguishable[size][i] = true;
		}

		/* Minimization algorithm implementation */
		boolean changed;
		do {
			changed = false;
			IteratorI ita = alphabet.iterator();
			while (ita.hasNext()) {
				int x = ita.next();
				for (int i = 0; i < size; i++) {
					int ti = transitions[i].get(x, size);
					for (int j = 0; j < size; j++) {
						if (distinguishable[i][j])
							continue;

						int tj = transitions[j].get(x, size);
						if (distinguishable[ti][tj]) {
							distinguishable[i][j] = true;
							changed = true;
						}
					}
				}
			}
		} while (changed);

		/* Group nodes */
		HashMapI<DFAState> nodeMap = new HashMapI<>();
		outer: for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (!distinguishable[i][j] && nodeMap.containsKey(j)) {
					nodeMap.put(i, nodeMap.get(j));
					if (compiled.finals[i] != NOFINAL) {
						if (nodeMap.get(j).getFinal() != NOFINAL && nodeMap.get(j).getFinal() != compiled.finals[i]) {
							throw new RuntimeException("Eh?");
						}
					}
					continue outer;
				}
			}
			DFAState node = new DFAState();
			node.setFinal(compiled.finals[i]);
			nodeMap.put(i, node);
		}

		for (int i = 0; i < compiled.transitions.length; i++) {
			IteratorI iter = transitions[i].keys();
			while (iter.hasNext()) {
				int k = iter.next();

				nodeMap.get(i).addTransition(k, nodeMap.get(transitions[i].get(k)));
			}
		}

		return new DFA(nodeMap.get(0));
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		CompiledDFA dfs = compile();
		for (int i = 0; i < dfs.transitions.length; i++) {
			HashMapII map = dfs.transitions[i];

			IteratorI it = map.keys();
			while (it.hasNext()) {
				int v = it.next();
				result.append("edge(");
				result.append(i);
				result.append(", ");
				result.append(v);
				result.append("): ");
				result.append(map.get(v));
				result.append("\r\n");
			}
		}
		for (int i = 0; i < dfs.finals.length; i++) {
			if (dfs.finals[i] != NOFINAL) {
				result.append("final(");
				result.append(i);
				result.append("): ");
				result.append(dfs.finals[i]);
				result.append("\r\n");
			}
		}
		return result.toString();
	}

	// ///////////////////////
	// Public inner classes
	// ///////////////////////

	/**
	 * Represents a state in a DFA.
	 */
	public static class DFAState {
		private HashMapI<DFAState> transitions;
		private int finalCode = NOFINAL;

		/**
		 * Creates a new DFA state.
		 */
		public DFAState() {
			transitions = new HashMapI<>();
		}

		/**
		 * Adds a transition.
		 *
		 * @param label transition edge label
		 * @param next next state
		 */
		public void addTransition(int label, DFAState next) {
			transitions.put(label, next);
		}

		/**
		 * Sets the final class of this state. Finals can be divided in multiple
		 * class, in which case each class gets its own index. Class NOFINAL is
		 * used to indicate nonfinals.
		 *
		 * @param finalCode final index
		 */
		public void setFinal(int finalCode) {
			this.finalCode = finalCode;
		}

		/**
		 * Gets the final class of this state. Equals NOFINAL if this state is
		 * not a final.
		 *
		 * @return final index
		 */
		public int getFinal() {
			return finalCode;
		}
	}
}
