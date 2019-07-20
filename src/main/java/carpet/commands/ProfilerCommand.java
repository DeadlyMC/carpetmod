package carpet.commands;

import carpet.CarpetSettings;
import carpet.utils.CarpetProfiler;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandSource;

import static net.minecraft.command.Commands.literal;
import static com.mojang.brigadier.arguments.IntegerArgumentType.*;
import static net.minecraft.command.Commands.*;

public class ProfilerCommand
{
    public static void register(CommandDispatcher<CommandSource> dispatcher)
    {
        LiteralArgumentBuilder<CommandSource> literalargumentbuilder = literal("profiler").
            requires((player) -> CarpetSettings.getBool("commandProfiler")).
            then(literal("tick").
                        executes( (c) -> healthReport(c.getSource(), 100)).
                        then(argument("ticks", integer(20,24000)).
                                executes( (c) -> healthReport(c.getSource(), getInteger(c, "ticks"))))).
            then(literal("entities").
                        executes((c) -> healthEntities(c.getSource(), 100)).
                        then(argument("ticks", integer(20,24000)).
                                executes((c) -> healthEntities(c.getSource(), getInteger(c, "ticks")))));
    
        dispatcher.register(literalargumentbuilder);
    }
    
    private static int healthReport(CommandSource source, int ticks)
    {
        CarpetProfiler.prepare_tick_report(ticks);
        return 1;
    }
    
    private static int healthEntities(CommandSource source, int ticks)
    {
        CarpetProfiler.prepare_entity_report(ticks);
        return 1;
    }
}
