package models.lights;

import java.awt.*;

public class Light {
	public final Color color;

	public static Light WHITE = new Light(Color.WHITE);
	public static Light RED = new Light(Color.RED);
	public static Light GREEN = new Light(Color.GREEN);
	public static Light BLUE = new Light(Color.BLUE);

	public Light(Color color) {
		this.color = color;
	}

	public Light(int r, int g, int b) {
		this.color = new Color(r, g, b);
	}

	public String toRGBString() {
		return color.getRed() + " " + color.getGreen() + " " + color.getBlue();
	}

	public String toHexString() {
		return "#" + toHex(color.getRed()) + toHex(color.getGreen()) + toHex(color.getBlue());
	}

	private String toHex(int value) {
		StringBuilder builder = new StringBuilder(Integer.toHexString(value & 0xff));
		while (builder.length() < 2) {
			builder.append("0");
		}
		return builder.toString().toUpperCase();
	}

}
