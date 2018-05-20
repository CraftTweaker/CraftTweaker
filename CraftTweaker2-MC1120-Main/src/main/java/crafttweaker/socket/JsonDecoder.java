package crafttweaker.socket;

import com.google.common.base.Preconditions;
import com.google.gson.*;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

public class JsonDecoder<T> extends MessageToMessageDecoder<String> {

    private static final Gson GSON = new GsonBuilder().create();

    private final Class<T> clazz;

    public JsonDecoder(Class<T> clazz, Class<? extends String> acceptedMsgType) {
        super(acceptedMsgType);
        this.clazz = Preconditions.checkNotNull(clazz);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, String msg, List<Object> out) {
        out.add(GSON.fromJson(msg, clazz));
    }
}