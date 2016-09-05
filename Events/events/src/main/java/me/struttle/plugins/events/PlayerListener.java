package me.struttle.plugins.events;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import me.struttle.plugins.events.helper.Conversion;
import me.struttle.plugins.events.helper.Log;

public class PlayerListener implements Listener{
	Events m_Plugin = null;
	public PlayerListener()
	{
		m_Plugin = Events.GetInstance();
	}
	
	@EventHandler
    public void OnPlayerCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        if(m_Plugin.IsInEvent(player) && !event.getMessage().equalsIgnoreCase("/spawn") && !player.hasPermission("events.bypass.blockcommands"))
        {
        	player.sendMessage(ChatColor.DARK_RED + "You are not allowed to use that command during an event");
        	event.setCancelled(true);
        }
    }
	
	@EventHandler
    public void OnPlayerTeleport(PlayerTeleportEvent event) {
		
        Player player = event.getPlayer();
        if(m_Plugin.IsTeleporting(player))
        {
        	m_Plugin.SetTeleported(player);
        }
        else
        {
	        m_Plugin.LeaveEvent(player);
	        if(!m_Plugin.IsInEvent(player))
	        {
	        	if(m_Plugin.EventIsStarted())
	        	{
		        	Log.Debug("Player Teleport: " + Conversion.ToVector(event.getTo()));
		        	if(m_Plugin.IsInArena(Conversion.ToVector(event.getTo())))
					{
		        		Log.Debug("Player " + player.getName() + "tried to teleport inside the event. Making him join the event.");
		        		event.setTo(m_Plugin.GetEventLocation());
		    			m_Plugin.SwitchToEventInventory(player);
		    			m_Plugin.m_IsInEvent.put(player.getUniqueId(), true);
					}
	        	}
	        	else
	        	{
	        		Log.Debug("Player " + player.getName() + "tried to teleport inside the event arena while event is not on. Teleporting him spawn.");
	        		event.setTo(player.getWorld().getSpawnLocation());
	        	}
	        }
        }
    }
	
	@EventHandler
	public void OnPlayerRespawn(PlayerRespawnEvent event)
	{
		Player player = event.getPlayer();
		m_Plugin.LeaveEvent(player);
	}
	
	@EventHandler
	public void OnPlayerDisconnect(PlayerQuitEvent event)
	{
		Player player = event.getPlayer();
		m_Plugin.m_PlayersToSendToSpawn.add(player.getUniqueId());
		m_Plugin.GetConfig().SavePlayersToSendToSpawn(m_Plugin.m_PlayersToSendToSpawn);
		m_Plugin.LeaveEvent(player);
	}
	
	@EventHandler
	public void OnPlayerJoin(PlayerJoinEvent event)
	{
		Player player = event.getPlayer();
		if(player.isDead())
		{
			m_Plugin.m_IsInEvent.put(player.getUniqueId(), true);
		}
		else
		{
			if(m_Plugin.m_PlayersToSendToSpawn.contains(player.getUniqueId()))
			{
				player.teleport(player.getWorld().getSpawnLocation());
				m_Plugin.SwitchToNormalInventory(player);
			}
		}
	}
}
