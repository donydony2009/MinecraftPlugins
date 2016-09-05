package me.struttle.plugins.events.commands;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import me.struttle.plugins.events.Events;

public class EventEndCommand extends CommandBase 
{
	public EventEndCommand() 
	{
		super(
			new ArrayList<String>()
			{
				private static final long serialVersionUID = 1L;
				{
					add("event");
					add("end");
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
	public boolean ExecuteInternal(Player player, String[] args, int indexStartingArguments) 
	{
		m_Plugin.EndEvent();
		player.sendMessage("Event ended successfully");
		return true;
	}

}
