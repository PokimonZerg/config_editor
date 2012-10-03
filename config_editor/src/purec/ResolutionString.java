package purec;

import java.awt.DisplayMode;

/**
 * Represents string with one available resolution.
 */
class ResolutionString
{
    /**
     * Default constructor.
     * @param dm reference to AWT DisplayMode class
     */
    public ResolutionString(DisplayMode dm)
    {
        display_mode = dm;
    }
    
    /**
     * Make resolution string.
     * @return String with next format <width> x <height>
     */
    @Override
    public String toString()
    {
        return String.format("%d x %d", display_mode.getWidth(), display_mode.getHeight());
    }
    
    /**
     * Resolution width getter.
     * @return resolution width
     */
    public int getWidth()
    {
        return display_mode.getWidth();
    }
    
    /**
     * Resolution height getter.
     * @return resolution height
     */
    public int getHeight()
    {
        return display_mode.getHeight();
    }
    
    /**
     * Compares himself with another resolution.
     * @param x resolution width
     * @param y resolution height
     * @return true if resolutions are equals
     */
    public boolean compare(int x, int y)
    {
        return display_mode.getWidth() == x && display_mode.getHeight() == y;
    }
    
    private DisplayMode display_mode;
}
