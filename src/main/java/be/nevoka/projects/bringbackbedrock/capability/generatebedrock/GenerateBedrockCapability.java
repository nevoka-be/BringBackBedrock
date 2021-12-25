package be.nevoka.projects.bringbackbedrock.capability.generatebedrock;

import be.nevoka.projects.bringbackbedrock.BringBackBedrock;
import be.nevoka.projects.bringbackbedrock.api.capability.generatebedrock.IGenerateBedrock;
import be.nevoka.projects.bringbackbedrock.capability.SerializableCapabilityProvider;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class GenerateBedrockCapability {

    public static final Capability<IGenerateBedrock> CHUNK_BEDROCK_STATUS_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {});
    public static final Direction DEFAULT_FACING = null;
    public static final byte DEFAULT_STATUS = 0;
    private static final ResourceLocation ID = new ResourceLocation(BringBackBedrock.MODID, "bedrock_status");

    public static void register(final RegisterCapabilitiesEvent event) {
        event.register(IGenerateBedrock.class);
    }

    public static LazyOptional<IGenerateBedrock> getChunkStatus(final Level level, final ChunkPos chunkPos) {
        return getChunkStatus(level.getChunk(chunkPos.x, chunkPos.z));
    }

    public static LazyOptional<IGenerateBedrock> getChunkStatus(final LevelChunk chunk) {
        return chunk.getCapability(CHUNK_BEDROCK_STATUS_CAPABILITY, DEFAULT_FACING);
    }

    @Mod.EventBusSubscriber(modid = BringBackBedrock.MODID)
    @SuppressWarnings("unused")
    private static class EventHandler {
        @SubscribeEvent
        public static void attachChunkCapabilities(final AttachCapabilitiesEvent<LevelChunk> event) {
            final LevelChunk chunk = event.getObject();
            final IGenerateBedrock generateBedrock = new GenerateBedrock(DEFAULT_STATUS);
            event.addCapability(ID, new SerializableCapabilityProvider<>(CHUNK_BEDROCK_STATUS_CAPABILITY, DEFAULT_FACING, generateBedrock));
        }
    }
}
