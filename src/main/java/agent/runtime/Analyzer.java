package agent.runtime;

import java.util.ArrayList;
import java.util.List;

import util.AnalyzerListenHandlerImp;
import agent.model.Cell;
import agent.model.Engine;

public class Analyzer {
	public static final int BASE_LENGTH = 0x10000;
	private static WordInstance[] matchingBuffer = new WordInstance[BASE_LENGTH];
	public static final int MAX_LENGTH = 0x20000;
	private static long SIGNAL_SEED = System.currentTimeMillis();

	public static Analyzer Instance(Engine engine) {
		Analyzer a = new Analyzer(engine);
		return a;
	}

	private java.util.ArrayList<Integer> activeStack = new java.util.ArrayList<Integer>();
	private AnalyzerListenHandlerImp analyzerListen = new AnalyzerListenHandlerImp();

	private List<CellInstance> candidate;
	private Engine engine;
	private boolean isNewSentence = false;

	private long signal = 0;

	private Analyzer(Engine engine) {
		this.engine = engine;
	}

	// private void checkDead(int index)
	// {
	// for (int j = 0; j < activeStack.Count; )
	// {
	// int i = activeStack[j];
	// WordInstance w = matchingBuffer[i];

	// //while (w.signal != this.signal && w.nextCandidateIndex > index)
	// //{
	// // w = w.Next;
	// //}

	// while (w.nextCandidateIndex == index && w.Next != null) { w = w.Next; }
	// if (w.nextCandidateIndex <= index)
	// {
	// w.die(this);
	// if (w.Previous != null)
	// {
	// w.Previous.Next = w.Next;
	// }
	// else
	// {
	// matchingBuffer[i] = w.Next;
	// }
	// this.activeStack.RemoveAt(j);
	// }
	// else
	// {
	// j++;
	// }

	// }
	// }

	private Cell createSubCell(List<CellInstance> can, int startIndex, int nextIndex) {
		if (nextIndex - startIndex == 1) {
			return can.get(startIndex).cell;
		} else {
			Cell subCell = new Cell();
			for (int j = startIndex; j < nextIndex;) {
				Cell sc = can.get(j).cell;
				subCell.comeFrom(sc);
				j += sc.getLength();
			}
			this.engine.add(subCell);
			return subCell;
		}
	}

	public final AnalyzerListenHandlerImp getAnalyzerListen() {
		return this.analyzerListen;
	}

	public final WordInstance getItem(int index) {
		index -= BASE_LENGTH;
		if (matchingBuffer[index] == null) // hasn't matched word
		{
		} else if (matchingBuffer[index].signal != signal) // has old matched
															// word
		{
		} else {
			return matchingBuffer[index];
		}

		return null;
	}

	public final int getLength() {
		return matchingBuffer.length;
	}

	public final boolean isFresh() {
		return isNewSentence;
	}

	public final void reasign(Cell oldCell, int convexStartIndex, int nextConvexIndex) {
		// Cell newCell = this.engine.newCell();
		// for (int i = 0; i < nextConvexIndex - convexStartIndex; i++)
		// {
		// Link link = oldCell.Convex[convexStartIndex];
		// link.To = newCell;
		// link.ConvexIndex = i;
		// newCell.Convex.Add(link);
		// oldCell.Convex.RemoveAt(convexStartIndex);
		// }

		// Link newLink = new Link();
		// oldCell.Convex.Insert(convexStartIndex, newLink);
		// newLink.From = newCell;
		// newLink.To = oldCell;
		// newLink.ConvexIndex = convexStartIndex;
		// newLink.Strength = 1;
		// newCell.Concave.Add(newLink);
		// oldCell.addTos(newLink);
	}

	public final Cell run(String strSample) {
		signal = SIGNAL_SEED++;

		char[] sample = strSample.toCharArray();

		candidate = new ArrayList<>(sample.length);

		activeStack.clear();

		int from = 0;
		int to = 0;
		for (int i = 0; i < sample.length; i++) {
			char c = sample[i];
			if (!Character.isLetter(c)) { // 如果不是字符
				if (i - from > 1 && candidate.get(from).cell.getLength() < i - from) { // 如果之前已经积累值了，则关闭前一个选择
					Cell cell = createSubCell(candidate, from, i);
					candidate.set(from, candidate.get(from).sibling(cell.getChildren().get(0)));
				}
				from = i + 1;
				to = from;
			}

			CellInstance charCell = new CharCellInstance(this.engine.getCell(sample[i]), signal, i);
			candidate.add(charCell);
			to = i;
			charCell.succeed(this, candidate); // 计算是否能够替换成词语

			// checkDead(i);
			if (this.analyzerListen != null) {
				this.analyzerListen.exec(strSample, candidate);
			}
		}

		if (from > 0 && to - from > 1 && candidate.get(from).cell.getLength() < candidate.size() - from) { // 如果之前已经积累值了，则关闭前一个选择
			Cell cell = createSubCell(candidate, from, to);
			candidate.set(from, candidate.get(from).sibling(cell.getChildren().get(0)));
		}

		Cell sentence;
		if (candidate.get(0).cell.getLength() < to) {
			sentence = makeCell();
			isNewSentence = true;
		} else {
			isNewSentence = false;
			sentence = candidate.get(0).cell;
		}

		return sentence;
	}

	private Cell makeCell() {
		Cell cell = new Cell();
		for (int j = 0; j < candidate.size();) {
			Cell sc = candidate.get(j).cell;
			cell.comeFrom(sc);
			j += sc.getLength();
		}
		return cell;
	}

	public final void runAndAdd(String sample) {
		Cell sentence = this.run(sample);
		this.engine.add(sentence);
	}

	public final void setAnalyzerListen(AnalyzerListenHandlerImp value) {
		this.analyzerListen = value;
	}

	public final void setItem(int index, WordInstance value) {
		index -= BASE_LENGTH;
		WordInstance tci = matchingBuffer[index];

		if (tci == null || tci.signal != signal) {
			matchingBuffer[index] = value;
		} else {
			while (tci.getNext() != null) {
				tci = tci.getNext();
			}
			tci.setNext(value);
		}
		this.activeStack.add(index);
	}

}
