package me.lor3mipsum.next.client.impl.settings;

import com.lukflug.panelstudio.settings.Toggleable;
import me.lor3mipsum.next.client.setting.Setting;

public class BooleanSetting extends Setting implements Toggleable {
    public boolean enabled;

    public BooleanSetting(String name, boolean enabled) {
        this.name = name;
        this.enabled = enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void toggle() {
        this.enabled = !this.enabled;
    }

    @Override
    public boolean isOn() {
        return enabled;
    }

}
