package cc.crss.mod.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.arguments.MessageArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;

public class BankCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean dedicated) {
        dispatcher.register((LiteralArgumentBuilder) ((LiteralArgumentBuilder) CommandManager.literal("bank"))
                .executes(BankCommand::run));
    }

    public static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        context.getSource().getMinecraftServer().sendMessage(new LiteralText("<CRSS> " + context.getSource().getName() + " is a little teapot."));
        context.getSource().getPlayer().sendMessage(new LiteralText("<CRSS> " + context.getSource().getName() + " is a little teapot."));
        return 1;
    }
}