package be.nevoka.projects.bringbedrockback.utils;

import be.nevoka.projects.bringbedrockback.BringBedrockBack;
import be.nevoka.projects.bringbedrockback.config.BBBConfig;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;

import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Arrays;
import java.util.Random;


@Mod.EventBusSubscriber(modid = BringBedrockBack.MOD_ID)
public class RetrogenHandler {

    /**
     * When a chunk is loaded
     * @param event
     */
    @SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
    public static void onEvent(ChunkEvent.Load event) {
        Chunk theChunk = event.getChunk();
        if (BBBConfig.enableRegeneration) {
            regenerateBedrockLayer(theChunk);
        }
    }

    /**
     * Regenerate bedrock layer
     * @param chunk the chunk to regenerate
     */
    public static void regenerateBedrockLayer(Chunk chunk) {
        if (Arrays.stream(BBBConfig.flooredDimensions).anyMatch((e) -> e == chunk.getWorld().provider.getDimensionType().getId())){
            Random rnd = new Random();
            if (!chunk.getBlockState(rnd.nextInt(16),0,rnd.nextInt(16)).getBlock().equals(Blocks.BEDROCK)){
                if(BBBConfig.enableLogOutput) BringBedrockBack.logger.info("Regenerate bedrock chunk at:" + chunk.getPos().toString());
                for (int x = 0; x < 16; ++x) {
                    for (int z = 0; z < 16; ++z) {
                        if (!chunk.getBlockState(x, 0, z).getBlock().equals(Blocks.BEDROCK)) {
                            if(BBBConfig.flooredBedrockLevel != -1){
                                chunk.setBlockState(new BlockPos(x, BBBConfig.flooredBedrockLevel, z), Blocks.BEDROCK.getDefaultState());
                            }
                            if(BBBConfig.flooredBedrockLevel != -1 && Arrays.stream(BBBConfig.roofedDimensions).anyMatch((e) -> e == chunk.getWorld().provider.getDimensionType().getId())){
                                chunk.setBlockState(new BlockPos(x, BBBConfig.roofedBedrockLevel, z), Blocks.BEDROCK.getDefaultState());
                            }
                        }
                    }
                }
                chunk.markDirty();
            }
        }
    }
}
