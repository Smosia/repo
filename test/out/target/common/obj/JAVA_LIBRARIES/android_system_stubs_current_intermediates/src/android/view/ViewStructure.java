package android.view;
public abstract class ViewStructure
{
public  ViewStructure() { throw new RuntimeException("Stub!"); }
public abstract  void setId(int id, java.lang.String packageName, java.lang.String typeName, java.lang.String entryName);
public abstract  void setDimens(int left, int top, int scrollX, int scrollY, int width, int height);
public abstract  void setTransformation(android.graphics.Matrix matrix);
public abstract  void setElevation(float elevation);
public abstract  void setAlpha(float alpha);
public abstract  void setVisibility(int visibility);
public abstract  void setEnabled(boolean state);
public abstract  void setClickable(boolean state);
public abstract  void setLongClickable(boolean state);
public abstract  void setContextClickable(boolean state);
public abstract  void setFocusable(boolean state);
public abstract  void setFocused(boolean state);
public abstract  void setAccessibilityFocused(boolean state);
public abstract  void setCheckable(boolean state);
public abstract  void setChecked(boolean state);
public abstract  void setSelected(boolean state);
public abstract  void setActivated(boolean state);
public abstract  void setClassName(java.lang.String className);
public abstract  void setContentDescription(java.lang.CharSequence contentDescription);
public abstract  void setText(java.lang.CharSequence text);
public abstract  void setText(java.lang.CharSequence text, int selectionStart, int selectionEnd);
public abstract  void setTextStyle(float size, int fgColor, int bgColor, int style);
public abstract  void setTextLines(int[] charOffsets, int[] baselines);
public abstract  void setHint(java.lang.CharSequence hint);
public abstract  java.lang.CharSequence getText();
public abstract  int getTextSelectionStart();
public abstract  int getTextSelectionEnd();
public abstract  java.lang.CharSequence getHint();
public abstract  android.os.Bundle getExtras();
public abstract  boolean hasExtras();
public abstract  void setChildCount(int num);
public abstract  int addChildCount(int num);
public abstract  int getChildCount();
public abstract  android.view.ViewStructure newChild(int index);
public abstract  android.view.ViewStructure asyncNewChild(int index);
public abstract  void asyncCommit();
}
