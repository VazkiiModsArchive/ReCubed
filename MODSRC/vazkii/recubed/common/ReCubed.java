/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the ReCubed Mod.
 * 
 * ReCubed is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 * 
 * File Created @ [Dec 13, 2013, 2:29:53 PM (GMT)]
 */
package vazkii.recubed.common;

import vazkii.recubed.common.core.proxy.CommonProxy;
import vazkii.recubed.common.lib.LibMisc;
import vazkii.recubed.common.network.PacketManager;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLServerStoppedEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(modid = LibMisc.MOD_ID, name = LibMisc.MOD_NAME, version = LibMisc.MOD_VERSION)
@NetworkMod(clientSideRequired = true, channels = { LibMisc.NETWORK_CHANNEL }, packetHandler = PacketManager.class)
public class ReCubed {

	@SidedProxy(clientSide = LibMisc.CLIENT_PROXY, serverSide = LibMisc.COMMON_PROXY)
	public static CommonProxy proxy;
	
	@Instance(LibMisc.MOD_ID)
	public static ReCubed instance;
	
	@EventHandler
	public void onPreInit(FMLPreInitializationEvent event) {
		proxy.preInit(event);
	}
	
	@EventHandler
	public void onInit(FMLInitializationEvent event) {
		proxy.init(event);
	}
	
	@EventHandler
	public void onServerStarting(FMLServerStartingEvent event) {
		proxy.serverStarting();
	}
	
	@EventHandler
	public void onServerStarting(FMLServerStartedEvent event) {
		proxy.serverStarted();
	}
	
	@EventHandler
	public void onServerStarting(FMLServerStoppedEvent event) {
		proxy.serverStopped();
	}
}
