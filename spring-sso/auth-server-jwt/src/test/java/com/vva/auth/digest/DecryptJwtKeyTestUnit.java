package com.vva.auth.digest;

import org.bouncycastle.util.encoders.Base64;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;

import java.io.IOException;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.interfaces.RSAPublicKey;


public class DecryptJwtKeyTestUnit {

    @Test
    public void getJwtKey() throws CertificateException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, IOException {

        String jwtToken = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsidG9kby1zZXJ2aWNlcyJdLCJzY29wZSI6WyJyZWFkIiwid3JpdGUiXSwib3JnYW5pemF0aW9uIjoicmVhZENsaWVudElkU3FaRSIsImV4cCI6MTUxODEwMjU1MywiYXV0aG9yaXRpZXMiOlsicm9sZV9hZG1pbiJdLCJqdGkiOiJiYTgxYjcwMC04ODg0LTQwZDMtODJjNi0xM2YxZWE3OTBlYjQiLCJjbGllbnRfaWQiOiJyZWFkQ2xpZW50SWQifQ.MITiwQcxmN-6JW5zr25QeQ2OFmZHcCPmtvu0tSBurMp0UV4BkWGjvkJfx3-t8tbojeIHK7uC9yp8U0_wTYlYJ8roqtWTvIib4cL-pMuOSgxAL49hBmyo6WZe6rJ6C2sannaK9a8TaMreaRIOPuXDXZw2pfXJEQtmNyK1PGKGDN4XdTiUeNc9XAYN_dplbNpZ9JKTVZOVbz-5tdI8--bxi2mC3utZ3DJXtOQYYID28OQqWvUvN2eNO3wR6r-H5ojOhy1YKAwywVQ-_N2Ra3emPYXdoOybycphE66cblhxEAvMBFlE-C8gUCsPNz7m-H2_FLXVUC9mZas3gMwBwWCCJA";

        String[] split_string = jwtToken.split("\\.");
        String base64EncodedHeader = split_string[0];
        String base64EncodedBody = split_string[1];
        String base64EncodedSignature = split_string[2];


        System.out.println("~~~~~~~~~ JWT Header ~~~~~~~");
        String header = new String(Base64.decode(base64EncodedHeader));
        System.out.println("JWT Header : " + header);

        String body = new String(Base64.decode(base64EncodedBody));
        System.out.println("JWT body : " + body);
    }

    @Test
    public void getJwtKeyValidation() throws CertificateException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, IOException, InvalidKeyException, SignatureException {
        String jwtToken = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsidG9kby1zZXJ2aWNlcyJdLCJzY29wZSI6WyJyZWFkIiwid3JpdGUiXSwib3JnYW5pemF0aW9uIjoicmVhZENsaWVudElkc1NJYyIsImV4cCI6MTUxODEwNjM3NywiYXV0aG9yaXRpZXMiOlsicm9sZV9hZG1pbiJdLCJqdGkiOiIzM2RjOWZkOC1lMzhmLTQ0M2YtOTA4OC00ZmJmYTE2Mzg5YzMiLCJjbGllbnRfaWQiOiJyZWFkQ2xpZW50SWQifQ.A1h0zJkjf9E4eiKmYxSk1ZHrDPDrsgu3EU5Pq0iwXUOQ9oWiz7bg3VC40vjpk932BaVSC0D8t5QXjhZsxrBKBfu9_6858NZXNd37gQZjdSMWDFoVwfeYnZw_jjnLrPgmtwBoQk1gRrx3SAiqfo8N2Iadkux75uV3apKdIwq79JVhu2k8NhOF6YzR7kOLm7u3y9qsqEbapQvPjZxY2CmKcfBxIgk2V1vGi2JDLWFW5-f8r50rlYE7UJH93iOi81LdTn6HCUmH6LhrejSIOzDgBxLdrbkx0W0NFO7Lug1POk79Q9E3vhrDk2-gfOTobEs3OUlbbXYLdbsMVjjCJvbC_Q";

        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        Certificate trust = cf.generateCertificate(new ClassPathResource("test_jwt.cer").getInputStream());

        RsaVerifier verifier = new RsaVerifier((RSAPublicKey) trust.getPublicKey());
        Jwt jwt = JwtHelper.decodeAndVerify(jwtToken, verifier);


        System.out.println("~~~~~~~~~ JWT Header ~~~~~~~");
        System.out.println(jwt.getClaims());
        System.out.println(jwt.getEncoded());
    }


//    @Test
//    public void getJwtKeyValidation2() throws CertificateException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, IOException, InvalidKeyException, SignatureException {
//
//        final KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("jwt-keys.jks"), "mypwd123".toCharArray());
//        KeyPair keyPair = keyStoreKeyFactory.getKeyPair("test-jwt", "qwerty".toCharArray());
//
//        RsaSigner signer = new RsaSigner((RSAPrivateKey) keyPair.getPrivate());
//
//        Jwt jwt1 = JwtHelper.encode("{test: hello}", signer);
//
//
//        String jwtToken = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsidG9kby1zZXJ2aWNlcyJdLCJzY29wZSI6WyJyZWFkIiwid3JpdGUiXSwib3JnYW5pemF0aW9uIjoicmVhZENsaWVudElkc1NJYyIsImV4cCI6MTUxODEwNjM3NywiYXV0aG9yaXRpZXMiOlsicm9sZV9hZG1pbiJdLCJqdGkiOiIzM2RjOWZkOC1lMzhmLTQ0M2YtOTA4OC00ZmJmYTE2Mzg5YzMiLCJjbGllbnRfaWQiOiJyZWFkQ2xpZW50SWQifQ.A1h0zJkjf9E4eiKmYxSk1ZHrDPDrsgu3EU5Pq0iwXUOQ9oWiz7bg3VC40vjpk932BaVSC0D8t5QXjhZsxrBKBfu9_6858NZXNd37gQZjdSMWDFoVwfeYnZw_jjnLrPgmtwBoQk1gRrx3SAiqfo8N2Iadkux75uV3apKdIwq79JVhu2k8NhOF6YzR7kOLm7u3y9qsqEbapQvPjZxY2CmKcfBxIgk2V1vGi2JDLWFW5-f8r50rlYE7UJH93iOi81LdTn6HCUmH6LhrejSIOzDgBxLdrbkx0W0NFO7Lug1POk79Q9E3vhrDk2-gfOTobEs3OUlbbXYLdbsMVjjCJvbC_Q";
//
//        jwtToken = jwt1.getEncoded();
//
//        CertificateFactory cf = CertificateFactory.getInstance("X.509");
//        Certificate trust = cf.generateCertificate(new ClassPathResource("test_jwt.cer").getInputStream());
//
//        RsaVerifier verifier = new RsaVerifier((RSAPublicKey) trust.getPublicKey());
//        Jwt jwt = JwtHelper.decodeAndVerify(jwtToken, verifier);
//
//
//        System.out.println("~~~~~~~~~ JWT Header ~~~~~~~");
//        System.out.println(jwt.getClaims());
//        System.out.println(jwt.getEncoded());
//
//
//
//
//
//        String[] split_string = jwtToken.split("\\.");
//        String base64EncodedHeader = split_string[0];
//        String base64EncodedBody = split_string[1];
//        String base64Sign = split_string[2];
//
//        String content = base64EncodedHeader + "." + base64EncodedBody;
//
//
//        Signature signature = Signature.getInstance("SHA256withRSA");
//        signature.initVerify(trust.getPublicKey());
//        signature.update(content.getBytes());
//
//        System.out.println(signature.verify(base64Sign.getBytes()));
//
//
////        signature.initVerify(this.key);
////        signature.update(content);
////        if (!signature.verify(sig)) {
////            throw new InvalidSignatureException("RSA Signature did not match content");
////        }
//
//    }
}
