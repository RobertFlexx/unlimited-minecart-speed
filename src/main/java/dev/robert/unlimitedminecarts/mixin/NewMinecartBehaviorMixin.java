package dev.robert.unlimitedminecarts.mixin;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.vehicle.minecart.NewMinecartBehavior;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PoweredRailBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = NewMinecartBehavior.class, remap = false)
abstract class NewMinecartBehaviorMixin {
    private static final double POWERED_RAIL_SPEED_MULTIPLIER = 1.5D;
    private static final double POWERED_RAIL_SPEED_ADDITION = 0.2D;

    @Inject(
        method = "calculateBoostTrackSpeed",
        at = @At("RETURN"),
        cancellable = true,
        remap = false
    )
    private void unlimitedMinecartSpeed$exponentialPoweredRailAcceleration(
        Vec3 movement,
        net.minecraft.core.BlockPos railPos,
        BlockState railState,
        CallbackInfoReturnable<Vec3> cir
    ) {
        if (!railState.is(Blocks.POWERED_RAIL) || !railState.getValue(PoweredRailBlock.POWERED)) {
            return;
        }

        Vec3 boosted = cir.getReturnValue();
        if (boosted.horizontalDistanceSqr() <= 0.0D) {
            return;
        }

        double currentSpeed = Math.max(movement.horizontalDistance(), boosted.horizontalDistance());
        double targetSpeed = currentSpeed * POWERED_RAIL_SPEED_MULTIPLIER + POWERED_RAIL_SPEED_ADDITION;
        double maxSpeed = getMaxMinecartSpeedPerTick();

        cir.setReturnValue(boosted.horizontal().normalize().scale(Math.min(targetSpeed, maxSpeed)));
    }

    private double getMaxMinecartSpeedPerTick() {
        NewMinecartBehavior behavior = (NewMinecartBehavior) (Object) this;
        Level level = behavior.level();
        if (!(level instanceof ServerLevel serverLevel)) {
            return 50.0D;
        }

        return behavior.getMaxSpeed(serverLevel);
    }
}
