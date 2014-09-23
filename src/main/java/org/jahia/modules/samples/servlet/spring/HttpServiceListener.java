package org.jahia.modules.samples.servlet.spring;

import org.eclipse.gemini.blueprint.context.BundleContextAware;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.http.HttpService;
import org.osgi.service.http.NamespaceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;

/**
 * Service listener to make sure that we don't block startup
 */
public class HttpServiceListener implements BundleContextAware {

    public static final Logger logger = LoggerFactory.getLogger(HttpServiceListener.class);

    SimpleServlet simpleServlet;
    BundleContext bundleContext;

    public void setBundleContext(BundleContext bundleContext) {
        this.bundleContext = bundleContext;
    }

    public void setSimpleServlet(SimpleServlet simpleServlet) {
        this.simpleServlet = simpleServlet;
    }

    public void onBind(ServiceReference serviceReference) {
        // hack : we don't use the passed service reference because it is a proxy class that we cannot use to retrieve the
        // real service object, so we simply look it up again
        ServiceReference realServiceReference = bundleContext.getServiceReference(HttpService.class.getName());
        HttpService httpService = (HttpService) bundleContext.getService(realServiceReference);
        try {
            httpService.registerServlet("/org.jahia.modules.samples.servlet.spring", simpleServlet, null, null);
            logger.info("Successfully registered custom servlet at /modules/org.jahia.modules.samples.servlet.spring");
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (NamespaceException e) {
            e.printStackTrace();
        }

    }

    public void onUnbind(ServiceReference serviceReference) {
        // hack : we don't use the passed service reference because it is a proxy class that we cannot use to retrieve the
        // real service object, so we simply look it up again
        // here we have a lot of null checks because in the case of a framework shutdown the service can disappear
        // at any time.
        if (serviceReference == null) {
            return;
        }
        ServiceReference realServiceReference = bundleContext.getServiceReference(HttpService.class.getName());
        if (realServiceReference == null) {
            return;
        }
        HttpService httpService = (HttpService) bundleContext.getService(realServiceReference);
        if (httpService == null) {
            return;
        }
        httpService.unregister("/org.jahia.modules.samples.servlet.spring");
        logger.info("Successfully unregistered custom servlet from /modules/org.jahia.modules.samples.servlet.spring");
    }
}
