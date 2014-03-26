package agent.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import util.Performance;
import util.PersisterTextReaderWriter;

import junit.framework.TestCase;

public class EngineImpTest extends TestCase {
	Engine engine;
	String folder = "";
	String fileSimpleName = "matrix.txt";

	protected void setUp() throws Exception {
		engine = new EngineImp();

		String filepathname = folder + fileSimpleName;
		if (new File(filepathname).exists()) {
			PersisterTextReaderWriter.load(fileSimpleName, engine);
		}
		this.dataRefresh();
	}

	protected void tearDown() throws Exception {
		String filepathname = folder + fileSimpleName;
		if (new File(filepathname).exists()) {
			PersisterTextReaderWriter.save(fileSimpleName, engine);
		}
	}

	public final void testEngine() {

	}

	public void testTrain() throws IOException {
		String[] sa = "test1.txt".split("[\r\n]");

		for (int i = 0; i < sa.length && sa[i].length() > 0; i++) {

			String filename = folder + sa[i];

			if (!new File(filename).exists()) {
				continue;
			}

			// Engine engine = new EngineImp();
			// engine.clear();
			// Performance p = Performance.start();
			// StreamReader r = File.OpenText(filename);
			// while (!r.EndOfStream)
			// {
			// string s = r.ReadLine();
			// if (s.Length > 0)
			// {
			// engine.train(s);
			// }
			// }
			// r.Close();
			// p.stop();

			// engine = new EngineImp();
			// engine.clear();
			Performance pNew = Performance.start();
			BufferedReader r = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "utf-8"));
			String line = r.readLine();
			while (line != null) {
				if (line.length() > 0) {
					engine.trainNew(line);
				}
				line = r.readLine();
			}
			r.close();
			pNew.stop();

			System.out.println(pNew.getSpan());

			this.dataRefresh();
			
		}
		
	}

	private void dataRefresh() {
//		java.util.ArrayList<Cell> cells = new java.util.ArrayList<Cell>(engine.getCells().size());

//		for (int i = 0; i < 0x10000; i++) {
//			if (engine.getCells().get(i).getConcave().size() > 0) {
//				cells.add(engine.getCells().get(i));
//			}
//		}
//
//		for (int i = 0x10000; i < engine.getCells().size(); i++) {
//			cells.add(engine.getCells().get(i));
//		}

		// Cell[] ca = cells.toArray(new Cell[]{});

		// lstCells.Items.Clear();
		// lstCells.Items.AddRange(ca);

		// this.splitContainer2.Panel1.SuspendLayout();
		// this.splitContainer2.Panel2.SuspendLayout();
		// this.splitContainer2.SuspendLayout();
		// this.panel1.SuspendLayout();
		// this.SuspendLayout();

		// //lstCells.Items.Clear();
		// int j = 0;
		// for (int i = 0; i < 0x10000; i++)
		// {
		// if (engine.Cells[i].Concave.Count > 0)
		// {
		// if (j >= lstCells.Items.Count)
		// {
		// lstCells.Items.Insert(j, engine.Cells[i]);
		// j++;
		// }
		// else if (lstCells.Items[j] == engine.Cells[i])
		// {
		// j++;
		// }
		// else
		// {
		// lstCells.Items.Insert(j, engine.Cells[i]);
		// j++;
		// }
		// }
		// }

		// for (int i = 0x10000; i < engine.Cells.Count; i++)
		// {
		// if (j >= lstCells.Items.Count)
		// {
		// lstCells.Items.Insert(j, engine.Cells[i]);
		// j++;
		// }
		// else if (lstCells.Items[j] == engine.Cells[i])
		// {
		// j++;
		// }
		// else
		// {
		// lstCells.Items.Insert(j, engine.Cells[i]);
		// j++;
		// }
		// }

		// for (; j < lstCells.Items.Count; )
		// {
		// lstCells.Items.RemoveAt(j);
		// }
		// //this.splitContainer2.Panel1.ResumeLayout(false);
		// //this.splitContainer2.Panel2.ResumeLayout(false);
		// //this.splitContainer2.ResumeLayout(false);
		// //this.panel1.ResumeLayout(false);
		// //this.panel1.PerformLayout();
		// this.ResumeLayout(false);
	}

	public final void testGetCells() {
		fail("Not yet implemented"); // TODO
	}

	public final void testGetItem() {
		fail("Not yet implemented"); // TODO
	}

	public final void testSetItem() {
		fail("Not yet implemented"); // TODO
	}

	public final void testGetLength() {
		fail("Not yet implemented"); // TODO
	}

	public final void testSave() {
		fail("Not yet implemented"); // TODO
	}

	public final void testLoad() {
		fail("Not yet implemented"); // TODO
	}

	public final void testClear() {
		fail("Not yet implemented"); // TODO
	}

	public final void testNewCell() {
		fail("Not yet implemented"); // TODO
	}

	public final void testTrainNew() {
		fail("Not yet implemented"); // TODO
	}

	public final void testFind() {
		fail("Not yet implemented"); // TODO
	}

	public final void testAdd() {
		fail("Not yet implemented"); // TODO
	}

}
