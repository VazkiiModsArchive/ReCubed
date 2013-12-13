/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the ReCubed Mod.
 * 
 * ReCubed is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 * 
 * File Created @ [Dec 13, 2013, 5:37:44 PM (GMT)]
 */
package vazkii.recubed.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.StatCollector;
import vazkii.recubed.api.ReCubedAPI;

public class GuiCategorySlot extends GuiScrollingList {

	GuiCategoryList parent;
	
	public GuiCategorySlot(GuiCategoryList parent) {
		super(Minecraft.getMinecraft(), 100, parent.guiHeight, parent.y, parent.y + parent.guiHeight, parent.x, 16);
		this.parent = parent;
	}

	@Override
	protected int getSize() {
		return ReCubedAPI.clientData.size();
	}
	
	@Override
	protected int getContentHeight() {
		return getSize() * 16;
	}

	@Override
	protected void elementClicked(int i, boolean flag) {
		parent.selectCategory(i);
	}

	@Override
	protected boolean isSelected(int i) {
		return parent.isCategorySelected(i);
	}

	@Override
	protected void drawBackground() {
		// NO-OP
	}

	@Override
	protected void drawSlot(int i, int j, int k, int l, Tessellator tessellator) {
		Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(StatCollector.translateToLocal(ReCubedAPI.categories.get(i)), j - 85, k + 2, 0xFFFFFF);
	}
}
