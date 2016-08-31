package me.struttle.plugins.events.commands;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import me.struttle.plugins.events.Events;

public class EventInventoryCommand extends CommandBase {

	public EventInventoryCommand() 
	{
		super(
			new ArrayList<String>()
			{
				private static final long serialVersionUID = 1L;
				{
					add("event");
					add("setinv");
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
		m_Plugin.GetConfig().SaveEventInventory(player);
		return true;
	}

}