package models.dmx;

import models.lights.Light;

import java.util.ArrayList;
import java.util.List;

/**
 * Information corresponding to one DMX message <br />
 * Capable of constructing a parameter list for the dmxSend command
 */
public class DMXMessage {
	private final String ipAddress;
	private static final int universe = 0;
	private static final int offset = 0;
	private static final int packetsToSend = 1;
	private static final int millisBetweenPackets = 1;
	private final List<Light> lights;

	public DMXMessage(String ipAddress) {
		this.ipAddress = ipAddress;
		lights = new ArrayList<>();
		lights.add(new Light(100, 100, 100));

	}

	public DMXMessage(String ipAddress, List<Light> lights) {
		this.ipAddress = ipAddress;
		this.lights = lights;
	}

	//dmxSend: IP address [universe=0] [offset] [packets to send = 100] [milliseconds between packets = 100] [RGB values]
	public String toParameterList() {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(ipAddress + " " + universe + " " + offset + " " + packetsToSend + " " + millisBetweenPackets);
		for (Light l : lights) {
			stringBuffer.append(" ").append(l.toRGBString());
		}
		return stringBuffer.toString();
	}
}
