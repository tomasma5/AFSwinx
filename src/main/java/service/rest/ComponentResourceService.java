package service.rest;

/**
 * Service for getting component resources
 *
 * @author Pavel Matyáš (matyapav@fel.cvut.cz).
 * @since 1.0.0
 */
public interface ComponentResourceService {

    public String getComponentModel();

    public String getComponentData();

    public String sendComponentData();

}
