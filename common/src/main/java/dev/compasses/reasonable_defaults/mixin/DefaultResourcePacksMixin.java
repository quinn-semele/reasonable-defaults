package dev.compasses.reasonable_defaults.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import dev.compasses.reasonable_defaults.Constants;
import net.minecraft.client.Options;
import net.minecraft.server.packs.repository.PackRepository;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Mixin(Options.class)
public class DefaultResourcePacksMixin {
    @Shadow @Final private File optionsFile;

    @Inject(
            method = "loadSelectedResourcePacks(Lnet/minecraft/server/packs/repository/PackRepository;)V",
            at = @At(
                    value = "INVOKE",
            target = "Lnet/minecraft/server/packs/repository/PackRepository;setSelected(Ljava/util/Collection;)V"
        )
    )
    private void reasonableDefaults$injectDefaultResourcePacks(PackRepository resourcePackList, CallbackInfo ci, @Local(ordinal = 0) Set<String> selectedPacks) {
        // if new install, selectedPacks = []
        // if existing, selectedPacks is at least [vanilla, mod_resources]

        List<String> modifiedPacks = new ArrayList<>(selectedPacks);

        Constants.LOG.info("Option file exists: {}", optionsFile.exists());
        Constants.LOG.info("Enabling the following packs: ");

        for (String selectedPack : modifiedPacks) {
            Constants.LOG.info(selectedPack);
        }

        selectedPacks.clear();
        selectedPacks.addAll(modifiedPacks);
    }
}
