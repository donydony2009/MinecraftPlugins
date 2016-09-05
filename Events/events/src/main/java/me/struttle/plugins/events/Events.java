package me.struttle.plugins.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import me.struttle.plugins.events.commands.*;
import me.struttle.plugins.events.helper.Conversion;
import me.struttle.plugins.events.helper.Log;
import me.struttle.plugins.events.helper.Zone;

public class Events extends JavaPlugin{
	EventsConfig m_PluginSave = new EventsConfig(getDataFolder());
	List<CommandBase> m_Commands = new ArrayList<CommandBase>();
	public HashMap<UUID, Boolean> m_IsInEvent = new HashMap<UUID, Boolean>();
	public HashMap<UUID, Boolean> m_IsTeleporting = new HashMap<UUID, Boolean>();
	public ArrayList<UUID> m_PlayersToSendToSpawn;
	PlayerListener m_PlayerListener = null;
	private static Events m_Instance = null;
	private boolean m_EventIsOn = false;
	private Zone m_ArenaZone = null;
	private Location m_EventLocation = null;
	
	public Events()
	{
		m_Instance = this;
		m_PlayerListener = new PlayerListener();
		m_Commands.add(new EventCommand());
		m_Commands.add(new EventLocationCommand());
		m_Commands.add(new EventStartCommand());
		m_Commands.add(new EventEndCommand());
		m_Commands.add(new EventInventoryCommand());
		m_Commands.add(new EventSetCornerCommand());
		m_Commands.add(new EventLogLevelCommand());
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
		m_PlayersToSendToSpawn = GetConfig().LoadPlayerToSendToSpawn();
		m_ArenaZone = GetConfig().LoadArenaLocation();
		m_EventLocation = new Location(getServer().getWorlds().get(0), 0, 0, 0);
		GetConfig().LoadEventLocation(m_EventLocation);
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
		m_EventIsOn = false;
		for(Entry<UUID, Boolean> entry : m_IsInEvent.entrySet())
		{
			Player player = getServer().getPlayer(entry.getKey());
			
			if(player != null)
			{
				SwitchToNormalInventory(player);
				player.teleport(player.getWorld().getSpawnLocation());
			}
		}
		m_IsInEvent.clear();
		
	}
	
	public boolean IsEventLocationSet()
	{
		return m_EventLocation != null;
	}
	
	public void SetEventLocation(Location location)
	{
		m_EventLocation = location;
		GetConfig().SetEventLocation(location);
	}
	
	public boolean IsTeleporting(Player player)
	{
		return m_IsTeleporting.containsKey(player.getUniqueId());
	}
	
	public boolean SetTeleported(Player player)
	{
		return m_IsTeleporting.remove(player.getUniqueId());
	}
	
	public Location GetEventLocation()
	{
		return m_EventLocation;
	}
	
	public boolean JoinEvent(Player player)
	{
		if(!IsInEvent(player) && m_EventLocation != null)
		{
			m_IsTeleporting.put(player.getUniqueId(), true);
			player.teleport(m_EventLocation);
			SwitchToEventInventory(player);
			m_IsInEvent.put(player.getUniqueId(), true);
			return true;
		}
		return false;
	}
	
	public void LeaveEvent(Player player)
	{
		if(IsInEvent(player))
		{
			SwitchToNormalInventory(player);
			m_IsInEvent.remove(player.getUniqueId());
		}
	}
	
	public boolean IsInEvent(Player player)
	{
		return m_IsInEvent.containsKey(player.getUniqueId());
	}
	
	public void SwitchToEventInventory(Player player)
	{
		GetConfig().SavePlayerInventory(player);
		GetConfig().LoadEventInventory(player);
	}
	
	public void SwitchToNormalInventory(Player player)
	{
		GetConfig().LoadPlayerInventory(player);
	}
	
	public void DefineArena(Zone zone)
	{
		m_ArenaZone = zone;
		GetConfig().SaveArenaLocation(m_ArenaZone);
		Log.Debug("Arena was defined with: " + m_ArenaZone);
	}

	public boolean IsArenaDefined()
	{
		return m_ArenaZone != null;
	}
	
	public boolean IsInArena(Player player)
	{
		if(m_ArenaZone == null)
			return false;
		
		return m_ArenaZone.IsInZone(Conversion.ToVector(player.getLocation()));
	}
	
	public boolean IsInArena(Vector position)
	{
		if(m_ArenaZone == null)
			return false;
		
		return m_ArenaZone.IsInZone(position);
	}
}
