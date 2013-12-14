/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the ReCubed Mod.
 * 
 * ReCubed is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 * 
 * File Created @ [Dec 14, 2013, 1:43:42 PM (GMT)]
 */
package vazkii.recubed.common.core.handler;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.entity.player.EntityPlayer;
import vazkii.recubed.common.core.helper.PlayerLastTickData;

public final class PlayerTickHandler {

	public static Map<String, PlayerLastTickData> playerData = new HashMap();
	
	public static void playerTicked(EntityPlayer player) {
		playerData.get(player.username).tickPlayer(player);
	}
	
}
