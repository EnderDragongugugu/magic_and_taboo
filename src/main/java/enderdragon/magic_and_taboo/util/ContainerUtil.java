package enderdragon.magic_and_taboo.util;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.Reference2IntOpenHashMap;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Predicate;

public class ContainerUtil {
    public static int find(Container container, Item item) {
        for (int i = 0, n = container.getContainerSize(); i < n; ++i) {
            if (container.getItem(i).getItem() == item) return i;
        }
        return -1;
    }

    public static int find(Container container, TagKey<Item> tag) {
        for (int i = 0, n = container.getContainerSize(); i < n; ++i) {
            if (container.getItem(i).is(tag)) return i;
        }
        return -1;
    }

    public static int find(Container container, Predicate<ItemStack> predicate) {
        for (int i = 0, n = container.getContainerSize(); i < n; ++i) {
            if (predicate.test(container.getItem(i))) return i;
        }
        return -1;
    }

    public static void addItem(Player player, ItemStack stack) {
        if (!player.addItem(stack)) {
            player.drop(stack, false);
        }
    }

    public static Reference2IntOpenHashMap<ItemStack> merge(List<ItemStack> stacks) {
        var merged = new Reference2IntOpenHashMap<ItemStack>();
        for (var stack : stacks) {
            if (stack.isEmpty()) continue;
            boolean found = false;
            for (var iterator = merged.reference2IntEntrySet().fastIterator(); iterator.hasNext(); ) {
                var entry = iterator.next();
                if (!ItemStack.isSameItemSameTags(entry.getKey(), stack)) continue;
                entry.setValue(entry.getIntValue() + stack.getCount());
                found = true;
                break;
            }
            if (found) continue;
            merged.put(stack, stack.getCount());
        }
        return merged;
    }

    public static List<Match> findAllStacks(Container container, List<ItemStack> stacks) {
        var merged = merge(stacks);
        var matches = new ObjectArrayList<Match>();
        for (int i = 0, n = container.getContainerSize(); i < n; ++i) {
            var stack = container.getItem(i);
            for (var iterator = merged.reference2IntEntrySet().fastIterator(); iterator.hasNext(); ) {
                var entry = iterator.next();
                if (!ItemStack.isSameItemSameTags(entry.getKey(), stack)) continue;
                int count = entry.getIntValue() - stack.getCount();
                if (count > 0) {
                    matches.add(new Match(i, stack.getCount()));
                    entry.setValue(count);
                } else {
                    matches.add(new Match(i, entry.getIntValue()));
                    iterator.remove();
                }
                break;
            }
        }
        return merged.isEmpty() ? matches : null;
    }

    @SafeVarargs
    public static @NotNull ItemStack findStack(Item item, List<ItemStack>... areas) {
        for (var stacks : areas) {
            for (var stack : stacks) {
                if (stack.getItem() == item) return stack;
            }
        }
        return ItemStack.EMPTY;
    }

    @SafeVarargs
    public static @Nullable ItemStack findStack(Predicate<ItemStack> predicate, List<ItemStack>... areas) {
        for (var stacks : areas) {
            for (var stack : stacks) {
                if (predicate.test(stack)) return stack;
            }
        }
        return null;
    }

    public static void forcedSync(ServerPlayer player, InteractionHand hand, ItemStack stack) {
        var menu = player.containerMenu;
        if (hand == InteractionHand.OFF_HAND) {
            player.connection.send(new ClientboundContainerSetSlotPacket(menu.containerId, menu.incrementStateId(), InventoryMenu.SHIELD_SLOT, stack));
            return;
        }
        var inventory = player.getInventory();
        var slots = menu.slots;
        for (int i = 0, n = slots.size(), selected = inventory.selected; i < n; ++i) {
            var slot = slots.get(i);
            if (slot.container == inventory && slot.getContainerSlot() == selected) {
                player.connection.send(new ClientboundContainerSetSlotPacket(menu.containerId, menu.incrementStateId(), i, stack));
                return;
            }
        }
    }

    public static boolean canMerge(ItemStack old, ItemStack neo) {
        return old.isEmpty() || (ItemStack.isSameItemSameTags(old, neo) && neo.getCount() + old.getCount() <= old.getMaxStackSize());
    }

    public record Match(int slot, int count) {}
}
