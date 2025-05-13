#!/bin/zsh

# Define the JWT directory path
JWT_DIR="main-app/src/main/resources/jwt"

# Create the jwt directory if it doesn't exist
mkdir -p "$JWT_DIR"

# Generate RSA private key
openssl genrsa -out "$JWT_DIR/rsaPrivateKey.pem" 2048

# Generate public key from the private key
openssl rsa -pubout -in "$JWT_DIR/rsaPrivateKey.pem" -out "$JWT_DIR/publicKey.pem"

# Convert private key to PKCS#8 format (for some libraries)
openssl pkcs8 -topk8 -nocrypt -inform pem \
  -in "$JWT_DIR/rsaPrivateKey.pem" -outform pem -out "$JWT_DIR/privateKey.pem"

# Set permissions
chmod 600 "$JWT_DIR/rsaPrivateKey.pem"
chmod 600 "$JWT_DIR/privateKey.pem"
