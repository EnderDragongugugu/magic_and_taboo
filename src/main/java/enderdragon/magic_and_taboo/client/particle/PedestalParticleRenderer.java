package enderdragon.magic_and_taboo.client.particle;

import enderdragon.magic_and_taboo.block.MagicPerfusionPedestalBlock;
import enderdragon.magic_and_taboo.block.entity.PedestalBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class PedestalParticleRenderer {
    private static final Minecraft MC = Minecraft.getInstance();
    private static final RandomSource RANDOM = RandomSource.create();

    public static void render(Level level, BlockPos centerPos, List<ItemStack> ingredients, ItemStack core, float progress, PedestalParticleType particleType) {
        if (particleType == PedestalParticleType.NONE) return;

        if (!(level instanceof ClientLevel clientLevel)) return;

        switch (particleType) {
            case RISE -> renderRise(clientLevel, centerPos, ingredients, core, progress);
            case SPIRAL -> renderSpiral(clientLevel, centerPos, ingredients, core, progress);
        }
    }

    private static void renderRise(ClientLevel clientLevel, BlockPos centerPos, List<ItemStack> ingredients, ItemStack core, float progress) {
        Vec3 center = Vec3.atCenterOf(centerPos);

        // 根据进度计算粒子数量和高度
        int particleCount = (int) (2 + progress * 6);
        float height = 1.0f + progress * 2.0f;

        // 使用 withCore 物品纹理的上升粒子 - 从方块中间上升
        ItemStack particleStack = !core.isEmpty() ? core : ingredients.stream().filter(s -> !s.isEmpty()).findFirst().orElse(ItemStack.EMPTY);

        if (!particleStack.isEmpty()) {
            for (int i = 0; i < particleCount; i++) {
                // 从方块中间位置生成粒子
                double offsetX = (RANDOM.nextDouble() - 0.5) * 0.3;
                double offsetZ = (RANDOM.nextDouble() - 0.5) * 0.3;

                double x = center.x + offsetX;
                double y = center.y + 0.5 + RANDOM.nextDouble() * height;
                double z = center.z + offsetZ;

                double velX = (RANDOM.nextDouble() - 0.5) * 0.01;
                double velY = 0.03 + progress * 0.08;
                double velZ = (RANDOM.nextDouble() - 0.5) * 0.01;

                // 使用 withCore 物品纹理
                clientLevel.addParticle(
                        new ItemParticleOption(ParticleTypes.ITEM, particleStack),
                        x, y, z, velX, velY, velZ
                );
            }
        }

        // requires 物品粒子飘向 withCore 物品（类似末影水晶链接效果）
        int ingredientIndex = 0;
        for (ItemStack stack : ingredients) {
            if (stack.isEmpty()) {
                ingredientIndex++;
                continue;
            }

            // 获取周围基座的位置
            if (ingredientIndex < MagicPerfusionPedestalBlock.POS_LIST.size()) {
                BlockPos pedestalPos = centerPos.offset(MagicPerfusionPedestalBlock.POS_LIST.get(ingredientIndex));
                Vec3 pedestalCenter = Vec3.atCenterOf(pedestalPos);

                // 为每个物品生成飘向中心的粒子
                int linkParticleCount = 1 + (int) (progress * 3);
                for (int i = 0; i < linkParticleCount; i++) {
                    // 从基座生成粒子
                    double startOffsetX = (RANDOM.nextDouble() - 0.5) * 0.4;
                    double startOffsetY = 0.3 + RANDOM.nextDouble() * 0.5;
                    double startOffsetZ = (RANDOM.nextDouble() - 0.5) * 0.4;

                    double startX = pedestalCenter.x + startOffsetX;
                    double startY = pedestalCenter.y + startOffsetY;
                    double startZ = pedestalCenter.z + startOffsetZ;

                    // 计算指向中心的速度向量（类似末影水晶链接效果）
                    Vec3 direction = center.subtract(pedestalCenter).normalize();
                    double speed = 0.05 + progress * 0.15;
                    double wobble = (RANDOM.nextDouble() - 0.5) * 0.02;

                    double velX = direction.x * speed + wobble;
                    double velY = direction.y * speed * 0.3 + 0.01; // 稍微向上的分量
                    double velZ = direction.z * speed + wobble;

                    // 使用 withCore 物品纹理
                    clientLevel.addParticle(
                            new ItemParticleOption(ParticleTypes.ITEM, particleStack),
                            startX, startY, startZ, velX, velY, velZ
                    );
                }
            }
            ingredientIndex++;
        }

        // 进度高时添加额外魔法特效
        if (progress > 0.5f) {
            int extraParticles = (int) ((progress - 0.5f) * 10);
            for (int i = 0; i < extraParticles; i++) {
                double angle = RANDOM.nextFloat() * Math.PI * 2;
                double radius = 0.5 + RANDOM.nextDouble() * 0.5;

                double x = center.x + Math.cos(angle) * radius;
                double z = center.z + Math.sin(angle) * radius;
                double y = center.y + 0.5 + RANDOM.nextDouble() * 0.5;

                clientLevel.addParticle(
                        ParticleTypes.ENCHANT,
                        x, y, z,
                        0, 0.05, 0
                );
            }
        }
    }

    private static void renderSpiral(ClientLevel clientLevel, BlockPos centerPos, List<ItemStack> ingredients, ItemStack core, float progress) {
        Vec3 center = Vec3.atCenterOf(centerPos);

        int particleCount = (int) (3 + progress * 5);
        double spiralSpeed = 0.1 + progress * 0.2;
        double spiralRadius = 1.5 + progress * 0.5;
        double inwardSpeed = 0.02 + progress * 0.03;

        double baseAngle = (System.currentTimeMillis() / 1000.0) * Math.PI * 2;

        // 使用 withCore 物品纹理
        ItemStack particleStack = !core.isEmpty() ? core : ingredients.stream().filter(s -> !s.isEmpty()).findFirst().orElse(ItemStack.EMPTY);

        for (ItemStack stack : ingredients) {
            if (stack.isEmpty()) continue;

            for (int i = 0; i < particleCount; i++) {
                double angle = baseAngle + (i * Math.PI * 2 / particleCount);
                double radius = spiralRadius * (0.5 + RANDOM.nextDouble() * 0.5);

                double startX = center.x + Math.cos(angle) * radius;
                double startZ = center.z + Math.sin(angle) * radius;
                double startY = center.y + 0.5 + RANDOM.nextDouble() * 0.5;

                // 向心的速度
                double velX = (center.x - startX) * inwardSpeed;
                double velZ = (center.z - startZ) * inwardSpeed;
                double velY = 0.02 + RANDOM.nextDouble() * 0.03;

                // 切向速度（螺旋效果）
                velX += Math.sin(angle) * spiralSpeed * 0.1;
                velZ -= Math.cos(angle) * spiralSpeed * 0.1;

                // 使用 withCore 物品纹理
                clientLevel.addParticle(
                        new ItemParticleOption(ParticleTypes.ITEM, particleStack),
                        startX, startY, startZ,
                        velX, velY, velZ
                );
            }
        }

        // requires 物品粒子飘向 withCore 物品（类似末影水晶链接效果）
        int ingredientIndex = 0;
        for (ItemStack stack : ingredients) {
            if (stack.isEmpty()) {
                ingredientIndex++;
                continue;
            }

            if (ingredientIndex < MagicPerfusionPedestalBlock.POS_LIST.size()) {
                BlockPos pedestalPos = centerPos.offset(MagicPerfusionPedestalBlock.POS_LIST.get(ingredientIndex));
                Vec3 pedestalCenter = Vec3.atCenterOf(pedestalPos);

                // 为每个物品生成飘向中心的粒子
                int linkParticleCount = 1 + (int) (progress * 2);
                for (int i = 0; i < linkParticleCount; i++) {
                    double startOffsetX = (RANDOM.nextDouble() - 0.5) * 0.4;
                    double startOffsetY = 0.3 + RANDOM.nextDouble() * 0.5;
                    double startOffsetZ = (RANDOM.nextDouble() - 0.5) * 0.4;

                    double startX = pedestalCenter.x + startOffsetX;
                    double startY = pedestalCenter.y + startOffsetY;
                    double startZ = pedestalCenter.z + startOffsetZ;

                    Vec3 direction = center.subtract(pedestalCenter).normalize();
                    double speed = 0.04 + progress * 0.12;
                    double wobble = (RANDOM.nextDouble() - 0.5) * 0.02;

                    double velX = direction.x * speed + wobble;
                    double velY = direction.y * speed * 0.3 + 0.01;
                    double velZ = direction.z * speed + wobble;

                    clientLevel.addParticle(
                            new ItemParticleOption(ParticleTypes.ITEM, particleStack),
                            startX, startY, startZ, velX, velY, velZ
                    );
                }
            }
            ingredientIndex++;
        }

        // 中心漩涡效果 - 使用附魔粒子
        for (int i = 0; i < particleCount; i++) {
            double angle = baseAngle + (i * Math.PI * 2 / particleCount) + Math.PI;
            double radius = 0.3 + RANDOM.nextDouble() * 0.3;

            double startX = center.x + Math.cos(angle) * radius;
            double startZ = center.z + Math.sin(angle) * radius;
            double startY = center.y + 0.3 + RANDOM.nextDouble() * 0.4;

            double velX = (center.x - startX) * 0.05;
            double velZ = (center.z - startZ) * 0.05;
            double velY = 0.05 + RANDOM.nextDouble() * 0.05;

            velX += Math.sin(angle) * 0.1;
            velZ -= Math.cos(angle) * 0.1;

            clientLevel.addParticle(
                    ParticleTypes.ENCHANT,
                    startX, startY, startZ,
                    velX, velY, velZ
            );
        }

        // 高进度时的额外效果
        if (progress > 0.7f) {
            int extraParticles = (int) ((progress - 0.7f) * 15);
            for (int i = 0; i < extraParticles; i++) {
                double angle = RANDOM.nextFloat() * Math.PI * 2;
                double radius = 0.2 + RANDOM.nextDouble() * 0.8;

                double x = center.x + Math.cos(angle) * radius;
                double z = center.z + Math.sin(angle) * radius;
                double y = center.y + 0.5 + RANDOM.nextDouble();

                clientLevel.addParticle(
                        ParticleTypes.EFFECT,
                        x, y, z,
                        (center.x - x) * 0.1,
                        0.1,
                        (center.z - z) * 0.1
                );
            }
        }
    }
}
