package net.ppekkungz.crafops_apskillfix.mixin.wom.skill_server;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(targets = "reascer.wom.skill.passive.PainAnticipationSkill", remap = false)
public class PainAnticipationParticleMixin {
    @Redirect(method = {"updateContainer", "lambda$onInitiate$0", "lambda$onInitiate$1"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;sendParticles(Lnet/minecraft/core/particles/ParticleOptions;DDDIDDDD)I", remap = false), remap = false)
    private static int womfix$noParticles(ServerLevel level, ParticleOptions type, double x, double y, double z, int n, double dx, double dy, double dz, double speed) {
        return 0;
    }

    @Redirect(method = "lambda$onInitiate$0", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;playSound(Lnet/minecraft/world/entity/player/Player;DDDLnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FF)V", remap = false), remap = false)
    private static void womfix$noSound(Level level, Player player, double x, double y, double z, SoundEvent sound, SoundSource source, float volume, float pitch) {
    }
}