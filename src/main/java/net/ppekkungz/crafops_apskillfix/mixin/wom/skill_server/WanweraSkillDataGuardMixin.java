package net.ppekkungz.crafops_apskillfix.mixin.wom.skill_server;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.logging.LogUtils;

import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerPlayer;
import yesman.epicfight.network.EpicFightNetworkManager;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.SkillDataKey;
import yesman.epicfight.skill.SkillDataManager;

@Mixin(value = SkillDataManager.class, remap = false)
public abstract class WanweraSkillDataGuardMixin {
    private static final Logger WANWERA_LOGGER = LogUtils.getLogger();
    private static final long WANWERA_RESYNC_INTERVAL_MS = 2000L;
    private static final Map<UUID, Long> WANWERA_LAST_RESYNC = new ConcurrentHashMap<>();

    @Shadow @Final
    private SkillContainer container;

    @Shadow
    public abstract boolean hasData(Holder<SkillDataKey<?>> key);

    @Inject(method = "setDataRawtype", at = @At("HEAD"), cancellable = true, remap = false)
    private void wanwera$guardUnregisteredKey(Holder<SkillDataKey<?>> key, Object data, CallbackInfo ci) {
        if (this.hasData(key) || this.container.getExecutor().isLogicalClient()) {
            return;
        }

        // Server side, key not registered for this container: the client thinks this slot holds a skill
        // using this key, the server does not. Drop the write instead of throwing and push the server's
        // slot state to the client (rate limited) so both sides agree again.
        ci.cancel();

        ServerPlayer player = this.container.getServerExecutor().getOriginal();
        long now = System.currentTimeMillis();
        Long last = WANWERA_LAST_RESYNC.get(player.getUUID());

        if (last == null || now - last >= WANWERA_RESYNC_INTERVAL_MS) {
            WANWERA_LAST_RESYNC.put(player.getUUID(), now);

            WANWERA_LOGGER.warn(
                    "[SkillFix-Wanwera] Skill desync from {}: key {} on slot {} but server container holds {}. Resyncing slot.",
                    player.getGameProfile().getName(),
                    key.getRegisteredName(),
                    this.container.getSlot(),
                    this.container.isEmpty() ? "nothing" : this.container.getSkill().getClass().getSimpleName()
            );

            EpicFightNetworkManager.sendToPlayer(this.container.createSyncPacketToLocalPlayer(), player);
        }
    }
}