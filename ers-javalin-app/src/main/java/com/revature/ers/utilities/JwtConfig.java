package com.revature.ers.utilities;

// this is copied from Trainer-P1 with only minor change to db.properties file location
// I do not see a need to do something else for this for the project
// May look into alternate ways of configuring and generating JWT to learn more
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.FileReader;
import java.io.IOException;
import java.security.Key;
import java.util.Properties;

public class JwtConfig {
    private static final int validForTime = 60 * 60 * 1000;
    private static final SignatureAlgorithm sigAlg = SignatureAlgorithm.HS256;
     // 60 milliseconds times 60 minutes * 1000 = 1 hour
    private final Key signingKey;

    public JwtConfig() {
        Properties properties = new Properties();
        try {
            properties.load(new FileReader("resources/db.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        byte[] saltyBytes = DatatypeConverter.parseBase64Binary(properties.getProperty("salt"));
        signingKey = new SecretKeySpec(saltyBytes, sigAlg.getJcaName());
    }

    public SignatureAlgorithm getSigAlg() {
        return sigAlg;
    }

    public int getValidForTime() {
        return validForTime;
    }

    public Key getSigningKey() {
        return signingKey;
    }
}
