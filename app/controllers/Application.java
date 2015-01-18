package controllers;

import models.dmx.DMXDevice;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

public class Application extends Controller {

	public static Result index() {
		return ok(index.render(DMXDevice.all(), Devices.deviceForm, models.status.Status.all(), Statuses.temperatureForm));
	}

	public static Result badDevice(Form<DMXDevice> deviceForm) {
		return badRequest(index.render(DMXDevice.all(), deviceForm, models.status.Status.all(), Statuses.temperatureForm));

	}

}
