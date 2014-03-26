package agent.model;

import java.io.IOException;

public interface Savable {
	void save(Engine engine, DataWriter v) throws IOException;

	void load(Engine engine, DateReader v) throws IOException;
}
