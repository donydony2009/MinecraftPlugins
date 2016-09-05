package me.struttle.plugins.events;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import me.struttle.plugins.events.helper.Log;
import me.struttle.plugins.events.helper.Zone;

public class EventsConfig {
	CustomConfigFile m_Config = null;
	public EventsConfig(File dataFolder)
	{
		m_Config = new CustomConfigFile(dataFolder, "SavedData");
	}
	
	public void Reload()
	{
		m_Config.Reload();
	}
	
	public void SetEventLocation(Location loc)
	{
		Vector position = new Vector(loc.getX(), loc.getY(), loc.getZ());
		m_Config.Get().set("location.position", position);
		m_Config.Get().set("location.pitch", (double)loc.getPitch());
		m_Config.Get().set("location.yaw", (double)loc.getYaw());
		m_Config.Get().set("location.world", Events.GetInstance().getServer().getWorlds().indexOf(loc.getWorld()));
		m_Config.Save();
	}
	
	public boolean LoadEventLocation(Location outLocation)
	{
		Vector position = m_Config.Get().getVector("location.position");
		if(position == null)
			return false;
		float pitch = (float)m_Config.Get().getDouble("location.pitch");
		float yaw = (float)m_Config.Get().getDouble("location.yaw");
		int worldIndex = m_Config.Get().getInt("location.world");
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
		Log.Debug("Save Normal Inventory:");
		for(int i = 0; i < player.getInventory().getSize(); i++)
		{
			m_Config.Get().set("inventories." + player.getName() + "." + i, player.getInventory().getItem(i));
			Log.Debug("Saved on " + i + " - " + player.getInventory().getItem(i));
		}
		m_Config.Save();
	}
	
	public void LoadPlayerInventory(Player player)
	{
		Log.Debug("Load Normal Inventory:");
		for(int i = 0; i < player.getInventory().getSize(); i++)
		{
			ItemStack item = m_Config.Get().getItemStack("inventories." + player.getName() + "." + i);
			Log.Debug("Loaded on " + i + " - " + item);
			player.getInventory().setItem(i, item);
		}
	}
	
	public void SaveEventInventory(Player player)
	{
		for(int i = 0; i < player.getInventory().getSize(); i++)
		{
			m_Config.Get().set("inventory." + i, player.getInventory().getItem(i));
		}
		m_Config.Save();
	}
	
	public void LoadEventInventory(Player player)
	{
		player.getInventory().clear();
		for(int i = 0; i < player.getInventory().getSize(); i++)
		{
			ItemStack item = m_Config.Get().getItemStack("inventory." + i);
			player.getInventory().setItem(i, item);
		}
	}
	
	public boolean IsEventInventoryEmpty()
	{
		return !m_Config.Get().contains("inventory");
	}
	
	public void SavePlayersToSendToSpawn(ArrayList<UUID> list)
	{
		ArrayList<String> toSave = new ArrayList<String>();
		for(UUID entry : list)
		{
			toSave.add(entry.toString());
		}
		m_Config.Get().set("playersToRespawn", toSave);
		m_Config.Save();
	}
	
	public ArrayList<UUID> LoadPlayerToSendToSpawn()
	{
		List<String> list = m_Config.Get().getStringList("playersToRespawn");
		ArrayList<UUID> result = new ArrayList<UUID>();
		if(list != null)
		{
			for(String entry : list)
			{
				result.add(UUID.fromString(entry));
			}
		}
		return result;
	}
	
	public void SaveArenaLocation(Zone zone)
	{
		m_Config.Get().set("arena.firstCorner", zone.m_Corner1);
		m_Config.Get().set("arena.secondCorner", zone.m_Corner2);
	}
	
	public Zone LoadArenaLocation()
	{
		if(m_Config.Get().contains("arena.firstCorner") && m_Config.Get().contains("arena.secondCorner"))
		{
			Zone zone = new Zone(null, null);
			zone.m_Corner1 = m_Config.Get().getVector("arena.firstCorner");
			zone.m_Corner2 = m_Config.Get().getVector("arena.secondCorner");
			m_Config.Save();
			return zone;
		}
		else
		{
			return null;
		}
	}
}
