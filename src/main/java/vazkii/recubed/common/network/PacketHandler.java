package vazkii.recubed.common.network;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import vazkii.recubed.common.lib.LibMisc;

public class PacketHandler {

	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(LibMisc.MOD_ID);

	public static void init() {
		INSTANCE.registerMessage(PacketCategory.class, PacketCategory.class, 0, Side.CLIENT);
	}
}
