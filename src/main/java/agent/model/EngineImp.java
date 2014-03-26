package agent.model;

import java.io.IOException;
import java.util.List;

import util.AnalyzerListenHandler;

public class EngineImp implements Engine, Savable {
	public static final int BASE_LENGTH = 0x10000;
	AnalyzerListenHandler analyzerListenHandler = new AnalyzerListenHandler() {

		@Override
		public void exec(String src, List<CellInstance> candidate) {
//			if (src.length() > 1) {
//				StringBuilder sb = new StringBuilder();
//				for (int i = 0; i < candidate.length;) {
//					CellInstance ci = candidate[i];
//					if (ci == null) break;
//					sb.append("[");
//					sb.append(ci.cell.getValue());
//					sb.append("]");
//					i += ci.cell.getLength();
//				}
//				System.out.println(sb.toString());
//			}
		}
	};

	private java.util.ArrayList<Cell> cells = new java.util.ArrayList<Cell>(BASE_LENGTH + BASE_LENGTH);

	public final java.util.ArrayList<Cell> getCells() {
		return cells;
	}

	public final Cell getCell(int index) {
		return cells.get(index);
	}

	public final void setItem(int index, Cell value) {
		cells.set(index, value);
	}

	public final int getLength() {
		return this.cells.size();
	}

	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	// /#region Savable Members

	public void save(Engine engine, DataWriter v) throws IOException {
		// all cell count
		v.save(cells.size() - BASE_LENGTH);
		v.clearWrite();

		for (int i = BASE_LENGTH; i < this.cells.size(); i++) {
			((Savable) cells.get(i)).save(engine, v);
		}

	}

	public void load(Engine engine, DateReader r) throws IOException {

		this.clear();

		// all cell count
		int count = r.readInt() + BASE_LENGTH;
		r.clearReader();

		for (int i = BASE_LENGTH; i < count; i++) {
			cells.add(new Cell(i));
		}
		for (int i = BASE_LENGTH; i < count; i++) {
			((Savable) cells.get(i)).load(engine, r);
		}

		for (int i = BASE_LENGTH; i < count; i++) {
			for (Link link : cells.get(i).getConvex()) {
				link.getFrom().getConcave().add(link);
			}
		}

	}

	public final void clear() {
		// clear list
		cells.clear();

		for (int i = 0; i < BASE_LENGTH; i++) {
			cells.add(new CharCell(i));
		}
	}

	public final Cell newCell() {
		Cell cell = new Cell(cells.size());
		cells.add(cell);
		return cell;
	}

	public final void trainNew(String sample) {
		Analyzer a = RunnableInstance.Instance(this);
		a.getAnalyzerListen().add(analyzerListenHandler);
		a.runAndAdd(sample);
	}

	public final Cell find(String sample) {

		Analyzer a = RunnableInstance.Instance(this).run(sample);
		if (!a.isFresh()) {
			return a.getCell();
		}
		return null;
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

	public final Cell add(Cell cell) {
		cell.index = cells.size();
		cells.add(cell);
		return cell;
	}

}
