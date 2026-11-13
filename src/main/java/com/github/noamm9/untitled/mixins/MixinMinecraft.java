package com.github.noamm9.untitled.mixins;

import com.github.noamm9.untitled.crit.control.ClientControlAdapter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MixinMinecraft {
    @Inject(method = "setLevel", at = @At("HEAD"))
    public void onSetLevel(ClientLevel clientLevel, CallbackInfo ci) {
        ClientControlAdapter.worldChanging();
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void critTick(CallbackInfo ci) { ClientControlAdapter.tick(); }
}
