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


@Mod.EventBusSubscriber(modid = BringBedrockBack.MOD_ID)
public class RetrogenHandler {

    @SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
    public static void onEvent(ChunkEvent.Load event) {
        Chunk theChunk = event.getChunk();
        if (BBBConfig.enableRegeneration) {
            generateBedrockLayer(theChunk);
        }
    }

    public static void generateBedrockLayer(Chunk chunk) {
        if (Arrays.stream(BBBConfig.regenerateFlooredDimensions).anyMatch((e) -> e == chunk.getWorld().provider.getDimensionType().getId())){
            BringBedrockBack.logger.info("Regenerate bedrock chunk at:" + chunk.getPos().toString());
            for (int x = 0; x < 16; ++x) {
                for (int z = 0; z < 16; ++z) {
                    if (chunk.getBlockState(x, 0, z).getBlock() != Blocks.BEDROCK) {
                        if(BBBConfig.flooredBedrockLevel != -1){
                            chunk.setBlockState(new BlockPos(x, BBBConfig.flooredBedrockLevel, z), Blocks.BEDROCK.getDefaultState());
                        }
                        if(BBBConfig.flooredBedrockLevel != -1 && Arrays.stream(BBBConfig.regenerateRoofedDimensions).anyMatch((e) -> e == chunk.getWorld().provider.getDimensionType().getId())){
                            chunk.setBlockState(new BlockPos(x, BBBConfig.roofedBedrockLevel, z), Blocks.BEDROCK.getDefaultState());
                        }
                    }
                }
            }
            chunk.markDirty();
        }
    }
}
