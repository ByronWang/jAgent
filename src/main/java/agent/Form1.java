package WindowsApplication1;

import util.Performance;
import util.PersisterTextReaderWriter;
import agent.model.*;

public class Form1 extends Form
{
	private EngineA engine = new EngineA();

	public Form1()
	{
		InitializeComponent();
		this.Cursor = Cursors.WaitCursor;
		EngineA newEngine = new EngineA();
		String filename = this.txtFolder.getText() + this.txtFilename.getText();
		if (File.Exists(filename))
		{
			PersisterTextReaderWriter.load(filename, newEngine);
		}
		this.Cursor = Cursors.Arrow;
		this.engine = newEngine;
		this.dataRefresh();

	}

	private void btnBrowse_Click(Object sender, EventArgs e)
	{
		if(this.txtFolder.getText().size() == 0)
		{
			folderBrowserDialog1.SelectedPath=this.txtFolder.getText();
		}

		folderBrowserDialog1.ShowNewFolderButton = true;
		folderBrowserDialog1.ShowDialog();
		this.txtFolder.setText(folderBrowserDialog1.SelectedPath);
	}

	private void btnLoad_Click(Object sender, EventArgs e)
	{
		this.Cursor = Cursors.WaitCursor;
		EngineA newEngine = new EngineA();
		String filename = this.txtFolder.getText() + this.txtFilename.getText();
		if (File.Exists(filename))
		{
			PersisterTextReaderWriter.load(filename, newEngine);
		}
		this.Cursor = Cursors.Arrow;
		this.engine = newEngine;
		this.dataRefresh();

	}

	private void btnSave_Click(Object sender, EventArgs e)
	{
		this.Cursor = Cursors.WaitCursor;
		PersisterTextReaderWriter.save(this.txtFolder.getText() + this.txtFilename.getText(), engine);
		PersisterBinaryWriter.save(this.txtFolder.getText() + "bin_" + this.txtFilename.getText(), engine);

		this.Cursor = Cursors.Arrow;
	}

	private void btnCreateData_Click(Object sender, EventArgs e)
	{
		this.Cursor = Cursors.WaitCursor;
		engine.clear();
		this.dataRefresh();
		this.Cursor = Cursors.Arrow;
	}

	private void dataRefresh()
	{
		java.util.ArrayList<Cell> cells = new java.util.ArrayList<Cell>(engine.getCells().size());

		for (int i = 0; i < 0x10000; i++)
		{
			if (engine.getCells().get(i).getParents().size() > 0)
			{
				cells.add(engine.getCells().get(i));
			}
		}

		for (int i = 0x10000; i < engine.getCells().size(); i++)
		{
			cells.add(engine.getCells().get(i));
		}

		Cell[] ca = cells.toArray(new Cell[]{});

		lstCells.Items.clear();
		lstCells.Items.AddRange(ca);

		//this.splitContainer2.Panel1.SuspendLayout();
		//this.splitContainer2.Panel2.SuspendLayout();
		//this.splitContainer2.SuspendLayout();
		//this.panel1.SuspendLayout();
		//this.SuspendLayout();

		////lstCells.Items.Clear();
		//int j = 0;
		//for (int i = 0; i < 0x10000; i++)
		//{
		//    if (engine.Cells[i].Concave.Count > 0)
		//    {
		//        if (j >= lstCells.Items.Count)
		//        {
		//            lstCells.Items.Insert(j, engine.Cells[i]);
		//            j++;
		//        }
		//        else if (lstCells.Items[j] == engine.Cells[i])
		//        {
		//            j++;
		//        }
		//        else
		//        {
		//            lstCells.Items.Insert(j, engine.Cells[i]);
		//            j++;
		//        }
		//    }
		//}

		//for (int i = 0x10000; i < engine.Cells.Count; i++)
		//{
		//    if (j >= lstCells.Items.Count)
		//    {
		//        lstCells.Items.Insert(j, engine.Cells[i]);
		//        j++;
		//    }
		//    else if (lstCells.Items[j] == engine.Cells[i])
		//    {
		//        j++;
		//    }
		//    else
		//    {
		//        lstCells.Items.Insert(j, engine.Cells[i]);
		//        j++;
		//    }
		//}

		//for (; j < lstCells.Items.Count; )
		//{
		//    lstCells.Items.RemoveAt(j);
		//}
		////this.splitContainer2.Panel1.ResumeLayout(false);
		////this.splitContainer2.Panel2.ResumeLayout(false);
		////this.splitContainer2.ResumeLayout(false);
		////this.panel1.ResumeLayout(false);
		////this.panel1.PerformLayout();
		//this.ResumeLayout(false);
	}

	private void lstCells_SelectedIndexChanged(Object sender, EventArgs e)
	{
		this.engineView.setCell((Cell)this.lstCells.SelectedItem);
	}

	private void btnLoadTrainData_Click(Object sender, EventArgs e)
	{
		String[] sa = this.txtTrainData.getText().split(new String[] { "\r\n" }, StringSplitOptions.None);


		for (int i = 0; i < sa.length && sa[i].length() >0; i++)
		{

			String filename = this.txtFolder.getText() + sa[i];

			if (!File.Exists(filename))
			{
				continue;
			}

			//Engine engine = new EngineImp();
			//engine.clear();
			//Performance p = Performance.start();
			//StreamReader r = File.OpenText(filename);
			//while (!r.EndOfStream)
			//{
			//    string s = r.ReadLine();
			//    if (s.Length > 0)
			//    {
			//        engine.train(s);
			//    }
			//}
			//r.Close();
			//p.stop();

			//engine = new EngineImp();
			//engine.clear();
			Performance pNew = Performance.start();
			StreamReader r = File.OpenText(filename);
			while (!r.EndOfStream)
			{
				String s = r.ReadLine();
				if (s.length() > 0)
				{
					engine.trainNew(s);
				}
			}
			r.Close();
			pNew.stop();

			System.out.println(pNew.getSpan().Milliseconds);

			this.dataRefresh();
		}

	}

	private void btnTrainNew_Click(Object sender, EventArgs e)
	{
		String[] sa = this.txtTrainNew.getText().split(new String[] { "\r\n" }, StringSplitOptions.None);

		for (int i = 0; i < sa.length && sa[i].length() > 0; i++)
		{
			String filename = this.txtFolder.getText() + sa[i];

			if (!File.Exists(filename))
			{
				continue;
			}

			StreamReader r = File.OpenText(filename);
			while (!r.EndOfStream)
			{
				String s = r.ReadLine();
				if (s.length() > 0)
				{
					engine.trainNew(s);
				}
			}
			r.Close();
			this.dataRefresh();
		}


	}

	private void txtFindText_KeyUp(Object sender, KeyEventArgs e)
	{
		if (e.KeyCode == Keys.Enter)
		{
			Cell cell = engine.find(txtFindText.getText());
			for (int i = 0; i < lstCells.Items.size(); i++)
			{
				if (lstCells.Items[i] == cell)
				{
					lstCells.SelectedIndex = i;
				}
			}
		}
	}

	private void engineView_SelectedChanged(Object sender, Agent.EngineView.CellEventArgs args)
	{
		Cell cell = args.value();
		for (int i = 0; i < lstCells.Items.size(); i++)
		{
			if (lstCells.Items[i] == cell)
			{
				lstCells.SelectedIndex = i;
			}
		}

	}

	private void Form1_Load(Object sender, EventArgs e)
	{

	}




	/** 
	 Required designer variable.
	 
	*/
	private System.ComponentModel.IContainer components = null;

	/** 
	 Clean up any resources being used.
	 
	 @param disposing true if managed resources should be disposed; otherwise, false.
	*/
	@Override
	protected void dispose(boolean disposing)
	{
		if (disposing && (components != null))
		{
			components.dispose();
		}
		super.dispose(disposing);
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Windows Form Designer generated code

	/** 
	 Required method for Designer support - do not modify
	 the contents of this method with the code editor.
	 
	*/
	private void InitializeComponent()
	{
		this.btnLoad = new System.Windows.Forms.Button();
		this.btnSave = new System.Windows.Forms.Button();
		this.txtFolder = new System.Windows.Forms.TextBox();
		this.folderBrowserDialog1 = new System.Windows.Forms.FolderBrowserDialog();
		this.txtFilename = new System.Windows.Forms.TextBox();
		this.label1 = new System.Windows.Forms.Label();
		this.btnBrowse = new System.Windows.Forms.Button();
		this.lstCells = new System.Windows.Forms.ListBox();
		this.label3 = new System.Windows.Forms.Label();
		this.txtTrainData = new System.Windows.Forms.TextBox();
		this.btnLoadTrainData = new System.Windows.Forms.Button();
		this.splitContainer2 = new System.Windows.Forms.SplitContainer();
		this.engineView = new Agent.EngineView();
		this.panel1 = new System.Windows.Forms.Panel();
		this.txtFindText = new System.Windows.Forms.TextBox();
		this.btnTrainNew = new System.Windows.Forms.Button();
		this.txtTrainNew = new System.Windows.Forms.TextBox();
		this.btnCreateData = new System.Windows.Forms.Button();
		this.splitContainer2.Panel1.SuspendLayout();
		this.splitContainer2.Panel2.SuspendLayout();
		this.splitContainer2.SuspendLayout();
		this.panel1.SuspendLayout();
		this.SuspendLayout();
		// 
		// btnLoad
		// 
		this.btnLoad.setLocation(new System.Drawing.Point(156, 30));
		this.btnLoad.setName("btnLoad");
		this.btnLoad.Size = new System.Drawing.Size(45, 21);
		this.btnLoad.TabIndex = 0;
		this.btnLoad.setText("Load");
		this.btnLoad.UseVisualStyleBackColor = true;
//C# TO JAVA CONVERTER TODO TASK: Java has no equivalent to C#-style event wireups:
		this.btnLoad.Click += new System.EventHandler(this.btnLoad_Click);
		// 
		// btnSave
		// 
		this.btnSave.setLocation(new System.Drawing.Point(207, 30));
		this.btnSave.setName("btnSave");
		this.btnSave.Size = new System.Drawing.Size(45, 21);
		this.btnSave.TabIndex = 1;
		this.btnSave.setText("Save");
		this.btnSave.UseVisualStyleBackColor = true;
//C# TO JAVA CONVERTER TODO TASK: Java has no equivalent to C#-style event wireups:
		this.btnSave.Click += new System.EventHandler(this.btnSave_Click);
		// 
		// txtFolder
		// 
		this.txtFolder.setLocation(new System.Drawing.Point(72, 3));
		this.txtFolder.setName("txtFolder");
		this.txtFolder.Size = new System.Drawing.Size(205, 20);
		this.txtFolder.TabIndex = 2;
		this.txtFolder.setText("e:\\");
		// 
		// txtFilename
		// 
		this.txtFilename.setLocation(new System.Drawing.Point(72, 30));
		this.txtFilename.setName("txtFilename");
		this.txtFilename.Size = new System.Drawing.Size(78, 20);
		this.txtFilename.TabIndex = 3;
		this.txtFilename.setText("matrix.txt");
		// 
		// label1
		// 
		this.label1.AutoSize = true;
		this.label1.setLocation(new System.Drawing.Point(2, 8));
		this.label1.setName("label1");
		this.label1.Size = new System.Drawing.Size(36, 13);
		this.label1.TabIndex = 4;
		this.label1.setText("Folder");
		// 
		// btnBrowse
		// 
		this.btnBrowse.setLocation(new System.Drawing.Point(283, 5));
		this.btnBrowse.setName("btnBrowse");
		this.btnBrowse.Size = new System.Drawing.Size(20, 18);
		this.btnBrowse.TabIndex = 6;
		this.btnBrowse.setText("::");
		this.btnBrowse.UseVisualStyleBackColor = true;
//C# TO JAVA CONVERTER TODO TASK: Java has no equivalent to C#-style event wireups:
		this.btnBrowse.Click += new System.EventHandler(this.btnBrowse_Click);
		// 
		// lstCells
		// 
		this.lstCells.BorderStyle = System.Windows.Forms.BorderStyle.None;
		this.lstCells.Dock = System.Windows.Forms.DockStyle.Fill;
		this.lstCells.FormattingEnabled = true;
		this.lstCells.setLocation(new System.Drawing.Point(0, 0));
		this.lstCells.setName("lstCells");
		this.lstCells.Size = new System.Drawing.Size(233, 416);
		this.lstCells.TabIndex = 9;
//C# TO JAVA CONVERTER TODO TASK: Java has no equivalent to C#-style event wireups:
		this.lstCells.SelectedIndexChanged += new System.EventHandler(this.lstCells_SelectedIndexChanged);
		// 
		// label3
		// 
		this.label3.AutoSize = true;
		this.label3.setLocation(new System.Drawing.Point(318, 3));
		this.label3.setName("label3");
		this.label3.Size = new System.Drawing.Size(57, 13);
		this.label3.TabIndex = 12;
		this.label3.setText("Train Data");
		// 
		// txtTrainData
		// 
		this.txtTrainData.setLocation(new System.Drawing.Point(397, 3));
		this.txtTrainData.Multiline = true;
		this.txtTrainData.setName("txtTrainData");
		this.txtTrainData.Size = new System.Drawing.Size(169, 35);
		this.txtTrainData.TabIndex = 11;
		this.txtTrainData.setText("Words.txt\r\ndict.txt");
		// 
		// btnLoadTrainData
		// 
		this.btnLoadTrainData.setLocation(new System.Drawing.Point(572, 0));
		this.btnLoadTrainData.setName("btnLoadTrainData");
		this.btnLoadTrainData.Size = new System.Drawing.Size(114, 39);
		this.btnLoadTrainData.TabIndex = 13;
		this.btnLoadTrainData.setText("Train");
		this.btnLoadTrainData.UseVisualStyleBackColor = true;
//C# TO JAVA CONVERTER TODO TASK: Java has no equivalent to C#-style event wireups:
		this.btnLoadTrainData.Click += new System.EventHandler(this.btnLoadTrainData_Click);
		// 
		// splitContainer2
		// 
		this.splitContainer2.BorderStyle = System.Windows.Forms.BorderStyle.Fixed3D;
		this.splitContainer2.Dock = System.Windows.Forms.DockStyle.Fill;
		this.splitContainer2.setLocation(new System.Drawing.Point(0, 91));
		this.splitContainer2.setName("splitContainer2");
		// 
		// splitContainer2.Panel1
		// 
		this.splitContainer2.Panel1.Controls.add(this.lstCells);
		// 
		// splitContainer2.Panel2
		// 
		this.splitContainer2.Panel2.setBackColor(System.Drawing.SystemColors.ControlLightLight);
		this.splitContainer2.Panel2.Controls.add(this.engineView);
		this.splitContainer2.Size = new System.Drawing.Size(915, 425);
		this.splitContainer2.SplitterDistance = 237;
		this.splitContainer2.TabIndex = 15;
		// 
		// engineView
		// 
		this.engineView.AutoScroll = true;
		this.engineView.AutoScrollMargin = new System.Drawing.Size(20, 20);
		this.engineView.AutoScrollMinSize = new System.Drawing.Size(50, 200);
		this.engineView.setBackColor(System.Drawing.SystemColors.Window);
		this.engineView.setCell(null);
		this.engineView.Dock = System.Windows.Forms.DockStyle.Fill;
		this.engineView.setItemBackgroundColor(System.Drawing.Color.FromArgb(((int)(((byte)(255)))), ((int)(((byte)(224)))), ((int)(((byte)(192))))));
		this.engineView.setLocation(new System.Drawing.Point(0, 0));
		this.engineView.setMainItemColor(System.Drawing.Color.LightSalmon);
		this.engineView.setName("engineView");
		this.engineView.Size = new System.Drawing.Size(670, 421);
		this.engineView.TabIndex = 17;
		this.engineView.setText("engineView1");
		this.engineView.setTitleColor(System.Drawing.Color.Green);
//C# TO JAVA CONVERTER TODO TASK: Java has no equivalent to C#-style event wireups:
		this.engineView.SelectedChanged += new Agent.EngineView.SelectedChangedHandler(this.engineView_SelectedChanged);
		// 
		// panel1
		// 
		this.panel1.Controls.add(this.txtFindText);
		this.panel1.Controls.add(this.btnTrainNew);
		this.panel1.Controls.add(this.txtTrainNew);
		this.panel1.Controls.add(this.btnCreateData);
		this.panel1.Controls.add(this.btnLoad);
		this.panel1.Controls.add(this.btnSave);
		this.panel1.Controls.add(this.btnLoadTrainData);
		this.panel1.Controls.add(this.txtFolder);
		this.panel1.Controls.add(this.label3);
		this.panel1.Controls.add(this.txtFilename);
		this.panel1.Controls.add(this.txtTrainData);
		this.panel1.Controls.add(this.label1);
		this.panel1.Controls.add(this.btnBrowse);
		this.panel1.Dock = System.Windows.Forms.DockStyle.Top;
		this.panel1.setLocation(new System.Drawing.Point(0, 0));
		this.panel1.setName("panel1");
		this.panel1.Size = new System.Drawing.Size(915, 91);
		this.panel1.TabIndex = 16;
		// 
		// txtFindText
		// 
		this.txtFindText.setLocation(new System.Drawing.Point(4, 64));
		this.txtFindText.setName("txtFindText");
		this.txtFindText.Size = new System.Drawing.Size(185, 20);
		this.txtFindText.TabIndex = 17;
//C# TO JAVA CONVERTER TODO TASK: Java has no equivalent to C#-style event wireups:
		this.txtFindText.KeyUp += new System.Windows.Forms.KeyEventHandler(this.txtFindText_KeyUp);
		// 
		// btnTrainNew
		// 
		this.btnTrainNew.setLocation(new System.Drawing.Point(572, 42));
		this.btnTrainNew.setName("btnTrainNew");
		this.btnTrainNew.Size = new System.Drawing.Size(114, 39);
		this.btnTrainNew.TabIndex = 16;
		this.btnTrainNew.setText("TrainNew");
		this.btnTrainNew.UseVisualStyleBackColor = true;
//C# TO JAVA CONVERTER TODO TASK: Java has no equivalent to C#-style event wireups:
		this.btnTrainNew.Click += new System.EventHandler(this.btnTrainNew_Click);
		// 
		// txtTrainNew
		// 
		this.txtTrainNew.setLocation(new System.Drawing.Point(397, 46));
		this.txtTrainNew.Multiline = true;
		this.txtTrainNew.setName("txtTrainNew");
		this.txtTrainNew.Size = new System.Drawing.Size(169, 35);
		this.txtTrainNew.TabIndex = 15;
		this.txtTrainNew.setText("traindata.txt");
		// 
		// btnCreateData
		// 
		this.btnCreateData.setLocation(new System.Drawing.Point(256, 30));
		this.btnCreateData.setName("btnCreateData");
		this.btnCreateData.Size = new System.Drawing.Size(46, 21);
		this.btnCreateData.TabIndex = 14;
		this.btnCreateData.setText("CreateData");
		this.btnCreateData.UseVisualStyleBackColor = true;
//C# TO JAVA CONVERTER TODO TASK: Java has no equivalent to C#-style event wireups:
		this.btnCreateData.Click += new System.EventHandler(this.btnCreateData_Click);
		// 
		// Form1
		// 
		this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
		this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
		this.ClientSize = new System.Drawing.Size(915, 516);
		this.Controls.add(this.splitContainer2);
		this.Controls.add(this.panel1);
		this.setName("Form1");
		this.setText("Form1");
//C# TO JAVA CONVERTER TODO TASK: Java has no equivalent to C#-style event wireups:
		this.Load += new System.EventHandler(this.Form1_Load);
		this.splitContainer2.Panel1.ResumeLayout(false);
		this.splitContainer2.Panel2.ResumeLayout(false);
		this.splitContainer2.ResumeLayout(false);
		this.panel1.ResumeLayout(false);
		this.panel1.PerformLayout();
		this.ResumeLayout(false);

	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

	private System.Windows.Forms.Button btnLoad;
	private System.Windows.Forms.Button btnSave;
	private System.Windows.Forms.TextBox txtFolder;
	private System.Windows.Forms.FolderBrowserDialog folderBrowserDialog1;
	private System.Windows.Forms.TextBox txtFilename;
	private System.Windows.Forms.Label label1;
	private System.Windows.Forms.Button btnBrowse;
	private System.Windows.Forms.ListBox lstCells;
	private System.Windows.Forms.Label label3;
	private System.Windows.Forms.TextBox txtTrainData;
	private System.Windows.Forms.Button btnLoadTrainData;
	private System.Windows.Forms.SplitContainer splitContainer2;
	private System.Windows.Forms.Panel panel1;
	private System.Windows.Forms.Button btnCreateData;
	private System.Windows.Forms.Button btnTrainNew;
	private System.Windows.Forms.TextBox txtTrainNew;
	private System.Windows.Forms.TextBox txtFindText;
	private Agent.EngineView engineView;
}