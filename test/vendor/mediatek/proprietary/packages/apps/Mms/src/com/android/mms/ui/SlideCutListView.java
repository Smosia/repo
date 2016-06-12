package com.android.mms.ui;

import java.util.ArrayList;
import java.util.List;

import com.android.mms.R;
import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.graphics.drawable.Drawable;
import android.graphics.Color;
import android.view.inputmethod.InputMethodManager;
import com.android.mms.data.ContactList;
import com.android.mms.data.Conversation;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.app.Activity;
import android.text.TextUtils;
import com.android.mms.ui.ConversationList.ThreadListQueryHandler;

/**
 * add by shu.chen 20160324
 */
public class SlideCutListView extends ListView {
	private final static String TAG = "SlideCutListView";
	
	private List<View> rightItems = new ArrayList<View>();
	private List<View.OnClickListener> rightListeners = new ArrayList<View.OnClickListener>();
    private Context mContext;
	/**
	 * current slided ListView item position
	 */
	private int slidePosition;
	
	/**
	 * opened position
	 */
	private int openedPosition;

    private boolean isInLongClick;

    public void setIsInLongClick(boolean InLongClick){
        this.isInLongClick = InLongClick;
    }

    public boolean getIsInLongClick(){
        return this.isInLongClick;
    }

	/**
	 * action down y point
	 */
	private int downY;
	/**
	 * action down x point
	 */
	private int downX;
	/**
	 * original action down point x
	 */
	private int startX;
	/**
	 * limit x point when slide left max
	 */
	private int limitX;
	/**
	 * isLimited
	 */
	private boolean isLimited = false;
    /**
     * pre X point
     */
	private int preX;
	/**
	 * listWidth
	 */
	private int listWidth;
	
	private long animationTime;
	
	/**
	 * ListView slide item
	 */
	//private View itemView;
    private ConversationListItem itemView;
    private Conversation mConversation = null;
	
    /**
	 * ListView left item
	 */
	private View itemMainView;
	/**
	 * ListView right item
	 */
	private View rightView;
	
	private static final int SNAP_VELOCITY = 600;
	/**
	 * velocityTracker
	 */
	private VelocityTracker velocityTracker;
	/**
	 * is in Slide
	 */
    public boolean isSlide = false;
	//private boolean isSlide = false;
	/**
	 * is in isFling(start animation)
	 */
	public boolean isFling = false;
	/**
	 * right view opened statu
	 */
	public boolean opened = false;
	
	private int rightItemWidth;
	private int deltaX = 0;
	
	/**
	 * min slide distance
	 */
	private int mTouchSlop;

	/**
	 * slide  Direction
	 */
	private SlideDirection slideDirection;

    /**
	 * slide  Direction, three status
	 */
	public enum SlideDirection {
		NONE, RIGHT, LEFT;
	}


	public SlideCutListView(Context context) {
		this(context, null);
        mContext = context;
	}

	public SlideCutListView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
        mContext = context;
	}

	public SlideCutListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        mContext = context;
	}
	
    public boolean isDisableLongClick(){
        Log.d(TAG, "isDisableLongClick:"+"isSlide:"+isSlide+"isFling:"+isFling+"opened:"+opened+"slideDirection:left"+(slideDirection == SlideDirection.LEFT));
        return isSlide || isFling || opened || slideDirection == SlideDirection.LEFT;
    }

	/**
	 * dispatch event, make sure which item click and slide
	 */
	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
        if (isInLongClick || ((ConversationList) ConversationList.getContext()).isActionMode()) {
            Log.d(TAG, "dispatchTouchEvent---isInLongClick---super.dispatchTouchEvent(event)");
		    return super.dispatchTouchEvent(event);
		}
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN: {
			addVelocityTracker(event);
			//animation isn't end
			if (isFling) {
                Log.d(TAG, "dispatchTouchEvent---isFling---super.dispatchTouchEvent(event)");
				return super.dispatchTouchEvent(event);
			}

			downX = (int) event.getX();
			downY = (int) event.getY();
			Log.i(TAG, "downX:"+downX);
			Log.i(TAG, "downY:"+downY);
			//click and slide item position in listview
			slidePosition = pointToPosition(downX, downY);

			//invalid position
			if (slidePosition == AdapterView.INVALID_POSITION) {
				return super.dispatchTouchEvent(event);
			}
			listWidth = getWidth();
			Log.i(TAG, "listWidth:"+listWidth);
			preX = downX;
			deltaX = 0;
			isLimited = false;
			slideDirection = SlideDirection.NONE;
			Log.i(TAG , "ACTION_DOWN - opened=" + opened + ", openedPosition=" + openedPosition);
			Log.i(TAG , "ACTION_DOWN - slidePosition=" + slidePosition);
			if (opened) {
				// item opened status ,get it
				Log.i(TAG , "opened - getFirstVisiblePosition:" + getFirstVisiblePosition());
                Log.i(TAG , "opened - getLastVisiblePosition:" + getLastVisiblePosition());
                //when switch verital to landsapce
                if (openedPosition > getLastVisiblePosition()) {
                    Log.i(TAG , "opened - openedPosition > getLastVisiblePosition()--super.dispatchTouchEvent(event);");
                    return super.dispatchTouchEvent(event);
                }
				//itemView = getChildAt(openedPosition - getFirstVisiblePosition());
                itemView = (ConversationListItem)getChildAt(openedPosition - getFirstVisiblePosition());
				Log.i(TAG , "opened - itemView" + itemView);
			} else {
				// item click status ,get it
				Log.i(TAG , "not opened - " + getFirstVisiblePosition());
				//itemView = getChildAt(slidePosition - getFirstVisiblePosition());
                itemView = (ConversationListItem)getChildAt(slidePosition - getFirstVisiblePosition());
				Log.i(TAG , "not opened - itemView" + itemView);
			}
            if (itemView == null) {
                Log.i(TAG , "dispatchTouchEvent:itemView == null");
                return super.dispatchTouchEvent(event);
            }

            mConversation = itemView.getConversation();
			itemMainView = itemView.findViewById(R.id.main);
			rightView = itemView.findViewById(R.id.item_right);
			// register your item and click listener
			rightItemWidth = 0;
			if (rightView instanceof ViewGroup) {
				int count = ((ViewGroup) rightView).getChildCount();
				for (int i= 0; i < count; i++) {
					View child = ((ViewGroup) rightView).getChildAt(i);
					Log.i(TAG , "child.getWidth() - " + child.getWidth());
					rightItemWidth += child.getWidth();
					rightItems.add(child);
					child.setOnClickListener(rightItemsOnClickListener);
				}
			}
			break;
		}
		case MotionEvent.ACTION_MOVE: {
			
			if (Math.abs(getScrollVelocity()) > SNAP_VELOCITY
					|| (Math.abs(event.getX() - downX) > mTouchSlop && Math
							.abs(event.getY() - downY) < mTouchSlop)) {
				isSlide = true;
                SlideCutListView.this.setLongClickable(false);
                Log.i(TAG , "dispatchTouchEvent--MotionEvent.ACTION_MOVE:--this.setLongClickable(false);");
                //itemMainView.setBackgroundColor(Color.WHITE);
                //android.util.Log.e("chenshu","1---itemMainView.setBackgroundColor(Color.WHITE);");
			}
			break;
		}
		case MotionEvent.ACTION_UP:
			recycleVelocityTracker();
			break;
		}
        /*if (isSlide) {
            Log.e(TAG , "isSlide--true -- return super.dispatchTouchEvent(event);");
            return super.dispatchTouchEvent(event);
        } else {
            Log.e(TAG , "isSlide--false -- false");
            return false;
        }*/
		return super.dispatchTouchEvent(event);
	}

	/**
	 * process the List view slide logic
	 */
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		int action1 = ev.getAction();
        if (action1==MotionEvent.ACTION_DOWN){
            Log.i(TAG, "onTouchEvent---ACTION_DOWN");
        }
		if(isFling) {
            Log.d(TAG, "onTouchEvent--isFling--true");
			return true;
		}
        if (isInLongClick || ((ConversationList) ConversationList.getContext()).isActionMode()) {
            Log.d(TAG, "onTouchEvent--isInLongClick--isActionMode");
            return super.onTouchEvent(ev);
        }
		if (opened) {
			int clickX = listWidth -  rightItemWidth;
			Log.d(TAG, "listWidth = " + listWidth + ", rightItemWidth = " + rightItemWidth);
			Log.d(TAG, "downX = " + downX + ", clickX = " + clickX);
			if (slidePosition != openedPosition || 
					(slidePosition == openedPosition && downX <= clickX)) {
				final int action = ev.getAction();
				switch (action) {
				case MotionEvent.ACTION_UP:
					Log.d(TAG, "ACTION_UP");
				case MotionEvent.ACTION_CANCEL:
					Log.d(TAG, "ACTION_CANCEL");
					slideDirection = SlideDirection.RIGHT;
					Log.d(TAG, "opened - startAnimation");
					startAnimation(itemMainView, 0, animationTime);
					break;
				}
                Log.d(TAG, "onTouchEvent--opened--true");
				return true;
			} else {
                Log.d(TAG, "onTouchEvent--opened--false");
				return false;
			}
		}
		
		
		if (isSlide && slidePosition != AdapterView.INVALID_POSITION) {
			addVelocityTracker(ev);
			final int action = ev.getAction();
			int x = (int) ev.getX();
			Log.i(TAG , "isSlide && slidePosition != AdapterView.INVALID_POSITION):x = "+x+" ,preX = "+preX);
			switch (action) {
			case MotionEvent.ACTION_MOVE:
				if (rightView.getVisibility() != View.VISIBLE) {
					rightView.setVisibility(View.VISIBLE);
				}
				if (deltaX == 0) {
					//none slide
					if (x < preX) {
						startX = preX;
						deltaX = x - startX;
						Log.d(TAG, "GO GO Slide!");
						Log.d(TAG, "GO GO Slide!---deltaX = "+deltaX);
					} else {
						deltaX = 0;
						Log.i(TAG , "onTouchEvent--not open--ACTION_MOVE--x > preX");
					}
				} else {
					//already slide
					deltaX = x - startX;
					Log.d(TAG, "x :"+x);
					Log.d(TAG, "startX :"+startX);
					Log.d(TAG, "deltaX :"+deltaX);
					Log.d(TAG, "isLimited :"+isLimited);
					Log.d(TAG, "limitX :"+limitX);
					if (x > preX) {
						slideDirection = SlideDirection.RIGHT;
						Log.d(TAG, "slideDirection = SlideDirection.RIGHT");
						if (deltaX > 0) {
							deltaX = 0;	
						} else if (isLimited) {
							//startX = startX - limitX + x;
							//startX = startX - limitX + x;
							//deltaX = x - startX;
							if (deltaX > -rightItemWidth) {
								isLimited = false;
								limitX = 0;
								Log.d(TAG, "limitX :"+limitX);
								Log.d(TAG, "isLimited :"+isLimited);
							}
						}
					} else {
						slideDirection = SlideDirection.LEFT;
						Log.d(TAG, "slideDirection = SlideDirection.LEFT");
						if (deltaX <= -rightItemWidth) {
							//slide to left and reach the max with
							deltaX = -rightItemWidth;
							Log.d(TAG, "deltaX :"+deltaX);
							if (!isLimited) {
								isLimited = true;
								limitX = x;
								Log.d(TAG, "limitX :"+limitX);
								Log.d(TAG, "isLimited :"+isLimited);
							}
						}
					}
				}
				//last modify status
				if (deltaX <= -rightItemWidth) {
					deltaX = -rightItemWidth;
				}
                if (itemMainView==null) {
                    Log.d(TAG, "itemMainView ==null--123");
                    return super.onTouchEvent(ev);
                }
                itemMainView.setBackgroundColor(Color.WHITE);
                android.util.Log.e("chenshu","2--itemMainView.setBackgroundColor(Color.WHITE);");
				itemMainView.setTranslationX(deltaX);
				preX = x;
                if (slideDirection == SlideDirection.LEFT) {
                    SlideCutListView.this.setLongClickable(false);
                    Log.i(TAG , "slideDirection == SlideDirection.LEFT--2--this.setLongClickable(false);");
                }
				break;
			case MotionEvent.ACTION_UP:
				if (opened) {
					break;
				}
				animationTime = 500;
				Log.i(TAG , "x = " + x + ", preX = " + preX);
				if (deltaX < 0) {
					//slide some distance
					if (deltaX != -rightItemWidth) {
						//slide not reach limit with
						if (slideDirection == SlideDirection.RIGHT) {
							startAnimation(itemMainView, 0, animationTime);
						} else {
							startAnimation(itemMainView, -rightItemWidth, animationTime);
						}
					} else {
						//reach max limit
						Log.i(TAG , "ACTION_UP - opened = " + opened);
						opened = true;
						Log.i(TAG , "openedPosition = " + openedPosition);
						openedPosition = slidePosition;
					}
				}
				
				recycleVelocityTracker();
				//reset status
				isSlide = false;
				break;
            case MotionEvent.ACTION_DOWN:
                Log.i(TAG, "onTouchEvent---ACTION_DOWN");
                break;
			}
            if (slideDirection == SlideDirection.LEFT || opened || isSlide || isFling) {
                SlideCutListView.this.setLongClickable(false);
                Log.i(TAG , "3--this.setLongClickable(false);");
            } else {
                Log.i(TAG , "4--this.setLongClickable(true);");
                SlideCutListView.this.setLongClickable(true);
            }
            Log.d(TAG, "onTouchEvent--isSlide && slidePosition != AdapterView.INVALID_POSITION--return true");
			return true; //when slide disable list view scorll up and down
		}

		//use list view onTouchEvent
        Log.d(TAG, "onTouchEvent--111--isSlide && slidePosition != AdapterView.INVALID_POSITION-----super.onTouchEvent(ev);");
		return super.onTouchEvent(ev);
        //return true;
	}

	private void startAnimation(View view, float translateX, long duration) {
		isFling = true;
		if (rightView.getVisibility() != View.VISIBLE) {			
			rightView.setVisibility(View.VISIBLE);
		}
		
		view.animate()
			.translationX(translateX)
			.setDuration(duration)
			.setInterpolator(interpolator)
			.setListener(animatorlistener);
	}
	
	TimeInterpolator interpolator = 
			//new BounceInterpolator();
			new DecelerateInterpolator();
	
	AnimatorListener animatorlistener = new AnimatorListener() {
		
		@Override
		public void onAnimationStart(Animator animation) {
			itemMainView.setBackgroundColor(Color.WHITE);
            android.util.Log.e("chenshu","3---itemMainView.setBackgroundColor(Color.WHITE);");
		}
		
		@Override
		public void onAnimationRepeat(Animator animation) {
			
		}
		
		@Override
		public void onAnimationEnd(Animator animation) {
			isFling = false;
            ConversationListAdapter cla = (ConversationListAdapter)getAdapter();
			if (slideDirection == SlideDirection.LEFT) {
				Log.d(TAG, "onAnimationEnd-LEFT: opened = true");
				opened = true;
				openedPosition = slidePosition;
				Log.w(TAG , "openedPosition = " + openedPosition);
				Log.w(TAG , "rightItemWidth = " + rightItemWidth);
				itemMainView.setTranslationX(-rightItemWidth);
                itemMainView.setBackgroundColor(Color.WHITE);
                //itemMainView.setBackgroundColor(Color.TRANSPARENT);
                /*cla.clearstate();
                if (cla != null) {
                    cla.notifyDataSetChanged();
                }*/
                SlideCutListView.this.setLongClickable(false);
                Log.i(TAG , "5--this.setLongClickable(false);");
                android.util.Log.e("chenshu","1---itemMainView.setBackgroundColor(Color.TRANSPARENT);");
			} else {
				Log.d(TAG, "onAnimationEnd-RIGHT: opened = false");
				opened = false;
				isSlide = false;
                Log.i(TAG , "6--this.setLongClickable(true);");
                SlideCutListView.this.setLongClickable(true);
				itemMainView.setTranslationX(0);
                //itemMainView.setBackgroundColor(Color.WHITE);
                itemMainView.setBackgroundColor(Color.TRANSPARENT);
                if (cla != null) {
                    cla.clearstate();
                    cla.notifyDataSetChanged();
                }
                if (rightView!= null && rightView instanceof ViewGroup) {
				    int count = ((ViewGroup) rightView).getChildCount();
				    for (int i= 0; i < count; i++) {
					    View child = ((ViewGroup) rightView).getChildAt(i);
					    rightItems.remove(child);
					    child.setOnClickListener(null);
                        Log.i(TAG , "1--child.setOnClickListener(null);");
				    }
			    }
                rightItemWidth =0;
                android.util.Log.e("chenshu","2---itemMainView.setBackgroundColor(Color.TRANSPARENT);");
				rightView.setVisibility(View.INVISIBLE);
				itemView = null;
				itemMainView = null;
				openedPosition = -1;
                slidePosition = -1;
                slideDirection = SlideDirection.NONE;
                isInLongClick = false;
                mConversation = null;
			}
		}

		@Override
		public void onAnimationCancel(Animator animation) {
			isFling = false;
			itemMainView.setTranslationX(0);
            itemMainView.setBackgroundColor(Color.TRANSPARENT);
            android.util.Log.e("chenshu","3---itemMainView.setBackgroundColor(Color.TRANSPARENT);");
            if (rightView!= null && rightView instanceof ViewGroup) {
				    int count = ((ViewGroup) rightView).getChildCount();
				    for (int i= 0; i < count; i++) {
					    View child = ((ViewGroup) rightView).getChildAt(i);
					    rightItems.remove(child);
					    child.setOnClickListener(null);
                        Log.i(TAG , "1--child.setOnClickListener(null);");
				    }
			    }
            rightItemWidth =0;
            mConversation = null;
            rightView.setVisibility(View.INVISIBLE);
		}
	}; 
		
	/**
	 * addVelocityTracker
	 * 
	 * @param event
	 */
	private void addVelocityTracker(MotionEvent event) {
		if (velocityTracker == null) {
			velocityTracker = VelocityTracker.obtain();
		} else {
			velocityTracker.clear();
		}

		velocityTracker.addMovement(event);
	}

	/**
	 * recycleVelocityTracker
	 */
	private void recycleVelocityTracker() {
		if (velocityTracker != null) {
			velocityTracker.recycle();
			velocityTracker = null;
		}
	}

	/**
	 * getScrollVelocity
	 * 
	 * @return
	 */
	private int getScrollVelocity() {
		velocityTracker.computeCurrentVelocity(1000);
		int velocity = (int) velocityTracker.getXVelocity();
		return velocity;
	}
	
	
	View.OnClickListener rightItemsOnClickListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			int id = v.getId();
            //Cursor cursor  = (Cursor)SlideCutListView.this.getItemAtPosition(openedPosition);
            /*if (cursor==null) {
               Log.d(TAG, "cursor=null");
               return;
            }*/
			switch (id) {
			case R.id.right_1:
				//Toast.makeText(getContext(), "Postion:" + openedPosition +"-1", Toast.LENGTH_SHORT).show();
				Log.d(TAG, "Postion:" + openedPosition +"right-1");
                //String recipientIds = cursor.getString(3);
                //ContactList recipients = ContactList.getByIds(recipientIds, false);
                ContactList recipients = mConversation.getRecipients();
                if (recipients == null) {
                    Log.d(TAG, "recipients == null");
                    return ;
                }
                String number = recipients.size() != 0 ? recipients.get(0).getNumber() : "";
                Log.d(TAG, "number:" + number);
                boolean isWellFormedSmsAddress = MessageUtils.isWellFormedSmsAddress(number);
                Log.d(TAG, "isWellFormedSmsAddress:" + isWellFormedSmsAddress);
                if (TextUtils.isEmpty(number) || !isWellFormedSmsAddress) {
                    Toast.makeText(mContext, R.string.invalid_destination, Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + number));
                Activity activity = ConversationList.getContext();
                if (activity.getWindow() != null && activity.getWindow().getCurrentFocus() != null) {
                    InputMethodManager mInputMethodManager = (InputMethodManager)mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                    mInputMethodManager.hideSoftInputFromWindow(activity.getWindow().getCurrentFocus().getWindowToken(), 0);
                }
                dialIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                try{
                    mContext.startActivity(dialIntent);
                }catch(Exception e) {
		            Log.e(TAG, e.toString()+"start call from sms failed", e);
	            }
				break;
			case R.id.right_2:
				//Toast.makeText(getContext(), "Postion:" + openedPosition +"-2", Toast.LENGTH_SHORT).show();
				Log.d(TAG, "Postion:" + openedPosition +"right_2");
                //Conversation conv = Conversation.from((ConversationList)ConversationList.getContext(), cursor);
                Conversation conv = mConversation;
                if (conv == null) {
                    Log.d(TAG, "conv==null");                   
                    break;
                }
                long threadId = conv.getThreadId();
                Log.d(TAG, "threadId:"+threadId); 
                ThreadListQueryHandler mQueryHandler= ((ConversationList)ConversationList.getContext()).new ThreadListQueryHandler(mContext.getContentResolver());
                ConversationList.confirmDeleteThread(threadId, mQueryHandler);                
				break;
			//case R.id.right_3:
				//Toast.makeText(getContext(), "Postion:" + openedPosition +"-3", Toast.LENGTH_SHORT).show();
				//Log.d(TAG, "Postion:" + openedPosition +"-3");
				//break;
			default:
				break;
			}
		}
	};

    public void removeRightItemOnClickListener(int childLocation, View.OnClickListener listener) {
		rightListeners.remove(childLocation);
		if (rightItems.size() > childLocation) {
			rightItems.get(childLocation).setOnClickListener(null);
		}
	}
	
	public int getOpenedPosition() {
		return openedPosition;
	}

    public void resetSildeListViewStatus(){
        isFling = false;
        opened = false;
		isSlide = false;
        Log.i(TAG , "right_2--reset--delete;");
        Log.i(TAG , "7--this.setLongClickable(true);");
        SlideCutListView.this.setLongClickable(true);
        if (itemMainView!=null) {
		    itemMainView.setTranslationX(0);
        }
        if (rightView!= null && rightView instanceof ViewGroup) {
	        int count = ((ViewGroup) rightView).getChildCount();
	        for (int i= 0; i < count; i++) {
		        View child = ((ViewGroup) rightView).getChildAt(i);
		        rightItems.remove(child);
		        child.setOnClickListener(null);
                Log.i(TAG , "1--child.setOnClickListener(null);");
	        }
            rightView.setVisibility(View.INVISIBLE);
        }
        ConversationListAdapter cla = (ConversationListAdapter)getAdapter();
        if (cla != null) {
            cla.clearstate();
            cla.notifyDataSetChanged();
        }
		itemView = null;
		itemMainView = null;
		openedPosition = -1;
        slidePosition = -1;
        slideDirection = SlideDirection.NONE;
        isInLongClick= false;
        rightItemWidth =0;
        mConversation = null;
    }
}
