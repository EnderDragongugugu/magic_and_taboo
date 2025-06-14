package enderdragon.magic_and_taboo.client.book;

import enderdragon.magic_and_taboo.client.ClientUtil;
import enderdragon.magic_and_taboo.client.book.graph.GraphBuilder;
import enderdragon.magic_and_taboo.client.book.graph.GraphChapter;
import enderdragon.magic_and_taboo.client.book.page.*;
import enderdragon.magic_and_taboo.init.MATItems;
import enderdragon.magic_and_taboo.registry.Element;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.FrameType;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.List;

import static enderdragon.magic_and_taboo.client.book.page.ElementCatalog.ICON_SIZE;

public class OccultCodexChapters {
    public static final GraphChapter ENTRY = new GraphBuilder()
            .child(FrameType.CHALLENGE, root -> root
                    .withIcon(() -> new ItemStack(Items.WRITABLE_BOOK))
                    .described("text.occult_codex.node.prologue")
                    .described("text.occult_codex.node.prologue.tooltip")
                    .posited(0, -50)
                    .clicked(book -> book.turnTo(OccultCodexChapters.PROLOGUE, true))
            )
            .child(FrameType.CHALLENGE, root -> root
                    .withIcon(() -> new ItemStack(MATItems.ENCHANTED_CRUCIBLE.get()))
                    .described("text.occult_codex.node.potion")
                    .described("text.occult_codex.node.potion.tooltip")
                    .posited(0, 0)
                    .clicked(book -> book.turnTo(OccultCodexChapters.POTION, true))
            ).build();

    public static final GraphChapter POTION = new GraphBuilder()
            .child(FrameType.TASK, root -> root
                    .withIcon(() -> new ItemStack(MATItems.FIR_SAPLING.get()))
                    .described("text.occult_codex.node.potion_1")
                    .described("text.occult_codex.node.potion_1.tooltip")
                    .posited(0, -50)
                    .clicked(book -> book.turnTo(OccultCodexChapters.ELEMENTS, true))
            ).build();

    public static final GraphChapter PROLOGUE = new GraphBuilder()
            .child(FrameType.TASK, root -> root
                    .withIcon(() -> new ItemStack(Items.DRAGON_EGG))
                    .described("text.occult_codex.node.prologue_1")
                    .described("text.occult_codex.node.prologue_1.tooltip")
                    .posited(0, -50)
                    .clicked(book -> book.turnTo(OccultCodexChapters.DEVELOPERS, true))
            ).child(FrameType.TASK, root -> root
                    .withIcon(() -> new ItemStack(Items.ENDER_PEARL))
                    .described("text.occult_codex.node.prologue_2")
                    .described("text.occult_codex.node.prologue_2.tooltip")
                    .clicked(book -> book.turnTo(OccultCodexChapters.STORY, true))
            ).build();

    public static final PageChapter STORY = ContentChapter.of(
            TitleChunk.of("text.occult_codex.node.prologue_2.content.title"),
            TextChunk.of("text.occult_codex.node.prologue_2.content")
    );

    public static final IndexedChapter<Element> ELEMENTS = new IndexedChapter<>(new ElementCatalog(
            Component.translatable("text.occult_codex.node.potion_1.content_1")
    ), List.of(
            TitleChunk.of("text.occult_codex.node.potion_1.content.title"),
            TextChunk.of("text.occult_codex.node.potion_1.content")
    ), () -> {
        var access = ClientUtil.getOptionalRegistries();
        return access == null ? null : access.registryOrThrow(Element.RESOURCE_KEY);
    }, element -> List.of(
            ImageChunk.squared(element.icon(), 0, 0, ICON_SIZE, 4),
            TextChunk.of(
                    Component.translatable("text.occult_codex.node.potion_1.content_1.name", element.getDisplayName()),
                    Component.translatable("text.occult_codex.node.potion_1.content_1.max_level", element.maxLevel() + 1),
                    Component.translatable("text.occult_codex.node.potion_1.content_1.max_time", element.maxTime()),
                    element.concentration().format("text.occult_codex.node.potion_1.content_1.concentration"),
                    element.temperature().format("text.occult_codex.node.potion_1.content_1.temperature_range")
            )
    ));

    public static final PageChapter DEVELOPERS = ContentChapter.of(
            TitleChunk.of("text.occult_codex.node.prologue_2.content.title"),
            TextChunk.of(
                    Component.translatable("book.magic_and_taboo.occult_codex.owner.label"),
                    Component.literal("EnderDragon").withStyle(style -> style.withClickEvent(
                            new ClickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/EnderDragongugugu")
                    ).applyFormat(ChatFormatting.GOLD))
            ),
            TextChunk.of(
                    Component.translatable("book.magic_and_taboo.occult_codex.collaborative_dev.label"),
                    Component.literal("2190303755").withStyle(style -> style.withClickEvent(
                            new ClickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/2190303755")
                    ).applyFormat(ChatFormatting.GOLD))
            ),
            TextChunk.of(
                    Component.translatable("book.magic_and_taboo.occult_codex.artist.label"),
                    Component.literal("bltsbb").withStyle(style -> style.withClickEvent(
                            new ClickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/bltsbb")
                    ).applyFormat(ChatFormatting.GOLD)),
                    Component.literal("这条大龙很灵性").withStyle(ChatFormatting.GOLD)
            ),
            TextChunk.of(
                    Component.translatable("book.magic_and_taboo.occult_codex.lpaicen.label"),
                    Component.literal("LPaicen").withStyle(style -> style.withClickEvent(
                            new ClickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/LPaicen")
                    ).withHoverEvent(
                            new HoverEvent(HoverEvent.Action.SHOW_TEXT, Component.literal("\\ LPaicen /"))
                    ).applyFormat(ChatFormatting.GOLD))
            ),
            TextChunk.of(
                    Component.translatable("book.magic_and_taboo.occult_codex.art_contributer.label"),
                    Component.literal("两个大脑同时坠毁").withStyle(ChatFormatting.GOLD)
            ),
            Separator.NEXT_PAGE,
            TextChunk.of(
                    Component.literal("\\ LPaicen!!! /").withStyle(ChatFormatting.DARK_RED)
            ),
            Separator.NEXT_PAGE,
            TextChunk.of(
                    Component.literal("\\ LPaicen!!!!! /").withStyle(ChatFormatting.DARK_RED)
            )
    );
}
