package purec;

/**
 * Represents settings for specific user.
 */
public class Profile
{
    /*
     * Default constructor.
     * Also initialize profile to default values.
     */
    public Profile()
    {
        setToDefault();
    }
    
    /**
     * Drop all settings to default.
     */
    public void setToDefault()
    {
        user        = "new user";
        resx        = 1024;
        resy        = 768;
        sizex       = 800;
        sizey       = 600;
        soundVolume = 50;
        musicVolume = 50;
        sound       = true;
        music       = true;
        fullscreen  = false;
        vsync       = false;
    }
    
    /**
     * Show user name.
     * @return user name string
     */
    @Override
    public String toString()
    {
        return user;
    }
    
    public String getUser()
    {
        return user;
    }
    
    public void setUser(String user)
    {
        this.user = user;
    }

    public int getResx()
    {
        return resx;
    }

    public void setResx(int res_x)
    {
        this.resx  = res_x;
    }

    public int getResy()
    {
        return resy;
    }

    public void setResy(int res_y)
    {
        this.resy = res_y;
    }

    public int getSoundVolume()
    {
        return soundVolume;
    }

    public void setSoundVolume(int sound_volume)
    {
        this.soundVolume = sound_volume;
    }

    public int getMusicVolume()
    {
        return musicVolume;
    }

    public void setMusicVolume(int music_volume)
    {
        this.musicVolume = music_volume;
    }

    public boolean isSound()
    {
        return sound;
    }

    public void setSound(boolean sound)
    {
        this.sound = sound;
    }

    public boolean isMusic()
    {
        return music;
    }

    public void setMusic(boolean music)
    {
        this.music = music;
    }

    public boolean isFullscreen()
    {
        return fullscreen;
    }

    public void setFullscreen(boolean fullscreen)
    {
        this.fullscreen = fullscreen;
    }

    public boolean isVsync()
    {
        return vsync;
    }

    public void setVsync(boolean vsync)
    {
        this.vsync = vsync;
    }
    
    public int getSizex()
    {
        return sizex;
    }

    public void setSizex(int size_x)
    {

        this.sizex = size_x;
    }

    public int getSizey()
    {
        return sizey;
    }

    public void setSizey(int size_y)
    {
        this.sizey = size_y;
    }
    
    public long getId()
    {
        return id;
    }
    
    public void setId(long id)
    {
        this.id = id;
    }
    
    private String  user;
    private int     resx;
    private int     resy;
    private int     sizex;
    private int     sizey;
    private int     soundVolume;
    private int     musicVolume;
    private boolean sound;
    private boolean music;
    private boolean fullscreen;
    private boolean vsync;
    private long    id;
}
