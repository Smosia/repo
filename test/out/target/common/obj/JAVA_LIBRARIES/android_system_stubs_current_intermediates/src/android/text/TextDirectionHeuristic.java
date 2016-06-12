package android.text;
public interface TextDirectionHeuristic
{
public abstract  boolean isRtl(char[] array, int start, int count);
public abstract  boolean isRtl(java.lang.CharSequence cs, int start, int count);
}
