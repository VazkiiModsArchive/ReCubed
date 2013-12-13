/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the ReCubed Mod.
 * 
 * ReCubed is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 * 
 * File Created @ [Dec 13, 2013, 6:02:37 PM (GMT)]
 */
package vazkii.recubed.client.renders;

import java.awt.Color;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.util.EnumChatFormatting;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

import vazkii.recubed.api.internal.Category;
import vazkii.recubed.api.internal.PlayerCategoryData;
import vazkii.recubed.client.core.helper.RenderHelper;
import vazkii.recubed.common.core.helper.MiscHelper;

public strictfp class PieChartRender {

	public boolean clickable;
	
	public List<Entry> entries = new ArrayList();
	
	public static class Entry {
		int val;
		int angle;
		int color;
		String name;

		public Entry(int val, String name) {
			this(val, MiscHelper.generateColorFromString(name), name);
		}
		
		public Entry(int val, int color, String name) {
			this.val = val;
			this.color = color;
			this.name = name;
		}
		
	}

	public static PieChartRender fromCategory(Category category) {
		PieChartRender render = new PieChartRender(true);
		for(String s : category.playerData.keySet())
			render.entries.add(new Entry(category.getTotalValueFromPlayerData(s), s));
		render.buildAngles();
		
		return render;
	}
	
	public static PieChartRender fromPlayerData(PlayerCategoryData data) {
		PieChartRender render = new PieChartRender(false);
			for(String s : data.stats.keySet())
				render.entries.add(new Entry(data.stats.get(s), s));
		render.buildAngles();

		return render;
	}
	
	
	private PieChartRender(boolean clickable) {
		this.clickable = clickable;
	}
	
	private void buildAngles() {
		int totalValue = 0;
		for(Entry entry : entries)
			totalValue += entry.val;
		
		float mul = 360F / totalValue;
		for(Entry entry : entries)
			entry.angle = Math.round(entry.val * mul);
	}
	
	public void renderChart(int radius, int x, int y, int mx, int my) {
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		
		Entry tooltip = null;
		int tooltipDeg = 0;
		
		boolean mouseIn = (x - mx) * (x - mx) + (y - my) * (y - my) <= radius * radius;
		float angle = mouseAngle(x, y, mx, my);
		int highlight = 5;
		
		int totalDeg = 0;
		for(Entry entry : entries) {
			boolean mouseInSector = mouseIn && angle > totalDeg && angle < totalDeg + entry.angle;
			Color color = new Color(entry.color);
			
			if(mouseInSector) {
				tooltip = entry;
				tooltipDeg = totalDeg;
				
				radius += highlight;
			}
			
			GL11.glBegin(GL11.GL_TRIANGLE_FAN);
			
			GL11.glColor4ub((byte) color.getRed(), (byte) color.getGreen(), (byte) color.getBlue(), (byte) 255);
			GL11.glVertex2i(x, y);			
			
			for(int i = entry.angle; i >= 0; i--) {
				float rad = (float) ((i + totalDeg) / 180F * Math.PI);
				GL11.glVertex2d(x + Math.cos(rad) * radius, y + Math.sin(rad) * radius);
			}
			totalDeg += entry.angle;
			
			GL11.glColor4ub((byte) 0, (byte) 0, (byte) 0, (byte) 255);
			GL11.glVertex2i(x, y);		
			GL11.glEnd();
			
			if(mouseInSector)
				radius -= highlight;
		}
		
		GL11.glLineWidth(3F);
		GL11.glColor4f(0F, 0F, 0F, 1F);

		GL11.glBegin(GL11.GL_LINE_LOOP);		
		for(int i = 0; i < 360; i++) {
			boolean sectorHighlighted = tooltip != null && i >= tooltipDeg && i < tooltip.angle + tooltipDeg;
			boolean first = tooltip != null && i - tooltipDeg == 0;
			boolean last = tooltip != null && i - (tooltipDeg + tooltip.angle) == -1;
			
			if(first)
				addVertexForAngle(x, y, i, radius);

			if(sectorHighlighted)
				radius += highlight;
			
			addVertexForAngle(x, y, i, radius);
			
			if(last)
				addVertexForAngle(x, y, i + 1, radius);
			
			if(sectorHighlighted)
				radius -= highlight;
		}
		GL11.glEnd();
		
		if(tooltip != null)
			RenderHelper.renderTooltip(mx, my, Arrays.asList(tooltip.name, EnumChatFormatting.GRAY + "" + (int) Math.round(tooltip.angle / 3.6F) + "%"));
		
		GL11.glPopMatrix();
	}
	
	private static void addVertexForAngle(int x, int y, int i, int radius) {
		double rad = i / 180F * Math.PI;
		GL11.glVertex2d(x + Math.cos(rad) * radius, y + Math.sin(rad) * radius);
	}
	
	private static float mouseAngle(int x, int y, int mx, int my) {
		Vector2f baseVec = new Vector2f(1F, 0F);
		Vector2f mouseVec = new Vector2f(mx - x, my - y);
		
		float ang = (float) (Math.acos(Vector2f.dot(baseVec, mouseVec) / (baseVec.length() * mouseVec.length())) * (180F / Math.PI));
		return my < y ? 360F - ang : ang;
	}
}
