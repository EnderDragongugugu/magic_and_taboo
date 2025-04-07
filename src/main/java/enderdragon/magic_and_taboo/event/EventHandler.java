package enderdragon.magic_and_taboo.event;

import enderdragon.magic_and_taboo.MagicAndTabooMod;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.*;

@Mod.EventBusSubscriber(modid = MagicAndTabooMod.MOD_ID)
public class EventHandler {
    private static final int MAX_SEARCH = 512;

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        Level level = player.level();
        if (player.isCreative()) return;
        if (level instanceof ServerLevel serverLevel) {
            BlockPos startPos = event.getPos();
            BlockState start = event.getState();
            Block startBlock = serverLevel.getBlockState(startPos).getBlock();
            BlockPos endPos = findFarthestConnectedBlock(serverLevel, startPos, startBlock);
            boolean canHarvest = startBlock.canHarvestBlock(start, level, startPos, player);
            if (endPos != null && canHarvest) {
                var end = serverLevel.getBlockState(endPos);
                if (end.isAir()) return;
                if (!player.mayInteract(serverLevel, endPos)) return;
                if (!endPos.equals(startPos)) {
                    event.setCanceled(true);
                }
                ItemStack tool = player.getMainHandItem();
                if (!tool.isEmpty() && tool.isDamageableItem()) {
                    tool.hurt(1, player.getRandom(), player instanceof ServerPlayer serverPlayer ? serverPlayer : null);
                }
                Block endBlock = end.getBlock();
                spawnLoot(serverLevel, player, event.getState(), end, startPos, endPos);
                endBlock.playerWillDestroy(serverLevel, endPos, end, player);
                endBlock.destroy(serverLevel, startPos, end);
                serverLevel.removeBlock(endPos, false);
                serverLevel.levelEvent(2001, startPos, Block.getId(end));
            }
        }
    }

    private static void spawnLoot(ServerLevel level, Player player, BlockState start, BlockState end, BlockPos startPos, BlockPos endPos) {
        var blockEntity = level.getBlockEntity(startPos);
        LootParams.Builder lootBuilder = new LootParams.Builder(level)
                .withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(endPos))
                .withParameter(LootContextParams.TOOL, player.getMainHandItem())
                .withParameter(LootContextParams.THIS_ENTITY, player)
                .withOptionalParameter(LootContextParams.BLOCK_ENTITY, blockEntity)
                .withParameter(LootContextParams.BLOCK_STATE, start);
        List<ItemStack> drops = end.getDrops(lootBuilder);
        for (ItemStack drop : drops) {
            Containers.dropItemStack(level, startPos.getX(), startPos.getY(), startPos.getZ(), drop);
        }
        int fortuneLevel = player.getMainHandItem().getEnchantmentLevel(Enchantments.BLOCK_FORTUNE);
        int silkTouchLevel = player.getMainHandItem().getEnchantmentLevel(Enchantments.SILK_TOUCH);
        var xp = end.getExpDrop(level, level.random, endPos, fortuneLevel, silkTouchLevel);
        if (xp > 0) {
            ExperienceOrb.award(level, Vec3.atCenterOf(startPos), xp);
        }
    }

    private static BlockPos findFarthestConnectedBlock(ServerLevel level, BlockPos start, Block blockType) {
        Queue<BlockPos> queue = new LinkedList<>();
        Set<BlockPos> set = new HashSet<>();
        queue.add(start);
        set.add(start);
        var end = start;
        int count = 0;
        while (!queue.isEmpty() && count < MAX_SEARCH) {
            var current = queue.poll();
            end = current;
            count++;
            for (var value : Direction.values()) {
                var neighbor = current.offset(value.getStepX(), value.getStepY(), value.getStepZ());
                if (!set.contains(neighbor) && level.getBlockState(neighbor).getBlock() == blockType) {
                    queue.add(neighbor);
                    set.add(neighbor);
                }
            }
        }
        return end;
    }
}
