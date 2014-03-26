package Agent;

import agent.model.*;

public class EngineView extends ScrollableControl
{
	public EngineView()
	{
		InitializeComponent();
	}

	private Cell cell = null;
	public final Cell getCell()
	{
		return cell;
	}
	public final void setCell(Cell value)
	{
		cell = value;
		panel = null;
		this.SetDisplayRectLocation(0, 0);
		super.Refresh();
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region design area
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[ComVisible(true)]
	public static class CellEventArgs extends EventArgs
	{
		public CellEventArgs(Cell cell)
		{
			this.cell = cell;
		}
		private Cell cell;
		public final Cell getCell()
		{
			return cell;
		}
	}
//C# TO JAVA CONVERTER TODO TASK: Delegates are not available in Java:
//	public delegate void SelectedChangedHandler(object sender, CellEventArgs args);
//C# TO JAVA CONVERTER TODO TASK: Events are not available in Java:
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
//[Browsable(true)]
//	public event SelectedChangedHandler SelectedChanged;

	private Color titleColor = Color.Black;
	private Color mainItemColor = Color.Red;
	private Color itemBackgroundColor = Color.LightGray;
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[DispId(-513)]
	public Color getTitleColor()
	{
		return titleColor;
	}
	public void setTitleColor(Color value)
	{
		titleColor = value;
	}
//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[DispId(-513)]
	public Color getMainItemColor()
	{
		return mainItemColor;
	}
	public void setMainItemColor(Color value)
	{
		mainItemColor = value;
	}

//C# TO JAVA CONVERTER TODO TASK: Java annotations will not correspond to .NET attributes:
	//[DispId(-513)]
	public Color getItemBackgroundColor()
	{
		return itemBackgroundColor;
	}
	public void setItemBackgroundColor(Color value)
	{
		itemBackgroundColor = value;
	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region aid object define
	public static class Span
	{

		private SizeF size;
		private Font font;
		private Brush brush;
		private Point position;
		private String value;
		private Cell cell;

		public final String getValue()
		{
			return value;
		}
		public final Cell getCell()
		{
			return cell;
		}
		public final SizeF getSize()
		{
			return size;
		}
		public final Font getFont()
		{
			return font;
		}
		public final Brush getBrush()
		{
			return brush;
		}
		public final Point getPosition()
		{
			return position;
		}

		public Span(String value, Font font, Brush brush, Point position, SizeF size)
		{
			this.value = value;
			this.font = font;
			this.position = position;
			this.size = size;
			this.brush = brush;
		}

		public Span(Cell cell, Font font, Brush brush, Point position, SizeF size)
		{
			this.cell = cell;
			this.value = cell.toString();
			this.font = font;
			this.position = position;
			this.size = size;
			this.brush = brush;
		}
		public Span(Cell cell, String value, Font font, Brush brush, Point position, SizeF size)
		{
			this.cell = cell;
			this.value = value;
			this.font = font;
			this.position = position;
			this.size = size;
			this.brush = brush;
		}
	}

	private static class Line
	{
		private Panel panel;
		public int y;
		private Cell cell;
		public final Cell getCell()
		{
			return cell;
		}
		public int maxX = 5;
		public Line(Panel panel, Cell cell, int y)
		{
			this.panel = panel;
			this.cell = cell;
			this.y = y;
		}
		public Line(Panel panel, int y)
		{
			this.panel = panel;
			this.y = y;
		}
		public java.util.ArrayList<Span> spans = new java.util.ArrayList<Span>();
		public final Span newSpan(String s, SizeF size)
		{
			return newSpan(s, panel.font, panel.brush, size);
		}

		public final Span newSpan(Cell cell, Font font, SizeF size)
		{
			return newSpan(cell, font, panel.brush, size);
		}

		public final Span newSpan(Cell cell, Brush brush, SizeF size)
		{
			return newSpan(cell, panel.font, brush, size);
		}
		public final Span newSpan(Cell cell, Font font, Brush brush, SizeF size)
		{
			Span span = new Span(cell, font, brush, new Point(maxX, y), size);
			maxX += (int)size.getWidth() + 2;
			panel.maxX = panel.maxX > maxX ? panel.maxX : maxX;
			this.spans.add(span);
			return span;
		}
		public final Span newSpan(String s, Font font, Brush brush, SizeF size)
		{
			Span span = new Span(s, font, brush, new Point(maxX, y), size);
			maxX += (int)size.getWidth() + 2;
			panel.maxX = panel.maxX > maxX ? panel.maxX : maxX;
			this.spans.add(span);
			return span;
		}
		public final Span newSpan(Cell cell, String s, Font font, Brush brush, SizeF size)
		{
			Span span = new Span(cell, s, font, brush, new Point(maxX, y), size);
			maxX += (int)size.getWidth() + 2;
			panel.maxX = panel.maxX > maxX ? panel.maxX : maxX;
			this.spans.add(span);
			return span;
		}
	}
	private static class Panel
	{
		public Font font;
		public Font titleFont;

		public Brush brush;
		public Brush titleBrush;
		public Brush mainItemBrush;
		public Brush itemBackgroundBrush;

		private java.util.ArrayList<Line> lines = new java.util.ArrayList<Line>();

		public int maxY = 5;
		public int maxX = 0;
		public int height;

		public final Line newLine()
		{
			Line line = new Line(this, maxY);
			maxY += height;
			lines.add(line);
			return line;
		}
		public final Line newLine(Cell cell)
		{
			Line line = new Line(this, cell, maxY);
			maxY += height;
			lines.add(line);
			return line;
		}

		public final Line getItem(int index)
		{
			return this.lines.get(index);
		}
		public final int getLength()
		{
			return this.lines.size();
		}

		public final Cell getCell(float x, float y)
		{
			for(Line line : lines)
			{
				if (line.y < y && y <line.y + this.height)
				{

					for (Span span : line.spans)
					{
						if (span.getPosition().X < x && x < span.getPosition().X + span.getSize().getWidth())
						{
							return span.getCell();
						}
					}

					//if (line.Cell != null && line.maxX < x && x < line.maxX + line.spans[line.spans.Count-1].Size.Width)
					//{
					//    return line.Cell;
					//}
				}
			}
			return null;
		}
	}
//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion

	private Panel panel;

	public final void cal(Graphics g, Cell cell)
	{
		panel = new Panel();

		panel.font = super.Font;
		panel.titleFont = new Font(super.Font, FontStyle.Bold);
		panel.brush = new SolidBrush(super.getForeColor());
		panel.titleBrush = new SolidBrush(this.titleColor);
		panel.mainItemBrush = new SolidBrush(this.mainItemColor);
		panel.itemBackgroundBrush = new SolidBrush(this.itemBackgroundColor);
		panel.height = panel.font.getHeight() + 4;

		Line line = panel.newLine(); // title
		if (cell.getChildren().isEmpty())
		{
			line.newSpan(cell, panel.titleFont, panel.titleBrush, g.MeasureString(cell.toString(), panel.titleFont));
		}
		else
		{
			for (Link l : cell.getChildren())
			{
				line.newSpan(l.from(), panel.titleFont, panel.titleBrush, g.MeasureString(l.from().toString(), panel.titleFont));
				line.newSpan((new Short(l.weight())).toString(), panel.font, panel.brush, g.MeasureString((new Short(l.weight())).toString(), panel.titleFont));
			}
		}

		line = panel.newLine(); // graw line

		for (Link c : cell.getParents())
		{
			line = panel.newLine(c.to());
			line.newSpan((new Short(c.weight())).toString(), g.MeasureString((new Short(c.weight())).toString(), panel.font));
			for (Link l : c.to().getChildren())
			{
				if (l != c)
				{
					line.newSpan(l.from(), panel.brush, g.MeasureString(l.from().toString(), panel.font));
				}
				else
				{
					line.newSpan(l.from(), panel.mainItemBrush, g.MeasureString(l.from().toString(), panel.font));
				}
			}
			line.newSpan(c.to(), "\u21d2", panel.font, panel.brush, g.MeasureString((new Short(c.weight())).toString(), panel.font));
		}
		this.AutoScrollMinSize = new Size((int)panel.maxX, (int)panel.maxY);
	}

	public final void calSample(Graphics g)
	{

		panel = new Panel();

		panel.font = super.Font;
		panel.titleFont = new Font(super.Font, FontStyle.Bold);
		panel.brush = new SolidBrush(super.getForeColor());
		panel.titleBrush = new SolidBrush(this.titleColor);
		panel.mainItemBrush = new SolidBrush(this.mainItemColor);
		panel.itemBackgroundBrush = new SolidBrush(this.itemBackgroundColor);
		panel.height = panel.font.getHeight() + 4;

		Line line = panel.newLine(); // title
		line.newSpan("�e�X�g", panel.titleFont, panel.titleBrush, g.MeasureString("�e�X�g", panel.titleFont));
		line.newSpan("�P�[�X", panel.titleFont, panel.titleBrush, g.MeasureString("�P�[�X", panel.titleFont));

		line = panel.newLine(); // graw line

		line = panel.newLine();
		line.newSpan("3", g.MeasureString("3", panel.font));
		line.newSpan("����", panel.font, panel.brush, g.MeasureString("����", panel.font));
		line.newSpan("��", panel.font, panel.brush, g.MeasureString("��", panel.font));
		line.newSpan("�e�X�g�P�[�X", panel.font, panel.mainItemBrush, g.MeasureString("�e�X�g�P�[�X", panel.font));
		line.newSpan(null, "��", panel.font, panel.brush, g.MeasureString("��", panel.font));

		line = panel.newLine();
		line.newSpan("2", g.MeasureString("3", panel.font));
		line.newSpan("�e�X�g�P�[�X", panel.font, panel.mainItemBrush, g.MeasureString("�e�X�g�P�[�X", panel.font));
		line.newSpan("��", panel.font, panel.brush, g.MeasureString("��", panel.font));
		line.newSpan("���", panel.font, panel.brush, g.MeasureString("���", panel.font));
		line.newSpan("����", panel.font, panel.brush, g.MeasureString("����", panel.font));
		line.newSpan("��", panel.font, panel.brush, g.MeasureString("��", panel.font));
		line.newSpan("��", panel.font, panel.brush, g.MeasureString("��", panel.font));
		line.newSpan(null, "��", panel.font, panel.brush, g.MeasureString("��", panel.font));

	}


	@Override
	protected void OnPaint(PaintEventArgs pe)
	{
		if (this.DesignMode)
		{
			calSample(pe.Graphics);
		}
		else if(cell == null)
		{
			return;
		}

		if (panel == null)
		{
			cal(pe.Graphics, cell);
		}

		Point location = this.DisplayRectangle.getLocation();

		pe.Graphics.DrawLine(Pens.Green, location.X, panel.getItem(1).y + location.Y, this.DisplayRectangle.Right, panel.getItem(1).y + location.Y);

		int i = -super.DisplayRectangle.Top / panel.height;
		int count =i+ super.ClientSize.getHeight() / panel.height + 1;


		for (; i < panel.getLength() && i<count; i++)
		{
			Line line = panel.getItem(i);
			for(int j =0;j<line.spans.size();j++)
			{
				Span span = line.spans.get(j);
				if (span.getValue().length() > 1)
				{
					pe.Graphics.FillRectangle(panel.itemBackgroundBrush, span.getPosition().X - 1 + location.X, span.getPosition().Y - 1 + location.Y, span.getSize().getWidth(), span.getSize().getHeight());
				}
				pe.Graphics.DrawString(span.getValue(), span.getFont(), span.getBrush(), span.getPosition()+(Size)location);
			}
		}

		// Calling the base class OnPaint
		super.OnPaint(pe);
	}


	private Cell selectedCell = null;
	@Override
	protected void OnMouseMove(MouseEventArgs e)
	{
		if (panel == null)
		{
			return;
		}
		Cell cell = panel.getCell(e.X - this.DisplayRectangle.X, e.Y - this.DisplayRectangle.Y);
		if (cell != null && cell != this.cell)
		{
			selectedCell = cell;
			this.Cursor = Cursors.Hand;
		}
		else
		{
			selectedCell = null;
			this.Cursor = Cursors.Default;
		}
		super.OnMouseMove(e);
	}

	@Override
	protected void OnClick(EventArgs e)
	{
		if (selectedCell != null)
		{
			SelectedChangedHandler temp = SelectedChanged;
			if (temp != null)
			{
				temp(this, new CellEventArgs(selectedCell));
			}
		}
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
		///#region Component Designer generated code

	/** 
	 Required method for Designer support - do not modify 
	 the contents of this method with the code editor.
	 
	*/
	private void InitializeComponent()
	{
		this.SuspendLayout();
		// 
		// EngineView
		// 
		this.AutoScroll = true;
		this.ResumeLayout(false);

	}

//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#endregion
}