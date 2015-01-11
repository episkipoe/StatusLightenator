package controllers;

import models.dmx.DMXDevice;
import models.dmx.DMXMessageSender;
import play.mvc.*;

import views.html.*;

import play.data.Form;

import java.util.List;

import play.db.ebean.Model;

import static play.libs.Json.*;

public class Application extends Controller {
    static Form<DMXDevice> deviceForm = Form.form(DMXDevice.class);

    private static DMXMessageSender messageSender = new DMXMessageSender();

    public static Result index() {
        return ok(index.render(DMXDevice.all(), deviceForm));
    }

    public static Result addDevice() {
        Form<DMXDevice> filledForm = deviceForm.bindFromRequest();
        if(filledForm.hasErrors()) {
            return badRequest(index.render(DMXDevice.all(), filledForm));
        } else {
            filledForm.get().save();
            return redirect(routes.Application.index());
        }
    }

    public static Result getDevices() {
    	List<DMXDevice> devices = new Model.Finder(String.class, DMXDevice.class).all();
    	return ok(toJson(devices));
    }

    public static Result deleteDevice(Long id) {
        DMXDevice.find.ref(id).delete();
        return redirect(routes.Application.index());
    }

    public static Result sendPacket(Long id) {
        DMXDevice.find.ref(id).sendPacket(messageSender);
        return redirect(routes.Application.index());
    }


}
