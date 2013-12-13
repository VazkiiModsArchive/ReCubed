/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the ReCubed Mod.
 * 
 * ReCubed is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 * 
 * File Created @ [Dec 13, 2013, 3:56:55 PM (GMT)]
 */
package vazkii.recubed.client.core.handler;

import cpw.mods.fml.common.registry.LanguageRegistry;
import vazkii.recubed.client.lib.LibResources;

public final class LocalizationHandler {
	
	public static void loadLangs() {
		for(String s : LibResources.LANGS)
			LanguageRegistry.instance().loadLocalization(LibResources.LOCALIZATION_LOC + s, s, false);
	}
	
}
