package cc.crss.mod.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.arguments.MessageArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;

import java.util.Random;

public class WhenCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean dedicated) {
        dispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal("when"))
                .then(CommandManager.argument("query", MessageArgumentType.message()).executes(WhenCommand::run)));
    }

    public static int run(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {

        Random rand = new Random();

        String weekSuffix;
        String daysSuffix;
        String monthsSuffix;
        String yearsSuffix;

        int weeks = rand.nextInt(4) + 1;
        int days = rand.nextInt(7) + 1;
        int months = rand.nextInt(12) + 1;
        int years = rand.nextInt(5) + 1;

        if (weeks > 1) weekSuffix = "weeks"; else weekSuffix = "week";
        if (days > 1) daysSuffix = "days"; else daysSuffix = "day";
        if (months > 1) monthsSuffix = "months"; else monthsSuffix = "month";
        if (years > 1) yearsSuffix = "years"; else yearsSuffix = "year";

        String[] dateStrings = {
                "now", "later", "never", "tomorrow", "yesterday", "Releasing 2025:tm:",
                "When Alto releases", "Soon:tm:" };
        String randomDateString = dateStrings[rand.nextInt(dateStrings.length)];

        String[] datesSelection = {
                days + " " + daysSuffix, weeks + " " + weekSuffix,
                months + " " + monthsSuffix, years + " " + yearsSuffix,
                randomDateString };
        String randomDate = datesSelection[rand.nextInt(datesSelection.length)];

        context.getSource().sendFeedback(new LiteralText("[CRSS] " + randomDate), false);

        return 1;
    }

}
