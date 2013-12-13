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

import vazkii.recubed.api.internal.ServerData;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.IPlayerTracker;

public final class PlayerTracker implements IPlayerTracker {

	@Override
	public void onPlayerLogin(EntityPlayer player) {
		ServerData.onPlayerLogin(player.username);
	}

	@Override
	public void onPlayerLogout(EntityPlayer player) {
		// NO-OP
	}

	@Override
	public void onPlayerChangedDimension(EntityPlayer player) {
		// NO-OP
	}

	@Override
	public void onPlayerRespawn(EntityPlayer player) {
		// NO-OP
	}

}
