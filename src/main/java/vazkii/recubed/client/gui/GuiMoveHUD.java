/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the ReCubed Mod.
 *
 * ReCubed is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * File Created @ [Dec 14, 2013, 3:05:21 PM (GMT)]
 */
package vazkii.recubed.client.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.StatCollector;
import vazkii.recubed.client.core.handler.ClientCacheHandler;

public class GuiMoveHUD extends GuiScreen {

	int originalPosX, originalPosY, originalRelativePos;

	public GuiMoveHUD() {
		originalPosX = ClientCacheHandler.hudPosX;
		originalPosY = ClientCacheHandler.hudPosY;
		originalRelativePos = ClientCacheHandler.hudRelativeTo;
	}

	@Override
	public void drawScreen(int par1, int par2, float par3) {
		super.drawScreen(par1, par2, par3);
		int mx = par1;
		int my = par2;
		int quadrant = getQuadrant(width, height, mx, my);

		drawCenteredString(fontRendererObj, StatCollector.translateToLocal("recubed.misc.click_to_set"), width / 2, 10, 0xFFFFFF);
		drawCenteredString(fontRendererObj, StatCollector.translateToLocal("recubed.misc.escape_to_reset"), width / 2, 21, 0xFFFFFF);

		switch(quadrant) {
		case 0 : {
			mx = width - mx;
			break;
		}
		case 1 : break;
		case 2 : {
			my = height - my;
			break;
		}
		case 3 : {
			mx = width - mx;
			my = height - my;
			break;
		}
		}

		fontRendererObj.drawStringWithShadow("W", 0, 0, 0xFFFFFF);
		fontRendererObj.drawStringWithShadow("A", 0, height - 9, 0xFFFFFF);
		fontRendererObj.drawStringWithShadow("S", width - 6, height - 9, 0xFFFFFF);
		fontRendererObj.drawStringWithShadow("D", width - 6, 0, 0xFFFFFF);

		ClientCacheHandler.hudPosX = mx;
		ClientCacheHandler.hudPosY = my;
		ClientCacheHandler.hudRelativeTo = quadrant;
	}

	@Override
	protected void mouseClicked(int par1, int par2, int par3) {
		if(par3 == 0) {
			ClientCacheHandler.findCompoundAndWrite();
			mc.displayGuiScreen(new GuiReCubedMenu());
		}
		super.mouseClicked(par1, par2, par3);
	}

	@Override
	protected void keyTyped(char par1, int par2) {
		if(par2 == 1) {
			ClientCacheHandler.hudPosX = originalPosX;
			ClientCacheHandler.hudPosY = originalPosY;
			ClientCacheHandler.hudRelativeTo = originalRelativePos;
			ClientCacheHandler.findCompoundAndWrite();
			mc.displayGuiScreen(new GuiReCubedMenu());
		}

		switch(par1) {
		case 'w' : {
			ClientCacheHandler.hudPosX = 0;
			ClientCacheHandler.hudPosY = 0;
			ClientCacheHandler.hudRelativeTo = 1;

			ClientCacheHandler.findCompoundAndWrite();
			mc.displayGuiScreen(new GuiReCubedMenu());
			return;
		}
		case 'a' : {
			ClientCacheHandler.hudPosX = 0;
			ClientCacheHandler.hudPosY = 98;
			ClientCacheHandler.hudRelativeTo = 2;

			ClientCacheHandler.findCompoundAndWrite();
			mc.displayGuiScreen(new GuiReCubedMenu());
			return;
		}
		case 's' : {
			ClientCacheHandler.hudPosX = 100;
			ClientCacheHandler.hudPosY = 98;
			ClientCacheHandler.hudRelativeTo = 3;

			ClientCacheHandler.findCompoundAndWrite();
			mc.displayGuiScreen(new GuiReCubedMenu());
			return;
		}
		case 'd' : {
			ClientCacheHandler.hudPosX = 100;
			ClientCacheHandler.hudPosY = 0;
			ClientCacheHandler.hudRelativeTo = 0;

			ClientCacheHandler.findCompoundAndWrite();
			mc.displayGuiScreen(new GuiReCubedMenu());
			return;
		}
		}
	}

	private int getQuadrant(int width, int height, int mx, int my) {
		boolean xpasses = mx >= width / 2;
		boolean ypasses = my >= height / 2;

		if(xpasses) {
			if(ypasses)
				return 3;
			return 0;
		}

		if(ypasses)
			return 2;
		return 1;
	}

}
