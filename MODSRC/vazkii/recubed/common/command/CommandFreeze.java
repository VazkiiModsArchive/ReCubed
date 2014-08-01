/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the ReCubed Mod.
 *
 * ReCubed is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * File Created @ [Dec 15, 2013, 2:13:25 PM (GMT)]
 */
package vazkii.recubed.common.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentTranslation;
import vazkii.recubed.api.internal.Category;
import vazkii.recubed.api.internal.ServerData;
import vazkii.recubed.common.core.helper.MiscHelper;

public class CommandFreeze extends CommandBase {

	@Override
	public String getCommandName() {
		return "recubed-freeze";
	}

	@Override
	public String getCommandUsage(ICommandSender icommandsender) {
		return "recubed-freeze <frozen(true/false)>";
	}

	@Override
	public void processCommand(ICommandSender icommandsender, String[] astring) {
		if(astring.length != 1)
			throw new WrongUsageException(getCommandUsage(icommandsender), (Object[]) astring);

		if(icommandsender instanceof EntityPlayer && !MiscHelper.isPlayerAllowedToUseCommands(icommandsender.getCommandSenderName()))
			throw new CommandException("recubed.commands.no_perms");

		boolean frozen = Boolean.parseBoolean(astring[0]);
		for(Category category : ServerData.categories.values())
			category.isFrozen = frozen;
		icommandsender.addChatMessage(new ChatComponentTranslation("recubed.commands.command_sucessful"));
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 3;
	}
}
