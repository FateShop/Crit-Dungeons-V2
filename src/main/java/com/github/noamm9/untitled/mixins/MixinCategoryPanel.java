package com.github.noamm9.untitled.mixins;

import com.github.noamm9.ui.clickgui.components.CategoryPanel;
import com.github.noamm9.ui.clickgui.enums.CategoryType;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(CategoryPanel.class)
public abstract class MixinCategoryPanel {
    @ModifyExpressionValue(method = "render", at = @At(value = "INVOKE",
        target = "Lcom/github/noamm9/ui/clickgui/enums/CategoryType;name()Ljava/lang/String;"))
    private String critAddonsLabel(String name) {
        return "CRITADDONS".equals(name) ? "Crit Addons" : name;
    }
}
