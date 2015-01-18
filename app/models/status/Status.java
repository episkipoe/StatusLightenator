package models.status;

import models.lights.GeneratesLights;
import models.lights.Light;
import play.db.ebean.Model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.List;

@Entity
public class Status extends Model {
	@Id
	public Long id;

	public String name;
	@OneToOne(cascade = CascadeType.PERSIST)
	public GeneratesLights lightSource;

	public Status(String name, GeneratesLights lightSource) {
		this.name = name;
		this.lightSource = lightSource;
	}

	public static Finder<Long, Status> find = new Finder(
			Long.class, Status.class
	);

	public static List<Status> all() {
		return find.all();
	}

	public String getLights() {
		if (lightSource == null) {
			return "*";
		} else {
			StringBuffer stringBuffer = new StringBuffer();
			GeneratesLights generatesLights = lightSource;
			for (Light l : generatesLights.getLights()) {
				stringBuffer.append(l.toHexString() + " ");
			}
			return stringBuffer.toString();
		}
	}

}
