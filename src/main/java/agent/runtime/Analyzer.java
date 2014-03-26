package agent.runtime;

import java.util.ArrayList;
import java.util.List;

import agent.model.Cell;
import agent.model.Engine;


public class Analyzer {	
	 public static void trainNew(Engine engine,String sample){
		Analyzer a = Analyzer.Instance(engine);
//		a.getAnalyzerListen().add(analyzerListenHandler);
		a.runAndAdd(sample);
	}
	 
	 static Analyzer instance=null;

	 public static Analyzer Instance(Engine engine) {
		 if(instance!=null)return instance;		 
		 instance= new Analyzer(engine);
		return instance;
	}
	
	protected Cell cell = null;

	 final Cell getCell() {
		return cell;
	}

	 static final int BASE_LENGTH = 0x10000;
	 static final int MAX_LENGTH = 0x20000;

//	private AnalyzerListenHandlerImp analyzerListen = new AnalyzerListenHandlerImp();

	private Engine engine;
	private static long SIGNAL_SEED = System.currentTimeMillis();

	private long signal = 0;
	private boolean fresh = false;
	private static WordInstance[] matchingBuffer = new WordInstance[BASE_LENGTH];
	private List<CellInstance> buffer = new ArrayList<CellInstance>();

	private java.util.ArrayList<Integer> activeStack = new java.util.ArrayList<Integer>();

	 final WordInstance getItem(int index) {
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

	 final void setItem(int index, WordInstance value) {
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

	 final Analyzer run(String sample) {
		signal = SIGNAL_SEED++;
		// if (sample.StartsWith("�K�v���K�v"))
		// {
		// System.Console.WriteLine(sample);
		// }
		// tmp.Clear();
		buffer.clear();
		activeStack.clear();

		int startIndex = 0;
		for (int i = 0; i < sample.length(); i++) {
			char c = sample.charAt(i);
			if (!Character.isLetter(c)) {
				if (i - startIndex > 1 && buffer.get(startIndex).getCell().getLength() < i - startIndex) {
					Cell cell = createSubCell(buffer, startIndex, i);
					buffer.set(startIndex, buffer.get(startIndex).sibling(cell.getConvex().get(0)));
				}
				startIndex = i + 1;
			}

			CellInstance charCell = new CharCellInstance(this.engine.getItem((int) sample.charAt(i)), signal, i);
			buffer.add(charCell);
			charCell.activate(this, buffer);

			// checkDead(i);

			// AnalyzerListenHandler temp = this.analyzerListen;
			// if (temp != null)
			// {
			// temp.exec(sample, candidate);
			// }
		}
		if (startIndex > 0 && buffer.size() - startIndex > 1 && buffer.get(startIndex).getCell().getLength() < buffer.size() - startIndex) {
			Cell cell = createSubCell(buffer, startIndex, buffer.size());
			buffer.set(startIndex, buffer.get(startIndex).sibling(cell.getConvex().get(0)));
		}

		if (buffer.get(0).getCell().getLength() < buffer.size()) {
			fresh = true;
		} else {
			this.cell = buffer.get(0).getCell();
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
		for (int j = 0; j < buffer.size();) {
			Cell sc = buffer.get(j).getCell();
			cell.comeFrom(sc);
			j += sc.getLength();
		}
		this.engine.add(cell);

		return cell;
	}

	 final void reasign(Cell oldCell, int convexStartIndex, int nextConvexIndex) {
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

	 final Analyzer runAndAdd(String sample) {
		this.run(sample);
		this.add();
		return this;
	}

	 final int getLength() {
		return matchingBuffer.length;
	}

	 final boolean isFresh() {
		return fresh;
	}

//	 final AnalyzerListenHandlerImp getAnalyzerListen() {
//		return this.analyzerListen;
//	}
//
//	 final void setAnalyzerListen(AnalyzerListenHandlerImp value) {
//		this.analyzerListen = value;
//	}

}
