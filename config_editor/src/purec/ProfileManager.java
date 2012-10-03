package purec;

import java.util.ArrayList;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

/**
 * Manage all profiles.
 * This class can save, delete or create profiles.
 * Use get/setCurrentProfiles methods to manipulate profiles.
 */
class ProfileManager
{
    /**
     * Default constructor.
     * Load profiles data from database.
     */
    public ProfileManager()
    {
        sessionFactory = createSessionFactory();
        profiles       = loadData();
        currentProfile = profiles.size() > 0 ? profiles.get(0) : createNewProfile();
    }
    
    /**
     * Return current profile for manipulating it.
     * @return current {@ling purec.Profile} class
     */
    public Profile getCurrentProfile()
    {
        return currentProfile;
    }
    
    /**
     * Set current profile for future operations.
     * @param p one of valid profiles
     */
    public void setCurrentProfile(Profile p)
    {
        currentProfile = p;
    }
    
    /**
     * Delete profile from database and manager.
     * Also create new one if no profile exists
     * @param p profile for destroy
     * @return true if all right
     */
    public boolean deleteProfile(Profile p)
    {
        try
        {
            Session s = sessionFactory.getCurrentSession();
            
            s.beginTransaction();
            s.delete(p);
            s.getTransaction().commit();
            
            profiles.remove(p);
            currentProfile = profiles.size() > 0 ? profiles.get(0) : createNewProfile();
        }
        catch(Exception e)
        {
            System.err.print(e);
            return false;
        }
        
        return true;
    }
    
    /**
     * Save profile to database.
     * @param p profile for saving
     * @return true if all right
     */
    public boolean saveProfile(Profile p)
    {
        try
        {
            Session s = sessionFactory.getCurrentSession();
            s.beginTransaction();
            s.saveOrUpdate(p);
            s.getTransaction().commit();
        }
        catch(Exception e)
        {
            return false;
        }
        
        return true;
    }
    
    /**
     * Return all profiles. Be care with it.
     * @return ArrayList with all profiles
     */
    public ArrayList<Profile> getProfiles()
    {
        return profiles;
    }
    
    /**
     * Create new profile, and add it into manager.
     * This function does not save profile in database or change current profile.
     * @return new {@ling purec.Profile} class instance
     */
    public Profile createNewProfile()
    {
        Profile p = new Profile();
        
        profiles.add(p);
        
        return p;
    }
    
    /**
     * Load profiles data from databse.
     * @return ArrayList with all loaded profiles
     */
    private ArrayList<Profile> loadData()
    {
        ArrayList<Profile> p = new ArrayList<>();
        
        try
        {
            Session s = sessionFactory.getCurrentSession();
            
            s.beginTransaction();
            p.addAll(s.createQuery("from Profile").list());
            s.getTransaction().commit();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
        return p;
    }
    
    /**
     * Create new session factory. Call this only once!.
     * @return new {@link org.hibernate.SessionFactory} object
     */
    private SessionFactory createSessionFactory()
    {
        try
        {
            Configuration configuration = new Configuration().configure();
            serviceRegistry             = new ServiceRegistryBuilder().applySettings(
                                              configuration.getProperties()).buildServiceRegistry();
            return configuration.buildSessionFactory(serviceRegistry);
        }
        catch(HibernateException e)
        {
            e.printStackTrace();
            return null;
        }
    }
    
    private Profile            currentProfile;
    private ArrayList<Profile> profiles;
    private SessionFactory     sessionFactory;
    private ServiceRegistry    serviceRegistry;
}
