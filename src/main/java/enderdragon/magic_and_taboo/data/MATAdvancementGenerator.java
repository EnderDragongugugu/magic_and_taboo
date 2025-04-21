package enderdragon.magic_and_taboo.data;

import enderdragon.magic_and_taboo.MagicAndTabooMod;
import enderdragon.magic_and_taboo.init.MATItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.ImpossibleTrigger;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;

import java.util.function.Consumer;

public class MATAdvancementGenerator implements ForgeAdvancementProvider.AdvancementGenerator {
    @Override
    public void generate(HolderLookup.Provider registries, Consumer<Advancement> saver, ExistingFileHelper existingFileHelper) {
        Advancement root = Advancement.Builder.advancement().display(
                        MATItems.OCCULT_CODEX.get(),
                        Component.translatable("advancement.magic_and_taboo.root"),
                        Component.translatable("advancement.magic_and_taboo.root.desc"),
                        MagicAndTabooMod.makeId("textures/block/fir/fir_planks.png"),
                        FrameType.TASK,
                        true,
                        false,
                        false)
                .addCriterion("has_item", InventoryChangeTrigger.TriggerInstance.hasItems(MATItems.OCCULT_CODEX.get()))
                .save(saver, "magic_and_taboo:root");
        Advancement.Builder.advancement().parent(root).display(
                        MATItems.OCCULT_CODEX.get(),
                        Component.translatable("advancement.magic_and_taboo.root"),
                        Component.translatable("advancement.magic_and_taboo.root.desc"),
                        null,
                        FrameType.TASK,
                        true,
                        false,
                        false)
                .addCriterion("dummy", new ImpossibleTrigger.TriggerInstance())
//                .addCriterion("test", InventoryChangeTrigger.TriggerInstance.hasItems(MATItems.BLAZE_BURNER.get()))
                .save(saver, "magic_and_taboo:test");
        Advancement.Builder.advancement().parent(root).display(
                        MATItems.BLAZE_BURNER.get(),
                        Component.translatable("advancement.magic_and_taboo.root"),
                        Component.translatable("advancement.magic_and_taboo.root.desc"),
                        null,
                        FrameType.TASK,
                        true,
                        true,
                        false)
//                .addCriterion("test", InventoryChangeTrigger.TriggerInstance.hasItems(MATItems.BLAZE_BURNER.get()))
                .save(saver, "magic_and_taboo:test_1");
    }
}
