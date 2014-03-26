package util;

import java.util.List;

import agent.model.CellInstance;

public class AnalyzerListenHandlerImp extends ListenHandler<AnalyzerListenHandler> implements AnalyzerListenHandler {

	@Override
	public void exec(String src, List<CellInstance> candidate) {
		for (AnalyzerListenHandler ins : super.list) {
			ins.exec(src, candidate);
		}
	}
}
