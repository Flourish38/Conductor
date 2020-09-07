package listeners;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

public class ListCommand extends Command {
    public ListCommand() {
        super("list");
    }

    @Override
    void command(@NotNull GuildMessageReceivedEvent event) {
        event.getChannel().sendMessage("Bots:\n" + String.join("\n", BotManager.getSubdirectories())).queue();
    }
}
