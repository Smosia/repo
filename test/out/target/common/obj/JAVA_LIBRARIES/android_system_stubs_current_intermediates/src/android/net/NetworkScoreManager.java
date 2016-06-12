package android.net;
public class NetworkScoreManager
{
NetworkScoreManager() { throw new RuntimeException("Stub!"); }
public  java.lang.String getActiveScorerPackage() { throw new RuntimeException("Stub!"); }
public  boolean updateScores(android.net.ScoredNetwork[] networks) throws java.lang.SecurityException { throw new RuntimeException("Stub!"); }
public  boolean clearScores() throws java.lang.SecurityException { throw new RuntimeException("Stub!"); }
public  boolean setActiveScorer(java.lang.String packageName) throws java.lang.SecurityException { throw new RuntimeException("Stub!"); }
public  void disableScoring() throws java.lang.SecurityException { throw new RuntimeException("Stub!"); }
public static final java.lang.String ACTION_CHANGE_ACTIVE = "android.net.scoring.CHANGE_ACTIVE";
public static final java.lang.String ACTION_CUSTOM_ENABLE = "android.net.scoring.CUSTOM_ENABLE";
public static final java.lang.String ACTION_SCORER_CHANGED = "android.net.scoring.SCORER_CHANGED";
public static final java.lang.String ACTION_SCORE_NETWORKS = "android.net.scoring.SCORE_NETWORKS";
public static final java.lang.String EXTRA_NETWORKS_TO_SCORE = "networksToScore";
public static final java.lang.String EXTRA_NEW_SCORER = "newScorer";
public static final java.lang.String EXTRA_PACKAGE_NAME = "packageName";
}
