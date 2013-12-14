/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the ReCubed Mod.
 * 
 * ReCubed is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 * 
 * File Created @ [Dec 14, 2013, 1:36:48 PM (GMT)]
 */
package vazkii.recubed.common.core.handler;

import java.io.File;

import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;

public final class ConfigHandler {

	private static final String CATEGORY_SERVER = "server";
	
	public static Configuration config;
	
	public static int packetInterval = 40;
	
	public static void load(File file) {
		config = new Configuration(file);
		
		config.load();
		
		Property propPacketInterval = config.get(CATEGORY_SERVER, "packet_interval", packetInterval);
		propPacketInterval.comment = "The interval in which packets are sent to client, default is 40 ticks (2 secs)";
		packetInterval = propPacketInterval.getInt(packetInterval);
		
		config.save();
	}
	
}
