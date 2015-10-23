# good-ugly-pad
## Short summary
The Good the Ugly and the Pad enter in a bar...
Sometimes knowing the trade is not enogh, sometimes you nee to pad

# Article

In crypto, when your message is not multiple of the crypto algorithm block size, you need to pad. But in Java(the Good) we have an problem: we are not used to do that; in fact we are used to have the platform take care of the nitty-gritty details and we just enjoy the ride. Of course the problem is that we forget the padding altogheter, we (I) dont even remeber that is there, and when confronted with non jvm languages, sometimes this goody backfires. It happend to me, when porting a protoco from a Java client to a Python (the Ugly) client, and i am putting the experience here, so maybe i will not forget it again and you don't have to loose precious game-time in theese things.

#The programs

Let's say that the interaction of the protocol is an instance the so called "double lock briefcase".

Let's see how it would have been done, in Groovy and in Python

Notice the lambdas (the Pad) that do the padding and the unpadding of the payload! It is the usual (https://en.wikipedia.org/wiki/Padding_%28cryptography%29#PKCS7) [PKCS(5|7)] , but one has to remember to do it or the communication will break, especially with small payloads.

##Python example

##Groovy Example
