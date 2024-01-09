package com.blamejared.crafttweaker.natives.text.content;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.openzen.zencode.java.ZenCodeType;

import java.util.Collection;

@ZenRegister
@Document("vanilla/api/text/CommonComponents")
@ZenCodeType.Name("crafttweaker.api.text.CommonComponents")
public class ExpandCommonComponents {
    
    @ZenCodeType.Field
    public static final Component EMPTY = CommonComponents.EMPTY;
    @ZenCodeType.Field
    public static final Component OPTION_ON = CommonComponents.OPTION_ON;
    @ZenCodeType.Field
    public static final Component OPTION_OFF = CommonComponents.OPTION_OFF;
    @ZenCodeType.Field
    public static final Component GUI_DONE = CommonComponents.GUI_DONE;
    @ZenCodeType.Field
    public static final Component GUI_CANCEL = CommonComponents.GUI_CANCEL;
    @ZenCodeType.Field
    public static final Component GUI_YES = CommonComponents.GUI_YES;
    @ZenCodeType.Field
    public static final Component GUI_NO = CommonComponents.GUI_NO;
    @ZenCodeType.Field
    public static final Component GUI_OK = CommonComponents.GUI_OK;
    @ZenCodeType.Field
    public static final Component GUI_PROCEED = CommonComponents.GUI_PROCEED;
    @ZenCodeType.Field
    public static final Component GUI_CONTINUE = CommonComponents.GUI_CONTINUE;
    @ZenCodeType.Field
    public static final Component GUI_BACK = CommonComponents.GUI_BACK;
    @ZenCodeType.Field
    public static final Component GUI_TO_TITLE = CommonComponents.GUI_TO_TITLE;
    @ZenCodeType.Field
    public static final Component GUI_ACKNOWLEDGE = CommonComponents.GUI_ACKNOWLEDGE;
    @ZenCodeType.Field
    public static final Component GUI_OPEN_IN_BROWSER = CommonComponents.GUI_OPEN_IN_BROWSER;
    @ZenCodeType.Field
    public static final Component GUI_COPY_LINK_TO_CLIPBOARD = CommonComponents.GUI_COPY_LINK_TO_CLIPBOARD;
    @ZenCodeType.Field
    public static final Component GUI_DISCONNECT = CommonComponents.GUI_DISCONNECT;
    @ZenCodeType.Field
    public static final Component CONNECT_FAILED = CommonComponents.CONNECT_FAILED;
    @ZenCodeType.Field
    public static final Component NEW_LINE = CommonComponents.NEW_LINE;
    @ZenCodeType.Field
    public static final Component NARRATION_SEPARATOR = CommonComponents.NARRATION_SEPARATOR;
    @ZenCodeType.Field
    public static final Component ELLIPSIS = CommonComponents.ELLIPSIS;
    @ZenCodeType.Field
    public static final Component SPACE = CommonComponents.SPACE;
    
    @ZenCodeType.StaticExpansionMethod
    public static MutableComponent space() {
        
        return CommonComponents.space();
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static MutableComponent days(long days) {
        
        return CommonComponents.days(days);
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static MutableComponent hours(long hours) {
        
        return CommonComponents.hours(hours);
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static MutableComponent minutes(long minutes) {
        
        return CommonComponents.minutes(minutes);
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static Component optionStatus(boolean status) {
        
        return CommonComponents.optionStatus(status);
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static MutableComponent optionStatus(Component option, boolean status) {
        
        return CommonComponents.optionStatus(option, status);
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static MutableComponent optionNameValue(Component option, Component value) {
        
        return CommonComponents.optionNameValue(option, value);
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static MutableComponent joinForNarration(Component... components) {
        
        return CommonComponents.joinForNarration(components);
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static Component joinLines(Component... components) {
        
        return CommonComponents.joinLines(components);
    }
    
    @ZenCodeType.StaticExpansionMethod
    public static Component joinLines(Collection<? extends Component> components) {
        
        return CommonComponents.joinLines(components);
    }
    
}
