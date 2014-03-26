package util;

import java.io.IOException;

public interface TypeReader {
	String readString() throws IOException;

	int readInt() throws IOException;

	short readShort() throws IOException;

	byte readByte() throws IOException;

	void clearReader() throws IOException;
}
