package models.dmx;

import models.dmx.DMXMessageSender.CommandThread;
import models.lights.Light;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class DMXMessageSenderTest {

    @Test
    public void testSend() {
        DMXMessageSender messageSender = new DMXMessageSender();
        List<Light> lights = new ArrayList<>();
        lights.add(Light.RED);
        lights.add(Light.GREEN);
        lights.add(Light.BLUE);
        DMXMessage message = new DMXMessage("127.0.0.1", lights);
        CommandThread commandThread = messageSender.send(message);
        commandThread.run();
        System.out.println(commandThread.getOutput());
    }
}