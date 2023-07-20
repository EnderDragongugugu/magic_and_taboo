package net.magic_and_taboo.init;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class MATItemGroups {
    public static final ItemGroup MAGIC = FabricItemGroupBuilder
            .create(new Identifier("magic_and_taboo","magic_group"))
            .icon(()-> new ItemStack(MATItems.COPPER_COIN))
            .build();
    public static final ItemGroup BLOOD = FabricItemGroupBuilder
            .create(new Identifier("magic_and_taboo","blood_group"))
            .icon(()-> new ItemStack(MATItems.COPPER_COIN))
            .build();
    public static final ItemGroup MOONLIGHT = FabricItemGroupBuilder
            .create(new Identifier("magic_and_taboo","moonlight_group"))
            .icon(()-> new ItemStack(MATItems.COPPER_COIN))
            .build();
}
