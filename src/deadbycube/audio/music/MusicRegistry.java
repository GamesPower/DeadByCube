package deadbycube.audio.music;

public enum MusicRegistry {

    SURVIVOR_NORMAL("music.survivor.normal", 1200L),
    KILLER_NORMAL("music.killer.normal", 1200L),
    LOBBY_WINTER("music.lobby.winter", 1200L);

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
