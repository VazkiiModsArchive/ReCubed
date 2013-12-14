/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the ReCubed Mod.
 *
 * ReCubed is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * File Created @ [Dec 13, 2013, 5:47:04 PM (GMT)]
 */
package vazkii.recubed.client.gui;

import java.util.Map;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import vazkii.recubed.api.internal.Category;
import vazkii.recubed.api.internal.PlayerCategoryData;
import vazkii.recubed.client.renders.PieChartRender;
import vazkii.recubed.client.renders.PieChartRender.Entry;
import vazkii.recubed.common.core.helper.MiscHelper;

public class GuiStatViewer extends GuiCategoryList {

	Object category;
	Entry hoveredEntry;
	
	GuiTextField searchBar;
	GuiButton visit;

	@Override
	public void initGui() {
		super.initGui();

		buttonList.clear();
		buttonList.add(new GuiButton(0, x + 340, y + 145, 50, 20, StatCollector.translateToLocal("recubed.misc.back")));
		buttonList.add(new GuiButton(1, x + 310, y + 170, 80, 20, StatCollector.translateToLocal("recubed.misc.your_stats")));
		
		String search = StatCollector.translateToLocal("recubed.misc.search");
		searchBar = new GuiTextField(fontRenderer, x + fontRenderer.getStringWidth(search) + 5, y - 20, 200, 18);
		searchBar.setFocused(true);
		searchBar.setCanLoseFocus(false);
		searchBar.setMaxStringLength(32);
		searchBar.setVisible(false);
		
		buttonList.add(visit = new GuiButton(2, x + fontRenderer.getStringWidth(search) + 205, y - 45, 70, 20, StatCollector.translateToLocal("recubed.misc.see_stats")));
		visit.drawButton = false;
	}

	@Override
	public void drawScreen(int par1, int par2, float par3) {
		super.drawScreen(par1, par2, par3);

		hoveredEntry = null;
		PieChartRender pie = category instanceof Category ? PieChartRender.fromCategory((Category) category) : PieChartRender.fromPlayerData((PlayerCategoryData) category);
		if(pie == null)
			drawCenteredString(fontRenderer, StatCollector.translateToLocal("recubed.no_data"), x + 250, y + 95, 0xFF7777);
		else{
			hoveredEntry = pie.renderChart(80, x + 250, y + 100, par1, par2);
			fontRenderer.drawStringWithShadow(String.format(StatCollector.translateToLocal("recubed.misc.total"), pie.totalVal), x + 134, y + 185, 0xFFFFFF);
		}

		String displayString = StatCollector.translateToLocal(fromCurrentCategoryInt().name);
		if(category instanceof PlayerCategoryData)
			displayString = displayString + " - " + EnumChatFormatting.AQUA + ((PlayerCategoryData) category).name;
		drawCenteredString(fontRenderer, displayString, x + 250, y + 5, 0xFFFFFF);
		
		searchBar.drawTextBox();
		
		String search = StatCollector.translateToLocal("recubed.misc.search");
		int length = fontRenderer.getStringWidth(search);
		String text = searchBar.getText(); 
		
		if(text.isEmpty()) {
			GL11.glEnable(GL11.GL_BLEND);
			fontRenderer.drawStringWithShadow(StatCollector.translateToLocal("recubed.misc.type_to_search"), x + length + 10, y- 15, 0x66FFFFFF);
			GL11.glDisable(GL11.GL_BLEND);
			visit.drawButton = false;
		} else {
			fontRenderer.drawStringWithShadow(search, x, y - 15, 0xFFFFFF);
			Category currentCategory = fromCurrentCategoryInt();
			
			boolean found = false;
			int color = 0;
			int value = 0;
			int total = 0;
			
			if(category instanceof Category) {
				PlayerCategoryData data = getValueFromCaseInsensitveString(currentCategory.playerData, text);
				if(data != null) {
					color = MiscHelper.generateColorFromString(data.name);
					value = data.getTotalValue();
					found = true;
					total = currentCategory.getTotalValue();
				}
			} else {
				PlayerCategoryData data = (PlayerCategoryData) category;
				Integer value_ = getValueFromCaseInsensitveString(data.stats, text);
				if(value_ != null) {
					color = MiscHelper.generateColorFromString(unlocalized);
					value = value_;
					found = true;
					total = data.getTotalValue();
				}
			}
			
			if(found) {
				drawRect(x + length + 187, y - 20, x + length + 205, y - 2, color);
				float percentage = Math.round((float) value / (float) total * 100F * 100F) / 100F;
				
				fontRenderer.drawStringWithShadow(value + " (" + percentage + "%)", x + length + 210, y - 15, 0xFFFFFF);
				visit.drawButton = category instanceof Category;
			} else {
				fontRenderer.drawStringWithShadow("0 (0%)", x + length + 210, y - 15, 0xFFFFFF);
				visit.drawButton = false;
			}
		}
	}
	
	String unlocalized;
	public <T> T getValueFromCaseInsensitveString(Map<String, T> map, String key) {
		for(String k : map.keySet())
			if(StatCollector.translateToLocal(k).compareToIgnoreCase(key) == 0) {
				unlocalized = k;
				return map.get(k);
			}
		
		return null;
	}
	
	@Override
	protected void keyTyped(char par1, int par2) {
		super.keyTyped(par1, par2);
		
		searchBar.textboxKeyTyped(par1, par2);
		searchBar.setVisible(!searchBar.getText().isEmpty());
	}
	
	public void clearSearchBar() {
		if(searchBar != null) {
			searchBar.setText("");
			searchBar.setVisible(false);
		}
	}

	@Override
	protected void actionPerformed(GuiButton par1GuiButton) {
		Category category = fromCurrentCategoryInt();

		switch(par1GuiButton.id) {
			case 0 : {
				if(this.category instanceof PlayerCategoryData) {
					this.category = category;
					clearSearchBar();
				} else mc.displayGuiScreen(new GuiReCubedMenu());
				return;
			}
			case 1 : {
				this.category = category.playerData.get(mc.thePlayer.username);
				clearSearchBar();
				return;
			}
			case 2 : {
				String text = unlocalized;
				this.category = fromCurrentCategoryInt().playerData.get(text);
				clearSearchBar();
				return;
			}
		}
	}

	@Override
	protected void mouseClicked(int par1, int par2, int par3) {
		if(category instanceof Category && par3 == 0 && hoveredEntry != null && shouldVisitStats() && !hoveredEntry.name.equals("recubed.misc.others")) {
			Category category = (Category) this.category;
			this.category = category.playerData.get(hoveredEntry.name);
			clearSearchBar();
		}

		searchBar.mouseClicked(par1, par2, par3);
		
		super.mouseClicked(par1, par2, par3);
	}

	boolean shouldVisitStats() {
		return true;
	}

	@Override
	public void selectCategory(int category) {
		boolean isCurrentCategory = this.category instanceof Category;
		
		super.selectCategory(category);
		this.category = fromCurrentCategoryInt();
		
		if(!isCurrentCategory)
			clearSearchBar();
	}
}
