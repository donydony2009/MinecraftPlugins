package me.struttle.plugins.events.commands;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class EventCommand extends CommandBase{
	public EventCommand() {
		super(
			new ArrayList<String>()
			{
				private static final long serialVersionUID = 1L;
				{
					add("event");
				}
			},
			true);
	}

	@Override
	public boolean ExecuteInternal(Player player, String[] args, int indexStartingArguments) {
		if(m_Plugin.EventIsStarted() && 
				m_Plugin.JoinEvent(player))
		{
			player.sendMessage(ChatColor.GREEN + "You have been teleported to the event");
		}
		else
		{
			player.sendMessage(ChatColor.DARK_RED + "There is no event");
		}
		return true;
	}

}
