package enderdragon.magic_and_taboo.data;

import enderdragon.magic_and_taboo.init.MATItems;
import enderdragon.magic_and_taboo.tag.MATBlockTags;
import enderdragon.magic_and_taboo.tag.MATItemTags;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

import static enderdragon.magic_and_taboo.MagicAndTabooMod.MOD_ID;

public class MATItemTagProvider extends ItemTagsProvider {
    public MATItemTagProvider(PackOutput output, CompletableFuture<Provider> registry, CompletableFuture<TagLookup<Block>> blocks, @Nullable ExistingFileHelper helper) {
        super(output, registry, blocks, MOD_ID, helper);
    }

    @Override
    protected void addTags(@NotNull Provider registry) {
        this.copy(MATBlockTags.FIR_LOGS, MATItemTags.FIR_LOGS);
        this.copy(BlockTags.LOGS_THAT_BURN, ItemTags.LOGS_THAT_BURN);
        this.copy(BlockTags.SAPLINGS, ItemTags.SAPLINGS);
        this.copy(BlockTags.LEAVES, ItemTags.LEAVES);
        this.copy(BlockTags.PLANKS, ItemTags.PLANKS);
        this.copy(BlockTags.STANDING_SIGNS, ItemTags.SIGNS);
        this.copy(BlockTags.CEILING_HANGING_SIGNS, ItemTags.HANGING_SIGNS);
        this.copy(BlockTags.WOODEN_BUTTONS, ItemTags.WOODEN_BUTTONS);
        this.copy(BlockTags.WOODEN_DOORS, ItemTags.WOODEN_DOORS);
        this.copy(BlockTags.WOODEN_STAIRS, ItemTags.WOODEN_STAIRS);
        this.copy(BlockTags.WOODEN_SLABS, ItemTags.WOODEN_SLABS);
        this.copy(BlockTags.WOODEN_FENCES, ItemTags.WOODEN_FENCES);
        this.copy(BlockTags.WOODEN_PRESSURE_PLATES, ItemTags.WOODEN_PRESSURE_PLATES);
        this.copy(BlockTags.WOODEN_TRAPDOORS, ItemTags.WOODEN_TRAPDOORS);
        this.tag(MATItemTags.MORTARS)
                .add(MATItems.MORTAR.get());
        this.tag(MATItemTags.BLAZE_BLAST_BURNERS)
                .add(MATItems.BLAZE_BLAST_BURNER.get());
        this.tag(MATItemTags.BLAZE_BURNERS)
                .add(MATItems.BLAZE_BURNER.get())
                .addTag(MATItemTags.BLAZE_BLAST_BURNERS);
        this.tag(MATItemTags.RAW_MEAT)
                .add(Items.BEEF)
                .add(Items.CHICKEN)
                .add(Items.MUTTON)
                .add(Items.PORKCHOP)
                .add(Items.RABBIT);
        this.tag(MATItemTags.MAGIC_POTION_BOTTLE)
                .add(MATItems.GLASS_POTION_BOTTLE.get())
                .add(MATItems.GLASS_POTION_BOTTLE_RED.get())
                .add(MATItems.GLASS_POTION_BOTTLE_GLOW.get())
                .add(MATItems.GLASS_POTION_SYRINGE.get());
        this.tag(MATItemTags.COOLANT)
                .add(Items.ICE)
                .add(Items.BLUE_ICE)
                .add(Items.PACKED_ICE);
        this.tag(MATItemTags.IS_ALCHEMY)
                .add(MATItems.ALCHEMY_ELEMENT.get())
                .add(MATItems.ALCHEMY_PASTE.get())
                .add(MATItems.ALCHEMY_SOLUTION.get())
                .add(MATItems.ALCHEMY_POWDER.get());
        this.tag(MATItemTags.IS_ALCHEMY_MATERIALS)
                .add(Items.BLAZE_POWDER)
                .add(Items.GLOWSTONE);
        this.tag(MATItemTags.IS_SOLVENT)
                .add(Items.WATER_BUCKET)
                .add(MATItems.HONEY_BUCKET.get())
                .add(MATItems.PLANT_EXTRACT_BUCKET.get());

        // 元素处理相关标签
        this.tag(MATItemTags.ELEMENT_ENHANCERS)
                .add(Items.FLINT)
                .add(Items.GLOWSTONE_DUST)
                .add(Items.REDSTONE);
        this.tag(MATItemTags.ELEMENT_Diluents)
                .add(Items.SUGAR)
                .add(Items.GUNPOWDER);
        this.tag(MATItemTags.ELEMENT_STABILIZERS)
                .add(Items.SLIME_BALL)
                .add(Items.GLISTERING_MELON_SLICE)
                .add(Items.GOLDEN_CARROT);

        // 特殊元素处理物品标签（用于特殊修正效果）
        this.tag(MATItemTags.ELEMENT_CONCENTRATED)
                .add(Items.GUNPOWDER);  // 1.5倍 + 自定义修正
        this.tag(MATItemTags.ELEMENT_STABILIZED)
                .add(Items.GLISTERING_MELON_SLICE)  // +15固定增量
                .add(Items.GOLDEN_CARROT);  // 1.15倍 + 自定义修正
        this.tag(MATItemTags.ELEMENT_DILUTED)
                .add(Items.SUGAR);  // 0.9倍 + 自定义修正
        this.tag(MATItemTags.ALCHEMY_INGREDIENTS)
                .addTag(MATItemTags.ELEMENT_ENHANCERS)
                .addTag(MATItemTags.ELEMENT_Diluents)
                .addTag(MATItemTags.ELEMENT_STABILIZERS)
                .add(Items.NETHER_WART)
                .add(Items.GOLDEN_APPLE)
                .add(Items.DRAGON_BREATH);
        this.tag(MATItemTags.CONTAINERS)
                .add(Items.PAPER)
                .add(Items.GLASS_BOTTLE);

        this.tag(MATItemTags.UNFINISHED)
//                书
                .add(MATItems.OCCULT_CODEX.get())
//                法杖
                .add(MATItems.STAFF.get())
                .add(MATItems.FIR_HANDLE.get())
                .add(MATItems.BRASS_TIP.get())
                .add(MATItems.HOLY_FEATHER.get())
//                法袍
                .add(MATItems.LIMITE_BOOTS.get())
                .add(MATItems.LIMITE_CHESTPLATE.get())
                .add(MATItems.LIMITE_HELMET.get())
                .add(MATItems.LIMITE_LEGGINGS.get());
    }
}
