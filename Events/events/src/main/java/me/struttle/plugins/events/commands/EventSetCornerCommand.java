package me.struttle.plugins.events.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import me.struttle.plugins.events.helper.Zone;
import net.md_5.bungee.api.ChatColor;

public class EventSetCornerCommand extends CommandBase {
	private Vector m_FirstCorner = null;
	public EventSetCornerCommand() 
	{
		super(
			new ArrayList<String>()
			{
				private static final long serialVersionUID = 1L;
				{
					add("event");
					add("setcorner");
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
	
	Vector GetPosition(Block block)
	{
		return new Vector(block.getX(), block.getY(), block.getZ());
	}
	
	Vector GetFirstBlockPosition(List<Block> blocks)
	{
		for(Block block : blocks)
		{
			if(block.getType() != Material.AIR)
				return GetPosition(block);
		}
		return null;
	}
	
	@Override
	public boolean ExecuteInternal(Player player, String[] args, int indexStartingArguments) {
		Vector blockPos = GetFirstBlockPosition(player.getLineOfSight((Set<Material>)null, 10));
		if(m_FirstCorner == null)
		{
			if(blockPos == null)
			{
				player.sendMessage(ChatColor.DARK_RED + "There is no block in your line of sight");
			}
			else
			{
				m_FirstCorner = blockPos;
				player.sendMessage(ChatColor.GREEN + "First corner is set to ("
						+ m_FirstCorner.getBlockX() + ", "
						+ m_FirstCorner.getBlockY() + ", "
						+ m_FirstCorner.getBlockZ() + ")");
			}
		}
		else
		{
			if(blockPos == null)
			{
				player.sendMessage(ChatColor.DARK_RED + "There is no block in your line of sight");
			}
			else
			{
				player.sendMessage(ChatColor.GREEN + "Second corner is set to ("
						+ blockPos.getBlockX() + ", "
						+ blockPos.getBlockY() + ", "
						+ blockPos.getBlockZ() + "). The arena coords were saved.");
				m_Plugin.DefineArena(new Zone(m_FirstCorner, blockPos));
				m_FirstCorner = null;
			}
		}

		return true;
	}

}
