package util;

import java.io.IOException;

public interface TypeReader {
	void clearReader() throws IOException;

	byte readByte() throws IOException;

	int readInt() throws IOException;

	short readShort() throws IOException;

	String readString() throws IOException;
}
