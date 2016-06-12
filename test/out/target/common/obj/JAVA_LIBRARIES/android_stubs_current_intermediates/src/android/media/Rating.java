package android.media;
public final class Rating
  implements android.os.Parcelable
{
Rating() { throw new RuntimeException("Stub!"); }
public  java.lang.String toString() { throw new RuntimeException("Stub!"); }
public  int describeContents() { throw new RuntimeException("Stub!"); }
public  void writeToParcel(android.os.Parcel dest, int flags) { throw new RuntimeException("Stub!"); }
public static  android.media.Rating newUnratedRating(int ratingStyle) { throw new RuntimeException("Stub!"); }
public static  android.media.Rating newHeartRating(boolean hasHeart) { throw new RuntimeException("Stub!"); }
public static  android.media.Rating newThumbRating(boolean thumbIsUp) { throw new RuntimeException("Stub!"); }
public static  android.media.Rating newStarRating(int starRatingStyle, float starRating) { throw new RuntimeException("Stub!"); }
public static  android.media.Rating newPercentageRating(float percent) { throw new RuntimeException("Stub!"); }
public  boolean isRated() { throw new RuntimeException("Stub!"); }
public  int getRatingStyle() { throw new RuntimeException("Stub!"); }
public  boolean hasHeart() { throw new RuntimeException("Stub!"); }
public  boolean isThumbUp() { throw new RuntimeException("Stub!"); }
public  float getStarRating() { throw new RuntimeException("Stub!"); }
public  float getPercentRating() { throw new RuntimeException("Stub!"); }
public static final android.os.Parcelable.Creator<android.media.Rating> CREATOR;
public static final int RATING_3_STARS = 3;
public static final int RATING_4_STARS = 4;
public static final int RATING_5_STARS = 5;
public static final int RATING_HEART = 1;
public static final int RATING_NONE = 0;
public static final int RATING_PERCENTAGE = 6;
public static final int RATING_THUMB_UP_DOWN = 2;
static { CREATOR = null; }
}
