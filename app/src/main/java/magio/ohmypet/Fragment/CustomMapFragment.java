package magio.ohmypet.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.google.android.gms.maps.SupportMapFragment;

/**
 * Created by hercules on 2016-08-28.
 */
public class CustomMapFragment extends SupportMapFragment {

    private View mOriginalView;
    private MapWrapperLayout mMapWrapperLayout;

    private OnDragListener mOnDragListener;
    private OnTouchListener mOnTouchListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mOriginalView = super.onCreateView(inflater, container, savedInstanceState);

        mMapWrapperLayout = new MapWrapperLayout(getActivity());
        mMapWrapperLayout.addView(mOriginalView);

        return mMapWrapperLayout;
    }

    @Override
    public View getView() {
        return mOriginalView;
    }

    public void setOnDragListener(MapWrapperLayout.OnDragListener onDragListener) {
        mMapWrapperLayout.setOnDragListener(onDragListener);
    }

    public void setOnTouchListener(OnTouchListener listener) {
        mOnTouchListener = listener;
    }

    public interface OnTouchListener {
        public abstract void onTouch();
    }
    public interface OnDragListener {
        public abstract void onDrag();
    }

    public class MapWrapperLayout extends FrameLayout {
       public MapWrapperLayout(Context context) {
            super(context);
        }

        @Override
        public boolean dispatchTouchEvent(MotionEvent ev) {
//        if (mOnDragListener != null) {ss
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mOnTouchListener.onTouch();
                    break;
                case MotionEvent.ACTION_UP:
                    mOnTouchListener.onTouch();
                    break;
            }
            return super.dispatchTouchEvent(ev);
        }

        public void setOnDragListener(OnDragListener mOnDragListener) {
            mOnDragListener = mOnDragListener;
        }
    }
}