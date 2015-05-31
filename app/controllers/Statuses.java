package controllers;

import models.cards.CardGames;
import models.lol.Summoner;
import models.weather.Temperature;
import play.data.Form;
import play.db.ebean.Model.Finder;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static play.libs.Json.toJson;


public class Statuses extends Controller {
	public static Form<Temperature> temperatureForm = Form.form(Temperature.class);
	public static Form<Summoner> summonerForm = Form.form(Summoner.class);
	public static Form<CardGames> gameForm = Form.form(CardGames.class);

	public static class StatusData {
		public final Form form;
		public final boolean isAvailable;

		public StatusData(Form form, boolean isAvailable) {
			this.form = form;
			this.isAvailable = isAvailable;
		}
	}

	public static Map<String, StatusData> getStatusData() {
		Map<String, StatusData> statusData = new HashMap<>();
		statusData.put("temperature", new StatusData(temperatureForm, Temperature.isAvailable()));
		statusData.put("summoner", new StatusData(summonerForm, Summoner.isAvailable()));
		statusData.put("game", new StatusData(gameForm, true));
		return statusData;
	}

	public static Result addTemperature() {
		Form<Temperature> filledForm = temperatureForm.bindFromRequest();
		if (filledForm.hasErrors()) {
			return redirect(routes.Application.index());
		} else {
			Temperature temperature = filledForm.get();
			models.status.Status status = new models.status.Status(temperature.city, temperature);
			status.save();
			return redirect(routes.Application.index());
		}
	}

	public static Result addSummoner() {
		Form<Summoner> filledForm = summonerForm.bindFromRequest();
		if (filledForm.hasErrors()) {
			return redirect(routes.Application.index());
		} else {
			Summoner summoner = filledForm.get();
			models.status.Status status = new models.status.Status(summoner.name, summoner);
			status.save();
			return redirect(routes.Application.index());
		}
	}

	public static Result addGame() {
		Form<CardGames> filledForm = gameForm.bindFromRequest();
		if (filledForm.hasErrors()) {
			return redirect(routes.Application.index());
		} else {
			CardGames game = filledForm.get();
			models.status.Status status = new models.status.Status(game.baseURL, game);
			status.save();
			return redirect(routes.Application.index());
		}
	}

	public static Result getStatuses() {
		List<models.status.Status> devices = new Finder(String.class, models.status.Status.class).all();
		return ok(toJson(devices));
	}

	public static Result deleteStatus(Long id) {
		models.status.Status.find.ref(id).delete();
		return redirect(routes.Application.index());
	}

	public static Result update(Long id) {
		models.status.Status.find.ref(id).lightSource.updateStatus();
		return redirect(routes.Application.index());
	}

}
