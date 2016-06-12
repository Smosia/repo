package android.widget;
public interface SectionIndexer
{
public abstract  java.lang.Object[] getSections();
public abstract  int getPositionForSection(int sectionIndex);
public abstract  int getSectionForPosition(int position);
}
