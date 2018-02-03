package deadbycube.audio.music;

public enum MusicRegistry {

    SURVIVOR_NORMAL("music.survivor.normal", 1200L),
    KILLER_NORMAL("music.killer.normal", 1200L),
    LOBBY_NORMAL("music.lobby.normal", 1200L),
    LOBBY_KILLER("music.lobby.killer", 1200L),
    LOBBY_SURVIVOR("music.lobby.survivor", 1200L);

    private final String key;
    private final long duration;

    MusicRegistry(String key, long duration) {
        this.key = key;
        this.duration = duration;
    }

    public String getKey() {
        return key;
    }

    public long getDuration() {
        return duration;
    }
}
