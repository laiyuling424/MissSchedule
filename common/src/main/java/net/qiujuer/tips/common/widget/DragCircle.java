package net.qiujuer.tips.common.widget;

import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Typeface;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Property;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;

import net.qiujuer.tips.common.R;


public class DragCircle extends View {
    private TextPaint mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);//抗齿距
    //private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mSubPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private Paint linePaint=new Paint();
    private Paint circlePaint=new Paint();

    private float mSubRadius;
    private float mRingRadius;
    private PointF mCenterPoint = new PointF();
    private PointF mPointClick = new PointF();

    private static int mDefaultBGColor = 0;

    private CirclePoint[] mSubCirclePoints = null;

    private OnSelectListener mOnSelectListener;

    public DragCircle(Context context) {
        this(context, null);
    }

    public DragCircle(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragCircle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }

    private void init(AttributeSet attrs, int defStyleAttr) {
        final Resources resources = getResources();
        final float density = getContext().getResources().getDisplayMetrics().density;
        if (mDefaultBGColor == 0)
            mDefaultBGColor = getResources().getColor(R.color.black_alpha_16);

        //mPaint.setStyle(Paint.Style.STROKE);
        //mPaint.setAntiAlias(true);
        //mPaint.setDither(true);
        //mPaint.setStrokeWidth(4);
        //mPaint.setColor(resources.getColor(R.color.black_alpha_128));
        //mPaint.setShadowLayer(10, 0, 0, resources.getColor(R.color.black_alpha_192));
        //ViewCompat.setLayerType(this, ViewCompat.LAYER_TYPE_SOFTWARE, mPaint);
        linePaint.setStyle(Paint.Style.FILL);
        linePaint.setAntiAlias(true);
        linePaint.setDither(true);//防抖动
        linePaint.setTextSize(200);
        linePaint.setStrokeWidth(10);
        linePaint.setColor(resources.getColor(R.color.orange_300));

        circlePaint.setStyle(Paint.Style.FILL);
        circlePaint.setAntiAlias(true);
        circlePaint.setDither(true);//防抖动


        mSubPaint.setStyle(Paint.Style.FILL);
        mSubPaint.setAntiAlias(true);
        mSubPaint.setDither(true);//防抖动

        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(13 * density);
        mTextPaint.setColor(resources.getColor(R.color.white_alpha_192));

        if (!isInEditMode())
            mTextPaint.setTypeface(Typeface.createFromAsset(resources.getAssets(), "fonts/FZLanTingHeiS-L-GB-Regular.TTF"));//字体

        //getParent().requestDisallowInterceptTouchEvent(true);
        setClickable(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        intCircle();
    }


    private void intCircle() {
        int width = getWidth();
        int height = getHeight();
        //Log.d("lyl","width=="+width+"-------height=="+height);

        int top = getPaddingTop();
        int left = getPaddingLeft();
        int right = getPaddingRight();
        int bottom = getPaddingBottom();

        int contentW = width - left - right;
        int contentH = height - top - bottom;

        int x = (contentW >> 1) + left;
        int y = (contentH >> 1) + top;

        mCenterPoint.set(x, y);
        mRingRadius = Math.min(contentW, contentH) / 3.8f;
        mSubRadius = mRingRadius * 0.48f;

        if (mSubCirclePoints != null) {
            for (CirclePoint circlePoint : mSubCirclePoints) {
                circlePoint.set(mSubRadius);
                circlePoint.init(mRingRadius, mCenterPoint, mTextPaint);
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //canvas.drawCircle(mCenterPoint.x, mCenterPoint.y, 0, mPaint);

        if (mSubCirclePoints == null)
            return;

        //Log.d("lyl","mSubCirclePoints.size=="+mSubCirclePoints.length);
        //Log.d("lyl","mSubCirclePoints.x=="+mSubCirclePoints[0]);
        for(int i=0;i<mSubCirclePoints.length;i++){
            mSubPaint.setColor(mSubCirclePoints[i].getColor());
            canvas.drawCircle(mSubCirclePoints[i].x, mSubCirclePoints[i].y, mSubCirclePoints[i].r, mSubPaint);
            mSubCirclePoints[i].drawText(canvas);
            if(i==0){
                circlePaint.setColor(getResources().getColor(R.color.orange_300));
                canvas.drawCircle(mSubCirclePoints[i].x-mSubCirclePoints[i].r*1.5f, mSubCirclePoints[i].y, mSubCirclePoints[i].r/3, circlePaint);
            }else if (i==1){
                circlePaint.setColor(getResources().getColor(R.color.pink_600));
                canvas.drawCircle(mSubCirclePoints[i].x+mSubCirclePoints[i].r*1.5f, mSubCirclePoints[i].y, mSubCirclePoints[i].r/4, circlePaint);
            }else if (i==2){
                circlePaint.setColor(getResources().getColor(R.color.teal_500));
                canvas.drawCircle(mSubCirclePoints[i].x-mSubCirclePoints[i].r-30, mSubCirclePoints[i].y+mSubCirclePoints[i].r-40, mSubCirclePoints[i].r/5, circlePaint);
            }else if (i==3){
                circlePaint.setColor(getResources().getColor(R.color.amber_300));
                canvas.drawCircle(getWidth()/2, mSubCirclePoints[i].y+mSubCirclePoints[i].r*1.5f, mSubCirclePoints[i].r/4, circlePaint);
            }else{
                circlePaint.setColor(getResources().getColor(R.color.blue_400));
                canvas.drawCircle(mSubCirclePoints[i].x+mSubCirclePoints[i].r*1.5f, mSubCirclePoints[i].y-mSubCirclePoints[i].r*1.5f, mSubCirclePoints[i].r/3, circlePaint);
            }
        }
/*        for (CirclePoint circlePoint : mSubCirclePoints) {
            Log.d("lyl","x=="+circlePoint.x+"-----y=="+circlePoint.y+"-----r=="+circlePoint.r);
            for(int i=1;i<1;i++){
                if(i==0){
                    circlePaint.setColor(getResources().getColor(R.color.orange_300));
                    canvas.drawCircle(circlePoint.x-circlePoint.r*1.5f, circlePoint.y, circlePoint.r/3, circlePaint);
                }else if (i==1){
                    circlePaint.setColor(getResources().getColor(R.color.pink_600));
                    canvas.drawCircle(circlePoint.x+circlePoint.r*1.5f, circlePoint.y, circlePoint.r/4, circlePaint);
                }else if (i==2){
                    circlePaint.setColor(getResources().getColor(R.color.teal_500));
                    canvas.drawCircle(circlePoint.x-circlePoint.r-30, circlePoint.y+circlePoint.r+40, circlePoint.r/5, circlePaint);
                }else if (i==3){
                    circlePaint.setColor(getResources().getColor(R.color.amber_300));
                    canvas.drawCircle(getWidth()/2, circlePoint.y+circlePoint.r*1.5f, circlePoint.r/4, circlePaint);
                }else{
                    circlePaint.setColor(getResources().getColor(R.color.blue_400));
                    canvas.drawCircle(circlePoint.x-circlePoint.r*1.5f, circlePoint.y-circlePoint.r*1.5f, circlePoint.r/3, circlePaint);
                }
            }
            mSubPaint.setColor(circlePoint.getColor());
            canvas.drawCircle(circlePoint.x, circlePoint.y, circlePoint.r, mSubPaint);
            circlePoint.drawText(canvas);

        }*/
        int w=getWidth();
        float n=w/15;
        //Log.d("lyl","n=="+n);
        int x=0;
        //Log.d("lyl","w=="+w);
        //Log.d("lyl","mRingRadius=="+mRingRadius);
        for(int i=0;i<n;i++){
            float y1=mRingRadius/2;
            float y2=(float) (Math.random()*y1);
            //Log.d("lyl","y2=="+y2);
            canvas.drawLine(x,0,x,y2,linePaint);
            x=x+15;
        }


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            mPointClick.set(event.getX(), event.getY());
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean performClick() {
        clickCircle();
        return super.performClick();
    }

    private void clickCircle() {
        if (mSubCirclePoints == null)
            return;
        for (int i = 0; i < mSubCirclePoints.length; i++) {
            if (mSubCirclePoints[i].include(mPointClick)) {
                if (mOnSelectListener != null)
                    mOnSelectListener.onSelect(i);
                break;
            }
        }
    }

    public void setTag(CircleTag[] tags) {
        final int len = tags.length;
        final int angle = 360 / len;

        mSubCirclePoints = new CirclePoint[len];

        for (int i = 0; i < len; i++) {
            CirclePoint circlePoint = new CirclePoint(tags[i]);
            circlePoint.angle = angle * i;
            circlePoint.set(mSubRadius);
            circlePoint.init(mRingRadius, mCenterPoint, mTextPaint);
            mSubCirclePoints[i] = circlePoint;
        }
        invalidate();
    }

    public void setOnSelectListener(OnSelectListener l) {
        this.mOnSelectListener = l;
    }

    public interface OnSelectListener {
        void onSelect(int index);
    }

    public static class CircleTag {
        public int color;
        public String str;

        public CircleTag(int color, String str) {
            this.color = color;
            this.str = str;
        }
    }

    private static class CirclePoint extends PointF {
        public static final Creator<PointF> CREATOR = null;
        private CircleTag tag;
        StaticLayout layout;
        int angle = 0;
        float r = 0;

        public CirclePoint(CircleTag tag) {
            this.tag = tag;
        }

        public void set(float radius) {
            this.r = radius;
        }

        public void init(float ringRadius, PointF point, TextPaint paint) {
            double radian = angle * Math.PI / 180;

            float x = (float) (Math.sin(radian) * ringRadius);
            float y = (float) (Math.cos(radian) * ringRadius);

            x = point.x - x;
            y = point.y - y;

            set(x, y);

            initText(paint);
        }

        public int getColor() {
            if (tag == null)
                return mDefaultBGColor;
            else
                return tag.color;
        }


        private void initText(TextPaint paint) {
            if (tag == null || tag.str == null)
                return;
            String str = tag.str;
            int len = str.length();
            if (len > 15)
                str = str.substring(0, 15);

            layout = new StaticLayout(str, paint, (int) (r * 1.5),
                    Layout.Alignment.ALIGN_CENTER, 1, 0, false);
        }


        public boolean include(PointF point) {
            final float dx = point.x - this.x;
            final float dy = point.y - this.y;
            return ((dx * dx) + (dy * dy)) < (r * r);
        }

        public void drawText(Canvas canvas) {
            if (layout == null)
                return;
            canvas.save();
            canvas.translate(x - r * 0.75f, y - layout.getHeight() / 2);
            layout.draw(canvas);
            canvas.restore();
        }
    }

    private static final int ANIMATION_DURATION = 20000;
    private AnimatorProperty mProperty = new AnimatorProperty();
    private ObjectAnimator mAnimator;

    private void setAnimatorValue(AnimatorProperty property) {
        mProperty = property;
        intCircle();
        invalidate();
    }

    private void animateCheckedState() {
        AnimatorProperty property = new AnimatorProperty();
        property.mAngle = -360;
        property.mRadius = 1;

        if (mAnimator == null) {
            mAnimator = ObjectAnimator.ofObject(this, ANIM_VALUE, new AnimatorEvaluator(mProperty), new AnimatorProperty(), property);
            mAnimator.setInterpolator(new LinearInterpolator());
            mAnimator.setDuration(ANIMATION_DURATION);
            /*
            mAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    animateCheckedState();
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            */
            mAnimator.setRepeatCount(-1);
            mAnimator.setRepeatMode(Animation.RESTART);
        } else {
            mAnimator.setObjectValues(new AnimatorProperty(), property);
        }
        mAnimator.start();
    }

    /**
     * =============================================================================================
     * The custom properties
     * =============================================================================================
     */

    private final static class AnimatorProperty {
        private int mAngle;
        private float mRadius;
    }

    private final static class AnimatorEvaluator implements TypeEvaluator<AnimatorProperty> {
        private final AnimatorProperty mProperty;

        public AnimatorEvaluator(AnimatorProperty property) {
            mProperty = property;
        }

        @Override
        public AnimatorProperty evaluate(float fraction, AnimatorProperty startValue, AnimatorProperty endValue) {
            // Values
            mProperty.mAngle = (int) (startValue.mAngle + (endValue.mAngle - startValue.mAngle) * fraction);
            mProperty.mRadius = startValue.mRadius + (endValue.mRadius - startValue.mRadius) * fraction;
            return mProperty;
        }
    }

    private final static Property<DragCircle, AnimatorProperty> ANIM_VALUE = new Property<DragCircle, AnimatorProperty>(AnimatorProperty.class, "animValue") {
        @Override
        public AnimatorProperty get(DragCircle object) {
            return object.mProperty;
        }

        @Override
        public void set(DragCircle object, AnimatorProperty value) {
            object.setAnimatorValue(value);
        }
    };
}
