package agent.runtime;

import java.util.ArrayList;
import java.util.List;

import agent.model.Cell;
import agent.model.Engine;

public class Context {
	public static void trainNew(Engine engine, String sample) {
		Context a = Context.Instance(engine);
		// a.getcontextListen().add(contextListenHandler);
		a.runAndAdd(sample);
	}

	static Context instance = null;

	public static Context Instance(Engine engine) {
		if (instance != null) return instance;
		instance = new Context(engine);
		return instance;
	}

	protected Cell cell = null;

	final Cell getCell() {
		return cell;
	}

	static final int BASE_LENGTH = 0x10000;
	static final int MAX_LENGTH = 0x20000;

	// private contextListenHandlerImp contextListen = new
	// contextListenHandlerImp();

	private Engine engine;
	private static long SIGNAL_SEED = System.currentTimeMillis();

	private long signal = 0;
	private boolean fresh = false;
	private static ActivatedWord[] matchingBuffer = new ActivatedWord[BASE_LENGTH];
	private List<ActivatedCell> buffer = new ArrayList<ActivatedCell>();

	private java.util.ArrayList<Integer> activeStack = new java.util.ArrayList<Integer>();

	final ActivatedWord getItem(int index) {
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

	final void setItem(int index, ActivatedWord value) {
		index -= BASE_LENGTH;
		ActivatedWord tci = matchingBuffer[index];

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

//	private void checkDead(int index) {
//		for (int j = 0; j < activeStack.size();) {
//			int i = activeStack.get(j);
//			WordInstance w = matchingBuffer[i];
//
//			while (w.signal != this.signal && w.nextCandidateIndex > index) {
//				w = w.getNext();
//			}
//
//			while (w.nextCandidateIndex == index && w.getNext() != null) {
//				w = w.getNext();
//			}
//			if (w.nextCandidateIndex <= index) {
//				w.die(this);
//				if (w.getPrevious() != null) {
//					w.getPrevious().setNext(w.getNext());
//				} else {
//					matchingBuffer[i] = w.getNext();
//				}
//				this.activeStack.remove(j);
//			} else {
//				j++;
//			}
//		}
//	}

	private Context(Engine engine) {
		this.engine = engine;
	}

	final Context run(String sample) {
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
				if (i - startIndex > 1 && buffer.get(startIndex).value().getLength() < i - startIndex) {
					Cell cell = createWord(buffer, startIndex, i);
					buffer.set(startIndex, buffer.get(startIndex).sibling(cell.getChildren().get(0)));
				}
				startIndex = i + 1;
			}

			ActivatedCell charCell = new ActivatedChar(this.engine.getItem((int) sample.charAt(i)), signal, i);
			buffer.add(charCell);
			charCell.activate(this, buffer);

			// checkDead(i);

			// contextListenHandler temp = this.contextListen;
			// if (temp != null)
			// {
			// temp.exec(sample, candidate);
			// }
		}
		if (startIndex > 0 && buffer.size() - startIndex > 1 && buffer.get(startIndex).value().getLength() < buffer.size() - startIndex) {
			Cell cell = createWord(buffer, startIndex, buffer.size());
			buffer.set(startIndex, buffer.get(startIndex).sibling(cell.getChildren().get(0)));
		}

		if (buffer.get(0).value().getLength() < buffer.size()) {
			fresh = true;
		} else {
			this.cell = buffer.get(0).value();
			fresh = false;
		}
		return this;
	}

	private Cell createWord(List<ActivatedCell> buffer, int indexFrom, int indexTo) {
		if (indexTo - indexFrom == 1) {
			return buffer.get(indexFrom).value();
		} else {
			Cell newWord = new Cell();
			for (int j = indexFrom; j < indexTo;) {
				Cell sc = buffer.get(j).value();
				newWord.comeFrom(sc);
				j += sc.getLength();
			}
			this.engine.add(newWord);
			return newWord;
		}
	}

	private Cell add() {
		cell = new Cell();
		for (int j = 0; j < buffer.size();) {
			Cell sc = buffer.get(j).value();
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

	final Context runAndAdd(String sample) {
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

	// final contextListenHandlerImp getcontextListen() {
	// return this.contextListen;
	// }
	//
	// final void setcontextListen(contextListenHandlerImp value) {
	// this.contextListen = value;
	// }

}
