@Grab('log4j:log4j:1.2.17')

import org.apache.log4j.*
import groovy.util.logging.*
import javax.crypto.*;
import java.security.MessageDigest;
import javax.crypto.spec.SecretKeySpec
import java.security.Key;
import java.net.URLEncoder;
import java.util.Base64;
import javax.crypto.spec.IvParameterSpec
import groovy.util.logging.Log4j

log = Logger.getLogger(this.class.name)
log.level=Level.DEBUG

log.debug(" ~Begin~ "*10)


encoder = Base64.getMimeEncoder()
decoder = Base64.getMimeDecoder()

class Util{


/**
 * Usually, the key is not the 16 or 32  bytes long
 * of the possible block sizes: a technique is to calculate the sha1 of the pass
 * and get the first 16 bytes
 */
static Key keyFromStringAndSha(String lolkey){
    def sha1 = MessageDigest.getInstance("SHA-1");
    def byte[] key_bytes=lolkey.getBytes("UTF-8");
    def digested_key = sha1.digest(key_bytes);
    def pre_key = Arrays.copyOf(digested_key, 16); // use only first 128 bit
    return new SecretKeySpec(pre_key, "AES");
}

/**
 * Of course, having the correct size, it is just a metter of
 * creatign the key
 */

static Key keyFromString(String lolkey){
    def byte[] key_bytes=lolkey.getBytes("UTF-8");
    return new SecretKeySpec(key_bytes, "AES");
}

}
@Log4j
class Good{
    String serverKey;
    IvParameterSpec iv;
    String algorithm;
    byte[] clientKey;
    byte[] communicationKey;
    public Good(String serverKey,String initializationVector,String algorithm){
        log.level = Level.DEBUG
        log.debug("Setting $serverKey as server key")
        this.serverKey = serverKey;
        log.debug("setting $initializationVector as initialization Vector")
        this.iv = new IvParameterSpec(initializationVector.getBytes()); //!! here i am implicitly guaranteeing that the iv will be always of the correcty size (for cleanliness) !!
        log.debug("setting $algorithm as crypto algorithm")
        this.algorithm = algorithm        
    }

    public byte[] decrypt(byte[] crypText){
        return decrypt(this.serverKey,crypText)
    }
    public byte[] decrypt(String key, byte[] crypText){
        Cipher decypher = Cipher.getInstance(this.algorithm)
        decypher.init(Cipher.DECRYPT_MODE, Util.keyFromString(key),this.iv)
        byte[] clearText = decypher.doFinal(crypText)
        return clearText


    }

    public encrypt(byte[] clearText){
        return encrypt(this.serverKey,clearText)
    }

    public encrypt(byte[] key, byte[] clearText){
        Cipher cypher = Cipher.getInstance(this.algorithm)
        cypher.init(Cipher.ENCRYPT_MODE,new SecretKeySpec(key,"AES"),this.iv)
        byte[] crypText = cypher.doFinal(clearText)
        return crypText;

    }
    public encrypt(String key, byte[] clearText){
        Cipher cypher = Cipher.getInstance(this.algorithm)
        cypher.init(Cipher.ENCRYPT_MODE,Util.keyFromString(key),this.iv)
        byte[] crypText = cypher.doFinal(clearText)
        return crypText;
    }

    public void setCommunicationKey(String base64key){
        log.debug("Setting setCommunicationKey $base64key")
        def decoder = Base64.getMimeDecoder()
        this.communicationKey = decoder.decode(base64key)
        log.debug("Communication Key is now ${this.communicationKey}")
        this.clientKey = decrypt(this.communicationKey);
    }

    public byte[] getClientKey(){
        if(this.communicationKey==null)
            throw new Error("No communication key")
        this.clientKey = decrypt(this.communicationKey);
        return this.clientKey
    }

    public byte[] getEncryptedPassword(String password){
        return encrypt(getClientKey(),password.getBytes())
    }

}

x = new Good("53cr3t0p4ssw0rd1","MSID-Security\u0000\u0000\u0000","AES/CBC/PKCS5Padding")

clientKey="J9WfqhH13Itsl0FoWcHxUKHyjYSwEsdG50o+pPp/jt8="

x.setCommunicationKey(clientKey);
ck = x.getClientKey();
log.info("Client key is $ck (${encoder.encodeToString(ck)})")
ep = x.getEncryptedPassword("somePassword")
log.info("Encrypted password is ${ep} (${encoder.encodeToString(ep)})")
log.debug(" ~ End ~ "*10)
