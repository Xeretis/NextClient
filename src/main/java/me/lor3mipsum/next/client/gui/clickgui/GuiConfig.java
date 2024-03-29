package me.lor3mipsum.next.client.gui.clickgui;

import com.google.gson.*;
import com.lukflug.panelstudio.ConfigList;
import com.lukflug.panelstudio.PanelConfig;

import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GuiConfig implements ConfigList {
    private final String fileLocation;
    private JsonObject panelObject = null;

    public GuiConfig(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    @Override
    public void begin(boolean loading) {
        if (loading) {
            if (!Files.exists(Paths.get(fileLocation + "ClickGui" + ".json"))) {
                return;
            }
            try {
                InputStream inputStream;
                inputStream = Files.newInputStream(Paths.get(fileLocation + "ClickGui" + ".json"));
                JsonObject mainObject = new JsonParser().parse(new InputStreamReader(inputStream)).getAsJsonObject();
                if (mainObject.get("Panels") == null) {
                    return;
                }
                panelObject = mainObject.get("Panels").getAsJsonObject();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            panelObject = new JsonObject();
        }
    }

    @Override
    public void end(boolean loading) {
        if (panelObject == null) return;
        if (!loading) {
            try {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                OutputStreamWriter fileOutputStreamWriter = new OutputStreamWriter(new FileOutputStream(fileLocation + "ClickGui" + ".json"), StandardCharsets.UTF_8);
                JsonObject mainObject = new JsonObject();
                mainObject.add("Panels", panelObject);
                String jsonString = gson.toJson(new JsonParser().parse(mainObject.toString()));
                fileOutputStreamWriter.write(jsonString);
                fileOutputStreamWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        panelObject = null;
    }

    @Override
    public PanelConfig addPanel(String title) {
        if (panelObject == null) return null;
        JsonObject valueObject = new JsonObject();
        panelObject.add(title, valueObject);
        return new NextPanelConfig(valueObject);
    }

    @Override
    public PanelConfig getPanel(String title) {
        if (panelObject == null) return null;
        JsonElement configObject = panelObject.get(title);
        if (configObject != null && configObject.isJsonObject())
            return new NextPanelConfig(configObject.getAsJsonObject());
        return null;
    }


    private static class NextPanelConfig implements PanelConfig {

        private final JsonObject configObject;

        public NextPanelConfig(JsonObject configObject) {
            this.configObject = configObject;
        }

        @Override
        public void savePositon(Point position) {
            configObject.add("X", new JsonPrimitive(position.x));
            configObject.add("Y", new JsonPrimitive(position.y));
        }

        @Override
        public Point loadPosition() {
            Point point = new Point();
            JsonElement panelPosXObject = configObject.get("X");
            if (panelPosXObject != null && panelPosXObject.isJsonPrimitive()) {
                point.x = panelPosXObject.getAsInt();
            } else return null;
            JsonElement panelPosYObject = configObject.get("Y");
            if (panelPosYObject != null && panelPosYObject.isJsonPrimitive()) {
                point.y = panelPosYObject.getAsInt();
            } else return null;
            return point;
        }

        @Override
        public void saveState(boolean state) {
            configObject.add("State", new JsonPrimitive(state));
        }

        @Override
        public boolean loadState() {
            JsonElement panelOpenObject = configObject.get("State");
            if (panelOpenObject != null && panelOpenObject.isJsonPrimitive()) {
                return panelOpenObject.getAsBoolean();
            }
            return false;
        }
    }
}
