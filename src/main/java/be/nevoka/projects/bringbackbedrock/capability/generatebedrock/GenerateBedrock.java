package be.nevoka.projects.bringbackbedrock.capability.generatebedrock;

import be.nevoka.projects.bringbackbedrock.api.capability.generatebedrock.IGenerateBedrock;
import net.minecraft.nbt.ByteTag;
import net.minecraft.nbt.Tag;
import net.minecraftforge.common.util.INBTSerializable;

public class GenerateBedrock implements IGenerateBedrock, INBTSerializable<Tag> {
    protected byte status;

    public GenerateBedrock(byte status) {
        this.status = status;
    }

    @Override
    public void setStatus(byte status) {
        this.status = status;
    }

    @Override
    public byte getStatus() {
        return this.status;
    }

    @Override
    public Tag serializeNBT() {
        return ByteTag.valueOf(this.getStatus());
    }

    @Override
    public void deserializeNBT(Tag nbt) {
        if (!(nbt instanceof ByteTag byteNbt))
            throw new IllegalArgumentException("Can not deserialize to an instance that isn't the default implementation");
        this.status = byteNbt.getAsByte();
    }
}
