package models.status;

import models.lights.GeneratesLights;
import models.lights.Light;
import play.db.ebean.Model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
public class Status extends Model {
	@Id
	public Long id;

	public final String name;
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

	public List<String> getLights() {
		if (lightSource == null) {
			return Arrays.asList("*");
		} else {
			List<String> lights = new ArrayList<>();
			GeneratesLights generatesLights = lightSource;
			for (Light l : generatesLights.getLights()) {
				lights.add(l.toHexString());
			}
			return lights;
		}
	}

}
