package net.ppekkungz.crafops_apskillfix.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.api.event.types.animation.StartActionEvent;
import yesman.epicfight.skill.SkillContainer;

@Mixin(targets = "reascer.wom.skill.passive.PainAnticipationSkill", remap = false)
public class PainAnticipationSkillMixin {
    @Inject(method = "lambda$onInitiate$1(Lyesman/epicfight/skill/SkillContainer;Lyesman/epicfight/api/event/types/animation/StartActionEvent;)V", at = @At("HEAD"), cancellable = true, remap = false)
    private void womfix$skipOnClient(SkillContainer container, StartActionEvent event, CallbackInfo ci) {
        if (container.getExecutor().isLogicalClient()) ci.cancel();
    }
}