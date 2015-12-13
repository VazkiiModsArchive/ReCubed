/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the ReCubed Mod.
 *
 * ReCubed is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * File Created @ [Dec 14, 2013, 5:12:41 PM (GMT)]
 */
package vazkii.recubed.common.core.helper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import vazkii.recubed.api.ReCubedAPI;
import vazkii.recubed.common.lib.LibCategories;
import vazkii.recubed.common.lib.LibObfuscation;
import cpw.mods.fml.relauncher.ReflectionHelper;

public final class PlayerLastTickData {

	int xp;
	int level;
	boolean riding;
	List<Integer> potionEffects = new ArrayList();

	public void tickPlayer(EntityPlayer player) {
		if(!ReCubedAPI.validatePlayer(player))
			return;
		String name = player.getGameProfile().getName();

		// ENTITIES RIDDEN
		if(!riding && player.ridingEntity != null)
			ReCubedAPI.addValueToCategory(LibCategories.ENTITIES_RIDDEN, name, MiscHelper.getEntityString(player.ridingEntity), 1);

		// EXPERIENCE GATHERED
		if(player.experienceTotal > xp) {
			int extra = player.experienceTotal - xp;
			ReCubedAPI.addValueToCategory(LibCategories.EXPERIENCE_GATHERED, name, "recubed.misc.experience", extra);
		}

		// FOOD EATEN + POTIONS DRANK
		ItemStack itemInUse = ReflectionHelper.getPrivateValue(EntityPlayer.class, player, LibObfuscation.ITEM_IN_USE);
		int itemInUseCount = ReflectionHelper.getPrivateValue(EntityPlayer.class, player, LibObfuscation.ITEM_IN_USE_COUNT);
		if(itemInUse != null && itemInUseCount == 1) {
			Item item = itemInUse.getItem();

			if(item instanceof ItemFood)
				ReCubedAPI.addValueToCategory(LibCategories.FOOD_EATEN, name, MiscHelper.getStackName(itemInUse), 1);

			if(item instanceof ItemPotion) {
				List<PotionEffect> effects = ((ItemPotion) item).getEffects(itemInUse);
				if(effects != null)
					for(PotionEffect effect : effects)
						ReCubedAPI.addValueToCategory(LibCategories.POTIONS_DRANK, name, Potion.potionTypes[effect.getPotionID()].getName(), 1);
			}
		}

		// LEVELS GAINED
		if(player.experienceLevel > level) {
			int extra = player.experienceLevel - level;
			ReCubedAPI.addValueToCategory(LibCategories.LEVELS_GAINED, name, "recubed.misc.level", extra);
		}

		// POTIONS AFFECTED BY
		Collection<PotionEffect> effects = player.getActivePotionEffects();
		for(PotionEffect effect : effects) {
			if(!potionEffects.contains(effect.getPotionID()))
				ReCubedAPI.addValueToCategory(LibCategories.POTIONS_GOTTEN, name, Potion.potionTypes[effect.getPotionID()].getName(), 1);
		}

		setData(player);
	}

	public void setData(EntityPlayer player) {
		xp = player.experienceTotal;
		level = player.experienceLevel;
		riding = player.ridingEntity != null;

		potionEffects.clear();
		Collection<PotionEffect> effects = player.getActivePotionEffects();
		if(effects != null)
			for(PotionEffect effect : effects)
				potionEffects.add(effect.getPotionID());
	}

}
