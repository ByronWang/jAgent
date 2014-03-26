package util;

import java.io.IOException;

import agent.model.DataWriter;
import agent.model.DateReader;
import agent.model.Engine;

public interface Savable {
	void save(Engine engine, DataWriter v) throws IOException;

	void load(Engine engine, DateReader v) throws IOException;
}
