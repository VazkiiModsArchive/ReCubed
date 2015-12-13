/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the ReCubed Mod.
 *
 * ReCubed is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * File Created @ [Dec 13, 2013, 4:04:23 PM (GMT)]
 */
package vazkii.recubed.client.core.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import vazkii.recubed.api.internal.ClientData;
import vazkii.recubed.client.renders.InventoryCogwheelRender;
import vazkii.recubed.common.core.handler.ConfigHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.RenderTickEvent;

public class ClientTickHandler {

	@SubscribeEvent
	public void clientTickEnd(ClientTickEvent event) {
		if(event.phase == Phase.END) {
			World world = Minecraft.getMinecraft().theWorld;
			if(world == null)
				ClientData.categories.clear();
		}
	}

	@SubscribeEvent
	public void renderTickEnd(RenderTickEvent event) {
		if(ConfigHandler.useCogwheel)
			InventoryCogwheelRender.renderCogwheel();
	}

}
