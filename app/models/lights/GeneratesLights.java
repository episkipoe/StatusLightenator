package models.lights;

import javax.persistence.*;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "light_type", discriminatorType = DiscriminatorType.STRING)
public abstract class GeneratesLights {
	@Id
	public Long id;

	abstract public List<Light> getLights();
}
