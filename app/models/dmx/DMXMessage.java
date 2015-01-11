package models.dmx;

/**
 * Information corresponding to one DMX message
 */
public class DMXMessage {
    private final String ipAddress;

    public DMXMessage(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String toParameterList() {
        return ipAddress + " 1";
    }
}
