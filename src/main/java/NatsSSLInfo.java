public class NatsSSLInfo{
    private String keyManagerType = "SunX509";
    private String keyStoreType = "JKS";
    private String protocol = "TLSv1.2";

    private String keyStoreLocation;
    private String keyStorePassPhrase;

    private String trustStoreLocation;
    private String trustStorePassPhrase;

    private String natsUsername;
    private String natsPassword;

    public String getKeyManagerType() {
        return keyManagerType;
    }
    public void setKeyManagerType(String keyManagerType) {
        this.keyManagerType = keyManagerType;
    }
    public String getKeyStoreType() {
        return keyStoreType;
    }
    public void setKeyStoreType(String keyStoreType) {
        this.keyStoreType = keyStoreType;
    }
    public String getProtocol() {
        return protocol;
    }
    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }
    public String getKeyStoreLocation() {
        return keyStoreLocation;
    }
    public void setKeyStoreLocation(String keyStoreLocation) {
        this.keyStoreLocation = keyStoreLocation;
    }
    public String getKeyStorePassPhrase() {
        return keyStorePassPhrase;
    }
    public void setKeyStorePassPhrase(String keyStorePassPhrase) {
        this.keyStorePassPhrase = keyStorePassPhrase;
    }
    public String getTrustStoreLocation() {
        return trustStoreLocation;
    }
    public void setTrustStoreLocation(String trustStoreLocation) {
        this.trustStoreLocation = trustStoreLocation;
    }
    public String getTrustStorePassPhrase() {
        return trustStorePassPhrase;
    }
    public void setTrustStorePassPhrase(String trustStorePassPhrase) {
        this.trustStorePassPhrase = trustStorePassPhrase;
    }
    public String getNatsUsername() {
        return natsUsername;
    }
    public void setNatsUsername(String natsUsername) {
        this.natsUsername = natsUsername;
    }
    public String getNatsPassword() {
        return natsPassword;
    }
    public void setNatsPassword(String natsPassword) {
        this.natsPassword = natsPassword;
    }
}
