package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import agent.model.Engine;

public class PersisterTextReaderWriter implements TypeReader, TypeWriter {
	public static char SEPERATOR = ';';

	public static void load(String filename, Engine engine) {
		new PersisterTextReaderWriter().doLoad(filename, engine);
	}

	public static void save(String filename, Engine engine) {
		new PersisterTextReaderWriter().doSave(filename, engine);
	}

	public int index = 0;

	public java.io.BufferedReader r = null;

	public String[] vs = null;
	public java.io.BufferedWriter w = null;
	public void clearReader() throws IOException {
		String s = r.readLine();
		index = 0;
		if (s == null) {
			vs = null;
		} else {
			vs = s.split(java.util.regex.Pattern.quote((new Character(SEPERATOR)).toString()), -1);
		}
		// System.Console.WriteLine(s);

	}

	public void clearWrite() throws IOException {
		w.write("\n");
	}

	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	// /#region Visit Members

	public String decode(String s) {
		s = s.replace("\\n", "\n");
		s = s.replace("\\c", ";");
		s = s.replace("\\t", "\t");
		s = s.replace("\\r", "\r");
		s = s.replace("\\\\", "\\");
		return s;
	}

	public void doLoad(String filename, Engine engine) {
		try {
			this.r = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "utf-8"));
			((TypeReader) this).clearReader();
			((Savable) engine).load(engine, this);

			this.r.close();
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void doSave(String filename, Engine engine) {
		try {
			this.w = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename), "utf-8"));

			((Savable) engine).save(engine, this);

			this.w.close();
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	public String encode(String s) {
		s = s.replace("\\", "\\\\");
		s = s.replace("\n", "\\n");
		s = s.replace("\r", "\\r");
		s = s.replace("\t", "\\t");
		s = s.replace(";", "\\c");
		return s;
	}

	public byte readByte() {
		return Byte.parseByte(vs[index++]);
	}

	public int readInt() {
		return Integer.parseInt(vs[index++]);
	}

	public short readShort() {
		return (short) Integer.parseInt(vs[index++]);
	}

	public String readString() {
		return decode(vs[index++]);
	}

	public void save(byte v) throws IOException {
//		w.write(v);
//		w.write(";");
	}

	public void save(int v) throws IOException {
		w.write(String.valueOf(v));
		w.write(";");
	}


	public void save(short v) throws IOException {
//		w.write(v);
//		w.write(";");
	}

	public void save(String v) throws IOException {
		w.write(encode(v));
//		w.write('\n');
	}

}
