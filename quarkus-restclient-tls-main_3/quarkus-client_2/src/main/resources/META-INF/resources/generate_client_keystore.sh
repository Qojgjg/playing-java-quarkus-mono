keytool -genkeypair \
        -storepass client_password \
        -keyalg RSA \
        -keysize 2048 \
        -dname "CN=client2" \
        -alias client2 \
        -ext "SAN:c=DNS:localhost,IP:127.0.0.1" \
        -keystore client2.keystore \