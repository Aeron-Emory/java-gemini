package bot;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;

public class DiscordBot extends ListenerAdapter {
    public static void main(String[] args) throws LoginException {
        String token = System.getenv("DISCORD_TOKEN"); // Discord bot token from environment variable
        JDABuilder.createDefault(token)
                  .addEventListeners(new DiscordBot())
                  .build();
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String content = event.getMessage().getContentDisplay();

        if (content.toLowerCase().contains("newtella") && !event.getAuthor().isBot()) {
            String response = GoogleAIClient.generateResponse("You're a bot named Newtella. " + content);
            TextChannel channel = event.getChannel().asTextChannel();
            channel.sendMessage(response).queue();
        }
    }
}
