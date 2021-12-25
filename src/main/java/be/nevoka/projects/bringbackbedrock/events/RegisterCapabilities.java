package be.nevoka.projects.bringbackbedrock.events;

import be.nevoka.projects.bringbackbedrock.BringBackBedrock;
import be.nevoka.projects.bringbackbedrock.capability.generatebedrock.GenerateBedrockCapability;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = BringBackBedrock.MODID, bus = Bus.MOD)
public class RegisterCapabilities {
    @SubscribeEvent
    public static void registerCapabilities(final RegisterCapabilitiesEvent event) {
        GenerateBedrockCapability.register(event);
    }
}
