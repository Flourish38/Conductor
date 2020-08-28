package listeners;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class ParameterCommand extends CommandListener {
    public ParameterCommand(String command, String... commands) {
        super(command, commands);
    }

    @Override
    void command(@NotNull GuildMessageReceivedEvent event, String command) {
        String raw = event.getMessage().getContentRaw();
        raw = raw.substring(raw.indexOf(command) + command.length()).strip();

        command(event, (raw.equals("") ? new ArrayList<>() : Arrays.asList(raw.split(" "))));
    }

    abstract void command(@Nonnull GuildMessageReceivedEvent event, List<String> params);
}