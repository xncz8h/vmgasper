package com.vmgasper;

import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.Player;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.GameObjectSpawned;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

import javax.inject.Inject;
import java.util.List;

@Slf4j
@PluginDescriptor(
        name = "VMGasper",
        description = "Gasps when platform in VM is about to disappear",
        enabledByDefault = false
)

/**
 * Code used from: https://github.com/hippipi/volcanic-mine-plugin/blob/master/src/main/java/com/volcanicmine/VMPlugin.java
 */
public class VMGasperPlugin extends Plugin {

    private static final int PLATFORM_STAGE_3_ID = 31000;

    @Inject
    private Client client;

    @Inject
    private VMGasperConfig config;

    @Subscribe
    public void onGameObjectSpawned(GameObjectSpawned event) {
        if(event.getGameObject().getId() == PLATFORM_STAGE_3_ID) {
            // Fetch coordinates of player and game object
            List<Player> players = client.getPlayers();
            for(Player player: players) {
                int playerX = player.getWorldLocation().getX();
                int playerY = player.getWorldLocation().getY();
                int objectX = event.getGameObject().getWorldLocation().getX();
                int objectY = event.getGameObject().getWorldLocation().getY();

                // Notify player if the stage 3 platform is beneath them
                if (playerX == objectX && playerY == objectY)
                {
                    player.setOverheadCycle(config.duration() > 0 ? config.duration() : 100);
                    player.setOverheadText(!config.text().isEmpty() ? config.text() : "* Gasp *");
                }
            }
        }
    }

    @Provides
    VMGasperConfig provideConfig(ConfigManager configManager)
    {
        return configManager.getConfig(VMGasperConfig.class);
    }
}
