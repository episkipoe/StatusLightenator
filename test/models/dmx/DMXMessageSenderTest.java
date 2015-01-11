package models.dmx;

import junit.framework.TestCase;
import org.junit.Test;

public class DMXMessageSenderTest extends TestCase {

    @Test
    public void testSend() {
        DMXMessageSender messageSender = new DMXMessageSender();
        DMXMessage message = new DMXMessage("127.0.0.1");
        DMXMessageSender.CommandThread commandThread = messageSender.send(message);
        commandThread.run();
        System.out.println(commandThread.getOutput());
    }
}