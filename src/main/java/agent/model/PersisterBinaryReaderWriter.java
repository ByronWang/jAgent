package agent.model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

public class PersisterBinaryReaderWriter implements TypeReader,TypeWriter
{
	
	public static void save(String filename, Engine engine)
	{
		new PersisterBinaryReaderWriter().doSave(filename, engine);
	}

	public static void load(String filename, Engine engine)
	{
		new PersisterBinaryReaderWriter().doLoad(filename, engine);
	}

	public java.io.OutputStream w = null;

	public void doSave(String filename, Engine engine)
	{
		try {
			this.w =new FileOutputStream(filename);

			((Savable)engine).save(engine, this);

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
	public void doLoad(String filename, Engine engine)
	{
		try {
			this.r = new FileInputStream(filename);
			((TypeReader)this).clearReader();
			((Savable)engine).load(engine, this);

			this.r.close();
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}


//C# TO JAVA CONVERTER TODO TASK: There is no preprocessor in Java:
		///#region Visit Members

	byte[] buffer = new byte[1024];
	
	// @formatter : off
    static private char makeChar(byte b1, byte b0) {
        return (char)((b1 << 8) | (b0 & 0xff));
    }    
    static private short makeShort(byte b1, byte b0) {
        return (short)((b1 << 8) | (b0 & 0xff));
    }
    static private int makeInt(byte b3, byte b2, byte b1, byte b0) {
        return (((b3       ) << 24) |
                ((b2 & 0xff) << 16) |
                ((b1 & 0xff) <<  8) |
                ((b0 & 0xff)      ));
    }    
    
    private static byte char1(char x) { return (byte)(x >> 8); }
    private static byte char0(char x) { return (byte)(x     ); }
    private static byte short1(short x) { return (byte)(x >> 8); }
    private static byte short0(short x) { return (byte)(x     ); }
    private static byte int3(int x) { return (byte)(x >> 24); }
    private static byte int2(int x) { return (byte)(x >> 16); }
    private static byte int1(int x) { return (byte)(x >>  8); }
    private static byte int0(int x) { return (byte)(x      ); }

	public void save(String x) throws IOException {
		char[] ca = x.toCharArray();
		save(ca.length);
		for (int i = 0; i < ca.length; i++) {
			char c = ca[i];
			w.write(char1(c));
			w.write(char0(c));
		}
	}
	
	public String readString() throws IOException
	{
		int cnt = readInt();

		StringBuilder sb = new StringBuilder(1024);
		
		for(int j=0;j<cnt;j+=512){
			int to = j+512>cnt?cnt:j+512;
			for (int i = j; i < to; i++) {
				char c = makeChar((byte)r.read(), (byte)r.read());
				sb.append(c);
			}
			j = to;
		}
		
		return sb.toString();
	}



	public void save(int x) throws IOException
	{
		w.write(int3(x));
		w.write(int2(x));
		w.write(int1(x));
		w.write(int0(x));
	}

	public int readInt() throws IOException
	{
		return makeInt((byte)r.read(),(byte)r.read(), (byte)r.read(), (byte)r.read());
	}
    
	public void save(short x) throws IOException
	{
		w.write(short1(x));
		w.write(short0(x));
	}

	public short readShort() throws IOException
	{
		return makeShort((byte)r.read(),(byte)r.read());
	}

	public void save(byte x) throws IOException
	{
		w.write(x);
	}

	public byte readByte() throws IOException
	{
		return (byte)r.read();
	}

	public void clearWrite()
	{
	}

	@Override
	public void clearReader() throws IOException {
		
	}


}