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
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
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
			WorldRenderer wr = Tessellator.getInstance().getWorldRenderer();
	        wr.begin(7, DefaultVertexFormats.POSITION_TEX);
			wr.pos(x, y + 16, zLevel).tex(0, 1).endVertex();
			wr.pos(x + 16, y + 16, zLevel).tex(1, 1).endVertex();
			wr.pos(x + 16, y, zLevel).tex(1, 0).endVertex();
			wr.pos(x, y, zLevel).tex(0, 0).endVertex();
			Tessellator.getInstance().draw();
			zLevel -= 1;
		}

		par1Minecraft.fontRendererObj.drawStringWithShadow(StatCollector.translateToLocal(text), xPosition + 25, yPosition + 7, 0xFFFFFF);
	}
}
