package cc.crss.mod.bridge;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.text.Text;
import net.minecraft.text.TextCodecs;
import net.minecraft.text.TextContent;

public record BridgeTextContent(Text content, boolean fromBot) implements TextContent {
    public static MapCodec<BridgeTextContent> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(TextCodecs.CODEC.fieldOf("text").forGetter(BridgeTextContent::content)).apply(instance, BridgeTextContent::new)
    );
    @Override
    public Type<?> getType() {
        return null;
    }
}
