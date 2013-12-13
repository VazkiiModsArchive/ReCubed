/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the ReCubed Mod.
 * 
 * ReCubed is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 * 
 * File Created @ [Dec 13, 2013, 4:54:27 PM (GMT)]
 */
package vazkii.recubed.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import vazkii.recubed.client.core.handler.ClientCacheHandler;
import vazkii.recubed.common.lib.LibMisc;

public class GuiReCubedMenu extends GuiScreen {

	int guiWidth = 200;
	int guiHeight = 200;
	int x, y;
	
	@Override
	public void initGui() {
		super.initGui();
		
		x = width / 2 - guiWidth / 2;
		y = height / 2 - guiHeight / 2;
		
		buttonList.clear();
		buttonList.add(new GuiButton(0, x + 20, y + 60, 160, 20, StatCollector.translateToLocal("recubed.misc.view_stats")));
		buttonList.add(new GuiButton(1, x + 20, y + 90, 160, 20, StatCollector.translateToLocal("recubed.misc.set_tracked")));
		buttonList.add(new GuiButton(2, x + 20, y + 120, 160, 20, StatCollector.translateToLocal("recubed.misc.move_hud")));
		buttonList.add(new GuiShowHUDCheckboxButton(3, x + 20, y + 150));
	}
	
	@Override
	protected void actionPerformed(GuiButton par1GuiButton) {
		switch(par1GuiButton.id) {
			case 0 : {
				mc.displayGuiScreen(new GuiStatViewer());
				return;
			}
			case 1 : {
				
			}
			case 2 : {
				
			}
			case 3 : {
				ClientCacheHandler.drawHud = !ClientCacheHandler.drawHud;
				ClientCacheHandler.findCompoundAndWrite();
			}
		}
	}
	
	@Override
	public void drawScreen(int par1, int par2, float par3) {
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		drawRect(x, y, x + guiWidth, y + guiHeight, 0x99000000);
		GL11.glDisable(GL11.GL_BLEND);
		
		GL11.glScalef(2F, 2F, 2F);
		drawCenteredString(fontRenderer, LibMisc.MOD_NAME, (x + guiWidth / 2) / 2, (y + 10) / 2, 0xFF0000);
		GL11.glScalef(0.5F, 0.5F, 0.5F);
		drawCenteredString(fontRenderer, StatCollector.translateToLocal("recubed.misc.credits"), x + guiWidth / 2, y + 30, 0xFF6666);
		
		super.drawScreen(par1, par2, par3);
	}
	
}
