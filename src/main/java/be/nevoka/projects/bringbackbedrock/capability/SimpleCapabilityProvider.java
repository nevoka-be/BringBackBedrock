package be.nevoka.projects.bringbackbedrock.capability;

import com.google.common.base.Preconditions;
import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nullable;

public class SimpleCapabilityProvider<HANDLER> implements ICapabilityProvider {

    protected final Capability<HANDLER> capability;
    protected final Direction facing;
    protected final HANDLER instance;

    protected final LazyOptional<HANDLER> lazyOptional;

    public SimpleCapabilityProvider(final Capability<HANDLER> capability, @Nullable final Direction facing, final HANDLER instance) {
        this.capability = Preconditions.checkNotNull(capability, "capability");
        this.facing = facing;
        this.instance = Preconditions.checkNotNull(instance, "instance");

        lazyOptional = LazyOptional.of(() -> this.instance);
    }

    @Override
    public <T> LazyOptional<T> getCapability(final Capability<T> capability, @Nullable final Direction facing) {
        return getCapability().orEmpty(capability, lazyOptional);
    }

    public final Capability<HANDLER> getCapability() {
        return capability;
    }

    @Nullable
    public Direction getFacing() {
        return facing;
    }

    public final HANDLER getInstance() {
        return instance;
    }
}