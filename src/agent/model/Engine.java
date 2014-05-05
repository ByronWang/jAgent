package agent.model;

import java.io.File;
import java.io.IOException;

import util.PersisterBinaryReaderWriter;
import util.PersisterTextReaderWriter;
import util.Savable;
import util.TypeReader;
import util.TypeWriter;
import agent.runtime.Context;

public class Engine implements Savable {
	public static final int BASE_LENGTH = 0x10000;
	public static final int BEGINE_OF_WORD = BASE_LENGTH;
	public static final int WORD_LENGTH = BASE_LENGTH + BASE_LENGTH;
	
	public static final byte VERSION = 11;
	
	String filename = "matrix.bin";
	private Engine(){
		this.load();
	}
	
	public void load(){
		this.init();
		File file = new File(filename);
		if(file.exists()){
			System.out.println(file.getAbsolutePath());
			PersisterBinaryReaderWriter.load(filename, this);			
		}else{
			this.save();
		}
	}
	public void save(){
		PersisterBinaryReaderWriter.save(filename, this);		
		PersisterTextReaderWriter.save(filename+ ".txt", this);		
	}
	
	static Engine instance;
	public static Engine getInstance(){
		if(instance!=null)return instance;
		else return (instance=new Engine());
	}

	// AnalyzerListenHandler analyzerListenHandler = new AnalyzerListenHandler()
	// {
	//
	// @Override
	// public void exec(String src, Candidator<CellInstance> candidate) {
	// if (src.length() > 10) {
	// StringBuilder sb = new StringBuilder();
	// for (CellInstance ci : candidate) {
	// sb.append("[");
	// sb.append(ci.getCell().getValue());
	// sb.append("]");
	// }
	// System.out.println(sb.toString());
	// }
	//
	// }
	// };

	private java.util.ArrayList<Cell> cells = new java.util.ArrayList<Cell>(BASE_LENGTH + BASE_LENGTH);
	private java.util.ArrayList<SentenceCell> sentences = new java.util.ArrayList<SentenceCell>(BASE_LENGTH);

	public final Cell addNewWord(Cell cell) {
		cell.valueIndex = cells.size();
		this.cells.add(cell);
		return cell;
	}

	public final Cell addSentence(Cell cell) {
		cell.valueIndex = sentences.size();
		this.sentences.add((SentenceCell) cell);
		return cell;
	}

	public final void init() {
		// clear list
		cells.clear();

		for (int i = 0; i < BASE_LENGTH; i++) {
			cells.add(Cell.newCharCell(i));
		}
	}

	public final Cell find(String sample) {
		//
		// Analyzer a = Analyzer.Instance(this).run(sample);
		// if (!a.isFresh()) {
		// return a.getCell();
		// }
		return null;
	}

	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	// /#region Savable Members

	public final java.util.ArrayList<Cell> getCells() {
		return cells;
	}

	public final Cell getItem(int index) {
		return cells.get(index);
	}

	public final int getLength() {
		return this.cells.size();
	}

	// public final Cell newCell() {
	// Cell cell = new CharCell(cells.size());
	// cells.add(cell);
	// return cell;
	// }

	public void load(Engine engine, TypeReader r) throws IOException {
		int version = r.readByte();
		if(version != Engine.VERSION){
			return;
		}
		// all cell count
		int cntWord = r.readInt() + BASE_LENGTH;
		r.clearReader();

		for (int i = BASE_LENGTH; i < cntWord; i++) {
			cells.add(Cell.newWord(i));
		}

		for (int i = BASE_LENGTH; i < cntWord; i++) {
			((Savable) cells.get(i)).load(engine, r);
		}

		for (int i = BASE_LENGTH; i < cntWord; i++) {
			WordCell wc = (WordCell) cells.get(i);
			for (Link link : wc.getChildren()) {
				link.from.getParents().add(link);
			}
		}

		// all sentence count
		int cntSentence = r.readInt();
		r.clearReader();

		for (int i = 0; i < cntSentence; i++) {
			sentences.add(Cell.newSentence(i));
		}
		for (int i = 0; i < cntSentence; i++) {
			((Savable) sentences.get(i)).load(engine, r);
		}
	}

	public void save(Engine engine, TypeWriter v) throws IOException {
		v.save(VERSION);
		// all cell count
		v.save(cells.size() - BASE_LENGTH);
		v.clearWrite();

		for (int i = BASE_LENGTH; i < this.cells.size(); i++) {
			((Savable) cells.get(i)).save(engine, v);
		}

		v.save(sentences.size());
		v.clearWrite();

		for (int i = 0; i < this.sentences.size(); i++) {
			((Savable) sentences.get(i)).save(engine, v);
		}
	}

	// List<CellInstance> march(List<CellInstance> activeList)
	// {
	// foreach (CellInstance ci in activeList) ci.move();
	// return activeList;
	// }

	// public void train(string sample)
	// {
	// Cell target = new Cell();

	// List<Cell> cs = new List<Cell>(sample.Length);
	// List<Link> tc = new List<Link>();

	// int depth = 0;
	// for(int i=0;i<sample.Length;i++){
	// Cell from = this.cells[(int)sample[i]];
	// cs.Add(from);

	// tc = filter(tc, from, depth);

	// if (tc.Count == 0)
	// {
	// tc.AddRange(from.Concave);
	// depth = 0;
	// tc = filter(tc, from, depth);
	// }

	// if (tc.Count > 0 && tc[0].To.Length == depth + 1)
	// {
	// cs[i-depth] = tc[0].To;
	// }

	// if (tc.Count > 0 && tc[0].To.Length > depth + 1)
	// {
	// depth++;
	// }
	// }

	// if (cs[0].Length < cs.Count)
	// {

	// for (int i = 0; i < sample.Length; )
	// {
	// Cell cell = cs[i];
	// target.comeFrom(cell);
	// i += cell.Length;
	// }
	// this.Cells.Add(target);
	// target.index = this.cells.Count-1;
	// }
	// else
	// {
	// cs[0].reinforce();
	// }
	// }

	// List<Link> filter(List<Link> ls,Cell cell, int index)
	// {
	// List<Link> newls = new List<Link>();
	// foreach (Link l in ls)
	// {
	// if (l.To.Convex[index].From == cell)
	// {
	// newls.Add(l);
	// }
	// }
	// return newls;
	// }

	public final void setItem(int index, Cell value) {
		cells.set(index, value);
	}

	public final void trainNew(String sample) {
		Context.trainNew(this, sample);
	}

	public java.util.ArrayList<SentenceCell> getSentences() {
		return sentences;
	}

}
