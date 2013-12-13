/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the ReCubed Mod.
 * 
 * ReCubed is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 * 
 * File Created @ [Dec 13, 2013, 2:33:09 PM (GMT)]
 */
package vazkii.recubed.client.core.proxy;

import net.minecraft.network.INetworkManager;
import vazkii.recubed.client.core.handler.ClientCacheHandler;
import vazkii.recubed.client.core.handler.ClientTickHandler;
import vazkii.recubed.client.core.handler.LocalizationHandler;
import vazkii.recubed.common.core.proxy.CommonProxy;
import vazkii.recubed.common.network.packet.IPacket;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

public class ClientProxy extends CommonProxy {
	
	@Override
	public void init(FMLInitializationEvent event) {
		super.init(event);
		LocalizationHandler.loadLangs();
		TickRegistry.registerTickHandler(new ClientTickHandler(), Side.CLIENT);
		
		ClientCacheHandler.findCompoundAndLoad();
	}
	
	@Override
	public void handlePacket(INetworkManager manager, Player player, IPacket packet) {
		packet.handle(manager, player);
	}
	
}
