package net.ppekkungz.crafops_apskillfix.mixin.wom.skill_server;

import net.minecraft.sounds.SoundEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

@Mixin(targets = "reascer.wom.skill.passive.HeartShieldSkill", remap = false)
public class HeartShieldSkillMixin {
    @Redirect(method = "updateContainer", at = @At(value = "INVOKE", target = "Lyesman/epicfight/world/capabilities/entitypatch/player/PlayerPatch;playSound(Lnet/minecraft/sounds/SoundEvent;FFF)V", remap = false), remap = false)
    private void womfix$noShieldSound(PlayerPatch<?> patch, SoundEvent sound, float volume, float minPitch, float maxPitch) {
    }
}