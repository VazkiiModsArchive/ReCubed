/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the ReCubed Mod.
 * 
 * ReCubed is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 * 
 * File Created @ [Dec 13, 2013, 5:37:38 PM (GMT)]
 */
package vazkii.recubed.client.gui;

import org.lwjgl.opengl.GL11;

import vazkii.recubed.common.lib.LibMisc;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.StatCollector;

public class GuiCategoryList extends GuiScreen {

	private GuiCategorySlot slot;
	
	int selectedCategory = 0; 
	
	public int guiWidth = 400;
	public int guiHeight = 200;
	public int x, y;
	
	@Override
	public void initGui() {
		super.initGui();
		
		x = width / 2 - guiWidth / 2;
		y = height / 2 - guiHeight / 2;
		
		slot = new GuiCategorySlot(this);
		selectCategory(0);
	}
	
	public void selectCategory(int category) {
		selectedCategory = category;
	}
	
	public boolean isCategorySelected(int category) {
		return selectedCategory == category;
	}
	
	@Override
	public void drawScreen(int par1, int par2, float par3) {
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		drawRect(x, y, x + guiWidth, y + guiHeight, 0x99000000);
		GL11.glDisable(GL11.GL_BLEND);
		
		slot.drawScreen(par1, par2, par3);
		
		super.drawScreen(par1, par2, par3);
	}
}
