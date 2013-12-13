/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the ReCubed Mod.
 * 
 * ReCubed is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 * 
 * File Created @ [Dec 13, 2013, 4:02:06 PM (GMT)]
 */
package vazkii.recubed.common.network.packet;

import java.util.ArrayList;
import java.util.Collection;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import vazkii.recubed.api.ReCubedAPI;
import vazkii.recubed.api.internal.Category;
import vazkii.recubed.api.internal.PlayerCategoryData;
import vazkii.recubed.api.internal.ServerData;
import cpw.mods.fml.common.network.Player;

public class PacketCategory implements IPacket {

	Category category;
	
	public PacketCategory(Category category) {
		this.category = category;
	}
	
	@Override
	public void handle(INetworkManager manager, Player player) {
		ReCubedAPI.clientData.put(category.name, category);
	}

	public static Collection<IPacket> allCategoryPackets() {
		Collection<IPacket> packets = new ArrayList();
		for(String s : ServerData.categories.keySet())
			packets.add(new PacketCategory(ServerData.categories.get(s)));
		
		return packets;
	}
	
}
