package enderdragon.magic_and_taboo.event;

import enderdragon.magic_and_taboo.MagicAndTabooMod;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Mod.EventBusSubscriber(modid = MagicAndTabooMod.MOD_ID)
public class EventHandler {
    private static final int MAX_SEARCH = 512;

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        if (event.getPlayer() instanceof ServerPlayer player && !player.isCreative() && event.getLevel() instanceof ServerLevel level) {
            var startPos = event.getPos();
            var startState = event.getState();
            if (startState.getBlock().canHarvestBlock(startState, level, startPos, player)) {
                var end = findFarthestConnectedBlock(level, startPos, startState);
                var endPos = end.pos;
                if (startPos.equals(endPos) || !player.mayInteract(level, endPos)) return;
                var endState = end.state;
                if (endState.isAir()) return; // ¿
                event.setCanceled(true);
                spawnLoot(level, player, endState, endPos, Vec3.atCenterOf(startPos));
                var tool = player.getMainHandItem();
                if (!tool.isEmpty() && tool.isDamageableItem()) {
                    tool.hurt(1, player.getRandom(), player);
                }
                var block = endState.getBlock();
                block.playerWillDestroy(level, endPos, endState, player);
                block.destroy(level, startPos, endState);
                level.removeBlock(endPos, false);
                level.levelEvent(2001, startPos, Block.getId(endState));
            }
        }
    }

    private static void spawnLoot(ServerLevel level, Player player, BlockState state, BlockPos pos, Vec3 loot) {
        var stack = player.getMainHandItem();
        for (var drop : state.getDrops(new LootParams.Builder(level)
                .withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(pos))
                .withParameter(LootContextParams.TOOL, stack)
                .withParameter(LootContextParams.THIS_ENTITY, player)
                .withOptionalParameter(LootContextParams.BLOCK_ENTITY, level.getBlockEntity(pos))
                .withParameter(LootContextParams.BLOCK_STATE, state)
        )) {
            Containers.dropItemStack(level, loot.x, loot.y, loot.z, drop);
        }
        int exp = state.getExpDrop(
                level,
                level.random,
                pos,
                stack.getEnchantmentLevel(Enchantments.BLOCK_FORTUNE),
                stack.getEnchantmentLevel(Enchantments.SILK_TOUCH)
        );
        if (exp > 0) {
            ExperienceOrb.award(level, loot, exp);
        }
    }

    public static class BlockEntry {
        public final BlockPos pos;
        public final BlockState state;
        public @Nullable BlockEntry next;

        public BlockEntry(BlockPos pos, BlockState state) {
            this.pos = pos;
            this.state = state;
        }
    }

    @Nonnull
    private static BlockEntry findFarthestConnectedBlock(ServerLevel level, BlockPos start, BlockState state) {
        var visited = new ObjectOpenHashSet<BlockPos>();
        visited.add(start);
        var target = state.getBlock();
        BlockEntry head = new BlockEntry(start, state), tail = head;
        for (int count = 0; count < MAX_SEARCH; ++count) {
            for (var direction : Direction.values()) {
                var neighbor = head.pos.relative(direction);
                if (visited.add(neighbor)) {
                    var candidate = level.getBlockState(neighbor);
                    if (candidate.getBlock().equals(target)) {
                        tail = tail.next = new BlockEntry(neighbor, state);
                    }
                }
            }
            if (head.next == null) return head;
            head = head.next;
        }
        return head;
    }
}
