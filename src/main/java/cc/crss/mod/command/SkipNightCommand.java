package cc.crss.mod.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.LiteralText;
import net.minecraft.world.dimension.DimensionType;

public class SkipNightCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean dedicated) {
        dispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder) CommandManager.literal("sleep"))
                .executes(SkipNightCommand::run));
    }

    public static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {

        if (context.getSource().getPlayer().dimension == DimensionType.OVERWORLD) {

            for(ServerWorld world : context.getSource().getMinecraftServer().getWorlds()) {

                long rTime = 24000 - world.getTime();
                world.setTimeOfDay(world.getTime() + rTime);

                if (world.isThundering()) {
                    world.setThunderGradient(0);
                }

            }

            context.getSource().sendFeedback(new LiteralText("[CRSS] " + "The night has been passed."), false);
        }

        return 1;
    }

}