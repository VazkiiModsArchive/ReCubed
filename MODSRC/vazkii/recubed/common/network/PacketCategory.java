package vazkii.recubed.common.network;

import io.netty.buffer.ByteBuf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import vazkii.recubed.api.internal.Category;
import vazkii.recubed.api.internal.ServerData;
import vazkii.recubed.common.ReCubed;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PacketCategory implements IMessage, IMessageHandler<PacketCategory, IMessage> {

	public Category category;
	public String name;
	
	public PacketCategory() { }

	public PacketCategory(Category category) {
		this.category = category;
		name = category.name;
	}

	@Override
	public void toBytes(ByteBuf buffer) {
		PacketBuffer packet = new PacketBuffer(buffer);

		NBTTagCompound cmp = new NBTTagCompound();
		category.writeToNBT(cmp);
		try {
			packet.writeStringToBuffer(name);
			packet.writeNBTTagCompoundToBuffer(cmp);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Collection<PacketCategory> allCategoryPackets() {
		Collection<PacketCategory> packets = new ArrayList();
		for(String s : ServerData.categories.keySet())
			packets.add(new PacketCategory(ServerData.categories.get(s)));

		return packets;
	}

	@Override
	public void fromBytes(ByteBuf buffer) {
		PacketBuffer packet = new PacketBuffer(buffer);
		try {
			name = packet.readStringFromBuffer(32767);
			category = new Category(name);
			category.loadFromNBT(packet.readNBTTagCompoundFromBuffer());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public IMessage onMessage(PacketCategory message, MessageContext context) {
		ReCubed.proxy.receivePacket(message);
		return null;
	}

}