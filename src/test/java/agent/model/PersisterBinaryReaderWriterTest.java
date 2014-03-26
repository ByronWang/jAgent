package agent.model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import junit.framework.TestCase;

public class PersisterBinaryReaderWriterTest extends TestCase {

	PersisterBinaryReaderWriter w = new PersisterBinaryReaderWriter();

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public final void testSaveStringEngine() {
		fail("Not yet implemented"); // TODO
	}

	public final void testLoad() {
		fail("Not yet implemented"); // TODO
	}

	public final void testDoSave() {
		fail("Not yet implemented"); // TODO
	}

	public final void testDoLoad() {
		fail("Not yet implemented"); // TODO
	}

	public final void testReadString() throws IOException {
		stringCheck("");
		stringCheck("c");
		stringCheck("中文");
		stringCheck("/n");
		stringCheck("1234567890");
	}

	private void stringCheck(String expected) throws IOException {

		byte[] buffer;
		ByteArrayOutputStream bo = new ByteArrayOutputStream(2048);

		w.w = bo;
		w.save(expected);
		bo.close();

		buffer = bo.toByteArray();

		ByteArrayInputStream bi = new ByteArrayInputStream(buffer);

		w.r = bi;
		assertEquals(expected, w.readString());
	}

	public final void testReadInt() throws IOException {
		intCheck(0);
		intCheck(1000);
		intCheck(-1000);
		intCheck(-1);
		intCheck(1);
	}

	private void intCheck(int expected) throws IOException {

		byte[] buffer;
		ByteArrayOutputStream bo = new ByteArrayOutputStream(2048);
		w.w = bo;
		w.save(expected);
		bo.close();

		buffer = bo.toByteArray();

		ByteArrayInputStream bi = new ByteArrayInputStream(buffer);

		w.r = bi;
		assertEquals(expected, w.readInt());
	}

	private void shortCheck(short expected) throws IOException {

		byte[] buffer;
		ByteArrayOutputStream bo = new ByteArrayOutputStream(2048);
		w.w = bo;
		w.save(expected);
		bo.close();

		buffer = bo.toByteArray();

		ByteArrayInputStream bi = new ByteArrayInputStream(buffer);

		w.r = bi;
		assertEquals(expected, w.readShort());
	}


	public final void testReadShort() throws IOException {
		shortCheck((short)0);
		shortCheck((short)1);
		shortCheck((short)-1);
		shortCheck((short)100);
		shortCheck((short)-100);
	}

	public final void testReadByte() throws IOException {
		byteCheck((byte)0);
		byteCheck((byte)1);
		byteCheck((byte)-1);
		byteCheck((byte)100);
		byteCheck((byte)-256);
	}
	private void byteCheck(byte expected) throws IOException {

		byte[] buffer;
		ByteArrayOutputStream bo = new ByteArrayOutputStream(2048);
		w.w = bo;
		w.save(expected);
		bo.close();

		buffer = bo.toByteArray();

		ByteArrayInputStream bi = new ByteArrayInputStream(buffer);

		w.r = bi;
		byte b = w.readByte();
		System.out.println(b);
		assertEquals(expected, b);
	}

	public final void testClearWrite() {
		fail("Not yet implemented"); // TODO
	}

	public final void testClearReader() {
		fail("Not yet implemented"); // TODO
	}
	
    private static byte int3(int x) { return (byte)(x >> 21& 0B01111111); }
    private static byte int2(int x) { return (byte)(x >> 14& 0B01111111); }
    private static byte int1(int x) { return (byte)(x >>  7 & 0B01111111); }
    private static byte int0(int x) { return (byte)(x     & 0B01111111 ); }
    
	public final void testIntEncode(){
		int x = 256;

		byte b3 = int3(x);
		byte b2 = int2(x);
		byte b1 = int1(x);
		byte b0 = int0(x);

		System.out.println(b3);
		System.out.println(b2);
		System.out.println(b1);
		System.out.println(b0);
		
		
	}
}
