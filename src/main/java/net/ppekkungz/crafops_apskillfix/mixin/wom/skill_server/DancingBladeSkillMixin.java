package net.ppekkungz.crafops_apskillfix.mixin.wom.skill_server;

import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(targets = "reascer.wom.skill.passive.DancingBladeSkill", remap = false)
public class DancingBladeSkillMixin {
    @Redirect(method = "lambda$onInitiate$2", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;playSound(Lnet/minecraft/world/entity/player/Player;DDDLnet/minecraft/core/Holder;Lnet/minecraft/sounds/SoundSource;FF)V", remap = false), remap = false)
    private static void womfix$noNoteSound(ServerLevel level, Player player, double x, double y, double z, Holder<SoundEvent> sound, SoundSource source, float volume, float pitch) {
    }
}