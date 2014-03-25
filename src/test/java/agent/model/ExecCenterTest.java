package agent.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

import junit.framework.TestCase;

import agent.model.Cell;
import agent.model.Engine;
import agent.model.EngineImp;
import agent.model.Performance;
import agent.model.PersisterBinaryReaderWriter;
import agent.model.PersisterTextReaderWriter;

//public class EngineImpTest extends TestCase {
public class ExecCenterTest extends TestCase {
	String path = "";
	String fileSimpleName = "matrix.txt";
	String trainData = "test1.txt";
	public Engine engine = new EngineImp();

	@Override
	protected void setUp() throws Exception {

		Engine newEngine = new EngineImp();
		String filename = path + fileSimpleName;
		if (new File(filename).exists()) {
			PersisterTextReaderWriter.load(filename, newEngine);
		}else{
			newEngine.clear();
		}
		this.engine = newEngine;
		this.dataRefresh();
	}

	public void testLoad() {
		Engine newEngine = new EngineImp();
		String filename = path + fileSimpleName;
		if (new File(filename).exists()) {
			PersisterTextReaderWriter.load(filename, newEngine);
		}
		this.engine = newEngine;
		this.dataRefresh();

	}

	public void testSave() {
		PersisterTextReaderWriter.save(path + fileSimpleName, engine);
		PersisterBinaryReaderWriter.save(path + "bin_" + fileSimpleName, engine);
	}

	public void testCreateData() {
		engine.clear();
		this.dataRefresh();
	}

	public void dataRefresh() {
		// java.util.ArrayList<Cell> cells = new
		// java.util.ArrayList<Cell>(engine.getCells().size());
		//
		// for (int i = 0; i < 0x10000; i++) {
		// if (engine.getCells().get(i).getConcave().size() > 0) {
		// cells.add(engine.getCells().get(i));
		// }
		// }
		//
		// for (int i = 0x10000; i < engine.getCells().size(); i++) {
		// cells.add(engine.getCells().get(i));
		// }
		//
		// Cell[] ca = cells.toArray(new Cell[] {});

		// lstCells.Items.Clear();
		// lstCells.Items.AddRange(ca);

	}

	public void testLoadTrainData() throws IOException {
		String[] sa = trainData.split("[\r\n]");

		for (int i = 0; i < sa.length && sa[i].length() > 0; i++) {

			String filename = path + sa[i];

			if (!new File(filename).exists()) {
				continue;
			}

			Performance pNew = Performance.start();
			BufferedReader r = util.Files.OpenText(filename);

			for (String s = r.readLine(); s != null; s = r.readLine()) {
				if (s.length() > 0) {
					engine.trainNew(s);
				}
			}
			r.close();
			pNew.stop();

			System.out.println(pNew.getSpan());

			this.dataRefresh();
		}

	}

	public void testTrainNew_Click() throws IOException {
		String[] sa = trainData.split("[\r\n]");

		for (int i = 0; i < sa.length && sa[i].length() > 0; i++) {
			String filename = path + sa[i];

			if (!new File(filename).exists()) {
				continue;
			}

			BufferedReader r = util.Files.OpenText(filename);
			for (String s = r.readLine(); s != null; s = r.readLine()) {
				if (s.length() > 0) {
					engine.trainNew(s);
				}
			}
			r.close();
			this.dataRefresh();
		}

	}

	// public void txtFindText_KeyUp(Object sender, KeyEventArgs e)
	// {
	// if (e.KeyCode == Keys.Enter)
	// {
	// Cell cell = engine.find(txtFindText.getText());
	// for (int i = 0; i < lstCells.Items.size(); i++)
	// {
	// if (lstCells.Items[i] == cell)
	// {
	// lstCells.SelectedIndex = i;
	// }
	// }
	// }
	// }

}
