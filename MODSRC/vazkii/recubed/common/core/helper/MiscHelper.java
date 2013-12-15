/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the ReCubed Mod.
 *
 * ReCubed is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * File Created @ [Dec 13, 2013, 4:18:48 PM (GMT)]
 */
package vazkii.recubed.common.core.helper;

import java.awt.Color;
import java.util.Comparator;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import vazkii.recubed.common.core.handler.ConfigHandler;

public final class MiscHelper {

	public static String getEntityString(Entity entity) {
		if(entity instanceof EntityPlayer)
			return ((EntityPlayer) entity).username;

		return "entity." + EntityList.getEntityString(entity) + ".name";
	}

	public static int generateColorFromString(String seed) {
		Random rand = new Random(seed.hashCode());
		int red = rand.nextInt(256);
		int green = rand.nextInt(256);
		int blue = rand.nextInt(256);

		return new Color(red, green, blue).getRGB();
	}

	public static <K, V> TreeMap<K, V> sortMap(Map<K, V> map, Comparator<K> comparator) {
		TreeMap<K, V> treemap = new TreeMap(comparator);
		for(K key : map.keySet())
			treemap.put(key, map.get(key));

		return treemap;
	}

	public static boolean isPlayerAllowedToUseCommands(String player) {
		return ConfigHandler.commandPlayers.isEmpty() || ConfigHandler.commandPlayers.contains(player.toLowerCase());
	}
}
