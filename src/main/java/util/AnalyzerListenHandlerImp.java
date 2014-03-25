package util;

import agent.model.Candidator;
import agent.model.CellInstance;

public class AnalyzerListenHandlerImp extends ListenHandler<AnalyzerListenHandler> implements AnalyzerListenHandler {

	@Override
	public void exec(String src, Candidator<CellInstance> candidate) {
		for (AnalyzerListenHandler ins : super.list) {
			ins.exec(src, candidate);
		}
	}
}
