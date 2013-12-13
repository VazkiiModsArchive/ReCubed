/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the ReCubed Mod.
 * 
 * ReCubed is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 * 
 * File Created @ [Dec 13, 2013, 2:32:54 PM (GMT)]
 */
package vazkii.recubed.common.core.proxy;

import net.minecraft.network.INetworkManager;
import net.minecraftforge.common.MinecraftForge;
import vazkii.recubed.api.ReCubedAPI;
import vazkii.recubed.api.internal.ServerData;
import vazkii.recubed.common.core.handler.GeneralEventHandler;
import vazkii.recubed.common.core.handler.WorldSaveHandler;
import vazkii.recubed.common.core.helper.CacheHelper;
import vazkii.recubed.common.lib.LibCategories;
import vazkii.recubed.common.network.PlayerTracker;
import vazkii.recubed.common.network.packet.IPacket;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.common.registry.GameRegistry;

public class CommonProxy {

	public void preInit(FMLPreInitializationEvent event) {
		ReCubedAPI.registerCategory(LibCategories.DAMAGE_DEALT);
		ReCubedAPI.registerCategory(LibCategories.DAMAGE_TAKEN);
		ReCubedAPI.registerCategory(LibCategories.TIMES_DIED);
	}
	
	public void init(FMLInitializationEvent event) {
		GameRegistry.registerPlayerTracker(new PlayerTracker());
		MinecraftForge.EVENT_BUS.register(new WorldSaveHandler());
		MinecraftForge.EVENT_BUS.register(new GeneralEventHandler());
	}
	
	public void serverStarting() {
		ServerData.init();
	}
	
	public void serverStarted() {
		CacheHelper.findCompoundAndLoad();
	}
	
	public void serverStopping() {
		CacheHelper.findCompoundAndWrite();
	}
	
	public void serverStopped() {
		ServerData.reset();
	}
	
	public void handlePacket(INetworkManager manager, Player player, IPacket packet) {
		// NO-OP
	}
}
