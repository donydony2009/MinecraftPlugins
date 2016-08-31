package me.struttle.plugins.events;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class CustomConfigFile {
	private FileConfiguration m_Config = null;
	private File m_ConfigFile = null;
	private String m_Name;
	private File m_DataFolder = null;
	public CustomConfigFile(File dataFolder, String name)
	{
		m_Name = name + ".yml";
		m_DataFolder = dataFolder;
		Reload();
	}
	
	public void Reload() {
	    if (m_ConfigFile == null) {
	    	m_ConfigFile = new File(m_DataFolder, m_Name);
	    }
	    m_Config = YamlConfiguration.loadConfiguration(m_ConfigFile);

	    // Look for defaults in the jar
	    // For now no defaults
	    /*Reader defConfigStream = new InputStreamReader(this.getResource(m_Name), "UTF8");
	    if (defConfigStream != null) {
	        YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
	        m_Config.setDefaults(defConfig);
	    }*/
	}
	
	public FileConfiguration Get() {
	    if (m_Config == null) {
	        Reload();
	    }
	    return m_Config;
	}
	
	public void Save() {
	    if (m_Config == null || m_ConfigFile == null) {
	        return;
	    }
	    try {
	        Get().save(m_ConfigFile);
	    } catch (IOException ex) {
	    	Log.Error("Could not save config to " + m_ConfigFile, ex);
	    }
	}
}
