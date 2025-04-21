package enderdragon.magic_and_taboo.client.book;

import enderdragon.magic_and_taboo.client.book.graph.GraphBuilder;
import enderdragon.magic_and_taboo.client.book.graph.GraphChapter;
import enderdragon.magic_and_taboo.client.book.linear.*;
import enderdragon.magic_and_taboo.init.MATItems;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.FrameType;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import static enderdragon.magic_and_taboo.util.CollectionUtil.collect;

public class OccultCodexChapters {
    public static final GraphChapter ENTRY = new GraphBuilder()
            .child(FrameType.CHALLENGE, root -> root
                    .withIcon(() -> new ItemStack(Items.WRITABLE_BOOK))
                    .described("text.occult_codex.node.prologue")
                    .described("text.occult_codex.node.prologue.tooltip")
                    .posited(0, -50)
                    .clicked(book -> book.turnTo(OccultCodexChapters.PROLOGUE))
            )
            .child(FrameType.CHALLENGE, root -> root
                    .withIcon(() -> new ItemStack(MATItems.ENCHANTED_CRUCIBLE.get()))
                    .described("text.occult_codex.node.potion")
                    .described("text.occult_codex.node.potion.tooltip")
                    .posited(0, 0)
                    .clicked(book -> book.turnTo(OccultCodexChapters.POTION))
            ).build();

    public static final GraphChapter POTION = new GraphBuilder()
            .child(FrameType.TASK, root -> root
                    .withIcon(() -> new ItemStack(MATItems.FIR_SAPLING.get()))
                    .described("text.occult_codex.node.potion_1")
                    .described("text.occult_codex.node.potion_1.tooltip")
                    .posited(0, -50)
                    .clicked(book -> book.turnTo(OccultCodexChapters.ELEMENTS))
            ).build();
    public static final GraphChapter PROLOGUE = new GraphBuilder()
            .child(FrameType.TASK, root -> root
                    .withIcon(() -> new ItemStack(Items.DRAGON_EGG))
                    .described("text.occult_codex.node.prologue_1")
                    .described("text.occult_codex.node.prologue_1.tooltip")
                    .posited(0, -50)
                    .clicked(book -> book.turnTo(OccultCodexChapters.DEVELOPERS))
            ).child(FrameType.TASK, root -> root
                    .withIcon(() -> new ItemStack(Items.ENDER_PEARL))
                    .described("text.occult_codex.node.prologue_2")
                    .described("text.occult_codex.node.prologue_2.tooltip")
                    .clicked(book -> book.turnTo(OccultCodexChapters.STORY))
            ).build();

    public static final LinearChapter STORY = collect(LinearChapter::new, chunk -> chunk

            .add(TitleChunk.of("text.occult_codex.node.prologue_2.content.title"))
            .add(collect(TextChunk::new, lines -> lines
                    .add(Component.translatable("text.occult_codex.node.prologue_2.content"))
            ))
    );
    public static final LinearChapter ELEMENTS = collect(LinearChapter::new, chunk -> chunk
            .add(TitleChunk.of("text.occult_codex.node.potion_1.content.title"))
            .add(collect(TextChunk::new, lines -> lines
                    .add(Component.translatable("text.occult_codex.node.potion_1.content"))
            ))
            .add(Separator.NEXT_PAGE)
            .add(collect(TextChunk::new, lines -> lines
                    .add(Component.translatable("text.occult_codex.node.potion_1.content_1"))
            ))
            .add(new ElementChunk())
    );
    public static final LinearChapter DEVELOPERS = collect(LinearChapter::new, chunks -> chunks
                    .add(TitleChunk.of("text.occult_codex.node.prologue_2.content.title"))
                    .add(collect(TextChunk::new, lines -> lines
                            .add(Component.translatable("book.magic_and_taboo.occult_codex.owner.label"))
                            .add(Component.literal("EnderDragon").withStyle(ChatFormatting.GOLD))
                    ))
                    .add(collect(TextChunk::new, lines -> lines
                            .add(Component.translatable("book.magic_and_taboo.occult_codex.collaborative_dev.label"))
                            .add(Component.literal("2190303755").withStyle(ChatFormatting.GOLD))
                    ))
                    .add(collect(TextChunk::new, lines -> lines
                            .add(Component.translatable("book.magic_and_taboo.occult_codex.artist.label"))
                            .add(Component.literal("bltsbb").withStyle(ChatFormatting.GOLD))
                            .add(Component.literal("这条大龙很灵性").withStyle(ChatFormatting.GOLD))
                    ))
                    .add(collect(TextChunk::new, lines -> lines
                            .add(Component.translatable("book.magic_and_taboo.occult_codex.lpaicen.label"))
                            .add(Component.literal("LPaicen").withStyle(ChatFormatting.GOLD))
                    ))
                    .add(collect(TextChunk::new, lines -> lines
                            .add(Component.translatable("book.magic_and_taboo.occult_codex.art_contributer.label"))
                            .add(Component.literal("两个大脑同时坠毁").withStyle(ChatFormatting.GOLD))
                    ))

//            .add(Separator.NEXT_PAGE)
//            .add(collect(TextChunk::new, lines -> lines
//                    .add(Component.literal("\\ LPaicen! /").withStyle(ChatFormatting.DARK_RED))
//            ))
                    .add(Separator.NEXT_PAGE)
                    .add(collect(TextChunk::new, lines -> lines
                            .add(Component.literal("\\ LPaicen!!! /").withStyle(ChatFormatting.DARK_RED))
                    ))
                    .add(Separator.NEXT_PAGE)
                    .add(collect(TextChunk::new, lines -> lines
                            .add(Component.literal("\\ LPaicen!!!!! /").withStyle(ChatFormatting.DARK_RED))
                    ))
    );
}
