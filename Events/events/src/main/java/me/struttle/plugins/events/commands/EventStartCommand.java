package me.struttle.plugins.events.commands;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

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
		if(m_Plugin.IsEventLocationSet() && m_Plugin.IsArenaDefined())
		{
			m_Plugin.StartEvent();
			player.sendMessage(ChatColor.GREEN + "Event has started.");
			if(m_Plugin.GetConfig().IsEventInventoryEmpty())
			{
				player.sendMessage(ChatColor.GOLD + "Warning! The inventory for the event is empty.");
			}
		}
		else
		{
			player.sendMessage(ChatColor.DARK_RED + "You need to set the location and arena first.");
		}
		return true;
	}
}
