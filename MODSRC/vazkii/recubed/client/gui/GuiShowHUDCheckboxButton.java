/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the ReCubed Mod.
 * 
 * ReCubed is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 * 
 * File Created @ [Dec 13, 2013, 5:21:53 PM (GMT)]
 */
package vazkii.recubed.client.gui;

import vazkii.recubed.client.lib.LibResources;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;

public class GuiShowHUDCheckboxButton extends GuiButton {

	static ResourceLocation cogwheel = new ResourceLocation(LibResources.RESOURCE_CHECK);
	
	public GuiShowHUDCheckboxButton(int par1, int par2, int par3) {
		super(par1, par2, par3, 20, 20, "");
	}

}
