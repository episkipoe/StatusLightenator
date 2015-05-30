package models.lights;

import java.awt.*;

public class Light {
	private final Color color;

	public static final Light BLACK = new Light(Color.BLACK);
	public static final Light WHITE = new Light(Color.WHITE);
	public static final Light RED = new Light(Color.RED);
	public static final Light GREEN = new Light(Color.GREEN);
	public static final Light BLUE = new Light(Color.BLUE);

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
