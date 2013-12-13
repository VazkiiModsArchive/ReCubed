/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the ReCubed Mod.
 * 
 * ReCubed is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 * 
 * File Created @ [Dec 13, 2013, 3:09:54 PM (GMT)]
 */
package vazkii.recubed.common.network.packet;

import java.io.Serializable;

import net.minecraft.network.INetworkManager;
import cpw.mods.fml.common.network.Player;

public interface IPacket extends Serializable {

	public void handle(INetworkManager manager, Player player);
	
}
