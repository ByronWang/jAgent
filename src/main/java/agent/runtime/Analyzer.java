package agent.runtime;

import java.util.ArrayList;
import java.util.List;

import agent.model.Cell;
import agent.model.Engine;


public class Analyzer {
	protected Cell cell = null;

	public final Cell getCell() {
		return cell;
	}

	public static final int BASE_LENGTH = 0x10000;
	public static final int MAX_LENGTH = 0x20000;

//	private AnalyzerListenHandlerImp analyzerListen = new AnalyzerListenHandlerImp();

	private Engine engine;
	private static long SIGNAL_SEED = System.currentTimeMillis();

	private long signal = 0;
	private boolean fresh = false;
	private static WordInstance[] matchingBuffer = new WordInstance[BASE_LENGTH];
	private List<CellInstance> candidate = new ArrayList<CellInstance>();

	private java.util.ArrayList<Integer> activeStack = new java.util.ArrayList<Integer>();

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

	private Analyzer(Engine engine) {
		this.engine = engine;
	}

	public static Analyzer Instance(Engine engine) {
		Analyzer a = new Analyzer(engine);
		return a;
	}

	public final Analyzer run(String sample) {
		signal = SIGNAL_SEED++;
		// if (sample.StartsWith("�K�v���K�v"))
		// {
		// System.Console.WriteLine(sample);
		// }
		// tmp.Clear();
		candidate.clear();
		activeStack.clear();

		int startIndex = 0;
		for (int i = 0; i < sample.length(); i++) {
			char c = sample.charAt(i);
			if (!Character.isLetter(c)) {
				if (i - startIndex > 1 && candidate.get(startIndex).getCell().getLength() < i - startIndex) {
					Cell cell = createSubCell(candidate, startIndex, i);
					candidate.set(startIndex, candidate.get(startIndex).sibling(cell.getConvex().get(0)));
				}
				startIndex = i + 1;
			}

			CellInstance charCell = new CharCellInstance(this.engine.getItem((int) sample.charAt(i)), signal, i);
			candidate.add(charCell);
			charCell.succeed(this, candidate);

			// checkDead(i);

			// AnalyzerListenHandler temp = this.analyzerListen;
			// if (temp != null)
			// {
			// temp.exec(sample, candidate);
			// }
		}
		if (startIndex > 0 && candidate.size() - startIndex > 1 && candidate.get(startIndex).getCell().getLength() < candidate.size() - startIndex) {
			Cell cell = createSubCell(candidate, startIndex, candidate.size());
			candidate.set(startIndex, candidate.get(startIndex).sibling(cell.getConvex().get(0)));
		}

		if (candidate.get(0).getCell().getLength() < candidate.size()) {
			fresh = true;
		} else {
			this.cell = candidate.get(0).getCell();
			fresh = false;
		}
		return this;
	}

	private Cell createSubCell(List<CellInstance> can, int startIndex, int nextIndex) {
		if (nextIndex - startIndex == 1) {
			return can.get(startIndex).getCell();
		} else {
			Cell subCell = new Cell();
			for (int j = startIndex; j < nextIndex;) {
				Cell sc = can.get(j).getCell();
				subCell.comeFrom(sc);
				j += sc.getLength();
			}
			this.engine.add(subCell);
			return subCell;
		}
	}

	private Cell add() {
		cell = new Cell();
		for (int j = 0; j < candidate.size();) {
			Cell sc = candidate.get(j).getCell();
			cell.comeFrom(sc);
			j += sc.getLength();
		}
		this.engine.add(cell);

		return cell;
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

	public final Analyzer runAndAdd(String sample) {
		this.run(sample);
		this.add();
		return this;
	}

	public final int getLength() {
		return matchingBuffer.length;
	}

	public final boolean isFresh() {
		return fresh;
	}

//	public final AnalyzerListenHandlerImp getAnalyzerListen() {
//		return this.analyzerListen;
//	}
//
//	public final void setAnalyzerListen(AnalyzerListenHandlerImp value) {
//		this.analyzerListen = value;
//	}

}
