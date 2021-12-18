package be.nevoka.projects.bringbackbedrock.config;

import be.nevoka.projects.bringbackbedrock.BringBackBedrock;
import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeConfigSpec;

import javax.annotation.Nonnull;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class BBBConfig {
    public static class Common{
        public static ForgeConfigSpec.ConfigValue<List<? extends String>> flooredDimensions;
        public static ForgeConfigSpec.ConfigValue<List<? extends String>> roofededDimensions;

        private static final ForgeConfigSpec.Builder COMMON = new ForgeConfigSpec.Builder();
        public static final ForgeConfigSpec common_config;

        static {
            Common.init(COMMON);
            common_config = COMMON.build();
            BringBackBedrock.LOGGER.debug("Config for server has been initialized");
        }

        public static void init(ForgeConfigSpec.Builder builder) {
            builder.push("General");
            flooredDimensions = builder
                    .comment("A list of dimensions where there needs to be a bedrock floor")
                    .defineList("flooredDimensions", Common::getFlooredLevels, o -> o instanceof String);
            roofededDimensions = builder
                    .comment("A list of dimensions where there needs to be a bedrock roof")
                    .defineList("roofededDimensions", Common::getRoofedLevels, o -> o instanceof String);
            builder.pop();
        }

        private static List<String> getFlooredLevels(){
            return Lists.newArrayList(
                            Level.OVERWORLD,
                            Level.NETHER
                    )
                    .stream()
                    .map(ResourceKey::location)
                    .map(ResourceLocation::toString)
                    .collect(Collectors.toList());
        }

        private static List<String> getRoofedLevels(){
            return Lists.newArrayList(
                            Level.OVERWORLD,
                            Level.NETHER
                    )
                    .stream()
                    .map(ResourceKey::location)
                    .map(ResourceLocation::toString)
                    .collect(Collectors.toList());
        }

        public static void loadConfig(ForgeConfigSpec config, Path path) {
            BringBackBedrock.LOGGER.debug("Loading server config");
            final CommentedFileConfig file = CommentedFileConfig
                    .builder(path)
                    .sync()
                    .autosave()
                    .writingMode(WritingMode.REPLACE)
                    .build();
            BringBackBedrock.LOGGER.debug("Built server config");
            file.load();
            BringBackBedrock.LOGGER.debug("Loaded server config");
            config.setConfig(file);
        }

        public static boolean containsInRoofFilter(@Nonnull ResourceLocation levelName) {
            Preconditions.checkNotNull(levelName, "String to parse must not be null");
            return roofededDimensions.get().stream().anyMatch(s -> s.contains(levelName.toString()));
        }

        public static boolean containsInFloorFilter(@Nonnull ResourceLocation levelName) {
            Preconditions.checkNotNull(levelName, "String to parse must not be null");
            return flooredDimensions.get().stream().anyMatch(s -> s.contains(levelName.toString()));
        }
    }
}

