/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the ReCubed Mod.
 *
 * ReCubed is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * File Created @ [Dec 14, 2013, 2:32:08 PM (GMT)]
 */
package vazkii.recubed.client.gui;

import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import vazkii.recubed.api.internal.Category;
import vazkii.recubed.api.internal.PlayerCategoryData;
import vazkii.recubed.client.core.handler.ClientCacheHandler;

public class GuiStatSelector extends GuiStatViewer {

	@Override
	public void drawScreen(int par1, int par2, float par3) {
		super.drawScreen(par1, par2, par3);

		drawCenteredString(fontRendererObj, EnumChatFormatting.GOLD + StatCollector.translateToLocal("recubed.misc.enter"), x + guiWidth / 2, y + guiHeight + 5, 0xFFFFFF);
	}

	@Override
	protected void keyTyped(char par1, int par2) {
		super.keyTyped(par1, par2);
		if(par2 == 28) {
			if(category instanceof Category) {
				ClientCacheHandler.hudCategory = ((Category) category).name;
				ClientCacheHandler.hudPlayer = "";
				ClientCacheHandler.findCompoundAndWrite();
			} else {
				Category category = fromCurrentCategoryInt();
				ClientCacheHandler.hudCategory = category.name;
				ClientCacheHandler.hudPlayer = ((PlayerCategoryData) this.category).name;
				ClientCacheHandler.findCompoundAndWrite();
			}
		}
	}

}
