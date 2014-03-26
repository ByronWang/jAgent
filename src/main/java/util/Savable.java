package util;

import java.io.IOException;

import agent.model.Engine;



public interface Savable {
	void save(Engine engine, TypeWriter v) throws IOException;

	void load(Engine engine, TypeReader v) throws IOException;
}
