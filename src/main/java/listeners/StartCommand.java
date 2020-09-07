package listeners;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

public class StartCommand extends ParameterCommand {

    public StartCommand() {
        super("start");
    }

    @Override
    void command(@NotNull GuildMessageReceivedEvent event, List<String> params) {
        if(params.size() == 0){
            event.getChannel().sendMessage("Needs one or more bots to start.").queue();
            return;
        }
        BotManager.start(params);
    }
}
