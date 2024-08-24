package cc.crss.mod.util;

import cc.crss.mod.command.BankCommand;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import cc.crss.mod.command.WhenCommand;

public class CommandRegister {
    public static void registerCommands() {
        CommandRegistrationCallback.EVENT.register(WhenCommand::register);
        CommandRegistrationCallback.EVENT.register(BankCommand::register);
    }
}
