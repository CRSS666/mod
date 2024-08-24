package cc.crss.mod.mixin;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.ProtectionEnchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(Enchantment.class)
public class EnchantmentMixin {

    @Shadow
    protected boolean differs(Enchantment other) {
        return ((Object)this) != other;
    }

    @Unique
    protected boolean inverseDiffers(Enchantment other) {
        return other != ((Object)this);
    }

    /**
     * @author WorldWidePixel
     * @reason for 1.14+ CRSS fun
     */
    @Overwrite
    public final boolean isDifferent(Enchantment other) {
        if (other instanceof ProtectionEnchantment) {
            return true;
        }
        return this.differs(other) && inverseDiffers(other);
    }
}
