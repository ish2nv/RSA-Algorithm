# Overview
The RSA algorithm is used by modern devices to encrypt/decrypt messages. It uses asymmetric cryptography, meaning that it use two different keys: public and private key. The public key is known to everyone in the party, whereas the private key is only known to either the sender or the receiver of the message.

# Key steps

Their are four key algorithms that are needed to build model with RSA security:

* Key generator algorithm - this will generate the public and private keys for both users who want to interact securely
* RSA encryption algorithm - the message is encrypted by the sender using the public key
* RSA decryption algorithm - the message is decrypted by the receiver using the private key
* RSA modular exponentiation algorithm - this algorithm will be used by the two algorithms above, in order to encrypt or decrypt the message


