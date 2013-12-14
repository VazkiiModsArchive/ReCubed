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
package vazkii.recubed.common.network;

import net.minecraft.entity.player.EntityPlayer;
import vazkii.recubed.api.ReCubedAPI;
import vazkii.recubed.api.internal.ServerData;
import vazkii.recubed.common.core.handler.PlayerTickHandler;
import vazkii.recubed.common.core.helper.PlayerLastTickData;
import vazkii.recubed.common.lib.LibCategories;
import vazkii.recubed.common.network.packet.IPacket;
import vazkii.recubed.common.network.packet.PacketCategory;
import cpw.mods.fml.common.IPlayerTracker;
import cpw.mods.fml.common.network.Player;

public final class PlayerTracker implements IPlayerTracker {

	@Override
	public void onPlayerLogin(EntityPlayer player) {
		ServerData.onPlayerLogin(player.username);

		PlayerLastTickData data = new PlayerLastTickData();
		data.setData(player);
		PlayerTickHandler.playerData.put(player.username, data);

		if(ReCubedAPI.validatePlayer(player))
			ReCubedAPI.addValueToCategory(LibCategories.TIMES_PLAYED, player.username, "recubed.misc.login", 1);

		for(IPacket packet : PacketCategory.allCategoryPackets())
			PacketManager.dispatchToClient(packet, (Player) player);
	}

	@Override
	public void onPlayerLogout(EntityPlayer player) {
		PlayerTickHandler.playerData.remove(player.username);
	}

	@Override
	public void onPlayerChangedDimension(EntityPlayer player) {
		if(ReCubedAPI.validatePlayer(player))
			ReCubedAPI.addValueToCategory(LibCategories.DIMENSIONS_CHANGED, player.username, player.worldObj.provider.getDimensionName(), 1);
	}

	@Override
	public void onPlayerRespawn(EntityPlayer player) {
		// NO-OP
	}

}
