/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the ReCubed Mod.
 *
 * ReCubed is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * File Created @ [Dec 14, 2013, 8:17:48 PM (GMT)]
 */
package vazkii.recubed.common.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentTranslation;
import vazkii.recubed.api.internal.Category;
import vazkii.recubed.api.internal.PlayerCategoryData;
import vazkii.recubed.api.internal.ServerData;
import vazkii.recubed.common.core.helper.MiscHelper;

public class CommandWipePlayer extends CommandBase {

	@Override
	public String getCommandName() {
		return "recubed-wipeplayer";
	}

	@Override
	public String getCommandUsage(ICommandSender icommandsender) {
		return "recubed-wipeplayer <username>";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender icommandsender, String[] astring) throws CommandException {
		if(astring.length != 1)
			throw new WrongUsageException(getCommandUsage(icommandsender), (Object[]) astring);

		if(icommandsender instanceof EntityPlayer && !MiscHelper.isPlayerAllowedToUseCommands(icommandsender.getName()))
			throw new CommandException("recubed.commands.no_perms");

		for(String s : ServerData.categories.keySet()) {
			Category category = ServerData.categories.get(s);
			category.playerData.put(astring[0], new PlayerCategoryData(astring[0]));
		}

		icommandsender.addChatMessage(new TextComponentTranslation("recubed.commands.command_sucessful"));
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 3;
	}

}
