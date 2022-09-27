package org.gs;


import java.security.cert.X509Certificate;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.quarkus.security.credential.CertificateCredential;
import io.quarkus.security.identity.SecurityIdentity;

@Path("/server")
public class ServerResource {

    @Inject
    SecurityIdentity identity;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        X509Certificate cert;

        cert = extractCertificate();
        return "Hello " + cert.getIssuerX500Principal().getName() + " from server side\n";

    }

    private X509Certificate extractCertificate() {
        CertificateCredential credential = identity.getCredential(CertificateCredential.class);
        X509Certificate certificate = credential.getCertificate();
        return certificate;
    }

}