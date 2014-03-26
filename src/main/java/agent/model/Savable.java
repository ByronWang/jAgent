package agent.model;

import java.io.IOException;


public interface Savable {
	void save(Engine engine, TypeWriter v) throws IOException;

	void load(Engine engine, TypeReader v) throws IOException;
}
