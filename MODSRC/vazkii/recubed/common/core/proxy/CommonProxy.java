/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the ReCubed Mod.
 * 
 * ReCubed is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 * 
 * File Created @ [Dec 13, 2013, 2:32:54 PM (GMT)]
 */
package vazkii.recubed.common.core.proxy;

import vazkii.recubed.api.internal.ServerData;
import vazkii.recubed.common.core.helper.CacheHelper;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {

	public void preInit(FMLPreInitializationEvent event) {
		
	}
	
	public void init(FMLInitializationEvent event) {
		
	}
	
	public void serverStarting() {
		ServerData.init();
	}
	
	public void serverStarted() {
		CacheHelper.findCompoundAndLoad();
	}
	
	public void serverStopped() {
		ServerData.reset();
	}
}
