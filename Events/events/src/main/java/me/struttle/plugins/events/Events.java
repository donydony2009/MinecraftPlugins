package me.struttle.plugins.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.struttle.plugins.events.commands.*;

public class Events extends JavaPlugin{
	
	EventsConfig m_PluginSave = new EventsConfig(getDataFolder());
	List<CommandBase> m_Commands = new ArrayList<CommandBase>();
	HashMap<String, Boolean> m_IsInEvent = new HashMap<String, Boolean>();
	PlayerListener m_PlayerListener = null;
	private static Events m_Instance = null;
	private boolean m_EventIsOn = false;
	
	public Events()
	{
		m_Instance = this;
		m_PlayerListener = new PlayerListener();
		m_Commands.add(new EventCommand());
		m_Commands.add(new EventLocationCommand());
		m_Commands.add(new EventStartCommand());
		m_Commands.add(new EventEndCommand());
	}
	
	public static Events GetInstance()
	{
		return m_Instance;
	}
	
	@Override
	public void onEnable() {
		// TODO Auto-generated method stub
		PluginManager pm=getServer().getPluginManager();
		pm.registerEvents(m_PlayerListener, this);
		Log.Initalize(getLogger());
		m_PluginSave.Reload();
		getLogger().info("Events was loaded successfully");
		
		m_EventIsOn = false;
		m_IsInEvent.clear();
		
		super.onEnable();
	}
	
	@Override
	public void onDisable() {
		// TODO Auto-generated method stub
		super.onDisable();
		getLogger().info("Events was disabled");
		getDataFolder();
		
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player)
		{
			Player player = (Player) sender;
			if(label.equalsIgnoreCase("SaveInventory"))
			{
				m_PluginSave.SavePlayerInventory(player);
			}
			else if(label.equalsIgnoreCase("LoadInventory"))
			{
				m_PluginSave.LoadPlayerInventory(player);
			}
			for(CommandBase commandExec : m_Commands)
			{
				commandExec.ExecuteCommand(player, label, args);
			}
		}
		return super.onCommand(sender, command, label, args);
	}
	
	public EventsConfig GetConfig()
	{
		return m_PluginSave;
	}
	
	public boolean EventIsStarted()
	{
		return m_EventIsOn;
	}
	
	public void StartEvent()
	{
		m_EventIsOn = true;
	}
	
	public void EndEvent()
	{
		m_IsInEvent.clear();
		m_EventIsOn = false;
	}
	
	public void JoinEvent(Player player)
	{
		m_IsInEvent.put(player.getName(), true);
	}
	
	public void LeaveEvent(Player player)
	{
		m_IsInEvent.remove(player.getName());
	}
	
	public boolean IsInEvent(Player player)
	{
		return m_IsInEvent.containsKey(player.getName());
	}
}
