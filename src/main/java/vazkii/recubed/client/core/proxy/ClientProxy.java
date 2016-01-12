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

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import vazkii.recubed.api.internal.ClientData;
import vazkii.recubed.client.core.handler.ClientCacheHandler;
import vazkii.recubed.client.core.handler.ClientTickHandler;
import vazkii.recubed.client.core.handler.HUDHandler;
import vazkii.recubed.client.core.handler.KeybindHandler;
import vazkii.recubed.client.core.handler.LocalizationHandler;
import vazkii.recubed.common.core.handler.ConfigHandler;
import vazkii.recubed.common.core.proxy.CommonProxy;
import vazkii.recubed.common.network.PacketCategory;

public class ClientProxy extends CommonProxy {

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);

		if(!ConfigHandler.useCogwheel)
			FMLCommonHandler.instance().bus().register(new KeybindHandler());
	}

	@Override
	public void init(FMLInitializationEvent event) {
		super.init(event);
		LocalizationHandler.loadLangs();
		FMLCommonHandler.instance().bus().register(new ClientTickHandler());
		MinecraftForge.EVENT_BUS.register(new HUDHandler());

		ClientCacheHandler.findCompoundAndLoad();
	}

	@Override
	public void receivePacket(PacketCategory packet) {
		ClientData.categories.put(packet.category.name, packet.category);
	}

}
