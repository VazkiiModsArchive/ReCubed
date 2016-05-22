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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import vazkii.recubed.api.ReCubedAPI;
import vazkii.recubed.api.internal.Category;
import vazkii.recubed.api.internal.ClientData;

public class GuiCategoryList extends GuiScreen {

	GuiCategorySlot slot;

	int selectedCategory = 0;

	public int guiWidth = 400;
	public int guiHeight = 200;
	public int x, y;

	List<Integer> indexes = new ArrayList();

	@Override
	public void initGui() {
		super.initGui();

		x = width / 2 - guiWidth / 2;
		y = height / 2 - guiHeight / 2;

		sortIndexMappings();
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

	private void sortIndexMappings() {
		indexes.clear();
		for(int i = 0; i < ReCubedAPI.categories.size(); i++)
			indexes.add(i);

		Collections.sort(indexes, new Comparator<Integer>() {

			@Override
			public int compare(Integer a, Integer b) {
				String categoryA = I18n.format(ReCubedAPI.categories.get(a));
				String categoryB = I18n.format(ReCubedAPI.categories.get(b));

				return categoryA.compareTo(categoryB);
			}

		});
	}

	Category fromCurrentCategoryInt() {
		return ClientData.categories.get(ReCubedAPI.categories.get(indexes.get(selectedCategory)));
	}
}
