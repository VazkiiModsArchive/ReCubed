/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the ReCubed Mod.
 *
 * ReCubed is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * File Created @ [Dec 13, 2013, 3:01:44 PM (GMT)]
 */
package vazkii.recubed.common.core.handler;

import net.minecraft.server.MinecraftServer;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.world.WorldEvent;
import vazkii.recubed.common.core.helper.CacheHelper;
import vazkii.recubed.common.network.PacketManager;
import vazkii.recubed.common.network.packet.IPacket;
import vazkii.recubed.common.network.packet.PacketCategory;

public final class WorldSaveHandler {

	@ForgeSubscribe
	public void onWorldSave(WorldEvent.Save event) {
		MinecraftServer server = MinecraftServer.getServer();

		if(event.world == server.worldServers[0]) {
			CacheHelper.findCompoundAndWrite();

			for(IPacket packet : PacketCategory.allCategoryPackets())
				PacketManager.dispatchToAllClients(packet);
		}
	}

}
