package dony.ItemRemover;

import java.util.ArrayList;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class ItemRemover extends JavaPlugin{
	private ArrayList<ItemStack> m_BannedItems = new ArrayList<ItemStack>();
	private PlayerListener m_PlayerListener = new PlayerListener(this);
	
	public boolean IsBanned(ItemStack item)
	{
		return FindItemByMaterial(item) != null;
	}
	
	@Override
	public void onEnable() {
		// TODO Auto-generated method stub
		PluginManager pm=getServer().getPluginManager();
		pm.registerEvents(m_PlayerListener, this);
		LoadConfig();
		getLogger().info("ItemRemover was loaded successfully");
		super.onEnable();
	}
	
	@Override
	public void onDisable() {
		// TODO Auto-generated method stub
		super.onDisable();
		getLogger().info("ItemRemover was disabled");
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		// TODO Auto-generated method stub
		Player player=(Player)sender;
		if(label.equalsIgnoreCase("banitem"))
		{
			if(player.hasPermission("ItemRemover.modify"))
			{
				if(player.getInventory().getItemInMainHand() != null && 
						player.getInventory().getItemInMainHand().getType() != Material.AIR )
				{
					BanItem(player.getInventory().getItemInMainHand());
					getLogger().info(player.getName() + 
							" has banned the item " + 
							player.getInventory().getItemInMainHand().getType());
					player.sendMessage(ChatColor.GREEN + 
							" You banned the item " + 
							player.getInventory().getItemInMainHand().getType());
				}
				else
				{
					player.sendMessage(ChatColor.DARK_RED+"You don't have anything in your hand");
				}
			}
			else
			{
				player.sendMessage(ChatColor.DARK_RED+"You don't have permission for that");
			}
		}
		else if(label.equalsIgnoreCase("unbanitem"))
		{
			if(player.hasPermission("ItemRemover.modify"))
			{
				UnbanItem(player.getInventory().getItemInMainHand());
				getLogger().info(player.getName() + " has unbanned the item " + player.getInventory().getItemInMainHand().getType());
				player.sendMessage(ChatColor.GREEN + " You unbanned the item " + player.getInventory().getItemInMainHand().getType());
			}
			else
			{
				player.sendMessage(ChatColor.DARK_RED+"You don't have permission for that");
			}
		}
		
		return super.onCommand(sender, command, label, args);
	}
	
	public void LoadConfig()
	{
		if(getConfig().contains("BannedItems"))
		{
			ConfigurationSection configuration = getConfig().getConfigurationSection("BannedItems");
			Set<String> keys = configuration.getKeys(false);
			for(String key : keys)
			{
				m_BannedItems.add(configuration.getItemStack(key));
			}
		}
	}
	
	public void BanItem(ItemStack item)
	{
		if(FindItemByMaterial(item) == null)
		{
			m_BannedItems.add(item);
			getConfig().set("BannedItems", m_BannedItems);
			SaveConfig();
		}
		
	}
	
	public void UnbanItem(ItemStack item)
	{
		ItemStack found = FindItemByMaterial(item);
		if(found != null)
		{
			m_BannedItems.remove(found);
			SaveConfig();
		}
		
	}
	
	public void SaveConfig()
	{
		for(int i = 0; i < m_BannedItems.size(); i++)
		{
			getConfig().set("BannedItems.I" + i, m_BannedItems.get(i));
		}
		saveConfig();
	}
	
	public ItemStack FindItemByMaterial(ItemStack item)
	{
		return FindItemByMaterial(item.getType());
	}
	
	public ItemStack FindItemByMaterial(Material material)
	{
		for(ItemStack bannedItem : m_BannedItems)
		{
			if(bannedItem.getType().equals(material))
			{
				return bannedItem;
			}
		}
		return null;
	}
}
