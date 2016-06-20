package dony.ItemRemover;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerListener implements Listener{
	ItemRemover m_Plugin;
	public PlayerListener(ItemRemover plugin)
	{
		m_Plugin = plugin;
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event)
	{
		if(m_Plugin.IsBanned(event.getCurrentItem()))
		{
			if(!event.getWhoClicked().hasPermission("ItemRemover.bypass"))
			{
				event.setCurrentItem(new ItemStack(Material.AIR));
			}
		}
	}
	
	@EventHandler
	public void onItemConsumed(PlayerItemConsumeEvent event)
	{
		if(m_Plugin.IsBanned(event.getItem()))
		{
			if(!event.getPlayer().hasPermission("ItemRemover.bypass"))
			{
				event.setCancelled(true);
				event.getPlayer().getInventory().setItemInMainHand(new ItemStack(Material.AIR));
			}
		}
	}
	
	@EventHandler
	public void onItemPickup(PlayerPickupItemEvent event)
	{
		if(m_Plugin.IsBanned(event.getItem().getItemStack()))
		{
			if(!event.getPlayer().hasPermission("ItemRemover.bypass"))
			{
				event.getItem().remove();
				event.setCancelled(true);
			}
		}
	}
}
