package models.dmx;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DMXMessageSender {
	private final ExecutorService executor = Executors.newCachedThreadPool();
	private final static String PROGRAM_NAME = "dmxSend ";

	public static class CommandThread implements Runnable {
		private final String command;
		private String output;

		private CommandThread(String command) {
			this.command = command;
		}

		@Override
		public void run() {
			try {
				Process process = Runtime.getRuntime().exec(command);
				output = IOUtils.toString(process.getInputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public String getOutput() {
			return output;
		}
	}

	public CommandThread send(DMXMessage message) {
		String command = PROGRAM_NAME + message.toParameterList();
		CommandThread commandThread = new CommandThread(command);
		executor.execute(commandThread);
		return commandThread;
	}
}
