/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the ReCubed Mod.
 * 
 * ReCubed is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 * 
 * File Created @ [Dec 13, 2013, 2:48:50 PM (GMT)]
 */
package vazkii.recubed.common.core.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;
import vazkii.recubed.api.internal.ServerData;
import vazkii.recubed.common.lib.LibMisc;

public class CacheHelper {

	public static File getCacheFile() throws IOException {
		MinecraftServer server = MinecraftServer.getServer();
		
		WorldServer world = server.worldServers[0];
		File loc = world.getChunkSaveLocation();
		File cacheFile = new File(loc, LibMisc.MOD_ID + ".dat");
		
		if(!cacheFile.exists())
			cacheFile.createNewFile();
		
		return cacheFile;
	}
	
	public static NBTTagCompound getCacheCompound() {
		File cache = null;
		
		try {
			cache = getCacheFile();
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		if(cache == null)
			throw new RuntimeException("No cache file!");
		
		try {
			NBTTagCompound cmp = CompressedStreamTools.readCompressed(new FileInputStream(cache));
			return cmp;
		} catch(IOException e) {
			NBTTagCompound cmp = new NBTTagCompound();
			
			try {
				CompressedStreamTools.writeCompressed(cmp, new FileOutputStream(cache));
				return getCacheCompound();
			} catch (IOException e1) {
				e1.printStackTrace();
				return null;
			}
		}

		
	}
	
	public static void injectNBTToFile(NBTTagCompound cmp) {
		try {
			File f = getCacheFile();
			CompressedStreamTools.writeCompressed(cmp, new FileOutputStream(f));
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void findCompoundAndLoad() {
		NBTTagCompound cmp = getCacheCompound();
		ServerData.loadFromNBT(cmp);
	}
	
	public static void findCompoundAndWrite() {
		NBTTagCompound cmp = new NBTTagCompound();
		ServerData.writeToNBT(cmp);
		injectNBTToFile(cmp);
	}
	
}
