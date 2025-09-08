package enderdragon.magic_and_taboo.capability;

import enderdragon.magic_and_taboo.init.MATCapabilities;
import enderdragon.magic_and_taboo.init.MagicPotionSolvents;
import enderdragon.magic_and_taboo.registry.Element;
import it.unimi.dsi.fastutil.objects.Reference2FloatMap;
import it.unimi.dsi.fastutil.objects.Reference2FloatOpenHashMap;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MagicPotionImpl extends ElementStorageImpl implements MagicPotion {
    private @Nullable FluidType solvent;
    protected final float timeFactor;
    protected final int baseLevel;

    public MagicPotionImpl(float timeFactor, int baseLevel) {
        this.timeFactor = timeFactor;
        this.baseLevel = baseLevel;
    }

    @Override
    protected int getDefaultColor() {
        return MagicPotionSolvents.getSolvent(this.solvent).getColor();
    }

    @Override
    protected List<MobEffectInstance> resolveEffects(Reference2FloatMap<Element> concentrations) {
        return MagicPotionSolvents.getSolvent(this.solvent)
                .getEffects(this.getConcentrations(), this.timeFactor, this.baseLevel);
    }

    @Override
    public void setContent(@Nullable FluidType solvent, Reference2FloatMap<Element> elements) {
        super.setConcentrations(elements);
        this.solvent = solvent == ForgeMod.EMPTY_TYPE.get() ? null : solvent;
    }

    @Override
    public @Nullable FluidType getSolvent() {
        return this.solvent;
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {
        return cap == MATCapabilities.MAGIC_POTION ? this.holder.cast() : super.getCapability(cap, side);
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    public CompoundTag serializeNBT() {
        var root = new CompoundTag();
        if (this.solvent != null) {
            root.putString("Solvent", ForgeRegistries.FLUID_TYPES.get().getKey(this.solvent).toString());
        }
        root.put("Elements", super.serializeNBT());
        return root;
    }

    @Override
    public void deserializeNBT(CompoundTag root) {
        var elements = deserializeElements(root.getCompound("Elements"));
        if (elements == null) {
            this.setContent(null, new Reference2FloatOpenHashMap<>());
            return;
        }
        var solvent = ResourceLocation.tryParse(root.getString("Solvent"));
        this.setContent(solvent == null ? null : ForgeRegistries.FLUID_TYPES.get().getValue(solvent), elements);
    }
}
