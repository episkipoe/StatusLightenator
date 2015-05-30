package controllers;

import models.dmx.DMXDevice;
import models.lights.Light;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

import java.util.ArrayList;
import java.util.List;

public class Application extends Controller {

	public static Result index() {
		return ok(index.render(DMXDevice.all(), Devices.deviceForm, models.status.Status.all(), Statuses.getStatusData()));
	}

	public static Result badDevice(Form<DMXDevice> deviceForm) {
		return badRequest(index.render(DMXDevice.all(), deviceForm, models.status.Status.all(), Statuses.getStatusData()));
	}

	public static Result send(Long deviceId) {
		List<Light> lights = new ArrayList<>();
		boolean first=true;
		for(models.status.Status status : models.status.Status.all()) {
			if(!first) {
				lights.add(Light.BLACK);
			}
			lights.addAll(status.lightSource.getLights());
			first=false;
		}
		DMXDevice.find.ref(deviceId).send(Devices.messageSender, lights);
		return redirect(routes.Application.index());
	}
}
