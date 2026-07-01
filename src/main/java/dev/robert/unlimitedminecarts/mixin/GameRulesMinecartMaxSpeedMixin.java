package dev.robert.unlimitedminecarts.mixin;

import net.minecraft.world.level.gamerules.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(value = GameRules.class, remap = false)
abstract class GameRulesMinecartMaxSpeedMixin {
    private static final int STUPID_HIGH_MINECART_GAMERULE_MAX = 1_000_000_000;

    @ModifyArg(
        method = "<clinit>",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/level/gamerules/GameRules;registerInteger(Ljava/lang/String;Lnet/minecraft/world/level/gamerules/GameRuleCategory;IIILnet/minecraft/world/flag/FeatureFlagSet;)Lnet/minecraft/world/level/gamerules/GameRule;"
        ),
        slice = @Slice(
            from = @At(value = "CONSTANT", args = "stringValue=minecartMaxSpeed")
        ),
        index = 4,
        require = 1,
        remap = false
    )
    private static int unlimitedMinecartSpeed$increaseMinecartMaxSpeedRuleCap(int maxValue) {
        return maxValue == 1000 ? STUPID_HIGH_MINECART_GAMERULE_MAX : maxValue;
    }
}
