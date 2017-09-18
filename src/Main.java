public class Main {
	public static void main(String[] args) {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				try {
					if (args.length != 0) {
						System.out.println("Programm was closed :(");
					}
				} catch (Exception e) {
					System.out.println(e.getLocalizedMessage());
				}
			}
		});
		OpenFileUI window = new OpenFileUI();
		window.setVisible(true);
	}
}