package me.struttle.plugins.events.helper;

import org.bukkit.Location;
import org.bukkit.util.Vector;

public class Conversion {
	public static Vector ToVector(Location loc)
	{
		return new Vector(loc.getX(), loc.getY(), loc.getZ());
	}
}
