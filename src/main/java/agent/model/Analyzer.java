package agent.model;

import util.AnalyzerListenHandlerImp;

public interface Analyzer {
	WordInstance getItem(int index);

	void setItem(int index, WordInstance value);

	int getLength();

	boolean isFresh();

	Cell getCell();

	AnalyzerListenHandlerImp getAnalyzerListen();

	void setAnalyzerListen(AnalyzerListenHandlerImp value);

	Analyzer run(String sample);

	Analyzer runAndAdd(String sample);

	void reasign(Cell oldCell, int convexStartIndex, int nextConvexIndex);
}
