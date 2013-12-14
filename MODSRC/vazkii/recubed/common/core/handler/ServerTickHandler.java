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

import java.util.EnumSet;

import net.minecraft.entity.player.EntityPlayer;
import vazkii.recubed.common.core.helper.CacheHelper;
import vazkii.recubed.common.network.PacketManager;
import vazkii.recubed.common.network.packet.IPacket;
import vazkii.recubed.common.network.packet.PacketCategory;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public final class ServerTickHandler implements ITickHandler {

	long ticksElapsed = 0;

	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {
		// NO-OP
	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {
		if(type.equals(EnumSet.of(TickType.PLAYER)))
			PlayerTickHandler.playerTicked((EntityPlayer) tickData[0]);
		else {
			if(ticksElapsed % ConfigHandler.packetInterval == 0) {
				CacheHelper.findCompoundAndWrite();

				for(IPacket packet : PacketCategory.allCategoryPackets())
					PacketManager.dispatchToAllClients(packet);
			}

			++ticksElapsed;
		}
	}

	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.SERVER, TickType.PLAYER);
	}

	@Override
	public String getLabel() {
		return "ReCubed_server";
	}

}
