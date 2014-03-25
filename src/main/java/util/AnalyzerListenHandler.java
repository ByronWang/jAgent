package util;

import agent.model.Candidator;
import agent.model.CellInstance;

public interface AnalyzerListenHandler {
	void exec(String src, Candidator<CellInstance> candidate);
}
