/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the ReCubed Mod.
 *
 * ReCubed is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * File Created @ [Dec 13, 2013, 3:09:46 PM (GMT)]
 */
package vazkii.recubed.common.network;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import vazkii.recubed.common.ReCubed;
import vazkii.recubed.common.lib.LibMisc;
import vazkii.recubed.common.network.packet.IPacket;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

public class PacketManager implements IPacketHandler {

	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
		try {
			ByteArrayInputStream stream = new ByteArrayInputStream(packet.data);
			ObjectInputStream objStream = new ObjectInputStream(stream);
			IPacket ipacket = (IPacket) objStream.readObject();

			ReCubed.proxy.handlePacket(manager, player, ipacket);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Packet250CustomPayload buildPacket(IPacket ipacket) {
		try {
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			ObjectOutputStream objStream = new ObjectOutputStream(stream);

			objStream.writeObject(ipacket);
			Packet250CustomPayload packet = new Packet250CustomPayload(LibMisc.NETWORK_CHANNEL, stream.toByteArray());
			return packet;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void dispatchToClient(IPacket ipacket, Player player) {
		Packet250CustomPayload packet = buildPacket(ipacket);
		PacketDispatcher.sendPacketToPlayer(packet, player);
	}

	public static void dispatchToAllClients(IPacket ipacket) {
		Packet250CustomPayload packet = buildPacket(ipacket);
		PacketDispatcher.sendPacketToAllPlayers(packet);
	}

}
