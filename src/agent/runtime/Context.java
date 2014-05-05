package agent.runtime;

import java.util.ArrayList;
import java.util.List;

import agent.model.Cell;
import agent.model.CharCell;
import agent.model.Engine;
import agent.model.SentenceCell;
import agent.model.WordCell;

public class Context {
	static final int BASE_LENGTH = 0x10000;

	static Context instance = null;

	private static ActivatedWord[] matchingBuffer = new ActivatedWord[BASE_LENGTH];

	static final int MAX_LENGTH = 0x20000;
	private static long SIGNAL_SEED = System.currentTimeMillis();

	// private contextListenHandlerImp contextListen = new
	// contextListenHandlerImp();

	public static Context Instance(Engine engine) {
		if (instance != null) return instance;
		instance = new Context(engine);
		return instance;
	}

	public static void trainNew(Engine engine, String sample) {
		Context a = Context.Instance(engine);
		// a.getcontextListen().add(contextListenHandler);
		a.runAndAdd(sample);
	}

	private java.util.ArrayList<Integer> activeStack = new java.util.ArrayList<Integer>();
	private List<ActivatedCell> buffer = new ArrayList<ActivatedCell>();
	private Engine engine;
	private boolean fresh = false;

	private long signal = 0;

	private Context(Engine engine) {
		this.engine = engine;
	}

	private Cell addNewSentence() {
		SentenceCell sentence = Cell.newSentence();

		boolean stillRow = true;
		for (int j = 0; j < buffer.size(); j++) {
			if (!(buffer.get(j).cell instanceof CharCell)) {
				stillRow = false;
				break;
			}
		}

		if (stillRow && buffer.size() > 1) {
			Cell cell = createWord(buffer, 0, buffer.size());
			buffer.set(0, buffer.get(0).sibling(cell.getChildren().get(0)));
		}

		for (int j = 0; j < buffer.size();) {
			Cell sc = buffer.get(j).cell();
			sentence.comeFrom(sc);
			sc.usedBy(sentence);
			j += sc.getLength();
		}
		this.engine.addSentence(sentence);
		return sentence;
	}

	// private void checkDead(int index) {
	// for (int j = 0; j < activeStack.size();) {
	// int i = activeStack.get(j);
	// WordInstance w = matchingBuffer[i];
	//
	// while (w.signal != this.signal && w.nextCandidateIndex > index) {
	// w = w.getNext();
	// }
	//
	// while (w.nextCandidateIndex == index && w.getNext() != null) {
	// w = w.getNext();
	// }
	// if (w.nextCandidateIndex <= index) {
	// w.die(this);
	// if (w.getPrevious() != null) {
	// w.getPrevious().setNext(w.getNext());
	// } else {
	// matchingBuffer[i] = w.getNext();
	// }
	// this.activeStack.remove(j);
	// } else {
	// j++;
	// }
	// }
	// }

	private Cell createWord(List<ActivatedCell> buffer, int indexFrom, int indexTo) {
		if (indexTo - indexFrom == 1) {
			return buffer.get(indexFrom).cell();
		} else {
			WordCell newWord = Cell.newWord();
			for (int j = indexFrom; j < indexTo;) {
				Cell sc = buffer.get(j).cell();
				newWord.comeFrom(sc);
				j += sc.getLength();
			}
			this.engine.addNewWord(newWord);
			return newWord;
		}
	}

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

	final int getLength() {
		return matchingBuffer.length;
	}

	final boolean isFresh() {
		return fresh;
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

	/**
	 * @see Character#UNASSIGNED = 0;
	 * @see Character#UPPERCASE_LETTER = 1;
	 * @see Character#LOWERCASE_LETTER = 2;
	 * @see Character#TITLECASE_LETTER = 3;
	 * @see Character#MODIFIER_LETTER = 4;
	 * @see Character#OTHER_LETTER = 5;
	 * @see Character#NON_SPACING_MARK = 6;
	 * @see Character#ENCLOSING_MARK = 7;
	 * @see Character#COMBINING_SPACING_MARK = 8;
	 * @see Character#DECIMAL_DIGIT_NUMBER = 9;
	 * @see Character#LETTER_NUMBER = 10;
	 * @see Character#OTHER_NUMBER = 11;
	 * @see Character#SPACE_SEPARATOR = 12;
	 * @see Character#LINE_SEPARATOR = 13;
	 * @see Character#PARAGRAPH_SEPARATOR = 14;
	 * @see Character#CONTROL = 15;
	 * @see Character#FORMAT = 16;
	 * @see Character#PRIVATE_USE = 18;
	 * @see Character#SURROGATE = 19;
	 * @see Character#DASH_PUNCTUATION = 20;
	 * @see Character#START_PUNCTUATION = 21;
	 * @see Character#END_PUNCTUATION = 22;
	 * @see Character#CONNECTOR_PUNCTUATION = 23;
	 * @see Character#OTHER_PUNCTUATION = 24;
	 * @see Character#MATH_SYMBOL = 25;
	 * @see Character#CURRENCY_SYMBOL = 26;
	 * @see Character#MODIFIER_SYMBOL = 27;
	 * @see Character#OTHER_SYMBOL = 28;
	 * @see Character#INITIAL_QUOTE_PUNCTUATION = 29;
	 * @see Character#FINAL_QUOTE_PUNCTUATION = 30;
	 */
	final Context analyze(String sample) {
		signal = SIGNAL_SEED++;
		// if (sample.StartsWith("�K�v���K�v"))
		// {
		// System.Console.WriteLine(sample);
		// }
		// tmp.Clear();
		buffer.clear();
		activeStack.clear();

		int startIndex = 0;

		int charType;
		int lastCharType = -1;
		for (int i = 0; i < sample.length(); i++) {
			char c = sample.charAt(i);
			charType = Character.getType(c);

			if (!Character.isLetter(c)) {
				if (i - startIndex > 1 && buffer.get(startIndex).cell().getLength() < i - startIndex) {
					Cell cell = createWord(buffer, startIndex, i);
					buffer.set(startIndex, buffer.get(startIndex).sibling(cell.getChildren().get(0)));
				}
				startIndex = i + 1;
//				System.out.print(c);
//				System.out.print('-');
//				System.out.print(charType);
//				System.out.print('\n');
			} else if (charType != lastCharType && !(lastCharType == 1 && charType == 2)) {
				if (i - startIndex > 1 && buffer.get(startIndex).cell().getLength() < i - startIndex) {
					Cell cell = createWord(buffer, startIndex, i);
					buffer.set(startIndex, buffer.get(startIndex).sibling(cell.getChildren().get(0)));
//					System.out.print(c);
//					System.out.print('-');
//					System.out.print(charType);
//					System.out.print('-');
//					System.out.print(lastCharType);
//					System.out.print('\n');
				}
				startIndex = i;
			}

			lastCharType = charType;

			ActivatedCell charCell = new ActivatedChar(this.engine.getItem((int) sample.charAt(i)), signal, i);
			buffer.add(charCell);
			charCell.activate(this, buffer);

			// checkDead(i);
		}
		if (startIndex > 0 && buffer.size() - startIndex > 1 && buffer.get(startIndex).cell().getLength() < buffer.size() - startIndex) {
			Cell cell = createWord(buffer, startIndex, buffer.size());
			buffer.set(startIndex, buffer.get(startIndex).sibling(cell.getChildren().get(0)));
		}

		// if (buffer.get(0).cell().getLength() < buffer.size()) {
		// fresh = true;
		// } else {
		// this.sentence = buffer.get(0).cell();
		// fresh = false;
		// }
		return this;
	}

	final Context runAndAdd(String sample) {
		this.analyze(sample);
		this.addNewSentence();
		return this;
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

	// final contextListenHandlerImp getcontextListen() {
	// return this.contextListen;
	// }
	//
	// final void setcontextListen(contextListenHandlerImp value) {
	// this.contextListen = value;
	// }

}
