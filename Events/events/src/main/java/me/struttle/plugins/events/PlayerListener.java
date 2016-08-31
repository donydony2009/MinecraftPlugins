package me.struttle.plugins.events;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class PlayerListener implements Listener{
	Events m_Plugin = null;
	public PlayerListener()
	{
		m_Plugin = Events.GetInstance();
	}
	
	@EventHandler
    public void OnPlayerCommand(PlayerCommandPreprocessEvent event) {
		if(m_Plugin == null)
		{
			Log.Error("m_Plugin is null");
		}
		
        Player player = event.getPlayer();
        if(m_Plugin.IsInEvent(player) && !event.getMessage().equalsIgnoreCase("/spawn"))
        {
        	player.sendMessage(ChatColor.DARK_RED + "You are not allowed to use that command during an event");
        	event.setCancelled(true);
        }
    }
	
	@EventHandler
    public void OnPlayerTeleport(PlayerTeleportEvent event) {
        Player player = event.getPlayer();
        m_Plugin.LeaveEvent(player);
    }
	
	@EventHandler
	public void OnPlayerDeath(PlayerDeathEvent event)
	{
		Player player = event.getEntity();
		m_Plugin.LeaveEvent(player);
	}
}
