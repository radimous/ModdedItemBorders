package com.radimous.moddeditemborders;

import net.minecraftforge.common.ForgeConfigSpec;

public class Config {
    public static final ForgeConfigSpec.ConfigValue<Boolean> itemBordersEverywhere;
    public static final ForgeConfigSpec.ConfigValue<Boolean> moddedItemBorders;
    public static final ForgeConfigSpec.ConfigValue<Boolean> jewelColorInsteadOfRarity;
    public static final ForgeConfigSpec CLIENT_CONFIG;

    static {
        final ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        builder.push("DEBUG");
        itemBordersEverywhere= builder.comment("Display item borders everywhere (might break with some mods, used mostly to get stacktrace for proper impl)")
            .define("itemBordersEverywhere", false);
        builder.pop();
        moddedItemBorders= builder.comment("Enable mod specific implementations of item borders")
            .define("moddedItemBorders", true);
        jewelColorInsteadOfRarity = builder.comment("Show jewel color instead of rarity")
            .define("jewelColorInsteadOfRarity", false);
        CLIENT_CONFIG = builder.build();
    }
}
