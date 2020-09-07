package listeners;

import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.events.user.update.UserUpdateOnlineStatusEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class BotManager{
    private static final Map<String, Process> bots = new HashMap<>();

    public static synchronized boolean start(String bot){
        if(bot.equals("all")){
            return start(getSubdirectories().stream()
                    .filter((x) -> !x.startsWith("_"))
                    .collect(Collectors.toList()));
        }
        if(bots.containsKey(bot)) return true;
        try{
            bots.put(bot, new ProcessBuilder().directory(new File(bot)).command("java", "-jar", "bot.jar").inheritIO().start());
        }
        catch (IOException e){
            System.out.println(e.toString());
            return false;
        }
        return true;
    }

    public static synchronized boolean start(Collection<String> bots){
        boolean output = true;
        for (String bot : bots) {
            output = start(bot) && output;
        }
        return output;
    }

    public static synchronized boolean end(String bot){
        if(bot.equals("all")) return end(bots.keySet());
        if(!bots.containsKey(bot)) return true;
        bots.get(bot).destroy();
        bots.remove(bot);
        return true;
    }

    public static synchronized boolean end(Collection<String> bots){
        boolean output = true;
        for (String bot : bots) {
            output = end(bot) && output;
        }
        return output;
    }

    public static synchronized boolean restart(String bot){
        if(bot.equals("all")) return restart(bots.keySet());
        return end(bot) & start(bot); // intentionally does not short circuit
    }

    public static synchronized boolean restart(Collection<String> bots){
        boolean output = true;
        for (String bot : bots) {
            output = restart(bot) && output;
        }
        return output;
    }

    public static List<String> getSubdirectories(){
        return Arrays.asList(new File(".").list((file, name) -> new File(file, name).isDirectory() && !name.equals("all")));
    }
}
