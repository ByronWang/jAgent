package agent.model;

import java.io.IOException;

public interface TypeWriter {
	void save(String v) throws IOException;

	void save(int v) throws IOException;

	void save(short v) throws IOException;

	void save(byte v) throws IOException;

	void clearWrite() throws IOException;
}
