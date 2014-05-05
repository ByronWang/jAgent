package jagent;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import agent.model.Cell;
import agent.model.Link;
import agent.model.SentenceCell;

/**
 * Servlet implementation class Engine
 */
public class Engine extends HttpServlet {
	private static final long serialVersionUID = 1L;
	agent.model.Engine newEngine;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Engine() {
		super();

		newEngine = agent.model.Engine.getInstance();
//		newEngine.init();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		if (request.getPathInfo() == null) {
			showData(request.getContextPath(), response);
		} else {
			String path = request.getPathInfo().substring(1);
			int index = Integer.parseInt(path);
			showData(index, request.getContextPath(), response);
		}
	}

	private void showSentence(SentenceCell cell, StringBuilder sb, String contextPath) {
		for (Cell c : cell.getWords()) {
			sb.append("<a href=\"" + contextPath + "/engine/");
			sb.append(c.getValueIndex());
			sb.append("\">");
			sb.append('[');
			sb.append(c.toString());
			sb.append(']');
			sb.append("</a>");
		}
	}

	private void showCell(Cell cell, StringBuilder sb, String contextPath) {
		if (cell.getChildren() != null) {
			for (Link l : cell.getChildren()) {
				sb.append("<a href=\"" + contextPath + "/engine/");
				sb.append(l.from.getValueIndex());
				sb.append("\">");
				sb.append('[');
				sb.append(l.from.toString());
				sb.append(']');
				sb.append("</a>");
			}
		} else {
			sb.append(cell.toString());
		}
	}

	private void showData(int index, String contextPath, HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("utf-8");
		Writer w = response.getWriter();

		w.write("<!DOCTYPE html>");
		w.write("<html>");
		w.write("<head>");
		w.write("<meta charset=\"utf-8\">");
		w.write("<title>Insert title here</title> ");
		w.write("<link rel=\"stylesheet\" href=\"" + contextPath + "/css/main.css\">");
		w.write("</head>");
		w.write("<body> ");
		w.write("<div id=\"input\">");
		w.write("<form action=\"./engine\" method=\"post\"  accept-charset=\"utf-8\">");
		w.write("<input type=\"text\" name=\"train\" >");
		w.write("<input type=\"submit\">");
		w.write("</form>");
		w.write("</div>");
		w.write("<div id=\"main\">");
		w.write("<div id=\"mainHeader\">");

		Cell cell;
		if (index < agent.model.Engine.WORD_LENGTH) {
			cell = newEngine.getItem(index);
		} else {
			cell = newEngine.getSentences().get(index);
		}

		w.write(cell.toString());
		w.write(" : ");
		w.write(String.valueOf(cell.getWeight()));
		w.write("</div>");
		w.write("<div id=\"mainBody\">");

		{
			StringBuilder sb = new StringBuilder();
			showCell(cell, sb, contextPath);
			w.write(sb.toString());
		}

		if (cell.getParents() != null) {
			StringBuilder sb = new StringBuilder();
			sb.append("<hr>");
			List<Link> parents = cell.getParents();
			for (int i = 0; i < parents.size() && i < 50; i++) {
				showCell(parents.get(i).to, sb, contextPath);
				sb.append("<br/ >");
			}
		}

		w.write("</div>");
		w.write("</div>");
		w.write("</body>");
		w.write("</html>");
	}

	private void showData(String contextPath, HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("utf-8");
		Writer w = response.getWriter();

		w.write("<!DOCTYPE html>");
		w.write("<html>");
		w.write("<head>");
		w.write("<meta charset=\"utf-8\">");
		w.write("<title>Insert title here</title> ");
		w.write("<link rel=\"stylesheet\" href=\"" + contextPath + "/css/main.css\">");
		w.write("</head>");
		w.write("<body> ");
		w.write("<div id=\"input\">");
		w.write("<form action=\"./engine\" method=\"post\"  accept-charset=\"utf-8\">");
		w.write("<input type=\"text\" name=\"train\" >");
		w.write("<input type=\"submit\">");
		w.write("</form>");
		w.write("</div>");
		w.write("<div id=\"main\">");
		w.write("<div id=\"mainHeader\">");

		{
			List<SentenceCell> sentences = newEngine.getSentences();
			if (sentences.size() > 0) {
				SentenceCell sentence = sentences.get(sentences.size() - 1);
				StringBuilder sb = new StringBuilder();
				showSentence(sentence, sb, contextPath);
				w.write(sb.toString());
			}
		}
		w.write("</div>");
		w.write("<div id=\"mainRefer\">");

		{
			int maxLine = 1000;
			StringBuilder sb = new StringBuilder();
			int max = newEngine.getLength() - agent.model.Engine.BASE_LENGTH > maxLine ? agent.model.Engine.BASE_LENGTH + maxLine : newEngine.getLength();
			for (int i = agent.model.Engine.BASE_LENGTH; i < max; i++) {
				Cell cell = newEngine.getItem(i);
				sb.append("<a href=\"" + contextPath + "/engine/");
				sb.append(cell.getValueIndex());
				sb.append("\">");
				sb.append(cell.toString());
				sb.append("</a>");
				sb.append("&nbsp;&gt;&nbsp;");
				showCell(cell, sb, contextPath);
				sb.append("<br/ >");
			}
			w.write(sb.toString());
		}
		w.write("</div>");
		w.write("<div id=\"mainSentences\">");
		{
			int maxLine = 1000;
			StringBuilder sb = new StringBuilder();
			List<SentenceCell> sentences = newEngine.getSentences();

			for (int i = 0; i < sentences.size() && i< maxLine; i++) {
				SentenceCell cell = sentences.get(i);
				sb.append("<a href=\"" + contextPath + "/engine/");
				sb.append(cell.getValueIndex());
				sb.append("\">");
				sb.append(cell.toString());
				sb.append("</a>");
				sb.append("&nbsp;&gt;&nbsp;");
				showSentence(cell, sb, contextPath);
				sb.append("<br/ >");
			}
			w.write(sb.toString());
		}

		w.write("</div>");
		w.write("</div>");
		w.write("</body>");
		w.write("</html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String param = request.getParameter("train");
		System.out.println(param);
		if (param != null) {
			if (!param.startsWith("@")) {
				newEngine.trainNew(param);
				newEngine.save();
			} else {
				String filename = param.substring(1);
				if (new File(filename).exists()) {
					BufferedReader r = util.Files.OpenText(filename);
					for (String s = r.readLine(); s != null; s = r.readLine()) {
						if (s.length() > 0) {
							newEngine.trainNew(s);
						}
					}
					newEngine.save();
				}
			}
		}
		showData(request.getContextPath(), response);
	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
