package me.struttle.plugins.events.commands;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import me.struttle.plugins.events.Events;

public class EventLocationCommand extends CommandBase{

	public EventLocationCommand()
	{
		super(
				new ArrayList<String>()
				{
					private static final long serialVersionUID = 1L;
					{
						add("event");
						add("setlocation");
					}
				},
				new ArrayList<String>()
				{
					private static final long serialVersionUID = 1L;
					{
						add("event.admin");
					}
				},
				true);
	}

	@Override
	public boolean ExecuteInternal(Player player, String[] args, int indexStartingArguments) {
		Location loc = player.getLocation();
		m_Plugin.GetConfig().SetEventLocation(loc);
		player.sendMessage(ChatColor.GREEN + "Event location set");
		return true;
	}

}
