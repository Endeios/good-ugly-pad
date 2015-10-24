# good-ugly-pad
## Short summary
The Good the Ugly and the Pad enter in a bar...
Sometimes knowing the trade is not enough, sometimes you need to pad

# Article

In crypto, when your message size is not multiple of the crypto algorithm block size, you need to pad. But we in Java(the Good) have an problem: we are not used to do that; in fact we are used to have the platform take care of the nitty-gritty details and we just enjoy the ride. Of course the problem is that we forget the padding altogheter, we (I) don't even remeber that is there, and when confronted with non jvm languages, sometimes this goodie backfires. It happend to me, when porting a protocol from a Java client to a Python (the Ugly) client, and i am putting the experience here, so maybe i will not forget it again and you don't have to loose precious game-time in these things.

#The programs

Let's say that the interaction of the protocol is an instance the so called "double lock briefcase".

Let's see how it would have been done, in Groovy and in Python

Notice the lambdas (the Pad) that do the padding and the unpadding of the payload! It is the usual (https://en.wikipedia.org/wiki/Padding_%28cryptography%29#PKCS7) [PKCS(5|7)] , but one has to remember to do it or the communication will break, especially with small payloads.

##Python example

The python example is made from the class ugly.Ugly: this class is first initialized with the shared password and the
initialization vector. Then is simulated the arrival of an externa exchanged key: this key is assumed to be randomic and encrypted with the shared key. The key is then decryped, and the corresponding (random) client key is obtained.
In the next step the client key is used to encrypt a password used for login.

##Groovy Example

The groovy example does the same as python, but notice that no padding is explicitly added or evicted from the payload: the crypto module does everithing on its own (so we do not make errors). As before the Good class is instanced with a password , an initialization vector and a crypto algorithm. The encrypted key exchange is simulated and the client key is obtained. The client key is then used to encrypt a password for login.
