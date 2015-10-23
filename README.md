# good-ugly-pad
## Short summary
The Good the Ugly and the Pad enter in a bar...
Sometimes knowing the trade is not enogh, sometimes you nee to pad

# Article

In crypto, when your message is multiple of the crypto algorithm block size, you need to pad. But in Java(the Good) we have an problem: we are not used to do that; in fact we are used to have the platform take care of the nitty-gritty details and we just enjoy the ride. Of course the problem is that we forget the padding altogheter, we (I) dont even remeber that is there, and when confronted with non jvm languages, sometimes this goody backfires. It happend to me, when porting a protoco from a Java client to a Python client, and i am putting the experience here, so maybe i will not forget it again and you don't have to loose precious game-time in theese things.
