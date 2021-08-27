package com.vmgasper;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("example")
public interface VMGasperConfig extends Config
{
    @ConfigItem(
            keyName = "text",
            name = "text",
            description = "Which text to display above players head when the platform is about to despawn",
            position = 1
    )
    default String text() {
        return "* Gasp *";
    }

    @ConfigItem(
            keyName = "duration",
            name = "duration",
            description = "The amount of cycles to display the text (default 100)",
            position = 2
    )
    default int duration() {
        return 100;
    }
}
