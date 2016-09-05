package me.struttle.plugins.events.helper;

import org.bukkit.util.Vector;

public class Zone {
	public Vector m_Corner1;
	public Vector m_Corner2;
	
	public Zone(Vector corner1, Vector corner2)
	{
		if(corner1 == null)
		{
			m_Corner1 = null;
		}
		else
		{
			m_Corner1 = new Vector(Math.min(corner1.getBlockX(), corner2.getBlockX()),
					Math.min(corner1.getBlockY(), corner2.getBlockY()),
					Math.min(corner1.getBlockZ(), corner2.getBlockZ()));
		}
		
		if(corner2 == null)
		{
			m_Corner2 = null;
		}
		else
		{
			m_Corner2 = new Vector(Math.max(corner1.getBlockX(), corner2.getBlockX()),
					Math.max(corner1.getBlockY(), corner2.getBlockY()),
					Math.max(corner1.getBlockZ(), corner2.getBlockZ()));
		}
	}
	
	public boolean IsInZone(Vector location)
	{
		if(m_Corner1 == null || m_Corner2 == null)
			return false;
		return location.getX() >= m_Corner1.getX() && location.getX() <= m_Corner2.getX()
				&& location.getY() >= m_Corner1.getY() && location.getY() <= m_Corner2.getY()
				&& location.getZ() >= m_Corner1.getZ() && location.getZ() <= m_Corner2.getZ();
	}
	
	@Override
	public String toString() {
		return "Zone(<" + m_Corner1.getBlockX() + "," +
				m_Corner1.getBlockY() + "," +
				m_Corner1.getBlockZ() + ">, <" +
				m_Corner2.getBlockX() + "," +
				m_Corner2.getBlockY() + "," +
				m_Corner2.getBlockZ() + ">)";
	}
}