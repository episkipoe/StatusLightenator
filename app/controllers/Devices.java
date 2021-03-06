package controllers;

import models.dmx.DMXDevice;
import models.dmx.DMXMessageSender;
import play.data.Form;
import play.db.ebean.Model.Finder;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.List;

import static play.libs.Json.toJson;

public class Devices extends Controller {
	public static final Form<DMXDevice> deviceForm = Form.form(DMXDevice.class);
	public static final DMXMessageSender messageSender = new DMXMessageSender();

	public static Result addDevice() {
		Form<DMXDevice> filledForm = deviceForm.bindFromRequest();
		if (filledForm.hasErrors()) {
			return controllers.Application.badDevice(filledForm);
		} else {
			filledForm.get().save();
			return redirect(routes.Application.index());
		}
	}

	public static Result getDevices() {
		List<DMXDevice> devices = new Finder(String.class, DMXDevice.class).all();
		return ok(toJson(devices));
	}

	public static Result deleteDevice(Long id) {
		DMXDevice.find.ref(id).delete();
		return redirect(routes.Application.index());
	}

}
