package me.struttle.plugins.events.commands;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import me.struttle.plugins.events.Events;

public class EventStartCommand extends CommandBase{
	public EventStartCommand() {
		super(
			new ArrayList<String>()
			{
				private static final long serialVersionUID = 1L;
				{
					add("event");
					add("start");
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
		Location outLocation = player.getLocation();
		if(Events.GetInstance().GetConfig().LoadEventLocation(outLocation))
		{
			Events.GetInstance().StartEvent();
			player.sendMessage(ChatColor.GREEN + "Event has started.");
		}
		else
		{
			player.sendMessage(ChatColor.DARK_RED + "You need to set the location first.");
		}
		return true;
	}
}
