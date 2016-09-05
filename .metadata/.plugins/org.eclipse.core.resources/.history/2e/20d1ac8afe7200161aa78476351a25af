package me.struttle.plugins.events;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Log {
	private static Logger ms_Logger = null;
	public static void Initalize(Logger logger)
	{
		ms_Logger = logger;
	}
	
	public static void Debug(String string)
	{
		if(ms_Logger != null)
		{
			ms_Logger.log(Level.INFO, string);
		}
	}
	
	public static void Error(String string)
	{
		if(ms_Logger != null)
		{
			ms_Logger.log(Level.SEVERE, string);
		}
	}
	
	public static void Error(String string, Exception ex)
	{
		if(ms_Logger != null)
		{
			ms_Logger.log(Level.SEVERE, string, ex);
		}
	}
}
