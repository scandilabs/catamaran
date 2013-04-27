package com.scandilabs.catamaran.tomcat.valve.urlrewrite;

import java.io.IOException;
import java.net.URI;

import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;

/**
 * This implementation will rewrite the host component of any URL that doesn't match the configured host
 * @author mkvalsvik
 *
 */
public class SubdomainStrippingRewriteUrlValve extends RewriteUrlValve {
    
    protected boolean processHost(URI uri, Request request, Response response) throws IOException {
        
        String host = uri.getHost().toString();
        
        if (URIUtils.hasSubDomain(uri.getHost())) {
            URI redirectURI = URIUtils.stripSubdomain(uri);                
            response.sendRedirect(redirectURI.toString());
            response.finishResponse();
            return true;                
        } 
        
        // Signal to caller that no action was taken
        return false;
    }

}
