package misc;


public class FlowUtilities {
	private FlowUtilities() {}
	public static void wait(int complexity) {
		if(complexity <= 0) return;
		for(int i = 0; i < 10; ++i)
			wait(complexity - 1);
	}
}
