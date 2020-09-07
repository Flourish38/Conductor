package listeners;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RestartCommand extends ParameterCommand {
    public RestartCommand() {
        super("restart");
    }

    @Override
    void command(@NotNull GuildMessageReceivedEvent event, List<String> params) {
        if(params.size() == 0){
            event.getChannel().sendMessage("Needs one or more bots to restart.").queue();
            return;
        }
        BotManager.restart(params);
    }
}
