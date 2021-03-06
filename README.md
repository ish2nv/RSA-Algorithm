# Introduction
The RSA algorithm is used by modern devices to encrypt/decrypt messages. It uses asymmetric cryptography, meaning that it use two different keys: public and private key. The public key is known to everyone in the party, whereas the private key is only known to either the sender or the receiver of the message. My implementation includes this along with a scenario, where Alice sends a message to Bob and is intercepted by a middle man, CHARLIE. I also investigate a problem with the algorithm. Further detail of this can be seen below

# Key steps

Their are four key algorithms that are needed to build model with RSA security:

* Key generator algorithm - this will generate 256 bit public and private keys for both users who want to interact securely. It is important to generate 256 bit or higher keys, otherwise interceptors/hackers will find it easy to crack the message.
* RSA encryption algorithm - the message is encrypted by the sender using the public key
* RSA decryption algorithm - the message is decrypted by the receiver using the private key
* RSA modular exponentiation algorithm - this algorithm will be used by the two algorithms above, in order to encrypt or decrypt the message

# Implementation overview

* Interpreting user interface
    - Charlie intercepts and receives cipher from Alice who intends to send to Bob. Let’s call this cipher C
    - Charlie swaps C with C' = C * r^e mod N and sends this to Bob to decrypt. r is a random number, e and n is the public key of Bob 
    - After Bob has decrypted C', he encrypts and sends the tampered message to Alice as he does not understand it and wants it to be sent         again
    - Charlie intercepts the decrypted C' and simply divides it by r in the final stage. This gives Charlie the message without having to         know Bob's private key. 
    - Charlie has successfully managed to get the message through this cipher attack example
    
    
    <p align="center">
  <img src="https://github.com/ish2nv/RSA-Algorithm/blob/master/rsainterceptimg.jpg" alt="RSA intercept image">
</p>
    

* Interpreting problematic state
    - suppose we get the user to choose two small prime numbers p and q. from the result in the program, you will see that the encrypted           text and decrypted text are both wrong. This is because n is too small and needs to be much bigger in order for encryption and               decryption to work properly
    -	Using small prime numbers is also a security concern as interceptors can easily factor n to get the receivers private key.


# Activity flow of the implementation
The diagram below explains the programs flow of activity in a simple manner.
    <p align="center">
  <img src="https://github.com/ish2nv/RSA-Algorithm/blob/master/rsaimg_med.png" alt="Activity diagram">
</p>
    



