package models.dmx;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

/**
 * A device that understands the DMX protocol
 */
@Entity
public class DMXDevice extends Model {
	@Id
	public Long id;

	@Required
	public String ipAddress;
	public int packetsSent = 0;

	public static final Finder<Long, DMXDevice> find = new Finder(
			Long.class, DMXDevice.class
	);

	public static List<DMXDevice> all() {
		return find.all();
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public void sendPacket(DMXMessageSender messageSender) {
		messageSender.send(new DMXMessage(getIpAddress()));
		packetsSent++;
		update();
	}
}
