package me.struttle.plugins.events;

import java.io.File;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class EventsConfig {
	CustomConfigFile m_SavedInventoriesConfig = null;
	public EventsConfig(File dataFolder)
	{
		m_SavedInventoriesConfig = new CustomConfigFile(dataFolder, "SavedData");
	}
	
	public void Reload()
	{
		m_SavedInventoriesConfig.Reload();
	}
	
	public void SetEventLocation(Location loc)
	{
		Vector position = new Vector(loc.getX(), loc.getY(), loc.getZ());
		m_SavedInventoriesConfig.Get().set("location.position", position);
		m_SavedInventoriesConfig.Get().set("location.pitch", (double)loc.getPitch());
		m_SavedInventoriesConfig.Get().set("location.yaw", (double)loc.getYaw());
		m_SavedInventoriesConfig.Get().set("location.world", Events.GetInstance().getServer().getWorlds().indexOf(loc.getWorld()));
		m_SavedInventoriesConfig.Save();
	}
	
	public boolean LoadEventLocation(Location outLocation)
	{
		Vector position = m_SavedInventoriesConfig.Get().getVector("location.position");
		if(position == null)
			return false;
		float pitch = (float)m_SavedInventoriesConfig.Get().getDouble("location.pitch");
		float yaw = (float)m_SavedInventoriesConfig.Get().getDouble("location.yaw");
		int worldIndex = m_SavedInventoriesConfig.Get().getInt("location.world");
		outLocation.setWorld(Events.GetInstance().getServer().getWorlds().get(worldIndex));
		outLocation.setX(position.getX());
		outLocation.setY(position.getY());
		outLocation.setZ(position.getZ());
		outLocation.setPitch(pitch);
		outLocation.setYaw(yaw);
		return true;
	}

	public void SavePlayerInventory(Player player)
	{
		for(int i = 0; i < player.getInventory().getSize(); i++)
		{
			m_SavedInventoriesConfig.Get().set("inventories." + player.getName() + "." + i, player.getInventory().getItem(i));
		}
		m_SavedInventoriesConfig.Save();
	}
	
	public void LoadPlayerInventory(Player player)
	{
		for(int i = 0; i < player.getInventory().getSize(); i++)
		{
			ItemStack item = m_SavedInventoriesConfig.Get().getItemStack("inventories." + player.getName() + "." + i);
			player.getInventory().setItem(i, item);
		}
	}
}
