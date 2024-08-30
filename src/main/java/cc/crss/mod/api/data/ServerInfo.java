package cc.crss.mod.api.data;

import cc.crss.mod.api.Model;

public class ServerInfo extends Model {
    private String version;
    private Integer online;
    private String[] worlds;

    public ServerInfo(String version, Integer online, String[] worlds) {
        this.version = version;
        this.online = online;
        this.worlds = worlds;
    }

    public String getVersion() {
        return version;
    }

    public Integer getOnline() {
        return online;
    }

    public String[] getWorlds() {
        return worlds;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setOnline(Integer online) {
        this.online = online;
    }

    public void setWorlds(String[] worlds) {
        this.worlds = worlds;
    }
}
