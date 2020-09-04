package listeners;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The difference between this and ParameterCommand is that this will merge parameters in quotes to allow for spaces.
 *
 * NOTE: most of the time, ParameterCommand should be fine, since you can just merge parameters manually. The only case
 * where this should be useful is if you have at least two parameters that need spaces in them, or lots of optional
 * parameters.
 */
public abstract class QuoteParameterCommand extends CommandListener {
    public QuoteParameterCommand(String command, String... commands) {
        super(command, commands);
    }

    @Override
    void command(@NotNull GuildMessageReceivedEvent event, String command) {
        String raw = event.getMessage().getContentRaw();
        raw = raw.substring(raw.indexOf(command) + command.length()).trim();
        List<String> params = (raw.equals("") ? new ArrayList<>() :
                Arrays.stream(raw.split(" ")).collect(Collectors.toCollection(ArrayList::new)));

        int quoteIndex = -1;
        for(int i = 0; i < params.size(); i++){
            String param = params.get(i);
            if(quoteIndex != -1){
                if(param.contains("\"")){
                    StringBuilder sb = new StringBuilder(params.get(quoteIndex));
                    for(int j = quoteIndex + 1; j <= i; j++) {
                        sb.append(" ").append(params.get(quoteIndex + 1));
                        params.remove(quoteIndex + 1);
                    }
                    param = sb.toString();
                    i = quoteIndex;
                    quoteIndex = -1;
                }
            }
            else if(param.startsWith("\"")){
                quoteIndex = i;
                param = param.substring(1);
                if(param.contains("\""))
                    quoteIndex = -1;
            }
            params.set(i, param.replaceAll("\"", ""));
        }

        command(event, params);
    }

    abstract void command(@Nonnull GuildMessageReceivedEvent event, List<String> params);
}
