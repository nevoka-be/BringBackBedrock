package be.nevoka.projects.bringbackbedrock.events;

import be.nevoka.projects.bringbackbedrock.BringBackBedrock;
import be.nevoka.projects.bringbackbedrock.config.BBBConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

@Mod.EventBusSubscriber(modid = BringBackBedrock.MODID)
public class ChunkLoadEvent {
    @SubscribeEvent
    public static void onChunkLoad(ChunkEvent.Load chunkEvent) {
        LevelChunk theChunk = (LevelChunk) chunkEvent.getChunk();
        regenerateBedrockLayer(theChunk);
    }

    /**
     * Regenerate bedrock layer
     *
     * @param chunk the chunk to regenerate
     */
    public static void regenerateBedrockLayer(LevelChunk chunk) {
        Random rnd = chunk.getLevel().random;

        if (BBBConfig.Common.containsInFloorFilter(chunk.getLevel().dimension().location())) {
            if (!chunk.getBlockState(new BlockPos(rnd.nextInt(16), chunk.getLevel().getMinBuildHeight(), rnd.nextInt(16))).getBlock().equals(Blocks.BEDROCK)) {
                BlockPos
                        .betweenClosed(0, chunk.getLevel().getMinBuildHeight(), 0, 15, chunk.getLevel().getMinBuildHeight(), 15)
                        .forEach(blockPos -> {
                            chunk.setBlockState(blockPos, Blocks.BEDROCK.defaultBlockState(), false);
                        });
            }
        }
        if (BBBConfig.Common.containsInRoofFilter(chunk.getLevel().dimension().location())) {
            if (!chunk.getBlockState(new BlockPos(rnd.nextInt(16), chunk.getLevel().getMaxBuildHeight() - 1, rnd.nextInt(16))).getBlock().equals(Blocks.BEDROCK)) {
                BlockPos
                        .betweenClosed(0, chunk.getLevel().getMaxBuildHeight() - 1, 0, 15, chunk.getLevel().getMaxBuildHeight() - 1, 15)
                        .forEach(blockPos -> {
                            chunk.setBlockState(blockPos, Blocks.BEDROCK.defaultBlockState(), false);
                        });
            }
        }
    }
}
