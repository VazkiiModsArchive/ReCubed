/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the ReCubed Mod.
 *
 * ReCubed is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * File Created @ [Dec 13, 2013, 2:45:24 PM (GMT)]
 */
package vazkii.recubed.common.core.handler;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import vazkii.recubed.api.ReCubedAPI;
import vazkii.recubed.api.internal.ServerData;
import vazkii.recubed.common.core.helper.PlayerLastTickData;
import vazkii.recubed.common.lib.LibCategories;
import vazkii.recubed.common.network.PacketCategory;
import vazkii.recubed.common.network.PacketHandler;

public final class PlayerTracker {

	@SubscribeEvent
	public void onPlayerLogin(PlayerLoggedInEvent event) {
		String name = event.player.getGameProfile().getName();
		System.out.println(name);
		ServerData.onPlayerLogin(name);

		PlayerLastTickData data = new PlayerLastTickData();
		data.setData(event.player);
		PlayerTickHandler.playerData.put(name, data);

		if(ReCubedAPI.validatePlayer(event.player))
			ReCubedAPI.addValueToCategory(LibCategories.TIMES_PLAYED, name, "recubed.misc.login", 1);

		for(PacketCategory packet : PacketCategory.allCategoryPackets())
			PacketHandler.INSTANCE.sendToAll(packet);
	}

	@SubscribeEvent
	public void onPlayerLogout(PlayerLoggedOutEvent event) {
		PlayerTickHandler.playerData.remove(event.player);
	}

	@SubscribeEvent
	public void onPlayerChangedDimension(PlayerChangedDimensionEvent event) {
		if(ReCubedAPI.validatePlayer(event.player))
			ReCubedAPI.addValueToCategory(LibCategories.DIMENSIONS_CHANGED, event.player.getGameProfile().getName(), event.player.worldObj.provider.getDimensionType().getName(), 1);
	}

}
