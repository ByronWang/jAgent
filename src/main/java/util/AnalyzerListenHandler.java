package util;

import java.util.List;

import agent.model.CellInstance;

public interface AnalyzerListenHandler {
	void exec(String src, List<CellInstance> candidate);
}
