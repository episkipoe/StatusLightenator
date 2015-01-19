package models.lights;

import play.db.ebean.Model;

import javax.persistence.*;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "light_type", discriminatorType = DiscriminatorType.STRING)
public abstract class GeneratesLights extends Model {
	@Id
	public Long id;

	/**
	 * @return the list of lights that reflect the status of this object
	 */
	public abstract List<Light> getLights();

	/**
	 * Recalculate the lights to show
	 */
	public abstract void updateStatus();
}
