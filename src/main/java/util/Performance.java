package util;

public class Performance {
	public static Performance start() {
		return new Performance();
	}
	private long startTime;

	private long stopTime;

	private Performance() {
		this.startTime = System.currentTimeMillis();
	}

	public final long getSpan() {
		return stopTime - startTime;
	}

	public final void stop() {
		this.stopTime = System.currentTimeMillis();
	}
}
