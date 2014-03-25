package agent.model;

public class Performance {
	private long startTime;
	private long stopTime;

	private Performance() {
		this.startTime = System.currentTimeMillis();
	}

	public static Performance start() {
		return new Performance();
	}

	public final void stop() {
		this.stopTime = System.currentTimeMillis();
	}

	public final long getSpan() {
		return stopTime - startTime;
	}
}
