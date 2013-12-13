/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the ReCubed Mod.
 * 
 * ReCubed is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 * 
 * File Created @ [Dec 13, 2013, 2:33:09 PM (GMT)]
 */
package vazkii.recubed.client.core.proxy;

import net.minecraft.network.INetworkManager;
import vazkii.recubed.common.core.proxy.CommonProxy;
import vazkii.recubed.common.network.packet.IPacket;
import cpw.mods.fml.common.network.Player;

public class ClientProxy extends CommonProxy {

	@Override
	public void serverStarted() {
		// NO-OP
	}
	
	@Override
	public void handlePacket(INetworkManager manager, Player player, IPacket packet) {
		packet.handle(manager, player);
	}
}
