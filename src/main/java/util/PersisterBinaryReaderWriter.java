package util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import agent.model.Engine;

public class PersisterBinaryReaderWriter implements TypeReader, TypeWriter {

	public static void save(String filename, Engine engine) {
		new PersisterBinaryReaderWriter().doSave(filename, engine);
	}

	public static void load(String filename, Engine engine) {
		new PersisterBinaryReaderWriter().doLoad(filename, engine);
	}

	public java.io.OutputStream w = null;

	public void doSave(String filename, Engine engine) {
		try {
			this.w = new FileOutputStream(filename);

			((Savable) engine).save(engine, this);

			this.w.close();
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	public InputStream r = null;

	public void doLoad(String filename, Engine engine) {
		try {
			this.r = new FileInputStream(filename);
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

	// C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
	// /#region Visit Members

	byte[] buffer = new byte[1024];

	// @formatter : off
	static private char makeChar(byte b1, byte b0) {
		return (char) ((b1 << 8) | (b0 & 0xff));
	}

	static private short makeShort(byte b1, byte b0) {
		return (short) ((b1 << 8) | (b0 & 0xff));
	}

	// static private int makeInt(byte b3, byte b2, byte b1, byte b0) {
	// return (((b3) << 21) | ((b2 & 0xff) << 14) | ((b1 & 0xff) << 7) | ((b0 &
	// 0xff)));
	// }

	private static byte char1(char x) {
		return (byte) (x >> 8);
	}

	private static byte char0(char x) {
		return (byte) (x);
	}

	private static byte short1(short x) {
		return (byte) (x >> 8);
	}

	private static byte short0(short x) {
		return (byte) (x);
	}

	// private static byte int3(int x) { return (byte)(x >> 24); }
	// private static byte int2(int x) { return (byte)(x >> 16); }
	// private static byte int1(int x) { return (byte)(x >> 8); }
	// private static byte int0(int x) { return (byte)(x ); }

	private static byte int4(int x) {
		return (byte) (x >> 28 & 0x7F);
	}

	private static byte int3(int x) {
		return (byte) (x >> 21 & 0x7F);
	}

	private static byte int2(int x) {
		return (byte) (x >> 14 & 0x7F);
	}

	private static byte int1(int x) {
		return (byte) (x >> 7 & 0x7F);
	}

	private static byte int0(int x) {
		return (byte) (x & 0x7F);
	}

	public void save(String x) throws IOException {
		// char[] ca = x.toCharArray();
		// save(ca.length);
		// for (int i = 0; i < ca.length; i++) {
		// char c = ca[i];
		// w.write(char1(c));
		// w.write(char0(c));
		// }
	}

	public String readString() throws IOException {
		// int cnt = readInt();
		//
		// StringBuilder sb = new StringBuilder(1024);
		//
		// for (int j = 0; j < cnt; j += 512) {
		// int to = j + 512 > cnt ? cnt : j + 512;
		// for (int i = j; i < to; i++) {
		// char c = makeChar((byte) r.read(), (byte) r.read());
		// sb.append(c);
		// }
		// j = to;
		// }
		//
		// return sb.toString();
		return null;
	}

	public void save(int x) throws IOException {

		if (x < 0X80) {
			w.write(int0(x));
			return;
		} else {
			w.write(int0(x) | 0X80);
		}
		if (x < 0X4000) {
			w.write(int1(x));
			return;
		} else {
			w.write(int1(x) | 0X80);
		}
		if (x < 0X20000) {
			w.write(int2(x));
			return;
		} else {
			w.write(int2(x) | 0X80);
		}

		if (x < 0X10000000) {
			w.write(int3(x));
			return;
		} else {
			w.write(int3(x) | 0X80);
			w.write(int4(x));
		}
	}

	public int readInt() throws IOException {
		int b0 = r.read();
		if (b0 < 0X80) {
			return b0;
		}
		int b1 = (int) r.read();
		if (b1 < 0X80) {
			return (b1 << 7) | (b0 & 0x7F);
		}

		int b2 = (int) r.read();
		if (b2 < 0X80) {
			return (((b2) << 14) | ((b1 & 0x7F) << 7) | ((b0 & 0x7F)));
		}

		int b3 = (int) r.read();
		if (b3 < 0X80) {
			return (((b3) << 21) | ((b2 & 0x7F) << 14) | ((b1 & 0x7F) << 7) | ((b0 & 0x7F)));
		}

		int b4 = (int) r.read();
		return (((b4) << 28) | ((b3 & 0x7F) << 21) | ((b2 & 0x7F) << 14) | ((b1 & 0x7F) << 7) | ((b0 & 0x7F)));

	}

	public void save(short x) throws IOException {
		w.write(short1(x));
		w.write(short0(x));
	}

	public short readShort() throws IOException {
		return makeShort((byte) r.read(), (byte) r.read());
	}

	public void save(byte x) throws IOException {
		w.write(x);
	}

	public byte readByte() throws IOException {
		return (byte) r.read();
	}

	public void clearWrite() {
	}

	@Override
	public void clearReader() throws IOException {

	}

}
