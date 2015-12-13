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

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import vazkii.recubed.client.core.helper.SafeCallable;
import vazkii.recubed.client.lib.LibResources;

public class GuiCheckboxButton extends GuiButton {

	static ResourceLocation check = new ResourceLocation(LibResources.RESOURCE_CHECK);
	SafeCallable<Boolean> isChecked;
	String text;

	public GuiCheckboxButton(int par1, int par2, int par3, String text, SafeCallable<Boolean> isChecked) {
		super(par1, par2, par3, 20, 20, "");
		this.isChecked = isChecked;
		this.text = text;
	}

	@Override
	public void drawButton(Minecraft par1Minecraft, int par2, int par3) {
		super.drawButton(par1Minecraft, par2, par3);

		if(isChecked.call()) {
			par1Minecraft.renderEngine.bindTexture(check);
			int x = xPosition + 2;
			int y = yPosition + 2;

			zLevel += 1;
			Tessellator tess = Tessellator.instance;
			tess.startDrawingQuads();
			tess.addVertexWithUV(x, y + 16, zLevel, 0, 1);
			tess.addVertexWithUV(x + 16, y + 16, zLevel, 1, 1);
			tess.addVertexWithUV(x + 16, y, zLevel, 1, 0);
			tess.addVertexWithUV(x, y, zLevel, 0, 0);
			tess.draw();
			zLevel -= 1;
		}

		par1Minecraft.fontRenderer.drawStringWithShadow(StatCollector.translateToLocal(text), xPosition + 25, yPosition + 7, 0xFFFFFF);
	}
}
