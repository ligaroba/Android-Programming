package com.telosoft.ligaroba.contactsstrore;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import java.util.logging.Logger;

import javax.inject.Named;

/** An endpoint class we are exposing */
@Api(name = "navigationDrawerFragmentEndpoint", version = "v1", namespace = @ApiNamespace(ownerDomain = "contactsstrore.ligaroba.telosoft.com", ownerName = "contactsstrore.ligaroba.telosoft.com", packagePath=""))
public class NavigationDrawerFragmentEndpoint {

    // Make sure to add this endpoint to your web.xml file if this is a web application.

    private static final Logger LOG = Logger.getLogger(NavigationDrawerFragmentEndpoint.class.getName());

    /**
     * This method gets the <code>NavigationDrawerFragment</code> object associated with the specified <code>id</code>.
     * @param id The id of the object to be returned.
     * @return The <code>NavigationDrawerFragment</code> associated with <code>id</code>.
     */
    @ApiMethod(name = "getNavigationDrawerFragment")
    public NavigationDrawerFragment getNavigationDrawerFragment(@Named("id") Long id) {
        // Implement this function

        LOG.info("Calling getNavigationDrawerFragment method");
        return null;
    }

    /**
     * This inserts a new <code>NavigationDrawerFragment</code> object.
     * @param navigationDrawerFragment The object to be added.
     * @return The object to be added.
     */
    @ApiMethod(name = "insertNavigationDrawerFragment")
    public NavigationDrawerFragment insertNavigationDrawerFragment(NavigationDrawerFragment navigationDrawerFragment) {
        // Implement this function

        LOG.info("Calling insertNavigationDrawerFragment method");
        return navigationDrawerFragment;
    }
}