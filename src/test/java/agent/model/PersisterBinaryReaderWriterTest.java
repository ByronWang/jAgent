package agent.model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

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
		intCheck(10);
		intCheck(127);
		intCheck(128);
		intCheck(256);
		intCheck(512);
		intCheck(1024);
		intCheck(2048);
		intCheck(4096);
		intCheck(1000);
		intCheck(1);
		intCheck(0XFFFFFF);
		intCheck(1073741824);
		intCheck(2147483647);
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
		shortCheck((short) 0);
		shortCheck((short) 1);
		shortCheck((short) -1);
		shortCheck((short) 100);
		shortCheck((short) -100);
	}

	public final void testReadByte() throws IOException {
		byteCheck((byte) 0);
		byteCheck((byte) 1);
		byteCheck((byte) -1);
		byteCheck((byte) 100);
		byteCheck((byte) -256);
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

}
