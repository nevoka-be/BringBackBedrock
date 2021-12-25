package be.nevoka.projects.bringbackbedrock.events;

import be.nevoka.projects.bringbackbedrock.BringBackBedrock;
import be.nevoka.projects.bringbackbedrock.capability.generatebedrock.GenerateBedrock;
import be.nevoka.projects.bringbackbedrock.capability.generatebedrock.GenerateBedrockCapability;
import be.nevoka.projects.bringbackbedrock.config.BBBConfig;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.LevelChunk;

import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod.EventBusSubscriber(modid = BringBackBedrock.MODID)
public class ChunkLoadEvent {

    static Logger logger = LogManager.getLogger();

    @SubscribeEvent
    public static void onChunkLoad(ChunkEvent.Load chunkEvent) {
        LevelChunk theChunk = (LevelChunk) chunkEvent.getChunk();
        regenerateBedrockLayer(theChunk);
    }

    /**
     * Regenerate bedrock layer
     * @param chunk the chunk to regenerate
     */
    public static void regenerateBedrockLayer(LevelChunk chunk) {
        GenerateBedrockCapability
                .getChunkStatus(chunk)
                .ifPresent(generateBedrock -> {
                    if (!(generateBedrock instanceof GenerateBedrock)) return;
                    if (!generateBedrock.getStatus()) {
                        if(BBBConfig.Common.enableLogging.get()) logger.info("Generate bedrock for chunk " + chunk.getPos() + "");
                        if (BBBConfig.Common.containsInFloorFilter(chunk.getLevel().dimension().location())) {
                            BlockPos
                                    .betweenClosed(0, chunk.getLevel().getMinBuildHeight(), 0, 15, chunk.getLevel().getMinBuildHeight(), 15)
                                    .forEach(blockPos -> {
                                        if (!chunk.getBlockState(blockPos).is(Blocks.BEDROCK))
                                            chunk.setBlockState(blockPos, Blocks.BEDROCK.defaultBlockState(), false);
                                    });
                        }

                        if (BBBConfig.Common.containsInRoofFilter(chunk.getLevel().dimension().location())) {
                            BlockPos
                                    .betweenClosed(0, chunk.getLevel().getMaxBuildHeight() - 1, 0, 15, chunk.getLevel().getMaxBuildHeight() - 1, 15)
                                    .forEach(blockPos -> {
                                        if (!chunk.getBlockState(blockPos).is(Blocks.BEDROCK))
                                            chunk.setBlockState(blockPos, Blocks.BEDROCK.defaultBlockState(), false);
                                    });
                        }

                        generateBedrock.setStatus((byte) 1);
                        chunk.setUnsaved(true);
                    }
                });
    }
}
