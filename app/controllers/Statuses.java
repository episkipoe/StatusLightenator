package controllers;

import models.weather.Temperature;
import play.data.Form;
import play.db.ebean.Model;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.List;

import static play.libs.Json.toJson;


public class Statuses extends Controller {
	public static Form<Temperature> temperatureForm = Form.form(Temperature.class);

	public static Result addTemperature() {
		Form<Temperature> filledForm = temperatureForm.bindFromRequest();
		if (filledForm.hasErrors()) {
			return redirect(routes.Application.index());
		} else {
			Temperature temperature = filledForm.get();
			models.status.Status status = new models.status.Status("Temperature", temperature);
			status.save();
			return redirect(routes.Application.index());
		}
	}

	public static Result getStatuses() {
		List<models.status.Status> devices = new Model.Finder(String.class, models.status.Status.class).all();
		return ok(toJson(devices));
	}

	public static Result deleteStatus(Long id) {
		models.status.Status.find.ref(id).delete();
		return redirect(routes.Application.index());
	}

}
