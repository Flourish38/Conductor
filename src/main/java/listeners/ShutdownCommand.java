package listeners;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

public class ShutdownCommand extends Command {
    public ShutdownCommand(){
        super("shutdown");
    }

    @Override
    void command(@NotNull GuildMessageReceivedEvent event) {
        if(event.getAuthor().getIdLong() != BotConfig.BOT_ADMIN_ID) return;
        event.getChannel().sendMessage("Shutting down...").queue();
        System.exit(0); // This will trigger the hook contained in ShutdownHandler.java
    }
}

