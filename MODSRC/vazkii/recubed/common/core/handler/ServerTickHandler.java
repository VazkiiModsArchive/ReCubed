/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the ReCubed Mod.
 *
 * ReCubed is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * File Created @ [Dec 14, 2013, 1:43:30 PM (GMT)]
 */
package vazkii.recubed.common.core.handler;

import vazkii.recubed.common.core.helper.CacheHelper;
import vazkii.recubed.common.network.PacketCategory;
import vazkii.recubed.common.network.PacketHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ServerTickEvent;

public final class ServerTickHandler  {

	long ticksElapsed = 0;

	@SubscribeEvent
	public void playerTickEnd(PlayerTickEvent event) {
		if(event.phase == Phase.END)
			PlayerTickHandler.playerTicked(event.player);
	}

	@SubscribeEvent
	public void serverTickEnd(ServerTickEvent event) {
		if(event.phase == Phase.END) {
			if(ticksElapsed % ConfigHandler.packetInterval == 0) {
				CacheHelper.findCompoundAndWrite();

				for(PacketCategory packet : PacketCategory.allCategoryPackets())
					PacketHandler.INSTANCE.sendToAll(packet);
			}

			++ticksElapsed;
		}
	}

}
